package framework;

import org.testng.Assert;
import java.io.FileInputStream;
import java.util.Properties;

public final class PropertiesUtil {

	private static Integer asyncWait;
	private static Integer implicitWait;
	private static Integer explicitWait;
	private static Integer sqlWait;
	private static String seleniumBoxUrl;
	private static String envServerUrl;
	private static String dbHostName;
	private static Integer dbPort;
	private static String dbServiceName;
	private static String dbUser;
	private static String sftpHost;
	private static Integer sftpPort;
	private static String sftpUser;
	private static String sftpRemoteDir;
		
	// path to the configuration file
	private static final String taPropertiesFile = "src/test/resources/ta.properties";
	private static final String envPropertiesFile = "src/test/resources/env.properties";

	// load configuration
	static {

		// load properties from the property file
		loadProperties();

		// overwrite values from environment variables if those are present
		String EV_seleniumUrl = System.getenv("SELENIUM_URL");
		if (!"".equals(EV_seleniumUrl) && EV_seleniumUrl != null) {
			seleniumBoxUrl = EV_seleniumUrl;
		}

		String EV_envServerUrl = System.getenv("ENV_SERVER_URL");
		if (!"".equals(EV_envServerUrl) && EV_envServerUrl != null) {
			envServerUrl = EV_envServerUrl;
		}
	}

	// get the value from system variable localExecution which will decide if we want to run locally (chrome driver) or remotely (remote driver and Selenium server)
	public static String getLocalExecution() {
		String valueToReturn = "false";
		String executeLocally = System.getProperty("localExecution");
		if (!"".equals(executeLocally) && executeLocally != null) {
			valueToReturn = executeLocally;
		}
		
		return valueToReturn;
	}
	
	public static Integer getAsyncWait() {
		if (asyncWait == null) {
			asyncWait = 0;
		}

		return asyncWait;
	}

	public static Integer getImplicitWait() {
		if (implicitWait == null) {
			implicitWait = 0;
		}

		return implicitWait;
	}

	public static Integer getExplicitWait() {
		if (explicitWait == null) {
			explicitWait = 0;
		}

		return explicitWait;
	}

	public static Integer getSqlWait() {
		if (sqlWait == null) {
			sqlWait = 0;
		}

		return sqlWait;
	}

	public static String getSeleniumBoxUrl() {
		if (seleniumBoxUrl == null || seleniumBoxUrl.isEmpty()) {
			seleniumBoxUrl = "";
		}

		return seleniumBoxUrl;
	}

	public static String getEnvUrl() {

		if (envServerUrl == null || envServerUrl.isEmpty()) {
			envServerUrl = "";
		}

		return envServerUrl;
	}

	public static String getDbHostName() {
		if (dbHostName == null || dbHostName.isEmpty()) {
			dbHostName = "";
		}

		return dbHostName;
	}

	public static Integer getDbPort() {
		if (dbPort == null) {
			dbPort = 0;
		}

		return dbPort;
	}

	public static String getDbServiceName() {
		if (dbServiceName == null || dbServiceName.isEmpty()) {
			dbServiceName = "";
		}

		return dbServiceName;
	}

	public static String getDbUser() {
		if (dbUser == null || dbUser.isEmpty()) {
			dbUser = "";
		}

		return dbUser;
	}
	
	public static String getSftpHost() {
		if (sftpHost == null || sftpHost.isEmpty()) {
			sftpHost = "";
		}

		return sftpHost;
	}
	
	public static Integer getSftpPort() {
		if (sftpPort == null) {
			sftpPort = 0;
		}

		return sftpPort;
	}
	
	public static String getSftpUser() {
		if (sftpUser == null || sftpUser.isEmpty()) {
			sftpUser = "";
		}

		return sftpUser;
	}
	
	public static String getSftpRemoteDir() {
		if (sftpRemoteDir == null || sftpRemoteDir.isEmpty()) {
			sftpRemoteDir = "";
		}

		return sftpRemoteDir;
	}

	private static void loadProperties() {

		Properties taProperties = new Properties();
		Properties envProperties = new Properties();

		try {

			// load a properties file
			taProperties.load(new FileInputStream(taPropertiesFile));
			envProperties.load(new FileInputStream(envPropertiesFile));

			// get the property value and print it out
			asyncWait = Integer.parseInt(taProperties.getProperty("asyncWait"));
			implicitWait = Integer.parseInt(taProperties.getProperty("implicitWait"));
			explicitWait = Integer.parseInt(taProperties.getProperty("explicitWait"));
			sqlWait = Integer.parseInt(taProperties.getProperty("sqlWait"));
			seleniumBoxUrl = taProperties.getProperty("seleniumBoxUrl");

			// get system property for specifying execution environment
			//String selectedEnvironment = System.getProperty("environment"); //for TAMan execution
			String selectedEnvironment = envProperties.getProperty("environment"); //for IntelliJ execution

			if (selectedEnvironment != null) {

				System.out.println("[INFO] Selected environment for execution: " +  selectedEnvironment);
				
				switch (selectedEnvironment.toLowerCase()) {

				case ("dev"):
					envServerUrl = envProperties.getProperty("devServerUrl");
					dbHostName = envProperties.getProperty("devDbHostName");
					dbPort = Integer.parseInt(envProperties.getProperty("devDbPort"));
					dbServiceName = envProperties.getProperty("devDbServiceName");
					dbUser = envProperties.getProperty("devDbUser");
					sftpHost = envProperties.getProperty("devSftpHost");
					sftpPort = Integer.parseInt(envProperties.getProperty("devSftpPort"));
					sftpUser = envProperties.getProperty("devSftpUser");
					sftpRemoteDir = envProperties.getProperty("devSftpRemoteDir");
					break;
				case ("test"):
					envServerUrl = envProperties.getProperty("testServerUrl");
					dbHostName = envProperties.getProperty("testDbHostName");
					dbPort = Integer.parseInt(envProperties.getProperty("testDbPort"));
					dbServiceName = envProperties.getProperty("testDbServiceName");
					dbUser = envProperties.getProperty("testDbUser");
					sftpHost = envProperties.getProperty("testSftpHost");
					sftpPort = Integer.parseInt(envProperties.getProperty("testSftpPort"));
					sftpUser = envProperties.getProperty("testSftpUser");
					sftpRemoteDir = envProperties.getProperty("testSftpRemoteDir");
					break;
				default:
					Assert.fail("[ERROR] Environment: " + selectedEnvironment + " is not configured in properties file env.properties!");
				}

			} else {
				Assert.fail("[ERROR] Environment is not defined in system variable: 'environment'!");
			}

		} catch (Exception e) {
			Assert.fail("[ERROR] An error occurred while trying to read the properties file: " + e);
		}
	}
}
