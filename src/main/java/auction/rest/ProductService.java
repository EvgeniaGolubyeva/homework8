package auction.rest;

import auction.dao.BidDAO;
import auction.dao.ProductDAO;
import auction.entity.Bid;
import auction.entity.Product;
import auction.service.BidService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Evgenia
 */

@Path("/product")
@Produces("application/json")
public class ProductService {
    private ProductDAO productDAO;
    private BidDAO bidDAO;

    private BidService bidService;

    @GET
    @Path("/featured")
    public List<Product> getFeaturedProducts() {
        return productDAO.getFeaturedProducts();
    }

    @GET
    @Path("/search")
    public List<Product> getSearchProducts(@Context UriInfo uriInfo) {
        return productDAO.getSearchProducts(uriInfo.getQueryParameters());
    }

    @GET
    @Path("/{id}")
    public Product getProduct(@PathParam("id") int id) {
        return productDAO.getById(id);
    }

    @GET
    @Path("/{productId}/bid")
    public List<Bid> getProductBids(@PathParam("productId") int productId) {
        return bidDAO.getBidsByProduct(productId);
    }

    @POST
    @Consumes("application/json")
    @Path("/{id}/bid")
    public Response placeBid(@PathParam("id") int productId, Bid bid) {
        bid.setProduct(productDAO.getById(productId));
        return Response.ok(bidService.placeBid(bid)).build();
    }

    @Inject
    public void setBidService(BidService bidService) {
        this.bidService = bidService;
    }

    @Inject
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Inject
    public void setBidDAO(BidDAO bidDAO) {
        this.bidDAO = bidDAO;
    }
}
