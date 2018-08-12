package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;

import pages.Login;
import utils.BrowserLauncher;

public class LoginTest {

	WebDriver driver;
    Login login;
    
    @BeforeTest
    public void setup() {
    	BrowserLauncher.getBrowser().get("http://www.google.com");;
    }

}
