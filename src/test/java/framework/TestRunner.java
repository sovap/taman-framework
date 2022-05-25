package framework;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features"}
        ,plugin = {"json:target/cucumber.json","html:target/site/cucumber-pretty"}
        ,glue = "steps"
        )

public class TestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeSuite
	public static void setup() {
		
	}
	
	@AfterSuite
	public static void cleanup() {
	}
	
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}