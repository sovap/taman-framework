package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

// ******************** LOCATORS **********************************************************************************************************

	@FindBy(how = How.XPATH, using = "//button[text()='Ok']")
	private WebElement btnOk;

	@FindBy(how = How.ID, using = "j_username")
	private WebElement inputUsername;

	@FindBy(how = How.ID, using = "j_password")
	private WebElement inputPassword;

	@FindBy(how = How.XPATH, using = "//button[text()='Login']")
	private WebElement btnLogin;

	@FindBy(how = How.XPATH, using = "//button[text()='Close']")
	private WebElement btnClose;

// ******************** BASIC METHODS ****************************************************************************************************

	public boolean isPageLoaded() {

		return waitAndCheckIfElementIsDisplayed(inputUsername);
	}

	private void openLoginPage(String loginPageUrl) {

		// navigate to url
		driver.get(loginPageUrl);
	}

	private void clickBtnOk() {

		waitThenClick(btnOk);
	}

	private void setUsername(String username) {

		waitThenSendKeys(inputUsername, username);
	}

	private void setPassword(String password) {

		waitThenSendKeys(inputPassword, password);
	}

	private void clickBtnLogin() {

		waitThenClick(btnLogin);
	}

// ******************** BUSINESS METHODS **************************************************************************************************

}