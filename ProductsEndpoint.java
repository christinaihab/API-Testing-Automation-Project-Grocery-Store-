package api.Endpoints;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProductsEndpoint {

    public static Response getAllProducts() {
        Response response= given()
                .when()
                .get(Routes.get_all_products);
        return response;
    }
    public static Response getAProduct(int productId) {
        Response response= given()
                .pathParams("productId", productId)
                .when()
                .get(Routes.get_a_product);
        return response;
    }

}
