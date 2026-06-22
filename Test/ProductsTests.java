package api.Test;

import api.Endpoints.ProductsEndpoint;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ProductsTests {

    //1. Get All Products Test
    @Test
    public void getAllProductsTest() {
        Response response = ProductsEndpoint.getAllProducts();
        response.then().log().all().assertThat().statusCode(200);
    }

    //2. Get Certain Product Test by ID
    @Test
    public void getCertainProductTest() {
        Response response = ProductsEndpoint.getAProduct(3486);
        response.then().log().all().assertThat().statusCode(200);
    }

    //3. Get product by INVALID ID Test
    @Test
    public void testGetProductByInvalidID() {
        Response response = ProductsEndpoint.getAProduct(9999);
        response.then().log().all().assertThat().statusCode(404);
    }

    /*4. Get product by NO ID Test
    @Test
    public void testGetProductByNoID() {
        Response response = ProductsEndpoint.getAProductNoID();
        response.then().log().all().assertThat().statusCode(405);
    }*/
}
