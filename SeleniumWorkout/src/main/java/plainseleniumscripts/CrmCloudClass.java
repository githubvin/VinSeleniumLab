package plainseleniumscripts;

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
import org.openqa.selenium.support.ui.Select;

public class CrmCloudClass { 
	
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
		
		driver = new ChromeDriver(); 
		driver.get("https://demo.1crmcloud.com/"); 
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		// Login the application 
		driver.findElementById("login_user").sendKeys("admin");
		driver.findElementById("login_pass").sendKeys("admin"); 
		
		// Applying Claro theme 
		WebElement elementTheme = driver.findElementById("login_theme"); 
		Select themeOptions = new Select(elementTheme); 
		themeOptions.selectByVisibleText("Claro Theme");
		
		driver.findElementById("login_button").click(); 
		Thread.sleep(5000); 
		
		driver.findElementByLinkText("Sales & Marketing").click(); 
		Thread.sleep(3000);
		
		driver.findElementByXPath("//div[@id='page-shortcuts']//div[text()='Create Contact']").click(); 
		Thread.sleep(8000);
		
		Actions act = new Actions(driver); 
		act.click(driver.findElementById("DetailFormsalutation-input")).build().perform(); 
		//act.click(driver.findElementByXPath("//div[@id='DetailFormsalutation-input-label' and text()='Mr.']")).build().perform(); 
		
		act.click(driver.findElementByXPath("//div[text()='Mr.']")).build().perform(); 
		
		driver.findElementById("DetailFormfirst_name-input").sendKeys("Vinothkumar"); 
		driver.findElementById("DetailFormlast_name-input").sendKeys("S"); 
		driver.findElementById("DetailFormemail1-input").sendKeys("test@email.com");
		driver.findElementById("DetailFormphone_work-input").sendKeys("1234567890");
		
		driver.findElementById("DetailFormlead_source-input").click(); 
		act.click(driver.findElementByXPath("//div[text()='Public Relations']")).build().perform(); 
		
		driver.findElementById("DetailFormbusiness_role-input").click(); 
		act.click(driver.findElementByXPath("//div[text()='Sales']")).build().perform(); 
		
		driver.findElementById("DetailFormprimary_address_street-input").sendKeys("4054 Westfall Avenue");
		driver.findElementById("DetailFormalt_address_city-input").sendKeys("Detroit");
		driver.findElementById("DetailFormalt_address_state-input").sendKeys("Michigan"); 
		driver.findElementById("DetailFormalt_address_country-input").sendKeys("USA"); 
		driver.findElementById("DetailFormalt_address_postalcode-input").sendKeys("48219"); 
		driver.findElementById("DetailForm_save2").click(); 
		Thread.sleep(3000); 
		
		// Navigate to Meeting page 
		act.moveToElement(driver.findElementByXPath("//div[(contains(text(),'Today'))]")).build().perform(); 
		//act.click(driver.findElementByXPath("//div[(contains(text(),'Meetings'))]")).build().perform(); 
		driver.findElementByXPath("//div[(contains(text(),'Meetings'))]").click();
		Thread.sleep(5000); 
		
		// Creating Meeting 
		driver.findElementByXPath("(//span[text()='Create'])[1]").click(); 
		
		driver.findElementById("DetailFormname-input").sendKeys("Project Status");
		Thread.sleep(2000);
		driver.findElementById("DetailFormstatus-input").click(); 
		driver.findElementByXPath("(//div[text()='Planned'])[2]").click(); 
		
		// Setting Date as Tomorrow and time as 3PM with Duration 1hr 
		driver.findElementById("DetailFormdate_start-input").click(); 
		driver.findElementByXPath("//div[@class='grid-cell number-cell text-right day inside current selected quiet responsive']/following::div[1]").click(); 
		Thread.sleep(2000);
		driver.findElementByXPath("//div[@id='DetailFormdate_start-calendar-text']//input[@class='input-text']").clear();
		driver.findElementByXPath("//div[@id='DetailFormdate_start-calendar-text']//input[@class='input-text']")
		.sendKeys("3:00pm", Keys.ENTER); 
		
		driver.findElementById("DetailFormduration-time").clear(); 
		driver.findElementById("DetailFormduration-time").sendKeys("1hr", Keys.TAB);
		
		// Adding Participants 
		driver.findElementByXPath("//span[contains(text(),'Add Participants')]").click(); 
		driver.findElementByXPath("//div[@id='app-search-text']//input[@class='input-text']").sendKeys("Vinothkumar");
		act.click(driver.findElementByXPath("//div[@id='app-search-list']//div[(contains(text(),'Vinothkumar'))]")).build().perform(); 
		
		// Saving the Meeting 
		driver.findElementById("DetailForm_save2-label").click(); 
		Thread.sleep(5000);
		
		// Navigating to Sales & Contacts --> Contacts 
		act.moveToElement(driver.findElementByXPath("//div[text()='Sales & Marketing']")).build().perform(); 
		Thread.sleep(2000);
		act.click(driver.findElementByXPath("//div[text()='Contacts']")).build().perform(); 
		
		// Searching Lead Name 
		driver.findElementById("filter_text").sendKeys("Vinothkumar"); 
		Thread.sleep(3000); 
		act.click(driver.findElementByXPath("//a[contains(text(),'Vinothkumar')]")).build().perform(); 
		Thread.sleep(3000); 
		
		// Checking the Meeting assigned for the Contact 
		WebElement meetingRecord = driver.findElementByXPath("(//span[@id='subpanel-activities']/ancestor::div[@id='DetailForm-subpanels']//a[contains(text(),'Project Status')])[1]"); 
		
		if (meetingRecord.isDisplayed()) {
			System.out.println("Meeting is assigned for the Contact."); 
		} else { 
			System.out.println("Meeting is not available for the Contact.");
		}
		
		driver.close();

	}

}
