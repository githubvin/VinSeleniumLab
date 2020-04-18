package plainseleniumscripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MakemytripClass { 
	
	public static RemoteWebDriver driver; 
	
	public String pickWindow() {
		Set<String> winSet = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(winSet);
		int size = winList.size(); 
		return winList.get(size-1); 
	}

	public static void main(String[] args) throws InterruptedException { 
		
		// Creating object for the other method 
		MakemytripClass hand = new MakemytripClass(); 
		
		// Launching the MakemyTrip application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				
		driver.findElementByXPath("//span[text()='Hotels']").click();		
		
		driver.findElementById("city").click(); 
		driver.findElementByXPath("//input[@placeholder='Enter city/ Hotel/ Area/ Building']").sendKeys("Goa"); 
		Actions act = new Actions(driver); 
		act.click(driver.findElementByXPath("//div[@class='flexOne']//p[text()='Goa, India']")).build().perform(); 
		
		// Picking the checkin and checkout date 
		driver.findElementById("checkin").click(); 
		WebElement eleStartDate = driver.findElementByXPath("//div[@class='DayPicker-Day' and text()='25']"); 
		String checkinDay = eleStartDate.getText(); 
		String startDateString = checkinDay.replaceAll("\\D", ""); 
		int startDateNumber = Integer.parseInt(startDateString); 
		eleStartDate.click(); 
		Thread.sleep(3000);
		
		// Using Regular expression adding 5 days for the checkout date 
		driver.findElementById("checkout").click(); 
		int checkoutDate = startDateNumber + 5; 
		driver.findElementByXPath("//div[@class='DayPicker-Day' and text()='"+checkoutDate+"']").click();
		
		driver.findElementById("guest").click();
		driver.findElementByXPath("//ul[@data-cy='adultCount']//li[@data-cy='adults-2']").click();  
		driver.findElementByXPath("//li[@data-cy='children-1']").click(); 
		WebElement childAgeBox = driver.findElementByClassName("ageSelectBox"); 
		Select childAgeOption = new Select(childAgeBox); 
		childAgeOption.selectByVisibleText("11"); 
		driver.findElementByXPath("//button[@data-cy='submitGuest']").click(); 
		driver.findElementById("hsw_search_button").click(); 
		Thread.sleep(5000); 
		
		// Selecting Area and Hotel 
		driver.findElementByXPath("//div[@class='mmBackdrop wholeBlack']").click();
		driver.findElementByXPath("//div[@class='locationFtrModal']//label[text()='Baga']").click(); 
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		WebElement ele5Star = driver.findElementByXPath("//label[text()='5 Star']"); 
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(ele5Star)); 
		ele5Star.click();
		
		// Choosing first resulting Hotel 
		driver.findElementById("Listing_hotel_0").click(); 
		
		// Switching to the new window 
		driver.switchTo().window(hand.pickWindow()); 
		Thread.sleep(5000); 
		
		// Printing Hotel name 
		System.out.println("Hotel Name: " + driver.findElementById("detpg_hotel_name").getText()); 
		driver.findElementByXPath("//span[text()='MORE OPTIONS']").click(); 
		Thread.sleep(3000); 
		
		// Handling EMI popup and choosing 3 months EMI  
		driver.findElementByXPath
		("//table[@class='tblEmiOption']//td[contains(text(),'3')]/following-sibling::td//span[text()='SELECT']").click();
		driver.findElementByClassName("close").click(); 
		driver.findElementById("detpg_headerright_book_now").click(); 
		Thread.sleep(5000); 
		
		// Handling the modal popup 
		if (driver.findElementByClassName("_Modal modalCont").isDisplayed()) {
			driver.findElementByClassName("close").click();
		}
		
		// Displaying Total Payable amount  
		System.out.println("Total Payable amount: " + driver.findElementById("revpg_total_payable_amt").getText());
		
		driver.quit();

	}

}
