package auction.service;

import auction.entity.Bid;

import java.util.List;

/**
 * @author Evgenia
 */
public interface INotificationService {

    void sendBidWasPlacedNotification(Bid bid, List<Bid> productBids);

    void sendOverbidNotification(Bid bid, Bid winningBid);

    void sendWinNotification(Bid bid);

    void sendSorryNotification(Bid bid);

}
