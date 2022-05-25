package pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import framework.PropertiesUtil;

public class BasePage {
	
	protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
    
// ******************** WEB ELEMENT SYNCHRONIZATION METHODS *******************************************************************************

    public void waitUntilDataRowsAreDisplayed(String dataRowsLocator) {

        // wait for all scripts on page to finish (javascript, jquery, angular)
        waitWrapper();

        // wait until the list of data rows is bigger than zero or timeout
        WebDriverWait wait = new WebDriverWait(driver, PropertiesUtil.getExplicitWait());
        wait.until((ExpectedCondition<Boolean>) driver -> {
            assert driver != null;
            return driver.findElements(By.xpath(dataRowsLocator)).size() > 0;
        });
    }

    public WebElement waitForElementToBeClickable(WebElement elementToWaitFor) {
    	   	
    	// wait for all scripts on page to finish (javascript, jquery, angular)
    	waitWrapper();
    	
    	// wait until element is clickable (visible and enabled)
        return new WebDriverWait(driver, PropertiesUtil.getExplicitWait()).until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
    }
    
    public boolean waitAndCheckIfElementIsDisplayed(WebElement elementToWaitFor) {
    	
    	// wait for all scripts on page to finish (javascript, jquery, angular)
    	waitWrapper();
    	
    	boolean isDisplayed;
    	try {
    		isDisplayed = new WebDriverWait(driver, PropertiesUtil.getExplicitWait()).until(ExpectedConditions.visibilityOf(elementToWaitFor)).isDisplayed();
    	} catch(WebDriverException ignored) {
    		isDisplayed = false;
    	}
    	
    	return isDisplayed;
    }
        
    public void waitThenClick(WebElement elementToClick) {
    	
    	waitForElementToBeClickable(elementToClick).click();
    }

    public void waitThenSendKeys(WebElement elementToSendKeys, String keysToSend) {
    	
    	waitForElementToBeClickable(elementToSendKeys).clear();
    	waitForElementToBeClickable(elementToSendKeys).sendKeys(keysToSend);
    }

// ******************** TECHNOLOGY SYNCHRONIZATION METHODS ********************************************************************************
    
    public void waitWrapper() {
    	
    	// wait for javascript to finish (this will also provide enough time for jquery and angular to be recognized in browser)
    	waitForJavaScript();
    	
    	// in case jquery is present, wait for it to finish
    	waitForJquery();
    	
    	// in case angular is present, wait for it to finish
    	waitForAngular();
    }
    
    public void waitForJavaScript() {
        
        try {
        	// create a new condition
        	ExpectedCondition<Boolean> condition = driver -> {
                assert driver != null;
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            };
 
           // wait until condition is true
        	new WebDriverWait(driver, PropertiesUtil.getExplicitWait()).until(condition);

        } catch (WebDriverException ignored) {
        }
    }
    
    public void waitForAngular() {
    	
    	try {
            
        	// create a new condition
        	ExpectedCondition<Boolean> condition = driver -> {
                assert driver != null;
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1");
            };
 
           // wait until condition is true
        	new WebDriverWait(driver, PropertiesUtil.getExplicitWait()).until(condition);
        	
        } catch (WebDriverException ignored) {
        }
    }
    
    public void waitForJquery() {
    	
    	try {
            
    		// create a new condition
    		ExpectedCondition<Boolean> condition = driver -> {
                assert driver != null;
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
            };
            	
            // wait until condition is true
        	new WebDriverWait(driver, PropertiesUtil.getExplicitWait()).until(condition);
        	
        } catch (WebDriverException ignored) {
        }
    }
    
// ******************** COMMON METHODS ****************************************************************************************************
    
    public boolean isElementPresent(String byType, String locator) {
    	
    	boolean isPresent = false;
    	    	
    	// wait until scripts on page are finished
    	waitWrapper();
    	
    	// change implicit wait to skip the waiting
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        
        switch (byType) {
        	
        case "xpath":
        	isPresent = driver.findElements(By.xpath(locator)).size()>0;
        	break;
        case "name":
        	isPresent = driver.findElements(By.name(locator)).size()>0;
        	break;
        default:
        	System.out.println("byType value not supported");
        }

    	// change implicit back to original value
        driver.manage().timeouts().implicitlyWait(PropertiesUtil.getImplicitWait(), TimeUnit.SECONDS);
        
    	return isPresent;
    }

    public boolean doesElementContainsClass(WebElement elementToCheck, String expectedClass) {

    	return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].matches('.' + arguments[1]);", elementToCheck, expectedClass);
    }
}