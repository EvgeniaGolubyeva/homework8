package auction.service;

import auction.entity.Bid;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;

import static auction.utils.StringUtils.currencyFormat;

/**
 * @author Evgenia
 */

@Singleton
public class ConsoleNotificationService implements INotificationService {

    public void sendBidWasPlacedNotification(Bid bid, List<Bid> productBids) {
        printHeader(bid.getUser().getEmail());
        printMessage("Your bid was successfully placed.");
        printProductBids(bid, productBids);
    }

    public void sendOverbidNotification(Bid bid, Bid winningBid) {
        printHeader(bid.getUser().getEmail());

        String newAmount = currencyFormat(winningBid.getAmount());
        String oldAmount = currencyFormat(bid.getAmount());

        printMessage("You were overbidden. New bid amount " + newAmount + " is more than you placed " + oldAmount);
    }

    public void sendWinNotification(Bid bid) {
        printHeader(bid.getUser().getEmail());

        String resPrice = currencyFormat(bid.getProduct().getReservedPrice());
        String amount   = currencyFormat(bid.getAmount());

        printMessage("Congratulations! Your bid " + amount + " is winning, product reserved price " + resPrice);
    }

    public void sendSorryNotification(Bid bid) {
        printHeader(bid.getUser().getEmail());

        String minPrice = currencyFormat(bid.getProduct().getMinimalPrice());
        String amount   = currencyFormat(bid.getAmount());

        printMessage("Sorry! The amount you placed " + amount + " is less than product minimum price " + minPrice);
    }

    private void printHeader(String email) {
        System.out.println();
        System.out.println("*** Message to " + email + ":");
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private void printProductBids(Bid currentBid, List<Bid> productBids) {
        System.out.print("Current bids for " + currentBid.getProduct().getTitle() + " are: ");

        productBids.stream()
                .sorted(Comparator.comparing(Bid::getAmount).reversed())
                .forEach(b -> printBid(b, b.equals(currentBid)));

        System.out.println();
    }

    private void printBid(Bid b, boolean shouldMark) {
        System.out.print(shouldMark ? "*" : "");
        System.out.print(currencyFormat(b.getAmount()) + "(" + b.getUser().getId());
        System.out.print(b.getUser().isGetOverbidNotifications() ? "+)" : "-)");
        System.out.print(shouldMark ? "* " : " ");
    }
}
