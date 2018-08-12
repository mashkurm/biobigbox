package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserLauncher {
	public static WebDriver driver;

	public static WebDriver getBrowser() {
		ConfigFileReader configFileReader = new ConfigFileReader();
		DesiredCapabilities capability = new DesiredCapabilities();
		if (configFileReader.getPropertyValue("browser").equalsIgnoreCase("chrome")) {
			capability.setJavascriptEnabled(true);
			capability.setCapability("chrome.switches", "--start-maximized");
			capability.setCapability(ChromeOptions.CAPABILITY, new ChromeOptions());
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			driver = new ChromeDriver(capability);
		} else if (configFileReader.getPropertyValue("browser").equalsIgnoreCase("firefox")) {
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("dom.max_chrome_script_run_time", 1000);
			profile.setPreference("dom.max_script_run_time", 1000);
			profile.setPreference("browser.cache.disk.enable", false);
			capability.setCapability(FirefoxDriver.PROFILE, profile);
			driver = new FirefoxDriver(capability);
			driver = new FirefoxDriver();
		}
		return driver;
	}
}
