package plainseleniumscripts;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyntraClass { 
	
	public static RemoteWebDriver driver;  

	public static void main(String[] args) throws InterruptedException { 
		
		// Launching the application 
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe"); 
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("--disable-notifications"); 
		driver = new ChromeDriver(options); 
		driver.get("https://www.myntra.com/"); 
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		// Navigating to the Jackets and Coats item list from Women menu 
		Actions act = new Actions(driver);
		WebElement womenMenu = driver.findElementByXPath("(//a[text()='Women'])[1]"); 
		act.moveToElement(womenMenu).build().perform(); 
		driver.findElementByLinkText("Jackets & Coats").click(); 
		
		// Getting the Total count of Jackets and Coats 
		String rawjcStr = driver.findElementByXPath("//span[@class='title-count']").getText();
		String refinedjcStr = rawjcStr.replaceAll("\\D", ""); 
		int jcTotalCount = Integer.parseInt(refinedjcStr); 
		System.out.println("Count of Jackets and Coats: " + jcTotalCount); 
		
		// Getting the count of Jackets 
		String rawJacketStr = driver.findElementByXPath("//label[text()='Jackets']//span").getText(); 
		String refinedJacketStr = rawJacketStr.replaceAll("\\D", ""); 
		int jacketsCount = Integer.parseInt(refinedJacketStr); 
		System.out.println("Count of Jackets alone: " + jacketsCount); 
		
		// Getting the count of Coats 
		String rawCoatStr = driver.findElementByXPath("//label[text()='Coats']//span").getText(); 
		String refinedCoatStr = rawCoatStr.replaceAll("\\D", ""); 
		int coatsCount = Integer.parseInt(refinedCoatStr); 
		System.out.println("Count of Coats alone: " + coatsCount);  
		
		//Verifying the Title Count 
		if(jacketsCount + coatsCount == jcTotalCount) {
			System.out.println("Sum of Categories count matches with Total count of Jackets and Coats.");
		} else {
			System.out.println("Sum of categories not matching.");
		}
			 
		// Clicking on the Coats filter 
		driver.findElementByXPath("//label[text()='Coats']").click(); 
		
		Thread.sleep(3000);
		// Clicking the More options from Brand filter 
		driver.findElementByXPath("//div[@class='brand-more']").click();
			
		// Searching the Brand MANGO 
		driver.findElementByXPath("//input[@placeholder='Search brand']").sendKeys("MANGO"); 
		driver.findElementByXPath("(//label[text()='MANGO'])[2]").click();
		driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();
		
		// Checking the Brand and count 
		Thread.sleep(5000);
		List<WebElement> allProducts = driver.findElementsByXPath("//h3[@class='product-brand']"); 
		int mangoCount = 0; 
		for (WebElement eachProduct : allProducts) {
			if(eachProduct.getText().equals("MANGO")) {
				mangoCount = mangoCount + 1; 
			}
		}
		
		if(mangoCount == allProducts.size()) {
			System.out.println("All products are MANGO brand.");
		} else {
			System.out.println("Displaying other brands too.");
		}
		
		// Sorting the items in the list 
		act.moveToElement(driver.findElementByClassName("sort-sortBy")).build().perform(); 
		driver.findElementByXPath("//label[text()='Better Discount']").click(); 
		
		Thread.sleep(5000);
		// Getting the Price of First displayed item 
		List<WebElement> allPrice = driver.findElementsByXPath("//span[@class='product-discountedPrice']"); 
		System.out.println("Discounted Price of the first item: " + allPrice.get(0).getText());
		
		// Clicking on the Wishlist button of first item 
		act.moveToElement(driver.findElementByClassName("product-productMetaInfo")).build().perform();
		driver.findElementByXPath("(//span[text()='wishlist now'])[1]").click(); 
		
		//driver.close();
		
	}

}
