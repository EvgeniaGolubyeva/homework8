package auction.mybatis;

import auction.entity.Product;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Evgenia
 */
public interface ProductMapper {
    @Select({ "select * from PRODUCT where rownum <= 3 order by PRODUCT_ID" })
    @ResultMap("ProductResultMap")
    List<Product> getFeaturedProducts();

    @Select({ "select * from PRODUCT where PRODUCT_ID = #{id}" })
    @ResultMap("ProductResultMap")
    Product getById(int id);

    List<Product> getSearchProducts(Map<String, Object> params);
}
