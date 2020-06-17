package plainseleniumscripts;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

public class PostmanClass {

	public static void main(String[] args) throws InterruptedException, IOException {

		
		/***** THIS IS INCOMPLETE ***************  
		 * 
		 * Here to invoke the desktop application we are using Winium WebDriver a third party API. 
		 * 
		 * Postman is designed in Electron JS and behaves almost like Chrome Browser except for the invoke part. 
		 * 
		 * So we can inspect the elements by using normal methods. 
		 * 
		 * Normally to handle the desktop applications we need Winium Desktop Element and any GUI inspect tool. 
		 * 
		 */ 
		
		/* 
		 * We can use Winium Driver to launch the desktop applications 
		 * 
		 * Here in Postman since the DOM behaves like Chrome Browser we are passing the options in Chrome Driver. 
		 * 
		 * 
		 */
		
		String appLocation = "C:\\Users\\vinvi\\AppData\\Local\\Postman\\Postman.exe";

		DesktopOptions option = new DesktopOptions();

		option.setApplicationPath(appLocation);

		URL url = new URL("http://localhost:9999");

		// This is to start the Winium Desktop Driver server 
		File drivePath = new File("./drivers/Winium.Desktop.Driver.exe");
		WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(drivePath).usingPort(9999)
				.buildDesktopService();
		service.start();

		RemoteWebDriver driver = new WiniumDriver(url, option);

		Thread.sleep(5000);
		
		Actions act = new Actions(driver); 
		
		WebDriverWait wait = new WebDriverWait(driver, 50); 
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//div[@class='btn btn-primary'])[1]"))); 
		Thread.sleep(500); 
		driver.findElementByXPath("(//div[@class='btn btn-primary'])[2]").click(); 
		
		act.click(driver.findElementByXPath("//span[text()='Collection']")).build().perform(); 
		
		driver.findElementByXPath("//input[@placeholder='Collection Name']").sendKeys("RestLearning"); 
		
		driver.findElementByXPath("//div[@class='ace_empty_message']").sendKeys("Test");
		
		driver.findElementByXPath("//div[@class='modal-footer collection-modal-footer']//div[@class='btn btn-primary']").click(); 
		
		
		
	}

}
