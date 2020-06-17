package plainseleniumscripts;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PepperfryClass { 
	
	public static RemoteWebDriver driver; 

	public static void main(String[] args) throws InterruptedException, IOException {
		
		// Launching the application 
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");  
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		
		driver = new ChromeDriver(options);
		driver.get("https://www.pepperfry.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 

		// Selecting Office Chairs 
		Actions act = new Actions(driver); 
		act.moveToElement(driver.findElementByLinkText("Furniture")).build().perform(); 
		act.click(driver.findElementByLinkText("Office Chairs")).build().perform(); 
		Thread.sleep(5000);
		
		// Clicking and exploring Executive Chairs 
		driver.findElementByXPath("//div[@class='cat-wrap-ttl']//h5[text()='Executive Chairs']").click(); 
		Thread.sleep(5000); 
		
		// Setting the Height filter 
		driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]").clear(); 
		driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]").sendKeys("50", Keys.ENTER); 
		
		Thread.sleep(8000);
		
		try {
			driver.findElementByXPath("//div[@id='regPopUp']//a[@class='popup-close']").click(); 
			System.out.println("Login popup closed.");
		} catch (Exception e) {
			System.out.println("Login popup not found.");
			e.printStackTrace();
		}
		
		// Adding to Wishlist 
		driver.findElementByXPath("//a[contains(@data-productname,'Poise Executive Chair in Black Colour')]").click(); 
		
		// Moving to Homeware 
		act.moveToElement(driver.findElementByLinkText("Homeware")).build().perform(); 
		act.click(driver.findElementByLinkText("Pressure Cookers")).build().perform(); 
		Thread.sleep(5000); 
		
		driver.findElementByXPath("//label[text()='Prestige']").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//label[text()='1 Ltr - 3 Ltr']").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//a[@data-productname='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']").click(); 
		Thread.sleep(3000); 
		
		// Validating the Wishlist count 
		String actualWishlist = driver.findElementByXPath("//a[@data-tooltip='Wishlist']/following::span[@class='count_alert'][1]").getText(); 
		int wishListNumber = Integer.parseInt(actualWishlist); 
		
		if(wishListNumber == 2) { 
			System.out.println("Wishlist has 2 items."); 
		} else { 
			System.out.println("Wishlist not updated properly.");
		}
		
		// Navigating to Wishlist 
		driver.findElementByXPath("//a[@data-tooltip='Wishlist']").click(); 
		Thread.sleep(3000); 
		
		// Adding the Pressure Cooker alone to the Cart 
		driver.findElementByXPath("//a[@data-tooltip='Compact view']").click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr By...']/following::a[@class='addtocart_icon']"))); 
		driver.findElementByXPath("//a[text()='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr By...']/following::a[@class='addtocart_icon']").click(); 
		Thread.sleep(2000); 
		
		driver.findElementById("mini-usercart-tab").click(); 
		
		driver.findElementByXPath("//a[contains(text(),'Proceed to pay securely')]").click(); 
		Thread.sleep(3000);
		
		driver.findElementById("pin_code").sendKeys("625001"); 
		driver.findElementById("pin_check").click(); 
		
		if (driver.findElementByXPath("//span[text()='Delivery by']").isDisplayed()) {
			System.out.println("Delivery is available for the given location."); 
		} else { 
			System.out.println("Delivery is not available."); 
		}
		
		driver.findElementByXPath("(//a[text()='PLACE ORDER'])[1]").click(); 
		Thread.sleep(3000);
		
		driver.findElementByXPath("//span[text()='ORDER SUMMARY']").click(); 
		
		
		// Taking screenshot of that particular item
		File src = driver.findElementByXPath("//div[@class='slick-list draggable']//li").getScreenshotAs(OutputType.FILE); 
		File dest = new File("./screens/PepperfryItem.jpg"); 
		FileUtils.copyFile(src, dest);
		
		driver.close();
		
	}

}
