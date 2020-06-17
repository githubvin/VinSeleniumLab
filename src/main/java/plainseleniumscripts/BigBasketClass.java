package plainseleniumscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BigBasketClass { 
	
	public static RemoteWebDriver driver; 

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		//System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications"); 
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 
		
		driver = new ChromeDriver(options);
		driver.get("https://www.bigbasket.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		
		act.moveToElement(driver.findElementByXPath("//a[contains(text(),'Shop by')]")).build().perform(); 
		act.moveToElement(driver.findElementByXPath("(//a[contains(text(),'Foodgrains, Oil & Masala')])[2]")).build().perform(); 
		act.click(driver.findElementByXPath("(//a[contains(text(),'Rice & Rice Products')])[2]")).build().perform(); 
		Thread.sleep(5000);
		
		driver.findElementByXPath("(//span[text()='Boiled & Steam Rice'])[1]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("(//label//span[text()='bb Royal'])[1]").click(); 
		Thread.sleep(3000);
		driver.findElementByXPath("(//a[contains(text(),'Ponni Boiled Rice - Super')]/following::button[@type='button'])[1]").click(); 
		driver.findElementByXPath("(//ul[@class='dropdown-menu drop-select']//span[text()='5 kg'])[3]").click(); 
		
		String ricePriceText = driver.findElementByXPath("(//a[contains(text(),'Ponni Boiled Rice - Super')]/following::span[@class='discnt-price']//span)[1]").getText(); 
		System.out.println("Price of the Rice Pack: " + ricePriceText);
		int ricePriceNumber = Integer.parseInt(ricePriceText); 
		
		driver.findElementByXPath("//a[contains(text(),'Ponni Boiled Rice - Super Premium')]/following::button[@qa='add'][1]").click(); 
		
		
		try {
			driver.findElementByLinkText("Continue").click();
		} catch (Exception e) {
			System.out.println("Location popup not displayed.");
			e.printStackTrace();
		}
		
		///System.out.println(driver.findElementByClassName("toast-title").getText());
		
		/*
		 * if (driver.findElementByClassName("toast-title").getText().
		 * contains("Successfully added Ponni Boiled Rice - Super Premium 5 kg to the basket"
		 * )) { System.out.println("Rice Product added successfully."); } else {
		 * System.out.println("Rice not added."); }
		 */
		
		Thread.sleep(3000);
		
		driver.findElementByXPath("//input[@qa='searchBar']").sendKeys("Dal", Keys.ENTER);
		Thread.sleep(5000);
		
		driver.findElementByXPath("(//a[contains(text(),'Thuvaram Paruppu')])[1]/following::span[2]").click();
		driver.findElementByXPath("(//a[contains(text(),'Thuvaram Paruppu')])[1]/following::span[2]/following::span[text()='2 kg'][1]").click();
		
		driver.findElementByXPath("(//a[contains(text(),'Thuvaram Paruppu')])[1]/following::input[@type='text'][1]").clear(); 
		driver.findElementByXPath("(//a[contains(text(),'Thuvaram Paruppu')])[1]/following::input[@type='text'][1]").sendKeys("2"); 
		
		String dalPriceText = driver.findElementByXPath("((//a[contains(text(),'Thuvaram Paruppu')])[1]/following::span[@class='discnt-price']//span)[1]").getText(); 
		System.out.println("Price of Dal: " + dalPriceText); 
		int dalPriceNumber = Integer.parseInt(dalPriceText); 
		
		// Adding to the Cart 
		driver.findElementByXPath("((//a[contains(text(),'Thuvaram Paruppu')])[1]/following::span[@class='discnt-price']//span/following::button)[1]").click();
		
		try {
			driver.findElementByXPath("//div[@class='toast toast-success']//div[@class='toast-title']").getText();
		} catch (Exception e) {
			System.out.println("Success message not displayed.");
			e.printStackTrace();
		}
		
		Thread.sleep(5000);
		
		act.moveToElement(driver.findElementById("totalNumberOfCartItems")).build().perform(); 
		
		Thread.sleep(5000);
		
		// Manipulating the Price details and validating 
		String riceString = driver.findElementByXPath("(//div[@qa='pcsMB'])[1]").getText(); 
		String riceQtyStr = riceString.substring(0, 1);  
		double riceQtyNumber = Double.parseDouble(riceQtyStr); 
		System.out.println("Quantity of Rice: " + riceQtyNumber);
		 
		String dalQtyStr = driver.findElementByXPath("(//div[@qa='pcsMB'])[2]").getText();
		dalQtyStr = dalQtyStr.substring(0, 1);
		double dalQtyNumber = Double.parseDouble(dalQtyStr); 
		System.out.println("Quantity of Dal: " + dalQtyNumber);

		String ricePriceCart = driver.findElementByXPath("(//span[@qa='priceMB'])[1]").getText();
		double riceTotal = Double.parseDouble(ricePriceCart); 
		riceTotal = riceTotal * riceQtyNumber;

		String dalPriceCart = driver.findElementByXPath("(//span[@qa='priceMB'])[2]").getText();
		double dalTotal = Double.parseDouble(dalPriceCart); 
		dalTotal = dalTotal * dalQtyNumber;

		String subTotalStr = driver.findElementByXPath("//span[@qa='subTotalMB']").getText();
		double subTotalNum = Double.parseDouble(subTotalStr); 
		
		Thread.sleep(3000);

		if (subTotalNum == riceTotal + dalTotal) {
			System.out.println("Sub Total is correct: " + subTotalNum);
		} else {
			System.out.println("Sub Total is wrong: " + subTotalNum);
		}

		Thread.sleep(3000);
		
		// Reducing the quantity of Dal to 1
		driver.findElementByXPath("(//button[@qa='decQtyMB'])[2]").click();
		Thread.sleep(3000);

		// Again getting the Dal quantity and price to validate the sub total 
		String dalQtyStr2 = driver.findElementByXPath("(//div[@qa='pcsMB'])[2]").getText();
		dalQtyStr = dalQtyStr2.substring(0, 1);
		double dalQtyNumber2 = Double.parseDouble(dalQtyStr); 
		System.out.println("Quantity of Dal after change: " + dalQtyNumber2);

		String dalPriceCart2 = driver.findElementByXPath("(//span[@qa='priceMB'])[2]").getText();
		double dalTotal2 = Double.parseDouble(dalPriceCart2); 
		dalTotal2 = dalTotal2 * dalQtyNumber2;

		String subTotalStr2 = driver.findElementByXPath("//span[@qa='subTotalMB']").getText();
		double subTotalNum2 = Double.parseDouble(subTotalStr2); 
		
		Thread.sleep(3000);

		if (subTotalNum2 == riceTotal + dalTotal2) {
			System.out.println("Sub Total is correct after change: " + subTotalNum2);
		} else {
			System.out.println("Sub Total is wrong: " + subTotalNum2);
		}
		
		driver.close();
		
	}

}
