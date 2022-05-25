package framework;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public final class DriverUtil {

	public static Map<Long, WebDriver> driverCache = new ConcurrentHashMap<>();
	
	public static WebDriver initDriver() {
 
		// Chrome browser capabilities (Chrome options)
		ChromeOptions options = new ChromeOptions();
		options.setCapability("takesScreenshot", true);
		options.setCapability("browserName", "chrome");
		options.setCapability("acceptSslCerts", true);		

		WebDriver driver = null;
		try {
			// disable logging in console
			java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

			// if system variable localExecution is set to true or yes then use Chromedriver, otherwise use Remotedriver with Selenium server on remote machine
			String executeLocally = PropertiesUtil.getLocalExecution();
			if (executeLocally.toLowerCase().contentEquals("true") || executeLocally.toLowerCase().contentEquals("yes")) {
				// execute locally without headless mode to see whats going on 
				
				System.setProperty("webdriver.chrome.driver", "src\\test\\java\\bin\\chromedriver.exe");
				driver = new ChromeDriver(options);

			} else {
				// execute remotely with headless mode

				// headless arguments
				options.addArguments("headless");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				
				// initialize new remote driver
				driver = new RemoteWebDriver(new URL(PropertiesUtil.getSeleniumBoxUrl() + "/wd/hub"), options);
			}
			
			// Selenium synchronization timeouts
			driver.manage().timeouts().setScriptTimeout(PropertiesUtil.getAsyncWait(), TimeUnit.SECONDS);
	        driver.manage().timeouts().implicitlyWait(PropertiesUtil.getImplicitWait(), TimeUnit.SECONDS);
	        
	        // set resolution
	        driver.manage().window().setSize(new Dimension(1920, 1080));

		} catch (Exception e ) {
			Assert.fail("[ERROR] Driver initialisation error: " + e);
		}
		return driver;
    }
		
	public static void quitDriver(WebDriver driver) {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				Assert.fail("[ERROR] An error occurred while trying to close the driver: " + e);
			}
		}
	}
}
