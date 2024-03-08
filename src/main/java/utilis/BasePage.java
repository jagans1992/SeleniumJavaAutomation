package utilis;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public static WebDriver driver;
	public static ResourceBundle env = ResourceBundle.getBundle("global");

	public static String host;
	
	public static WebDriver webDriver() throws IOException {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"//src/test//resources//global.properties");
		Properties prop = new Properties();
		prop.load(file);
		String browserProperties = prop.getProperty("browser");
		String browserMaven = System.getProperty("browser");
		String browser = browserMaven != null ? browserMaven : browserProperties;
		
		if(driver==null) {
			if(browser.contains("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				WebDriverManager.chromedriver().setup();
				if(browser.contains("headless")) {
					options.addArguments("headless");
				}
				driver= new ChromeDriver(options);
				driver.manage().window().setSize(new Dimension(1440, 990));
			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().setSize(new Dimension(1440, 700));
		}
		return driver;
	}
	
	public static String getHost() {
		String envGlobal = env.getString("environment");
		String envMaven = System.getProperty("environment");
		String currentEnv = envMaven != null ? envMaven : envGlobal;
		if(currentEnv.equals("stage")) {
			host = env.getString("stagehost");
		} else if(currentEnv.equals("live")) {
			host = env.getString("livehost");
		} else if(currentEnv.equals("qa")) {
			host = env.getString("qahost");
		}
		return host;
	}
}
