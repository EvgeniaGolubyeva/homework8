package auction.websockets;

import auction.dao.ProductDAO;
import auction.entity.Bid;
import auction.service.BidService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author Evgenia
 */

@ServerEndpoint(
        value = "/server/product/{id}/bidws",
        decoders = { BidDecoder.class })

public class PlaceBidEndpoint {
    private ProductDAO productDAO;
    private BidService bidService;

    @OnMessage
    public void placeBid(@PathParam("id") int productId, Bid bid, Session mySession) {
        bid.setProduct(productDAO.getById(productId));

        //place bid
        JsonObject result = bidService.placeBid(bid);

        //if bid was added than notify all clients,
        //if amount < min or amount > reserved then notify one client (that placed request) only.
        if (BidService.RESULT_STATUS_ADDED.equals(result.getString("status"))) {
            mySession.getOpenSessions().forEach(session -> sendText(session, result.toString()));
        } else {
            sendText(mySession, result.toString());
        }
    }

    private void sendText(Session session, String text) {
        try {
            session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Inject
    public void setBidService(BidService bidService) {
        this.bidService = bidService;
    }

    @Inject
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
