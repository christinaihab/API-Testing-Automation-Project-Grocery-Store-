package api.Utilities;

public class TestDataStore {
    public static String  clientName= "Mj Smarks";
    public static String  clientEmail="mjpeter3000@gmail.com";
    public static String  orderComment="Please deliver between 9 AM and 5 PM.";
    public static String accessToken;
    public static String cartId;
    public static String orderId;
    public static int itemId;

    public static String getCartId() {
        return cartId;
    }

    public static void setCartId(String cartId) {
        TestDataStore.cartId = cartId;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        TestDataStore.accessToken = accessToken;
    }

    public static String getOrderId() {
        return orderId;
    }

    public static void setOrderId(String orderId) {
        TestDataStore.orderId = orderId;
    }

    public static int getItemId() {
        return itemId;
    }

    public static void setItemId(int itemId) {
        TestDataStore.itemId = itemId;
    }



}
