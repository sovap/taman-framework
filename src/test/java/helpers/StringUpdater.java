package helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import data.Globals;

public class StringUpdater { 
	
	public static String replaceParameters(String stringToUpdate) {

		String resultString = stringToUpdate;

		// user defined parameters
		List<String> queryPlaceholders = new ArrayList<>();
		queryPlaceholders.add("TIMESTAMP");
		queryPlaceholders.add("TIME_STAMP");
		queryPlaceholders.add("TEMPLATE");
		queryPlaceholders.add("SYSDATE");

		for (String placeHolder : queryPlaceholders) {
	
			switch (placeHolder) {
			case ("TIMESTAMP"):
				resultString = resultString.replace("TIMESTAMP", Globals.mapTimeStamp.get(Thread.currentThread().getId()));
				break;
			case ("TIME_STAMP"):
				resultString = resultString.replace("TIME_STAMP", Globals.mapTime_Stamp.get(Thread.currentThread().getId()));
				break;
			case ("TEMPLATE"):
				resultString = resultString.replace("TEMPLATE", Globals.mapTimeStamp.get(Thread.currentThread().getId()));
				break;
			case ("SYSDATE"):
				resultString = resultString.replace("SYSDATE", Globals.mapSysDate.get(Thread.currentThread().getId()));
				break;
			}
		}

		// runtime stored parameters from map
		Map<String,	String> storedMap = Globals.mapScenarioData.get(Thread.currentThread().getId());

		if(storedMap != null){
			// for each key value pair, replace the key with value
			for (Map.Entry<String, String> entry : storedMap.entrySet()) {
				String storedKey = entry.getKey();
				String storedValue = entry.getValue();
				String parameterToReplace = "KEY-" + storedKey;

				// update the result
				resultString = resultString.replace(parameterToReplace, storedValue);
			}
		}

		return resultString;
	}	
}