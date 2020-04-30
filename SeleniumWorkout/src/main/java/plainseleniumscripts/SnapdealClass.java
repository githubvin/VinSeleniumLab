package plainseleniumscripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SnapdealClass { 
	
	public static RemoteWebDriver driver; 
	
	public String pickWindow() {
		Set<String> winSet = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(winSet);
		int size = winList.size(); 
		return winList.get(size-1); 
	}

	public static void main(String[] args) throws InterruptedException { 
		
		// Creating object for the other method 
		SnapdealClass hand = new SnapdealClass(); 
		
		// Launching the application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);

		driver = new ChromeDriver(options);
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		WebDriverWait wait = new WebDriverWait(driver, 15);  
		
		act.moveToElement(driver.findElementByLinkText("Toys, Kids' Fashion & more")).build().perform(); 
		act.click(driver.findElementByXPath("//span[text()='Toys']")).build().perform();
		Thread.sleep(3000); 
		
		// Clicking on Educational Toys 
		driver.findElementByXPath("//div[text()='Educational Toys']").click();  
		Thread.sleep(2000); 
		
		driver.findElementByXPath("//label[@for='avgRating-4.0']").click(); 
		Thread.sleep(5000); 
		
		act.moveToElement(driver.findElementByXPath("//label[@for='discount-40%20-%2050']//a")).build().perform(); 
		act.click(driver.findElementByXPath("//label[@for='discount-40%20-%2050']//a")).build().perform(); 
		Thread.sleep(3000);
		
		driver.findElementByXPath("//input[@placeholder='Enter your pincode']").sendKeys("625001"); 
		driver.findElementByXPath("//button[text()='Check']").click(); 
		
		if (driver.findElementByXPath("//span[contains(text(),'Deliver to:')]").isDisplayed()) {
			System.out.println("Delivery is available for the given pincode");
		} else { 
			System.out.println("Delivery not available.");
		} 
		
		// Clicking the Quick View of the first product 
		act.moveToElement(driver.findElementByXPath("(//p[@class='product-title'])[1]")).build().perform();
		Thread.sleep(2000);
		//act.click(driver.findElementByXPath("(//div[contains(text(),'Quick View')])[1]")).build().perform();
		driver.findElementByXPath("(//div[contains(text(),'Quick View')])[1]").click(); 
		
		// Viewing the details of the product 
		driver.findElementByXPath("//a[contains(text(),'view details')]").click(); 
		Thread.sleep(3000); 
		
		String priceStr = driver.findElementByXPath("//span[@itemprop='price']").getText(); 
		int price = Integer.parseInt(priceStr); 
		System.out.println("Price of the item: " + price); 
		
		String deliveryChargeStr = driver.findElementByXPath("(//div[@id='deliveryOptionsTooltip']//following::span[@class='availCharges'])[1]").getText(); 
		int deliveryCharges = Integer.parseInt(deliveryChargeStr.replaceAll("\\D", "")); 
		System.out.println("Delivery Charges: " + deliveryCharges);
		
		driver.findElementById("add-cart-button-id").click(); 
		Thread.sleep(3000); 
		
		String totalPriceStr = driver.findElementByXPath("//div[@class='you-pay']//span[@class='price']").getText(); 
		int totalPrice = Integer.parseInt(totalPriceStr.replaceAll("\\D", "")); 
		
		if (totalPrice == price + deliveryCharges) {
			System.out.println("Total calculated correctly for first product." + totalPrice);
		} else { 
			System.out.println("Incorrect total calculation for first product.");
		}
		
		driver.findElementById("inputValEnter").sendKeys("Sanitizer", Keys.ENTER);
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//p[@title='BioAyurveda Neem Power  Hand Sanitizer 500 mL Pack of 1']").click(); 
		
		// Switching to the new tab 
		driver.switchTo().window(hand.pickWindow()); 
		Thread.sleep(5000);
		
		String bioPriceStr = driver.findElementByXPath("//span[@itemprop='price']").getText(); 
		int bioPrice = Integer.parseInt(bioPriceStr); 
		System.out.println("Price of the item: " + bioPrice); 
		
		String bioDeliveryChargeStr = driver.findElementByXPath("(//div[@id='deliveryOptionsTooltip']//following::span[@class='availCharges'])[1]").getText(); 
		int bioDeliveryCharges = Integer.parseInt(bioDeliveryChargeStr.replaceAll("\\D", "")); 
		System.out.println("Delivery Charges: " + bioDeliveryCharges);
		
		// Total price of the Bio Sanitizer 
		int bioTotal = bioPrice + bioDeliveryCharges; 
		Thread.sleep(3000);
		
		driver.findElementById("add-cart-button-id").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//span[@class='cartQuantity']").click(); 
		Thread.sleep(3000); 
		
		String grandTotalStr = driver.findElementByXPath("//form[@id='checkout-continue']//input[2]").getAttribute("value"); 
		int grandTotal = Integer.parseInt(grandTotalStr.replaceAll("\\D", "")); 
		System.out.println("Grand Total to pay: " + grandTotal); 
		
		if (grandTotal == totalPrice + bioTotal) { 
			System.out.println("Proceed to Pay total is correct.");
		} else { 
			System.out.println("Incorrect total in Proceed to Pay.");
		}
		
		driver.quit(); 

	}

}
