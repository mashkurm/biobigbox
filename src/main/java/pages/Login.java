package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.jetty7.util.log.Log;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class Login {

	WebDriver driver;
	WebDriverWait wait;
	By lnkSignIn = By.id("loginLink");
	By txtEmail = By.name("uEmail");
	By txtPassword = By.name("password");
	By btnLogin = By.id("sing_in-btn");
	By chkboxRememberMe = By.name("is_remember_me");
	By lnkRegister = By.id("registerLink");
	By lnkForgotPwd = By.id("frg_pass");

	public Login(WebDriver driver) {
		this.driver = driver;
	}

	@SuppressWarnings("deprecation")
	public void clickSignIn() {
		WebElement signIn = null;
		wait = new WebDriverWait(driver, 10, 100);
		try {
			signIn = wait.until(ExpectedConditions.elementToBeClickable(lnkSignIn));
		} catch (ElementNotFoundException | ElementNotVisibleException e) {
			if(e instanceof ElementNotFoundException)
				Log.warn("Element is not found using locator " + lnkSignIn);
			else
				Log.warn("Element is displayed, but it is not visible");
			e.printStackTrace();
		}
		
		signIn.click();
	}

}
