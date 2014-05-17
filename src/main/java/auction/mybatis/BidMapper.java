package auction.mybatis;

import auction.entity.Bid;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Evgenia
 */
public interface BidMapper {
    @Select({ "select * from BID where PRODUCT_ID = #{productId} order by AMOUNT DESC" })
    @ResultMap("BidResultMap")
    List<Bid> getBidsByProduct(int productId);

    @Insert({"insert into BID (PRODUCT_ID, AMOUNT,DESIRED_QUANTITY, BIDDER_ID, BID_TIME)",
            "values (#{product.id}, #{amount}, 1, #{user.id}, current_timestamp)"})
    int insert(Bid bid);
}
