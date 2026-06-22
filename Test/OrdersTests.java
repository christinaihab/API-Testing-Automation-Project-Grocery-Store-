package api.Test;

import api.Endpoints.OrdersEndpoint;
import api.Utilities.TestDataStore;
import api.payload.OrdersPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static api.Utilities.TestDataStore.setOrderId;
import static api.Utilities.TestDataStore.getCartId;
import static api.Utilities.TestDataStore.getAccessToken;
import static api.Utilities.TestDataStore.getOrderId;
import static org.hamcrest.Matchers.containsString;

public class OrdersTests {

    OrdersPayload orderPayload;
    // REMOVED: String accessToken = TestDataStore.getAccessToken(); // This line is removed to prevent premature initialization.

    // Use @BeforeClass to initialize the payload object
    @BeforeClass
    public void setupPayloads() {
        orderPayload = new OrdersPayload();
    }

    // Helper method now uses the dynamic cartId
    public void setupOrderPayload() {
        // 1. Get the cartId generated in a previous test suite (e.g., CartTests)
        String dynamicCartId = getCartId(); // CORRECTLY CALLS STORED CART ID

        // 2. Set the dynamic cartId in the request payload
        // FIX: Use the dynamicCartId retrieved from TestDataStore, not a hardcoded value
        orderPayload.setCartId(dynamicCartId);

        // Assuming clientName and orderComment are static fields in TestDataStore
        orderPayload.setCustomerName(TestDataStore.clientName);
        orderPayload.setComment(TestDataStore.orderComment);

        // Check for prerequisite data
        if (dynamicCartId == null) {
            System.err.println("CRITICAL ERROR: Cart ID is null. Ensure CartTests ran successfully and saved the ID.");
            Assert.fail("Prerequisite failure: Cart ID is null.");
        }
    }

    // --- PRIORITY 1: CREATE ORDER ---

    // 1. Create New Order test (Priority 1)
    @Test(priority = 1)
    public void createNewOrderTest() {
        setupOrderPayload();

        // FIX: Retrieve the Access Token just before the API call
        String accessToken = getAccessToken();

//        if (accessToken == null) {
//            Assert.fail("Access Token is null. Ensure Login/Registration test ran successfully.");
//        }

        Response response = OrdersEndpoint.createAnOrder(accessToken, orderPayload);
        response.then().log().all().assertThat()
                .statusCode(201)
                .body("created", Matchers.equalTo(true))
                .body("orderId", Matchers.notNullValue());

        // Save the generated Order ID
        String orderId = response.jsonPath().getString("orderId");
        setOrderId(orderId);
        System.out.println("✅ Order ID Saved: " + orderId);
    }

    // --- INVALID TESTS (High Priority to run early, but after creation) ---

    // 1.1 Create New Order with Invalid Authorization test (Priority 2)
    @Test(priority = 2)
    public void createNewOrderInvalidAuthorizationTest() {
        setupOrderPayload();
        Response response = OrdersEndpoint.createAnOrder("invalid_token_123", orderPayload);
        response.then().log().all().assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("error", Matchers.equalTo("Invalid bearer token."));
    }

    // 1.2 Create New Order with Invalid Parameters test (Priority 3)
    @Test(priority = 3)
    public void createNewOrderInvalidParametersTest() {
        // Modify payload to cause a 400 (e.g., empty customerName or invalid cart ID)
        OrdersPayload invalidPayload = new OrdersPayload();
        invalidPayload.setCartId("nonExistentCartId"); // Example invalid parameter
        invalidPayload.setCustomerName("Valid Name");
        String accessToken = TestDataStore.getAccessToken();

        Response response = OrdersEndpoint.createAnOrder(accessToken, invalidPayload);
        response.then().log().all().assertThat()
                .statusCode(400) // Expect 400 for invalid data/parameter issues
                .body("error", containsString("cartId")); // Assert a relevant error message
    }
    // --- PRIORITY 4: UPDATE ORDER ---

    // 4. Update An Order tests (Priority 4)
    @Test(priority = 4)
    public void updateAnOrderTest() {
        setupOrderPayload(); // Initialize payload with dynamic cartId
        orderPayload.setComment("Please deliver between 6-7 PM - UPDATED"); // New comment

        // FIX: Retrieve the Access Token just before the API call
        String accessToken = getAccessToken();

        Response response = OrdersEndpoint.updateAnOrder(getOrderId(), accessToken, orderPayload);
        response.then().log().all()
                .assertThat()
                .statusCode(204);
    }

    // 4.1 Update An Order with invalid authorization tests (Priority 5)
    @Test(priority = 5)
    public void updateAnOrderInvalidAuthorizationTest() {
        setupOrderPayload();
        orderPayload.setComment("Invalid Auth Test");
        Response response = OrdersEndpoint.updateAnOrder(TestDataStore.getOrderId(), "invalid_token_123", orderPayload);
        response.then().log().all()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("error", containsString("Invalid bearer token."));
    }

    // 4.2 Update An Order with invalid Order ID tests (Priority 6)
    @Test(priority = 6)
    public void updateAnOrderInvalidParametersTest() {
        setupOrderPayload();
        orderPayload.setComment("Invalid Order ID Test");
        String accessToken = getAccessToken();

        Response response = OrdersEndpoint.updateAnOrder("invalid_orderId123", accessToken, orderPayload);
        response.then().log().all()
                .assertThat()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("error", containsString("id"));
    }

    // --- PRIORITY 7: GET SINGLE ORDER ---

    // 3. Get A single Order Test (Priority 7)
    @Test(priority = 7)
    public void getAnOrderTest() {
        String accessToken = getAccessToken();

        Response response = OrdersEndpoint.getAnOrder(getOrderId(), accessToken);
        response.then().log().all().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("orderId", Matchers.equalTo(getOrderId()))
                .body("cartId", Matchers.equalTo(getCartId()))
                .body("customerName", Matchers.equalTo("Jaden Smith"))
                .body("comment", Matchers.equalTo("Please deliver between 6-7 PM - UPDATED"));
    }

    // --- PRIORITY 9: GET ALL ORDERS ---

    // 2. Get All Orders Test (Priority 9)
    @Test(priority = 9)
    public void getAllOrdersTest() {
        // CORRECT: getAccessToken() is called here
        Response response = OrdersEndpoint.getAllOrders(getAccessToken());
        response.then().log().all().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    // --- PRIORITY 11: DELETE ORDER ---

    // 5. Delete An Order test (Priority 11)
    @Test(priority = 11)
    public void deleteAnOrderTest() {
        // CORRECT: getAccessToken() is called here
        Response response = OrdersEndpoint.deleteAnOrder(getOrderId(), getAccessToken());
        response.then().log().all().assertThat()
                .statusCode(204);
    }
}