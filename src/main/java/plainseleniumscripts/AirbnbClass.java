package plainseleniumscripts;

import static org.testng.Assert.expectThrows;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import org.openqa.selenium.support.ui.WebDriverWait;

public class AirbnbClass { 
	
	public static RemoteWebDriver driver; 
	
	public String pickWindow() { 
		Set<String> winSet = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(winSet); 
		int size = winList.size(); 
		return winList.get(size-1); 
	}

	public static void main(String[] args) throws Exception { 
		
		AirbnbClass obj = new AirbnbClass(); 

		// Launching the application 
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe"); 
		System.setProperty("webdriver.chrome.silentOutput", "true"); 
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("--disable-notifications"); 
		
		DesiredCapabilities cap = new DesiredCapabilities(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS); 
		options.merge(cap); 
		
		driver = new ChromeDriver(options);
		driver.get("https://www.airbnb.co.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		
		// Trying to close the cookies popup from the website 
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//button[@title='OK']"))); 
		Thread.sleep(500);
		try {
			driver.findElementByXPath("//button[@title='OK']").click();
		} catch (Exception e) { 
			System.out.println("Cookies popup not displayed.");
			e.printStackTrace();
		}
		
		// Entering the location and searching further 
		driver.findElementById("bigsearch-query-attached-query").sendKeys("Coorg"); 
		wait.until(ExpectedConditions.visibilityOf(driver.findElementById("Koan-query__listbox"))); 
		Thread.sleep(500);
		
		act.click(driver.findElementByXPath("//div[text()='Coorg, Karnataka']")).build().perform(); 
		
		// Selecting the Checkin and Checkout dates 
		driver.findElementByXPath("(//div[@aria-roledescription='datepicker']//div[@data-visible='true'])[2]//td[@aria-disabled='false']//div[text()='1']").click(); 
		driver.findElementByXPath("(//div[@aria-roledescription='datepicker']//div[@data-visible='true'])[2]//td[@aria-disabled='false']//div[text()='5']").click(); 
		
		// Adding Guests 
		driver.findElementByXPath("//div[text()='Add guests']").click(); 
		Thread.sleep(3000);
		
		// Adding adults using For loop   
		for (int i = 0; i < 6; i++) {
			driver.findElementByXPath("//div[@id='stepper-adults']//button[@aria-label='increase value']").click();
			Thread.sleep(1000);
		}
		
		
		/*
		 * do { driver.
		 * findElementByXPath("//div[@id='stepper-adults']//button[@aria-label='increase value']"
		 * ).click(); Thread.sleep(3000); } while
		 * (driver.findElementByXPath("//div[@id='stepper-adults']//span[text()='6']").
		 * isDisplayed());
		 */
		
		// Adding Children using For loop 
		for (int j = 0; j < 3; j++) {
			driver.findElementByXPath("//div[@id='stepper-children']//button[@aria-label='increase value']").click();
			Thread.sleep(1000);
		}
		
		
		/*
		 * do { driver.
		 * findElementByXPath("//div[@id='stepper-children']//button[@aria-label='increase value']"
		 * ).click(); Thread.sleep(3000); } while
		 * (driver.findElementByXPath("//div[@id='stepper-children']//span[text()='3']")
		 * .isDisplayed());
		 */
		
		driver.findElementByXPath("//button[@type='submit']").click(); 
		Thread.sleep(7000); 
		
		// Applying filters on the results 
		driver.findElementByXPath("//div[@id='menuItemButton-flexible_cancellation']//button").click(); 
		driver.findElementById("filterItem-switch-flexible_cancellation-true").click(); 
		driver.findElementById("filter-panel-save-button").click(); 
		Thread.sleep(6000); 
		
		driver.findElementByXPath("//div[@id='menuItemButton-room_type']//button").click(); 
		Thread.sleep(2000);
		driver.findElementByXPath("//div[text()='Entire place']").click(); 
		Thread.sleep(2000);
		driver.findElementById("filter-panel-save-button").click(); 
		Thread.sleep(5000); 
		
		driver.findElementByXPath("//div[@id='menuItemButton-price_range']//button").click(); 
		driver.findElementById("price_filter_min").sendKeys(Keys.CONTROL, "a", Keys.DELETE); 
		Thread.sleep(2000);
		driver.findElementById("price_filter_min").sendKeys("3000"); 
		driver.findElementById("price_filter_max").sendKeys(Keys.CONTROL, "a", Keys.DELETE); 
		Thread.sleep(2000);
		driver.findElementById("price_filter_max").sendKeys("5000"); 
		driver.findElementById("filter-panel-save-button").click();
		Thread.sleep(5000); 
		
		// Adding more filters 
		driver.findElementByXPath("//div[@id='menuItemButton-dynamicMoreFilters']//button").click(); 
		Thread.sleep(3000);
		
		// Adding number of Bedrooms 
		for (int k = 0; k < 3; k++) {
			driver.findElementByXPath("//div[@id='filterItem-stepper-min_bedrooms-0']//button[@aria-label='increase value']").click(); 
			Thread.sleep(1000);
		}
		
		/*
		 * do { driver.
		 * findElementByXPath("//div[@id='filterItem-stepper-min_bedrooms-0']//button[@aria-label='increase value']"
		 * ).click(); } while (driver.findElementByXPath(
		 * "//div[@id='filterItem-stepper-min_bedrooms-0']//span[text()='3']").
		 * isDisplayed());
		 */
		
		// Adding Bathrooms 
		for (int r = 0; r < 3; r++) {
			driver.findElementByXPath("//div[@id='filterItem-stepper-min_bathrooms-0']//button[@aria-label='increase value']").click(); 
			Thread.sleep(1000);
		}
		
		/*
		 * do { driver.
		 * findElementByXPath("//div[@id='filterItem-stepper-min_bathrooms-0']//button[@aria-label='increase value']"
		 * ).click(); } while (driver.findElementByXPath(
		 * "//div[@id='filterItem-stepper-min_bathrooms-0']//span[text()='3']").
		 * isDisplayed());
		 */
		
		driver.findElementByXPath("//div[text()='Kitchen']").click(); 
		Thread.sleep(2000);
		driver.findElementByXPath("//div[text()='Free parking on premises']").click(); 
		Thread.sleep(2000);
		driver.findElementByXPath("//div[text()='House']").click(); 
		Thread.sleep(2000);
		driver.findElementByXPath("//div[text()='English']").click(); 
		Thread.sleep(2000);
		
		// Verifying minimum 1 Stay with the button caption 
		String minStay = driver.findElementByXPath("(//footer//button)[2]").getText();
		int minStayNumber = Integer.parseInt(minStay.replaceAll("\\D", "")); 
		
		if (minStayNumber >= 1) {
			driver.findElementByXPath("(//footer//button)[2]").click(); 
		} else { 
			System.out.println("No Stay available.");
		}
		
		Thread.sleep(2000);
		// Selecting Prahari Nivas 
		driver.findElementByXPath("(//a[@aria-label='Prahari Nivas, the complete house'])[1]").click(); 
		
		Thread.sleep(3000);
		// Switching to new window 
		driver.switchTo().window(obj.pickWindow()); 
		Thread.sleep(5000);
		
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//*[contains(text(),'amenities')])[1]")));
		Thread.sleep(1000);
		driver.findElementByXPath("(//*[contains(text(),'amenities')])[1]").click(); 
		Thread.sleep(3000);
		
		// Printing Not included amenities 
		List<WebElement> unavailableAmenities = driver.findElementsByXPath("//div[text()='Not included']/parent::h2/following-sibling::div//span[contains(text(),'Unavailable')]/following-sibling::del"); 
		
		System.out.println("\nNot Included Amenities: ");
		for (WebElement eachUnavailable : unavailableAmenities) {
			System.out.println(eachUnavailable.getText());
		}
		
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.findElementByXPath("//button[@aria-label='Close' and @type='button']").click();
		} catch (Exception e) {
			driver.findElementByXPath("(//button[@aria-label='Close'])[2]").click();
			throw e;  
		} 
		
		// Verifying the Checkin and Checkout Dates 
		String checkinDate = driver.findElementByXPath("//input[@id='checkin']/following-sibling::div").getText(); 
		String checkoutDate = driver.findElementByXPath("//input[@id='checkout']/following-sibling::div").getText();
		
		System.out.println("\nCheckin Checkout details: ");
		if (checkinDate.equalsIgnoreCase("6/1/2020")) {
			System.out.println("Checkin Date is correct. " + checkinDate);
		} else { 
			System.out.println("Incorrect Checkin Date. " + checkinDate);
		}
		
		if (checkoutDate.equalsIgnoreCase("6/5/2020")) {
			System.out.println("Checkout Date is correct. " + checkoutDate);
		} else { 
			System.out.println("Incorrect Checkout Date. " + checkoutDate);
		}
		
		Thread.sleep(3000);
		
		// Verifying the number of Guests 
		String guestsNo = driver.findElementByXPath("//span[@class='guest-label__text guest-label__text-guests']").getText(); 
		
		System.out.println("\nGuests details: ");
		if (guestsNo.contains("9")) {
			System.out.println("Number of Guests are correct. " + guestsNo); 
		} else { 
			System.out.println("Incorrect guests number. " + guestsNo);
		}
		
		Thread.sleep(3000);
		
		// Printing the Sleeping arrangements 
		
		/*
		 * List<WebElement> sleepingList = driver.
		 * findElementsByXPath("//div[text()='Sleeping arrangements']/ancestor::section[1]//div[@aria-hidden='true']"
		 * );
		 * 
		 * System.out.println("\nSleeping arrangements: ");
		 * 
		 * for (int s = 0; s < sleepingList.size(); s++) { String sleepingDetails1 =
		 * driver.findElementByXPath(
		 * "(//div[text()='Sleeping arrangements']/ancestor::section[1]//div[@aria-hidden='true'])["
		 * +s+"]/following::div[1]").getText(); String sleepingDetails2 =
		 * driver.findElementByXPath(
		 * "(//div[text()='Sleeping arrangements']/ancestor::section[1]//div[@aria-hidden='true'])["
		 * +s+"]/following::div[2]").getText();
		 * 
		 * sleepMap.put(sleepingDetails1, sleepingDetails2); }
		 */
		 
		Map<String, String> sleepMap = new LinkedHashMap<String, String>(); 
		
		List<WebElement> bedroomList = driver.findElementsByXPath("//div[contains(text(),'Bed')]"); 
		List<WebElement> bedArrangementList = driver.findElementsByXPath("//div[contains(text(),'Bed')]/following-sibling::div"); 
		
		for (int b = 0; b < bedroomList.size(); b++) { 
			String sleepingInfo1 = bedroomList.get(b).getText(); 
			String sleepingInfo2 = bedArrangementList.get(b).getText(); 
			driver.findElementByXPath("//div[contains(text(),'Bed')]/ancestor::div//div[@class='_1mlprnc']").click(); 
			driver.findElementByXPath("(//div[contains(text(),'Bed')]/ancestor::div//div[@class='_1mlprnc'])[2]").click(); 
			sleepingInfo1 = bedroomList.get(b).getText(); 
			sleepingInfo2 = bedArrangementList.get(b).getText(); 
			sleepMap.put(sleepingInfo1, sleepingInfo2); 
		}
		
		
		System.out.println("\nSleeping arrangements: ");
		
		for (Map.Entry<String, String> eachMap : sleepMap.entrySet()) {
			System.out.println(eachMap.getKey() + " - " + eachMap.getValue());
		}
		
		
		driver.quit();

	}

}
