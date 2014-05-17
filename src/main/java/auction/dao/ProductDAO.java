package auction.dao;

import auction.entity.Product;
import auction.mybatis.ProductMapper;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Objects;

/**
 * @author Evgenia
 */


@Singleton
public class ProductDAO {
    private SqlSessionFactoryHolder sfh;

    public List<Product> getFeaturedProducts() {
        try (SqlSession session = sfh.getSessionFactory().openSession()) {
            return session.getMapper(ProductMapper.class).getFeaturedProducts();
        }
    }

    public List<Product> getSearchProducts(MultivaluedMap<String, String> params) {
        try (SqlSession session = sfh.getSessionFactory().openSession()) {
            return session.getMapper(ProductMapper.class).getSearchProducts(prepareCriteria(params));
        }
    }

    public Product getById(int id) {
        try (SqlSession session = sfh.getSessionFactory().openSession()) {
            return session.getMapper(ProductMapper.class).getById(id);
        }
    }

    private Map<String, Object> prepareCriteria(MultivaluedMap<String, String> params) {
        Map<String, Object> res = new HashMap<>();

        for (String k : params.keySet()) {

            if ("date".equals(k)) {
                //parse Date from iso formatted string
                res.put(k, DatatypeConverter.parseDateTime(params.getFirst(k)).getTime());
            } else {
                res.put(k, params.getFirst(k));
            }
        }

        return  res;
    }

    @Inject
    public void setSfh(SqlSessionFactoryHolder sfh) {
        this.sfh = sfh;
    }
}
