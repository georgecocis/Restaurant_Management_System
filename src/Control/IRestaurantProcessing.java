package Control;

public interface IRestaurantProcessing {

    /**
     * menu.size() @ pre == menu.size() - 1 @ post
     */

    void createNewMenuItem();

    /**
     * menu.size() @ pre greater than menu.size() @ post
     */

    void deleteMenuItem();

    /**
     * menuItem.price() @ pre != menuItem.price() @ post
     */

    void editMenuItem();

    /**
     * order != null
     * menuItem != null
     */

    void createNewOrder();

    /**
     * price @ pre == 0
     * price @ post greater than 0
     */

    void computePriceOrder();

    /**
     * init@pre.equals("")
     * init@post.!equals.("")
     */

    void generateBill();
}
