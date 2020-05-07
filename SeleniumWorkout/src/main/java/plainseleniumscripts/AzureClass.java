package plainseleniumscripts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AzureClass { 
	
	public static RemoteWebDriver driver; 

	public static void main(String[] args) throws InterruptedException {
		
		// Launching the application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);

		driver = new ChromeDriver(options);
		driver.get("https://azure.microsoft.com/en-in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Actions act = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		driver.findElementByLinkText("Pricing").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click(); 
		driver.findElementByXPath("//button[@value='containers']").click(); 
		driver.findElementByXPath("(//button[@data-event-property='container-instances'])[2]").click(); 
		driver.findElementById("new-module-loc").click(); 
		Thread.sleep(3000);
		
		// Setting the Region 
		WebElement regionElement = driver.findElementByName("region"); 
		Select regionOptions = new Select(regionElement); 
		regionOptions.selectByValue("south-india"); 
		Thread.sleep(3000);
		
		driver.findElementByXPath("//h5[text()='Duration']/following::div[2]/input").sendKeys(Keys.chord(Keys.CONTROL,"a"),"180000");
		Thread.sleep(3000);
		
		// Selection container Memory 
		WebElement memoryElement = driver.findElementByName("memory"); 
		Select memoryOptions = new Select(memoryElement); 
		memoryOptions.selectByValue("4"); 
		Thread.sleep(3000);
		
		// Toggle Dev/Test pricing 
		driver.findElementById("devtest-toggler").click(); 
		
		// Setting the currency to INR 
		WebElement currencyElement = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currencyOptions = new Select(currencyElement);
		currencyOptions.selectByValue("INR"); 
		
		// Printing the Monthly price estimate 
		String estimatedMonthlyPrice = driver.findElementByXPath("(//select[@class='select currency-dropdown']/parent::div//preceding-sibling::div[1]//span)[2]").getText(); 
		System.out.println("\nMonthly Price estimate: " + estimatedMonthlyPrice); 
		
		// Exporting the estimate 
		driver.findElementByXPath("//button[text()='Export']").click(); 
		Thread.sleep(5000);
		
		// Validating the file is downloaded or not 
		String downloadPath = "C:\\Users\\vinvi\\Downloads"; 
		String fileName = "ExportedEstimate.xlsx"; 
		
		File targetFile = new File(downloadPath, fileName); 
		
		if (targetFile.exists()) {
			System.out.println("File is available.");
		} else { 
			System.out.println("File not present.");
		}
		
		// Since second download also has same file name deleting this file to validating in the next steps 
		targetFile.delete(); 
		
		js.executeScript("window.scrollTo(0, document.body.scrollTop)");  
		
		// Navigating to Example Scenarios 
		driver.findElementByLinkText("Example Scenarios").click(); 
		Thread.sleep(3000);
		driver.findElementByXPath("//span[text()='CI/CD for Containers']").click(); 
		Thread.sleep(2000);
		
		js.executeScript("window.scrollBy(0,400)");  
		driver.findElementByXPath("//button[text()='Add to estimate']").click(); 
		Thread.sleep(5000); 
		
		js.executeScript("window.scrollBy(0, 900)"); 
		
		// Setting the currency to INR here 
		WebElement currencyElement2 = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currencyOptions2 = new Select(currencyElement2);
		currencyOptions2.selectByValue("INR");  
		Thread.sleep(5000);
		
		// Toggle Dev/Test pricing 
		driver.findElementById("devtest-toggler").click(); 
		
		// Exporting the Estimate again 
		driver.findElementByXPath("//button[text()='Export']").click(); 
		Thread.sleep(5000); 
		
		// Validating the downloaded file again 
		if (targetFile.exists()) {
			System.out.println("File is available.");
		} else { 
			System.out.println("File not present.");
		}
		
		
		driver.close();
		

	}

}
