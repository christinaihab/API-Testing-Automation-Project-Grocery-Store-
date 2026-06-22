package api.Endpoints;
import api.payload.CartPayload;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CartEndpoint {

    //1. Get a Cart
    public static Response getACart(String cartId) {

            Response response = given()
                    .pathParams("cartId", cartId)
                    .when()
                    .get(Routes.get_a_cart);
            return response;
        }

        //2. Get Cart Items
    public static Response getCartItems(String cartId){
            Response response = given()
                    .pathParams("cartId", cartId)
                    .when()
                    .get(Routes.get_cart_items);
            return response;
        }


        //3. Create A New Cart
        public static Response createANewCart() {
            Response response = given()
                    .when()
                    .post(Routes.create_new_cart);
            return response;
        }


//        4. Add An Item To Cart
//        Must have a body
        public static Response AddAnItemToCart (String cartId, CartPayload cartPayload){
            Response response = given()
                    .pathParams("cartId", cartId)
                    .contentType("application/json")
                    .body(cartPayload)
                    .when()
                    .post(Routes.add_item_to_cart);
            return response;
        }



        //5. Modify A Cart Item
        public static Response modifyACartItem (String cartId,int itemId,  CartPayload cartPayload){
            Response response = given()
                    .pathParams("cartId", cartId)
                    .pathParams("itemId", itemId)
                    .body(cartPayload)
                    .contentType("application/json")
                    .when()
                    .patch(Routes.modify_cart_item);
            return response;
        }

        //6. Replace A Cart Item
        public static Response replaceACartItem (String cartId,int itemId){
            Response response = given()
                    .pathParams("cartId", cartId)
                    .pathParams("itemId", itemId)
                    .contentType("application/json")
                    .when()
                    .put(Routes.replace_cart_item);
            return response;
        }


        //7. Delete A Cart Item
        public static Response deleteACartItem(String cartId,int itemId)
            {
                Response response = given()
                        .pathParams("cartId", cartId)
                        .pathParams("itemId", itemId)
                        .when()
                        .delete(Routes.delete_cart_item);
                return response;
            }
    }
