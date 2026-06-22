package api.Test;

import api.Endpoints.CartEndpoint;
import api.Endpoints.RegistrationEndpoint;
import api.Utilities.TestDataStore;
import api.payload.RegistrationPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class RegistrationTests {
    RegistrationPayload regPayload;

    public void setUpRegistrationPayload() {
        regPayload = new RegistrationPayload();
        regPayload.setClientName(TestDataStore.clientName);
        regPayload.setClientEmail(TestDataStore.clientEmail);

    }

        @Test
        public void testRegisterNewClient(){
            setUpRegistrationPayload();
            Response response = RegistrationEndpoint.registerNewClient(regPayload);
            response.then().log().all()
                    .assertThat().statusCode(201)
                    .contentType(ContentType.JSON)
                    .body("accessToken", Matchers.notNullValue())
                    .body("accessToken", Matchers.isA(String.class));

            TestDataStore.setAccessToken(response.jsonPath().getString("accessToken"));

            String token = response.jsonPath().getString("accessToken");

        }
}
