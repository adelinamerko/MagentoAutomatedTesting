package selenium.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.utils.Driver;
import selenium.training.utils.Wait;

import java.util.List;

public class ShoppingCartPage extends BasePage {

    private final NavigationBar navigationBar;

    //region WebElements
    @FindBy(css = "#shopping-cart-table")
    private List<WebElement> tblShoppingCart;

    @FindBy(css = "#shopping-cart-table > tbody.cart.item  .col.subtotal")
    private List<WebElement> productSubtotalPriceColumn;

    @FindBy(css = "#shopping-cart-table > tbody.cart.item  .col.qty input")
    private List<WebElement> productQtyInputColumn;

    @FindBy(css = ".cart-summary .totals.sub .amount .price")
    private WebElement subtotalPrice;

    @FindBy(css = ".cart.main.actions button[type='submit']")
    private WebElement btnUpdateCart;
    //endregion

    public ShoppingCartPage() {
        super();
        navigationBar = NavigationBar.getNavigationBar();
    }

    public void updateProductQuantity(int productIndex, int newQuantity) {
        // Since List indexes are 0-based, we need to subtract 1 from the productIndex
        int index = productIndex - 1;
        Wait.getWait().until(ExpectedConditions.visibilityOfAllElements(tblShoppingCart));
        Wait.getWait().until(ExpectedConditions.visibilityOfAllElements(productQtyInputColumn));
        productQtyInputColumn.get(index).clear();
        productQtyInputColumn.get(index).sendKeys(String.valueOf(newQuantity));

        // Update the shopping cart quantity in the navigation bar
        navigationBar.increaseShoppingCartQty(newQuantity - 1);
    }

    public void clickUpdateCart() {
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(btnUpdateCart));
        btnUpdateCart.click();
        waitForCartUpdate();
    }

    public double getCalculatedProductsTotalPrice() {
        double sum = 0;
        for (WebElement totalElement : productSubtotalPriceColumn) {
            // Get the text of the price element (e.g., "$57.00")
            String priceText = totalElement.getText().trim();
            double itemPrice = Double.parseDouble(priceText.replace("$", ""));

            // Add item price to total price
            sum += itemPrice;
        }
//        System.out.println("XXX - SUM: " + sum);
        return sum;
    }

    public double getSubtotalPrice() {
        Wait.getWait().until(ExpectedConditions.visibilityOf(subtotalPrice));
        String totalPriceText = subtotalPrice.getText().trim();
//        System.out.println("XXX - TOTAL PRICE: " + totalPriceText);
        return Double.parseDouble(totalPriceText.replace("$", ""));
    }

    private void waitForCartUpdate() {
        Wait.getWait().until(ExpectedConditions.stalenessOf(Driver.getDriver().findElement(By.cssSelector(".cart-summary .totals.sub .amount .price"))));
    }
}
