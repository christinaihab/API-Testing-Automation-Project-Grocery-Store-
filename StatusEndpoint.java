package api.Endpoints;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class StatusEndpoint {

    public static Response getStatusEndpoint() {;
        Response response= given()
                .when()
                .get(Routes.get_status);
        return response;
    }
}
