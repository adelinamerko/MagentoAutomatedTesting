package selenium.training.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.training.enums.AccountInfo;
import selenium.training.utils.Wait;

public class MyAccountPage extends BasePage {

    @FindBy(css = "div.box-content > p")
    private WebElement accountInfo;

    public String getAccountFullName() {
        return getAccountInfo(AccountInfo.FULL_NAME);
    }

    public String getAccountEmail() {
        return getAccountInfo(AccountInfo.EMAIL);
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
