package plainseleniumscripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShikshaClass { 
	
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

		driver = new ChromeDriver(options);
		driver.get("https://studyabroad.shiksha.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		
		// Selecting the Colleges 
		act.moveToElement(driver.findElementByXPath("//label[@class='menuTab-div fnt-wt melabel' and text()='Colleges ']")).build().perform(); 
		Thread.sleep(2000);  
		act.click(driver.findElementByLinkText("MS in Computer Science &Engg")).build().perform(); 
		Thread.sleep(5000); 
		
		js.executeScript("window.scrollBy(0,300)"); 
		// Selecting GRE scores and other criteria 
		driver.findElementByXPath("//p[text()='GRE']").click(); 
		wait.until(ExpectedConditions.elementToBeClickable(By.className("score-select-field"))); 
		
		WebElement elementScore = driver.findElementByClassName("score-select-field"); 
		Select scoreOptions = new Select(elementScore); 
		scoreOptions.selectByVisibleText("300 & below"); 
		Thread.sleep(3000); 
		driver.findElementByXPath("//p[text()='Max 10 Lakhs']").click(); 
		driver.findElementByXPath("//a[text()='USA']/ancestor::label[@for='country-3']").click(); 
		Thread.sleep(3000); 
		
		WebElement elementSort = driver.findElementById("categorySorter"); 
		Select sortOptions = new Select(elementSort); 
		sortOptions.selectByValue("fees_ASC"); 
		Thread.sleep(3000); 
		
		// Getting the list of Colleges with Public University, Scholarship and Accommodation altogether 
		
		// Getting the total list of colleges 
		List<WebElement> totalColleges = driver.findElementsByXPath("//div[@class='tuple-detail']"); 
		
		
		List<Double> feesList = new ArrayList<Double>(); 
		js.executeScript("window.scrollBy(0,350)"); 
		// Checking each college with the criteria 
		for (int i=1; i <= totalColleges.size(); i++) { 
			
			/*
			 * String puCheck = driver.
			 * findElementByXPath("(//p[text()='Public university']//span[@class='tick-mark'])["
			 * +i+"]").getText(); String scCheck = driver.findElementByXPath(
			 * "(//p[text()='Scholarship']//span[@class='tick-mark'])["+i+"]").getText();
			 * String acCheck = driver.findElementByXPath(
			 * "(//p[text()='Accommodation']//span[@class='tick-mark'])["+i+"]").getText();
			 */
			
			String puCheck = driver.findElementByXPath("(//p[text()='Public university']//span)["+i+"]").getAttribute("class"); 
			String scCheck = driver.findElementByXPath("(//p[text()='Scholarship']//span)["+i+"]").getAttribute("class"); 
			String acCheck = driver.findElementByXPath("(//p[text()='Accommodation']//span)["+i+"]").getAttribute("class"); 
			
			if(puCheck.equalsIgnoreCase("tick-mark") && scCheck.equalsIgnoreCase("tick-mark") && acCheck.equalsIgnoreCase("tick-mark")) { 
				
				String feeStr = driver.findElementByXPath("(//strong[text()=' 1st Year Total Fees']/following-sibling::p)["+i+"]").getText();
				System.out.println(feeStr);
				double fees = Double.parseDouble(feeStr.replaceAll("[\\s+a-zA-Z ]", ""));
				feesList.add(fees); 
			}
			
		}
		
		Thread.sleep(3000); 
		
		Collections.sort(feesList); 
		System.out.println("\nLess fees list: " + feesList);
		
		// Fetching the least fee and selecting that college 
		Double leastFee = feesList.get(0); 
		System.out.println("Least fees fetched: " + leastFee);
		
		// Adding the college to compare list 
		js.executeScript("window.scrollBy(0,200)");
		driver.findElementByXPath("//p[contains(text(),'"+leastFee+"')]/ancestor::div[@class='tuple-box']//span[@class='common-sprite']").click();
		Thread.sleep(3000);
		
		// Comparing with the first suggestion 
		driver.findElementByXPath("(//ul[@class='sticky-suggestion-list']//a)[1]").click(); 
		
		driver.findElementByXPath("//strong[contains(text(),'Compare Colleges')]").click();
		Thread.sleep(5000); 
		
		// Filling up the Questionnaire 
		driver.findElementByXPath("//label//strong[text()='2021']//preceding::span[@class='common-sprite'][1]").click(); 
		 
		act.click(driver.findElementByXPath("//div[text()='Preferred Countries']")).build().perform();
		
		driver.findElementByXPath("//strong[text()='Top countries']/parent::li/following-sibling::li//label[contains(@for,'USA')]").click(); 
		
		driver.findElementByLinkText("ok").click(); 
		
		driver.findElementByXPath("//label//strong[text()='Masters']//preceding::span[@class='common-sprite'][1]").click(); 
		
		act.click(driver.findElementByXPath("//div[text()='Preferred Course']")).build().perform(); 
		
		driver.findElementByXPath("//select[@name='fieldOfInterest']/following-sibling::div//li[text()='MS']").click(); 
		Thread.sleep(2000); 
		
		driver.findElementByXPath("//div[text()='All specializations']").click(); 
		
		driver.findElementByXPath("//select[@name='abroadSpecialization']/following-sibling::div//li[text()='Computer Science & Engineering']").click(); 
		
		driver.findElementById("signup").click(); 
		
		// Getting the list of error messages 
		List<WebElement> errorElement = driver.findElementsByXPath("//div[contains(@id,'error')]//div[contains(text(),'Please')]");
		
		System.out.println("\nError messages displayed: ");
		for (WebElement eachElement : errorElement) {
			
			String error = eachElement.getText(); 
			
			if(error.length()>0) {
				System.out.println(error);
			} 
			
		}
		
		
		driver.quit(); 		

	}

}
