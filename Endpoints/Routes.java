package api.Endpoints;

public class Routes {
        public static String baseUrl = "https://simple-grocery-store-api.click";


        //Stats Module
        public static String get_status=baseUrl+"/status";


        //Cart Module
        public static String get_a_cart=baseUrl+"/carts/{cartId}";
        public static String get_cart_items=baseUrl+"/carts/{cartId}/items";
        public static String create_new_cart=baseUrl+"/carts";
        public static String add_item_to_cart=baseUrl+"/carts/{cartId}/items";
        public static String modify_cart_item=baseUrl+"/carts/{cartId}/items/{itemId}";
        public static String replace_cart_item=baseUrl+"/carts/{cartId}/items/{itemId}";
        public static String delete_cart_item=baseUrl+"/carts/{cartId}/items/{itemId}";


        //Orders Module
        public static String get_all_orders=baseUrl+"/orders";
        public static String get_an_order=baseUrl+"/orders/{orderId}";
        public static String create_new_order=baseUrl+"/orders";
        public static String update_an_order=baseUrl+"/orders/{orderId}";
        public static String delete_an_order=baseUrl+"/orders/{orderId}";

        //Products Module
        public static String get_all_products=baseUrl+"/products";
        public static String get_a_product=baseUrl+"/products/{productId}";

        //Registration Module
        public static String register_new_client =baseUrl+"/api-clients";


}
