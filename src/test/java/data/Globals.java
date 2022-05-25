package data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.testng.asserts.SoftAssert;

public class Globals {

	public static final Map<Long, String> mapTimeStamp = new ConcurrentHashMap<>();
	public static final Map<Long, String> mapTime_Stamp = new ConcurrentHashMap<>();
	public static final Map<Long, String> mapSysDate = new ConcurrentHashMap<>();
	public static final Map<Long, SoftAssert> mapSoftAssert = new ConcurrentHashMap<>();
	public static final Map<Long, Map<String, String>> mapScenarioData = new ConcurrentHashMap<>();
}