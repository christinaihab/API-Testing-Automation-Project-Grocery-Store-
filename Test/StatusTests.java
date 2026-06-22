package api.Test;

import api.Endpoints.StatusEndpoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class StatusTests {

    @Test
    public void testStatus() {
        Response response = StatusEndpoint.getStatusEndpoint();
        response.then().log().all()
                .assertThat()
                .contentType(ContentType.JSON)
                .body("status", org.hamcrest.Matchers.equalTo("UP"));
    }
}
