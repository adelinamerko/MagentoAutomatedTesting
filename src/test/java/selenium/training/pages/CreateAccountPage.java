package selenium.training.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.utils.AccountDetails;
import selenium.training.utils.Wait;

public class CreateAccountPage extends BasePage {

    public static final String SUCCESSFUL_REGISTRATION_MSG = "Thank you for registering with Main Website Store.";
    public static final String STRONG_PASSWORD_MSG = "Very Strong";

    private final NavigationBar navigationBar;

    public CreateAccountPage() {
        super();
        this.navigationBar = new NavigationBar();
    }

    @FindBy(css= "form.create.account.form-create-account")
    private WebElement frmCreateAccount;

    @FindBy(id = "firstname")
    private WebElement txtFirstName;

    @FindBy(id = "lastname")
    private WebElement txtLastName;

    @FindBy(id = "email_address")
    private WebElement txtEmailAddress;

    @FindBy(id = "password")
    private WebElement txtPassword;

    @FindBy(id = "password-confirmation")
    private WebElement txtConfirmPassword;

    @FindBy(id="password-strength-meter-label")
    private WebElement passwordStrengthMessage;

    @FindBy(css = "button[title='Create an Account']")
    private WebElement btnCreateAccount;

    @FindBy(css = "div[data-bind='html: $parent.prepareMessageForHtml(message.text)']")
    private WebElement registrationConfirmationMessage;



    public void fillAccountDetails(String name, String lastName, String email, String psw, String confirmPsw) {
        // Let's wait for the form to be visible, so we do not have to wait for each field
        Wait.getWait().until(ExpectedConditions.visibilityOf(frmCreateAccount));

        txtFirstName.clear();
        this.txtFirstName.sendKeys(name);

        txtLastName.clear();
        this.txtLastName.sendKeys(lastName);

        txtEmailAddress.clear();
        this.txtEmailAddress.sendKeys(email);

        txtPassword.clear();
        this.txtPassword.sendKeys(psw);

        txtConfirmPassword.clear();
        this.txtConfirmPassword.sendKeys(confirmPsw);
    }

    public void submitAccountDetails() {
        Wait.getWait().until(ExpectedConditions.elementToBeClickable(btnCreateAccount));
        btnCreateAccount.click();
    }

    public void createAccount(AccountDetails accountDetails) {
        fillAccountDetails(accountDetails.getFirstName(), accountDetails.getLastName(), accountDetails.getEmail(), accountDetails.getPassword(), accountDetails.getPassword());
    }

    public void navigateToCreateAccountPage() {
        navigationBar.navigateToHomePage();
        navigationBar.navigateToCreateAccountPage();
    }

    public String getRegistrationConfirmationMessage() {
        Wait.getWait().until(ExpectedConditions.visibilityOf(registrationConfirmationMessage));
        return registrationConfirmationMessage.getText();
    }

    public String getPasswordStrengthMessage() {
        Wait.getWait().until(ExpectedConditions.visibilityOf(passwordStrengthMessage));
        return passwordStrengthMessage.getText();
    }
}