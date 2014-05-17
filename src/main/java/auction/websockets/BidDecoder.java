package auction.websockets;

import auction.entity.Bid;
import auction.entity.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

/**
 * @author Evgenia
 */
public class BidDecoder implements Decoder.Text<Bid> {
    @Override
    public Bid decode(String s) throws DecodeException {
        //no gson.fromJson(String json, Class<T> classOfT) in java ee, parsing manually...
        JsonReader reader = Json.createReader(new StringReader(s));
        JsonObject jsonBid = reader.readObject();

        JsonObject jsonUser = jsonBid.getJsonObject("user");
        User user = new User();
        user.setId(jsonUser.getInt("id"));
        user.setName(jsonUser.getString("name"));
        user.setEmail(jsonUser.getString("email"));
        user.setGetOverbidNotifications(jsonUser.getBoolean("getOverbidNotifications"));

        Bid bid = new Bid();
        bid.setUser(user);
        bid.setAmount(jsonBid.getJsonNumber("amount").bigDecimalValue());

        return bid;
    }

    @Override
    public boolean willDecode(String s) {
        // according to http://docs.oracle.com/javaee/7/tutorial/doc/websocket012.htm#BABDDAAG
        // this method should:
        // Convert JSON data from the message into a name-value map...
        // Check if the message has all the fields for its message type...

        try {
            JsonReader reader = Json.createReader(new StringReader(s));
            JsonObject jsonBid = reader.readObject();

            JsonObject jsonUser = jsonBid.getJsonObject("user");
            jsonUser.getInt("id");
            jsonUser.getString("name");
            jsonUser.getString("email");
            jsonUser.getBoolean("getOverbidNotifications");

            jsonBid.getJsonNumber("amount");

            return true;

        } catch (NullPointerException e) {
            System.out.println("Bid cannot be decoded");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
