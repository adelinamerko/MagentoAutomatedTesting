package selenium.training.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import selenium.training.enums.ClothesColor;
import selenium.training.enums.ClothesSize;
import selenium.training.pages.*;
import selenium.training.utils.AccountDetails;

import static org.testng.Assert.*;

@Listeners(selenium.training.utils.Listeners.class)
public class MagentoFullUserWorkflowTest {
    private final CreateAccountPage createAccountPage;
    private final MyAccountPage myAccountPage;
    private final NavigationBar navigationBar;
    private final ShoppingCartPage shoppingCartPage;

    public MagentoFullUserWorkflowTest() {
        createAccountPage = new CreateAccountPage();
        myAccountPage = new MyAccountPage();
        navigationBar = new NavigationBar();
        shoppingCartPage = new ShoppingCartPage();
    }

    @Test
    public void fullUserWorkflowTest() {
        // 1- Register
        createAccountPage.navigateToCreateAccountPage();
        createAccountPage.createAccount(AccountDetails.getAccountDetails());
        assertEquals(createAccountPage.getPasswordStrengthMessage(), CreateAccountPage.STRONG_PASSWORD_MSG);
        createAccountPage.submitAccountDetails();
        assertEquals(createAccountPage.getRegistrationConfirmationMessage(), CreateAccountPage.SUCCESSFUL_REGISTRATION_MSG);

        // 2- Assert Account Details
        assertEquals(myAccountPage.getAccountFullName(), AccountDetails.getAccountDetails().getFullName());
        assertEquals(myAccountPage.getAccountEmail(), AccountDetails.getAccountDetails().getEmail());

        // 3- Search for a product
        SearchResultsPage searchResultsPage = navigationBar.performSearch("Jacket");
        assertTrue(searchResultsPage.validateSearchResults());

        // 4- Add product to cart
        assertTrue(searchResultsPage.addProductToCart(1, ClothesSize.M, ClothesColor.FIRST_COLOR));
        assertTrue(searchResultsPage.addProductToCart(2, ClothesSize.M, ClothesColor.FIRST_COLOR));
        assertTrue(searchResultsPage.addProductToCart(3, ClothesSize.M, ClothesColor.FIRST_COLOR));

        // 5- Navigate to cart
        navigationBar.navigateToShoppingCart();

        // 6- Assert cart total price
        assertEquals(shoppingCartPage.getSubtotalPrice(), shoppingCartPage.getCalculatedProductsTotalPrice());

        // 7- Update product quantity
        shoppingCartPage.updateProductQuantity(1, 2);
        shoppingCartPage.updateProductQuantity(2, 4);
        shoppingCartPage.updateProductQuantity(3, 6);
        shoppingCartPage.clickUpdateCart();

        // 8- Assert cart total price
        assertEquals(shoppingCartPage.getSubtotalPrice(), shoppingCartPage.getCalculatedProductsTotalPrice());
    }
}
