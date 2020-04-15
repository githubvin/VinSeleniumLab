package plainseleniumscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
 
public class NykaaClass { 
	
	public static RemoteWebDriver driver; 

	public static void main(String[] args) { 
		
		// Launching the Nykaa application 
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe"); 
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("--disable-notifications"); 
		driver = new ChromeDriver(options); 
		driver.get("https://www.nykaa.com/"); 
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); 
		
		// Selection the L'Oreal Paris brand 
		

	}

}
