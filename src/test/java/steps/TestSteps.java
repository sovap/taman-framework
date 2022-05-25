package steps;

import data.Globals;
import cucumber.api.java.en.Given;
import java.util.HashMap;
import java.util.Map;

public class TestSteps {

	@Given("User will prepare some fake stored data")
	public void user_will_prepare_some_fake_stored_data() {

		Map<String,	String> storedMap = Globals.mapScenarioData.get(Thread.currentThread().getId());

		// create a new map if storedMap is null
		if(storedMap == null){
			storedMap = new HashMap<>();
		}

		// add fake values
		storedMap.put("TEST", "STATUS");

		// store back to the runtime
		Globals.mapScenarioData.put(Thread.currentThread().getId(), storedMap);
	}

	@Given("User will prepare some data")
	public void user_will_prepare_some_data() {
	    
	}

	@Given("User will sa something {string}")
	public void user_will_prepare_some_data(String sata) {
		System.out.println(sata);
	}

	@Given("User will do something")
	public void user_will_do_something() {
		
	}

	@Given("Something should be correct")
	public void something_should_be_correct() {
	    
	}

	@Given("User will wait for {string} seconds")
	public void user_will_wait_for_seconds(String seconds) {

		try {
			Thread.sleep(Integer.parseInt(seconds) * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Given("User will wait for {int} seconds")
	public void userWillWaitForSeconds(int arg0) {

		try {
			Thread.sleep(arg0 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}