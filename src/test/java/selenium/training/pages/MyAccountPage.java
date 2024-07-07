package selenium.training.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.enums.AccountInfo;
import selenium.training.utils.Wait;

public class MyAccountPage extends BasePage {

    public static final String SUCCESSFUL_REGISTRATION_MSG = "Thank you for registering with Main Website Store.";

    //region WebElements
    @FindBy(css = "div.box-content > p")
    private WebElement accountInfo;

    @FindBy(css = "div[data-bind='html: $parent.prepareMessageForHtml(message.text)']")
    private WebElement registrationConfirmationMessage;
    //endregion

    public String getAccountFullName() {
        return getAccountInfo(AccountInfo.FULL_NAME);
    }

    public String getAccountEmail() {
        return getAccountInfo(AccountInfo.EMAIL);
    }

    public String getRegistrationConfirmationMessage() {
        Wait.getWait().until(ExpectedConditions.visibilityOf(registrationConfirmationMessage));
        return registrationConfirmationMessage.getText();
    }

    private String getAccountInfo(AccountInfo accountInfoElement) {
        Wait.getWait().until(ExpectedConditions.visibilityOf(accountInfo));
        String[] contactInfo = accountInfo.getText().split("\n");
        if (accountInfoElement.equals(AccountInfo.FULL_NAME)) {
            return contactInfo[0];
        } else {
            return contactInfo[1];
        }
    }
}
