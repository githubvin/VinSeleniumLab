package plainseleniumscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HPStoreClass { 
	
	public static RemoteWebDriver driver; 

	public static void main(String[] args) throws InterruptedException {
		
		// Launching the HP Store application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.get("https://store.hp.com/in-en/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver);
		
		// To handle the Modal popup displayed intermittently 
		
		
		try {
			driver.findElementByXPath("//span[contains(@class,'close-icon')]").click();
		} catch (Exception e) { 
			System.out.println("Home page modal is not displayed.");
			e.printStackTrace();
		}
		
				
		WebElement laptopMenu = driver.findElementByXPath("//a[@role='menuitem']//span[text()='Laptops']"); 
		act.moveToElement(laptopMenu).build().perform(); 
		WebElement pavilionMenu = driver.findElementByXPath("(//a[@role='menuitem']//span[text()='Pavilion'])[1]"); 
		act.click(pavilionMenu).build().perform(); 

		try {
			driver.findElementByXPath("//div[@class='inside_closeButton fonticon icon-hclose']").click();
		} catch (Exception e) {
			System.out.println("Marketing modal not displayed.");
			e.printStackTrace();
		}
		
		// Used Javascript executor here to scroll down the window 
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.scrollBy(0, 250)");
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click(); 
		driver.findElementByXPath("//span[text()='Intel Core i7']").click(); 
		Thread.sleep(3000); 
		
		js.executeScript("window.scrollBy(0, 600)");
		driver.findElementByXPath("//span[text()='More than 1 TB']").click();
		 
		
		WebElement eleSort = driver.findElementById("sorter"); 
		Select sortOptions = new Select(eleSort); 
		sortOptions.selectByValue("price_asc"); 
		
		WebElement firstProductName = driver.findElementByXPath("(//a[@class='product-item-link'])[1]");
		System.out.println("First Product Name: " + firstProductName.getText());
		
		WebElement firstPrice = driver.findElementByXPath("(//span[contains(@class,'price-wrapper')])[1]//span[@class='price']");
		String priceString = firstPrice.getText().replaceAll("\\D", ""); 
		int priceNumber = Integer.parseInt(priceString);
		
		System.out.println("First Product Price: " + priceNumber); 
		Thread.sleep(3000);
		js.executeScript("window.scrollBy(0, 750)");
		driver.findElementByXPath("(//button[contains(@title,'Add To Cart')])[1]").click(); 
		Thread.sleep(5000); 
		
		js.executeScript("window.scrollBy(0, 300)");
		driver.findElementByXPath("//a[@title='Shopping Cart']").click(); 
		driver.findElementByXPath("//span[text()='View and edit cart']").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByName("pincode").sendKeys("625001"); 
		Thread.sleep(3000); 
		driver.findElementByXPath("//button[text()='check']").click(); 
		
		// Converting the Order Total to Integer and comparing with the previously taken price 
		WebElement eleOrderTotal = driver.findElementByXPath("//Strong[text()='Order Total']/ancestor::tr//span[@class='price']"); 
		String orderTotalString = eleOrderTotal.getText().replaceAll("\\D", ""); 
		int orderTotalNumber = Integer.parseInt(orderTotalString); 
		Thread.sleep(3000);
		
		// Comparing with the previous price 
		if(priceNumber == orderTotalNumber) {
			System.out.println("Order Total and Product Price is matching.");
			driver.findElementByXPath("(//span[text()='Proceed to Checkout'])[1]").click();
		} else {
			System.out.println("Order Total and Product Price not matching.");
		} 
		
		Thread.sleep(3000); 
		
		// Placing the order 
		js.executeScript("window.scrollBy(0, 300)"); 
		driver.findElementByXPath("//div[@class='place-order-primary']//button").click(); 
		System.out.println("Error message: " + driver.findElementByXPath("//div[@class='message notice']").getText()); 
		
		driver.close();

	}

}
