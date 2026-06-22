package api.Test;

import api.Endpoints.CartEndpoint;
import api.payload.CartPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static api.Utilities.TestDataStore.*;
import static api.Utilities.TestDataStore.getCartId;
import static org.hamcrest.Matchers.equalTo;

public class CartTests {

    private CartPayload validCartPayload;
    private CartPayload invalidProductCartPayload;
    private CartPayload invalidQuantityCartPayload;
    private int savedItemId;


    @BeforeClass
    public void setUpPayloads() {
        // 1. Payload for successful operations
        validCartPayload = new CartPayload();
        validCartPayload.setProductId(4875);
        validCartPayload.setQuantity(10);

        // 2. Payload for invalid product ID test
        invalidProductCartPayload = new CartPayload();
        invalidProductCartPayload.setProductId(9999);
        invalidProductCartPayload.setQuantity(3);

        // 3. Payload for invalid quantity test
        invalidQuantityCartPayload = new CartPayload();
        invalidQuantityCartPayload.setProductId(9999);
        invalidQuantityCartPayload.setQuantity(3);
    }

    // 1. Create A New Cart Test (Priority 1)
    @Test(priority = 1)
    public void createANewCartTest() {
        Response response = CartEndpoint.createANewCart();
        response.then().log().all().assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("created", Matchers.equalTo(true))
                .body("cartId", Matchers.notNullValue())
                .body("cartId", Matchers.isA(String.class));

        String cartId = response.jsonPath().getString("cartId");
        setCartId(cartId);
        //System.out.println("Cart ID Saved: " + cartId);
    }

    // 2. Add An Item To Cart Test (Priority 2)
    @Test(priority = 2)
    public void addAnItemToCartTest() {
        String savedCartId = getCartId();

        Response response = CartEndpoint.AddAnItemToCart(savedCartId, validCartPayload);
        response.then().log().all()
                .assertThat().statusCode(201)
                .body("created", equalTo(true))
                .body("itemId", Matchers.notNullValue())
                .body("itemId", Matchers.isA(Integer.class));

        savedItemId = response.jsonPath().getInt("itemId");
        setItemId(savedItemId);
        //System.out.println("Item ID Saved: " + savedItemId);
    }

    // 2.1. Add An Item To Cart with Invalid Product ID Test (Priority 3)
    @Test(priority = 3)
    public void addAnItemToCartWithInvalidProductIdTest() {

        String savedCartId = getCartId();
        Response response = CartEndpoint.AddAnItemToCart(savedCartId, invalidProductCartPayload);

        response.then().log().all()
                .assertThat().statusCode(400);
               // .body("error", Matchers.containsString("productId")); // Expect a message about the product
    }

    //2.2 Add An Item To Cart with Invalid Quantity Test (Priority 10)
    @Test(priority = 10)
    public void addAnItemToCartWithInvalidQuantityTest() {
        String savedCartId = getCartId();
        Response response = CartEndpoint.AddAnItemToCart(savedCartId, invalidQuantityCartPayload);

        response.then().log().all()
                .assertThat().statusCode(400)
                .body("error", Matchers.containsString("quantity"));
        Integer generatedItemId = response.jsonPath().getInt("itemId");
        setItemId(generatedItemId);
    }


    // 3. Get a Cart Test (Priority 4)
    @Test(priority = 4)
    public void getACartTest() {
        Response response = CartEndpoint.getACart(getCartId());
        response.then().log().all().assertThat();
        Assert.assertEquals(response.getStatusCode(), 200, "Get Cart Status Code");
    }

    //3.1 Get a Cart with invalid cart ID Test (Priority 8)
    @Test(priority = 8)
    public void getACartWithInvalidCartIdTest() {
        Response response = CartEndpoint.getACart("invalidCartId123");
        response.then().log().all().assertThat().statusCode(404)
                .body("error", equalTo("No cart with id invalidCartId123."));
    }

    // 4. Get Cart Items Test (Priority 5)
    @Test(priority = 5)
    public void getCartItemsTest() {
        Response response = CartEndpoint.getCartItems(getCartId()); // Fixed: use dynamic ID
        response.then().log().all().assertThat();
        Assert.assertEquals(response.getStatusCode(), 200, "Get Cart Items Status Code");
    }

    //4.1 Get a Cart with invalid cart ID Test (Priority 8)
    @Test(priority = 8)
    public void getCartItemsWithInvalidCartIdTest() {
        Response response = CartEndpoint.getCartItems("invalidCartId123");
        response.then().log().all().assertThat().statusCode(404)
                .body("error", equalTo("No cart with id invalidCartId123."));
    }

    // 5. Modify A Cart Item Test (Priority 6)
    @Test(priority = 6)
    public void modifyACartItemTest() {
        CartPayload modifyPayload = new CartPayload();
        modifyPayload.setQuantity(5);

        Response response = CartEndpoint.modifyACartItem(
                getCartId(),
                getItemId(),
                modifyPayload
        );
        response.then().log().all().assertThat().statusCode(204);
    }

    // 6. Replace A Cart Item Test (Priority 9)
    @Test(priority = 9)
    public void replaceACartItemTest() {
        CartPayload replacePayload = new CartPayload();
        replacePayload.setProductId(1710);
        replacePayload.setQuantity(4);
    }

        // 7. Delete A Cart Item Test (Priority 7)
        @Test(priority = 10)
        public void deleteACartItemTest(){
        Response response = CartEndpoint.deleteACartItem(
                getCartId(),
                getItemId()
        );
        response.then().log().all().assertThat()
                .statusCode(204);
    }
    @Test(priority = 11)
    public void addAnItemToCartTest2() {
        String savedCartId = getCartId();

        Response response = CartEndpoint.AddAnItemToCart(savedCartId, validCartPayload);
        response.then().log().all()
                .assertThat().statusCode(201)
                .body("created", equalTo(true))
                .body("itemId", Matchers.notNullValue())
                .body("itemId", Matchers.isA(Integer.class));

        savedItemId = response.jsonPath().getInt("itemId");
        setItemId(savedItemId);
        //System.out.println("Item ID Saved: " + savedItemId);
    }

    }


