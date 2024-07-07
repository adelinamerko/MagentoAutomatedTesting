package selenium.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.utils.Driver;
import selenium.training.utils.GlobalConfigs;
import selenium.training.utils.Wait;

public class NavigationBar extends BasePage {

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

    private int shoppingCartQty;

    public NavigationBar() {
        super();
        shoppingCartQty = 0;
    }

    // @TODO: Add NavBar WebElements such as <products> dropdowns

    public SearchResultsPage performSearch(String productName) {
        Wait.getWait().until(ExpectedConditions.visibilityOf(search));
        search.sendKeys(productName);
        searchButton.click();
        return new SearchResultsPage(productName);
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

    public int getShoppingCartQty() {
        Wait.getWait().until(ExpectedConditions.textToBePresentInElement(txtShoppingCartQty, String.valueOf(shoppingCartQty)));
        // Get inner HTML
        String innerHTML = txtShoppingCartQty.getAttribute("innerHTML");
        // Remove comments <!-- ... -->
        String cleanHTML = innerHTML.replaceAll("<!--(.*?)-->", "");
        // Extract the number
        String numberStr = cleanHTML.trim(); // This should be "0" based on your example
        // Parse the number as an integer
        return Integer.parseInt(numberStr);
    }

    public void incrementShoppingCartQty() {
        this.shoppingCartQty++;
    }

    public int getLocalShoppingCartQty() {
        return shoppingCartQty;
    }

}
