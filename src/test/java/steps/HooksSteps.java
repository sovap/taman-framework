package steps;

import data.Globals;
import framework.DriverUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import org.openqa.selenium.*;
import org.testng.asserts.SoftAssert;

public class HooksSteps {
	
	Globals globals;
	protected WebDriver driver;		
	private static AtomicInteger counter = new AtomicInteger(0);

    @Before
    public void InitializeTest(Scenario scenario) {
    	
    	// sof assert
    	final SoftAssert softAssertion= new SoftAssert();
    	Globals.mapSoftAssert.put(Thread.currentThread().getId(), softAssertion);
    	
    	// timestamp and time_stamp
    	String timeStamp = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
    	String time_Stamp = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		Globals.mapTimeStamp.put(Thread.currentThread().getId(), timeStamp + "-" + Thread.currentThread().getId());
		Globals.mapTime_Stamp.put(Thread.currentThread().getId(), time_Stamp + "_" + Thread.currentThread().getId());

		// sysdate
		String sysDate = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
		Globals.mapSysDate.put(Thread.currentThread().getId(), sysDate);

    	// decide if need to start a browser
    	boolean needBrowser = true;
    	Collection<String> tagArray = scenario.getSourceTagNames();

    	for (String tag : tagArray) {
			if(tag.contentEquals("@backend")) {
				needBrowser = false;
			}
		}
    	
    	if (needBrowser) {
        	// store the new driver to the concurrent hash map
            DriverUtil.driverCache.putIfAbsent(Thread.currentThread().getId(), DriverUtil.initDriver());
    	}
    }
    
    @After
    public void TearDownTest(Scenario scenario) {
    	
    	// get the driver from the hash map
        WebDriver driver = DriverUtil.driverCache.get(Thread.currentThread().getId());
        
        // get the soft assert object
        SoftAssert softAssertion = Globals.mapSoftAssert.get(Thread.currentThread().getId());
        
        // evaluate soft assert
        softAssertion.assertAll();
        
        // delete the record from the hash map
        DriverUtil.driverCache.remove(Thread.currentThread().getId(), driver);
        
        // delete the object of soft assert from the map
        Globals.mapSoftAssert.remove(Thread.currentThread().getId(), softAssertion);
        
        // delete the timestamp
		String timestamp = Globals.mapTimeStamp.get(Thread.currentThread().getId());
        Globals.mapTimeStamp.remove(Thread.currentThread().getId());
        
        boolean needBrowser = true;
    	Collection<String> tagArray = scenario.getSourceTagNames();

    	for (String tag : tagArray) {
			if(tag.contentEquals("@backend")) {
				needBrowser = false;
			}
		}
    	
    	if (scenario.isFailed() && needBrowser) {
            // Take a screenshot and embed it in the report.
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
            scenario.write("Screenshot attached");
        }

    	synchronized(counter) {
    		System.out.println(counter.incrementAndGet() + " Timestamp: " + timestamp + " Scenario: "+scenario.getName()+" Result: " + scenario.getStatus());
    	}
  		    		     	
        DriverUtil.quitDriver(driver);
    }
}
