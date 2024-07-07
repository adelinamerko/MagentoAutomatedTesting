package selenium.training.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.utils.Driver;
import selenium.training.utils.GlobalConfigs;
import selenium.training.utils.Wait;

public class NavigationBar extends BasePage {

    //region WebElements
    @FindBy(css = "div.panel.header ul.header.links li a[href='https://magento.softwaretestingboard.com/customer/account/create/']")
    private WebElement lnkCreateAccount;

    @FindBy(id = "search")
    private WebElement search;

    @FindBy(css = "button[type=submit].action.search")
    private WebElement searchButton;

    @FindBy(css = ".header.content a.action.showcart span.counter.qty > span.counter-number")
    private WebElement txtShoppingCartQty;

    @FindBy(css = "div.minicart-wrapper")
    private WebElement btnShoppingCart;

    @FindBy(css = "a.action.viewcart")
    private WebElement btnViewEditCart;
    //endregion

    private int localShoppingCartQty;

    private static NavigationBar navigationBar;

    private NavigationBar() {
        super();
        this.localShoppingCartQty = 0;
    }

    public static NavigationBar getNavigationBar() {
        if (navigationBar == null) {
            navigationBar = new NavigationBar();
        }
        return navigationBar;
    }

    public void navigateToCreateAccountPage() {
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(lnkCreateAccount));
        lnkCreateAccount.click();
    }

    public void navigateToHomePage() {
        Driver.getDriver().get(GlobalConfigs.URL);
    }

    public void navigateToShoppingCart() {
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(btnShoppingCart));
        btnShoppingCart.click();

        Wait.getWait().until(ExpectedConditions.elementToBeClickable(btnViewEditCart));
        btnViewEditCart.click();
    }

    public void increaseShoppingCartQty() {
        increaseShoppingCartQty(1);
    }

    public void increaseShoppingCartQty(int qty) {
        this.localShoppingCartQty += qty;
    }

    public int getShoppingCartQty() {
        Wait.getWait().until(ExpectedConditions.textToBePresentInElement(txtShoppingCartQty, String.valueOf(localShoppingCartQty)));
        // Get inner HTML
        String innerHTML = txtShoppingCartQty.getAttribute("innerHTML");
        // Remove comments <!-- ... -->
        String cleanHTML = innerHTML.replaceAll("<!--(.*?)-->", "");
        // Extract the number
        String numberStr = cleanHTML.trim(); // This should be "0" based on your example
        // Parse the number as an integer
//        System.out.println("XXX - NUMBER: " + numberStr);
        return Integer.parseInt(numberStr);
    }

    public int getLocalShoppingCartQty() {
        System.out.println("XXX - LOCAL SHOPPING CART QTY: " + this.localShoppingCartQty);
        return this.localShoppingCartQty;
    }

    public SearchResultsPage performSearch(String productName) {
        Wait.getWait().until(ExpectedConditions.visibilityOf(search));
        search.sendKeys(productName);
        searchButton.click();
        return new SearchResultsPage(productName);
    }
}
