package plainseleniumscripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AjioClass { 
	
	public static RemoteWebDriver driver; 
	
	public String pickWindow() { 
		Set<String> winSet = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(winSet); 
		int size = winList.size(); 
		return winList.get(size-1); 
	}

	public static void main(String[] args) throws InterruptedException { 
		
		AjioClass obj = new AjioClass(); 
		
		// Launching the application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);

		driver = new ChromeDriver(options);
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Actions act = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		
		// This block is consuming time initially 
		try {
			driver.findElementByXPath("//div[@class='ic-close-quickview']").click();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		// Searching Bags 
		driver.findElementByName("searchVal").sendKeys("Bags"); 
		Thread.sleep(3000); 
		act.click(driver.findElementByXPath("//span[text()='Bags in ']//following-sibling::span[text()='Women Handbags']/parent::a")).build().perform(); 
		Thread.sleep(5000); 
		
		// Clicking the Five Grid 
		driver.findElementByXPath("//div[@class='five-grid']").click(); 
		
		// Sorting by Whats New 
		WebElement sortElement = driver.findElementByXPath("//div[@class='filter-dropdown']//select"); 
		Select sortOptions = new Select(sortElement); 
		sortOptions.selectByValue("newn"); 
		
		driver.findElementByXPath("//span[text()='price']").click(); 
		driver.findElementById("minPrice").sendKeys("2000"); 
		driver.findElementById("maxPrice").sendKeys("5000"); 
		driver.findElementByXPath("//div[@class='input-price-filter']//button[@type='submit']").click(); 
		Thread.sleep(3000); 
		
		// Selecting the Puma bag 
		driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']").click(); 
		Thread.sleep(2000); 
		
		// Switching to the new window 
		driver.switchTo().window(obj.pickWindow()); 
		Thread.sleep(3000); 
		
		// Validating the coupon available 
		String productPriceStr = driver.findElementByXPath("//div[@class='prod-sp']").getText(); 
		int productPrice = Integer.parseInt(productPriceStr.replaceAll("\\D", "")); 
		
		String couponStr = driver.findElementByXPath("//div[@class='promo-desc']").getText(); 
		
		int discountedPrice = 0; 
		int savings = 0; 
		
		if (couponStr.contains("2690")) { 
			String coupon = driver.findElementByXPath("//div[@class='promo-title']").getText(); 
			System.out.println("\nCoupon code: " + coupon);
			String discountedPriceStr = driver.findElementByXPath("//div[@class='promo-discounted-price']").getText(); 
			discountedPrice = Integer.parseInt(discountedPriceStr.replaceAll("\\D", "")); 
			System.out.println("Discounted Price details: " + discountedPrice); 
		}
		
		savings = productPrice - discountedPrice; 
		System.out.println("Savings amount: " + savings); 
		
		// Checking the availability 
		driver.findElementByXPath("//span[@class='ic-address edd-pincode-msg-address-icon']//following-sibling::span").click(); 
		Thread.sleep(3000); 
		driver.findElementByName("pincode").sendKeys("560043"); 
		driver.findElementByClassName("edd-pincode-modal-submit-btn").click(); 
		Thread.sleep(3000); 
		
		String expDelivery = driver.findElementByXPath("//li[contains(text(),'Expected Delivery')]//span").getText(); 
		System.out.println("Expected Delivery Date: " + expDelivery); 
		
		driver.findElementByClassName("other-info-toggle").click();
		
		// Printing the Product Details 
		List<WebElement> prodList = driver.findElementsByXPath("//ul[@class='prod-list']//li"); 
		
		System.out.println("\nProduct Details:");
		for (int i = 0; i < prodList.size(); i++) { 
			String productDetails = prodList.get(i).getText(); 
			System.out.println(productDetails);
		} 
		
		// Adding to Bag 
		driver.findElementByClassName("ic-pdp-add-cart").click(); 
		Thread.sleep(5000); 
		
		act.moveToElement(driver.findElementByXPath("//div[@class='ic-cart ']")).build().perform(); 
		act.click(driver.findElementByClassName("mini-cart-btn")).build().perform(); 
		Thread.sleep(3000);
		
		// Verifying the Total 
		String orderTotalStr = driver.findElementByXPath("//span[text()='Order Total']//following-sibling::span").getText(); 
		double orderTotalDouble = Double.parseDouble(orderTotalStr.replaceAll("\\D", "")); 
		System.out.println("\nOrder Total from the checkout page: " + orderTotalDouble);
		
		// Converting the previous int to double 
		double productPriceDouble = (double)productPrice; 
		
		
		if (orderTotalDouble == productPriceDouble) { 
			System.out.println("Order Total is correct.");
		} else { 
			System.out.println("Incorrect Order Total.");
		}
		
		// Applying coupon 
		driver.findElementById("EPIC").click(); 
		driver.findElementByXPath("//button[text()='Apply']").click(); 
		Thread.sleep(3000); 
		
		// Verifying the savings in the checkout page 
		String savingsAmountStr = driver.findElementByXPath("//span[@class='cart-total-saving-text']").getText();
		savingsAmountStr = savingsAmountStr.replaceAll("[^0-9.]", ""); 
		
		String[] amountSplit = savingsAmountStr.split(".", 2); 
		
		double savingsDouble = Double.parseDouble(amountSplit[1]); 
		
		// Rounding off the Savings Double and converting to int 
		int roundOffSavings = (int)Math.round(savingsDouble); 
		System.out.println(roundOffSavings);
		
		System.out.println("Savings from the checkout page: " + savingsDouble);
		 
		// Comparing the two Integer savings 
		if (roundOffSavings == savings) { 
			System.out.println("Savings Amount matches.");
		} else { 
			System.out.println("Incorrect Savings."); 
		}
		
		// Deleting the item from the bag 
		driver.findElementByClassName("delete-btn").click(); 
		driver.findElementByXPath("//div[text()='DELETE']").click(); 
		Thread.sleep(3000); 
		
		driver.quit(); 
		
		

	}

}
