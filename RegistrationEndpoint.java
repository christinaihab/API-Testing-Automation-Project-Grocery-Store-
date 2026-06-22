package api.Endpoints;
import api.payload.RegistrationPayload;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class RegistrationEndpoint {
    public static Response registerNewClient(RegistrationPayload regPayload) {
        Response response = given()
                .body(regPayload)
                .contentType("application/json")
                .when()
                .post(Routes.register_new_client);
        return response;
    }
}
