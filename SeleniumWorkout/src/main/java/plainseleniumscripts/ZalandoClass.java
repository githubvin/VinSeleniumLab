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

public class ZalandoClass { 
	
	public static RemoteWebDriver driver; 
	
	public String pickWindow() { 
		Set<String> winSet = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(winSet); 
		int size = winList.size(); 
		return winList.get(size-1);  
	}

	public static void main(String[] args) throws InterruptedException { 
		
		ZalandoClass obj = new ZalandoClass(); 
		
		// Launching the application 
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 

		driver = new ChromeDriver(options);
		driver.get("https://www.zalando.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		
		Thread.sleep(3000);
		
		String initialAlertText = driver.switchTo().alert().getText(); 
		System.out.println("Initial Alert: " + initialAlertText); 
		
		driver.switchTo().alert().accept(); 
		
		// Choosing the country site UK 
		driver.switchTo().defaultContent(); 
		driver.findElementByXPath("//a[text()='Zalando.uk']").click();
		Thread.sleep(10000);  
		
		try {
			driver.findElementById("uc-btn-accept-banner").click();
		} catch (Exception e) {
			System.out.println("Cookies block not displayed.");
			e.printStackTrace();
		}
		
		driver.findElementByXPath("//div[@class='z-navicat-header_wrapper']//span[text()='Women']").click(); 
		
		act.moveToElement(driver.findElementByXPath("//span[text()='Clothing']")).build().perform(); 
		act.click(driver.findElementByXPath("//span[text()='Coats']")).build().perform(); 
		Thread.sleep(3000); 
		
		// Applying filters 
		driver.findElementByXPath("//span[text()='Material']").click(); 
		driver.findElementByXPath("//span[text()='cotton (100%)']").click(); 
		driver.findElementByXPath("//button[text()='Save']").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//span[text()='Length']").click(); 
		driver.findElementByXPath("//span[text()='thigh-length']").click(); 
		driver.findElementByXPath("//button[text()='Save']").click(); 
		Thread.sleep(3000); 
		
		// Selecting the product MANTEL 
		driver.findElementByXPath("//div[contains(text(),'MANTEL - Parka')]").click(); 
		Thread.sleep(3000); 
		
		// Verifying the available size 
		driver.findElementById("picker-trigger").click(); 
		driver.findElementByXPath("//span[text()='M']").click(); 
		
		// Selecting Olive colour 
		driver.findElementByXPath("(//img[@alt='olive'])[2]").click(); 
		Thread.sleep(3000); 
		
		if (driver.findElementByXPath("//h2[text()='Out of stock']").isDisplayed()) { 
			driver.findElementByXPath("(//img[@alt='navy'])[2]").click(); 
		} else if (driver.findElementById("picker-trigger").isDisplayed()) { 
			System.out.println("Olive is available."); 
		} else { 
			System.out.println("Both Navy and Olive are out of stock."); 
			driver.quit(); 
		} 
		
		driver.findElementById("picker-trigger").click(); 
		
		if (driver.findElementByXPath("//span[text()='M']").isDisplayed()) {
			driver.findElementByXPath("//span[text()='M']").click(); 
		} else { 
			System.out.println("Size M is not available.");
		}
		 
		
		if (driver.findElementByXPath("(//span[text()='Standard delivery']/parent::div//button[@aria-label='Free'])[1]").isDisplayed()) {
			driver.findElementByXPath("//span[text()='Add to bag']").click(); 
		} else { 
			System.out.println("Delivery is not Free."); 
			driver.quit(); 
		}
		
		act.moveToElement(driver.findElementByXPath("(//div[@class='z-navicat-header_navTools']//a[contains(@href,'cart')])[1]")).build().perform(); 
		Thread.sleep(3000); 
		act.click(driver.findElementByXPath("//div[text()='Go to bag']")).build().perform(); 
		
		// Getting the Estimated delivery 
		String estimatedDelivery = driver.findElementByXPath("//div[@data-id='delivery-estimation']//span").getText(); 
		System.out.println("Estimated Delivery: " + estimatedDelivery.replaceAll("&nbsp;", " "));
		
		// Getting the tooltip message on top 
		String toolTipText = driver.findElementByXPath("//a[text()='Free delivery & returns*']/parent::span").getAttribute("title"); 
		System.out.println("Tooltip message in Free Delivery and Returns: " + toolTipText); 
		
		driver.findElementByXPath("//a[text()='Free delivery & returns*']").click(); 
		Thread.sleep(3000); 
		
		// Going for Chat 
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Start chat']/parent::button")));
		driver.findElementByXPath("//span[text()='Start chat']/parent::button").click();  
		System.out.println("Chat clicked.");
		Thread.sleep(5000); 
		
		// Switching to new chat window 
		driver.switchTo().window(obj.pickWindow()); 
		
		driver.findElementById("prechat_customer_name_id").sendKeys("Vinothkumar"); 
		driver.findElementById("prechat_customer_email_id").sendKeys("test@test.com"); 
		driver.findElementByXPath("//span[text()='Start Chat']/parent::button").click(); 
		Thread.sleep(8000); 
		
		// Getting the reply from ChatBot 
		driver.findElementById("liveAgentChatTextArea").sendKeys("Hi"); 
		driver.findElementByXPath("//button[@title='Send']").click(); 
		
		String botResponse = driver.findElementByXPath("//span[@class='client']//following-sibling::span[@class='operator']//span[@class='messageText']").getText(); 
		System.out.println("ChatBot response: " + botResponse); 
		
		driver.quit();
		

	}

}
