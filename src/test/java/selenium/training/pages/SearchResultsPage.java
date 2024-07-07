package selenium.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.enums.ClothesColor;
import selenium.training.enums.ClothesSize;
import selenium.training.utils.Driver;
import selenium.training.utils.Wait;

public class SearchResultsPage extends BasePage {

    //region Constants
    private static final String ADDED_TO_CART_MESSAGE = "You added %s to your shopping cart.";
    private static final String productDetailsSelector = "div.product-item-info > div.product.details.product-item-details";
    private static final String productTitleSelector = productDetailsSelector + " > strong > a";
    //endregion

    private String expectedPageTitleTemplate = "Search results for: '%s'";
    private final String searchQuery;
    private final NavigationBar navigationBar;

    //region WebElements
    @FindBy(css = "div[data-bind='html: $parent.prepareMessageForHtml(message.text)']")
    private WebElement addedToCartConfirmationMessage;

    @FindBy(css = "h1.page-title > span.base")
    private WebElement pageTitleEl;
    //endregion

    public SearchResultsPage(String searchQuery) {
        super();
        this.searchQuery = searchQuery;
        this.expectedPageTitleTemplate = String.format(expectedPageTitleTemplate, searchQuery);
        this.navigationBar = NavigationBar.getNavigationBar();
    }

    public boolean addProductToCart(int elementIndex, ClothesSize size, ClothesColor color) {
        // Get the product element
        WebElement product = getNthProduct(elementIndex);
        Wait.getWait().until(ExpectedConditions.visibilityOf(product));

        // Get the product title
        String productTitle = product.findElement(By.cssSelector(productTitleSelector)).getText();

        // Hover over the product
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(product).perform();

        // Select the size and color
        String productSizeSelector = productDetailsSelector + " div.swatch-attribute.size > div.swatch-attribute-options.clearfix > div:nth-child(%s)";
        WebElement productSize = product.findElement(By.cssSelector(String.format(productSizeSelector, size.getOrdinalNumber())));
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(productSize));
        productSize.click();

        String productColorSelector = productDetailsSelector + " div.swatch-attribute.color > div.swatch-attribute-options.clearfix > div:nth-child(%s)";
        WebElement productColor = product.findElement(By.cssSelector(String.format(productColorSelector, color.getOrdinalNumber())));
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(productColor));
        productColor.click();

        // Add the product to the cart
        String btnAddToCartSelector = productDetailsSelector + " > div.product-item-inner > .product.actions.product-item-actions form > button[type='submit']";
        WebElement btnAddToCart = product.findElement(By.cssSelector(btnAddToCartSelector));
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(btnAddToCart));
        btnAddToCart.click();

        navigationBar.increaseShoppingCartQty();

        // Validate the product was added to the cart
        return getAddedToCartConfirmationMessage(productTitle).equals(String.format(ADDED_TO_CART_MESSAGE, productTitle))
                && navigationBar.getShoppingCartQty() == navigationBar.getLocalShoppingCartQty();
    }

    public boolean validateSearchResults() {
        WebElement firstProduct = getNthProduct(1);
        WebElement firstProductTitle = firstProduct.findElement(By.cssSelector(productTitleSelector));
        Wait.getWait().until(ExpectedConditions.visibilityOf(firstProduct));
        return firstProductTitle.getText().toLowerCase().contains(searchQuery.toLowerCase()) && getPageTitle().equals(expectedPageTitleTemplate);
    }

    private String getAddedToCartConfirmationMessage(String productTitle) {
        Wait.getWait().until(ExpectedConditions.visibilityOf(addedToCartConfirmationMessage));
        Wait.getWait().until(ExpectedConditions.textToBePresentInElement(addedToCartConfirmationMessage, String.format(ADDED_TO_CART_MESSAGE, productTitle)));
        return addedToCartConfirmationMessage.getText();
    }

    private String getPageTitle() {
        Wait.getWait().until(ExpectedConditions.visibilityOf(pageTitleEl));
        return pageTitleEl.getText();
    }

    private WebElement getNthProduct(int elementIndex) {
        String searchedProductSelector = "div.products.wrapper.grid.products-grid > ol > li:nth-child(%s)";
        String productSelector = String.format(searchedProductSelector, elementIndex);
        return Driver.getDriver().findElement(By.cssSelector(productSelector));
    }
}
