package api.Endpoints;

import api.payload.OrdersPayload;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersEndpoint {

    public static Response getAllOrders(String authToken) {
        Response response = given()
                .header("Authorization", authToken)
                .when()
                .get(Routes.get_all_orders);
        return response;

    }

    public static Response getAnOrder(String orderId, String authToken) {
        Response response = given()
                .header("Authorization", authToken)
                .pathParams("orderId", orderId)
                .when()
                .get(Routes.get_an_order);
        return response;
    }

    public static Response createAnOrder(String authToken, OrdersPayload orderPayload) {
        Response response = given()
                .header("Authorization", authToken)
                .body(orderPayload)
                .contentType("application/json")
                .when()
                .post(Routes.create_new_order);
        return response;
    }

    public static Response updateAnOrder(String orderId, String authToken, OrdersPayload orderPayload) {
        Response response = given()
                .header("Authorization", authToken)
                .pathParams("orderId", orderId)
                .body(orderPayload)
                .contentType("application/json")
                .when()
                .patch(Routes.update_an_order);
        return response;
    }

    public static Response deleteAnOrder(String orderId, String authToken) {
        Response response = given()
                .header("Authorization", authToken)
                .pathParams("orderId", orderId)
                .contentType("application/json")
                .when()
                .delete(Routes.delete_an_order);
        return response;

    }
}

