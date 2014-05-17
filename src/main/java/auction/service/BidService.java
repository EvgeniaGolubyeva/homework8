package auction.service;

import auction.dao.BidDAO;
import auction.entity.Bid;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @author Evgenia
 */

@Singleton
public class BidService {
    public static final String RESULT_STATUS_REJECTED = "rejected";
    public static final String RESULT_STATUS_WIN = "win";
    public static final String RESULT_STATUS_ADDED = "added";

    private BidDAO bidDAO;

    private INotificationService notificationService;

    public JsonObject placeBid(Bid bid) {
        BigDecimal min = bid.getProduct().getMinimalPrice();
        BigDecimal res = bid.getProduct().getReservedPrice();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        if (bid.getAmount().compareTo(min) < 0) {
            notificationService.sendSorryNotification(bid);
            return jsonBuilder.add("status", RESULT_STATUS_REJECTED)
                    .add("reason", "Sorry! Bid was rejected. Amount you placed is less then product minPrice [" + min + "]")
                    .build();
        }

        if (bid.getAmount().compareTo(res) >= 0) {
            notificationService.sendWinNotification(bid);
            return jsonBuilder.add("status", RESULT_STATUS_WIN)
                    .add("reason", "Congratulations! You win! Amount you placed is more then product resPrice [" + res + "]")
                    .build();
        }

        bidDAO.create(bid);

        List<Bid> productBids = bidDAO.getBidsByProduct(bid.getProduct().getId());
        notificationService.sendBidWasPlacedNotification(bid, productBids);
        notifyOverbidUsers(bid, productBids);

        Bid topBid = productBids.stream().max(Comparator.comparing(Bid::getAmount)).get();
        return jsonBuilder.add("status", RESULT_STATUS_ADDED)
                .add("topBidPrice", topBid.getAmount())
                .add("topBidUser", topBid.getUser().getId())
                .add("totalCountOfBids", productBids.size())
                .build();
    }

    @Inject
    public void setBidDAO(BidDAO bidDAO) {
        this.bidDAO = bidDAO;
    }

    @Inject
    public void setNotificationService(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private void notifyOverbidUsers(Bid bid, List<Bid> productBids) {
        productBids.stream()
                .filter(b -> bid.getAmount().compareTo(b.getAmount()) > 0)
                .filter(b -> b.getUser().isGetOverbidNotifications())
                .forEach(b -> notificationService.sendOverbidNotification(b, bid));
    }
}
