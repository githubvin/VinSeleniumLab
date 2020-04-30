package plainseleniumscripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Ordering;

public class CarwaleClass { 
	
	public static RemoteWebDriver driver; 
	
	public String pickWindow() { 
		Set<String> winSet = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(winSet); 
		int size = winList.size(); 
		return winList.get(size-1);
	}

	public static void main(String[] args) throws InterruptedException { 
		
		CarwaleClass hand = new CarwaleClass(); 
		
		// Launching the application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap); 

		driver = new ChromeDriver(options);
		driver.get("https://www.carwale.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		// Clicking Used tab 
		driver.findElementByXPath("//li[@data-tabs='usedCars']").click(); 
		
		// Selecting the City 
		driver.findElementById("usedCarsList").sendKeys("Chennai"); 
		Thread.sleep(3000); 
		driver.findElementByXPath("//a[@cityname='chennai,tamilnadu']").click();  
		
		// Entering Minimum and Maximum KMs 
		driver.findElementById("minInput").sendKeys("8"); 
		driver.findElementById("maxInput").sendKeys("12"); 
		driver.findElementById("btnFindCar").click(); 
		Thread.sleep(5000); 
		
		// Closing the additional popups 
		try {
			driver.findElementByLinkText("Don't show anymore tips").click();
		} catch (Exception e) {
			System.out.println("Popup not displayed.");
			e.printStackTrace();
		}
		
		
		// Applying filters on the search results 
		driver.findElementByXPath("//span[text()='Cars with Photos']").click(); 
		Thread.sleep(5000); 
		
		js.executeScript("window.scrollBy(0, 250)");
		driver.findElementByXPath("//span[@class='filterText' and contains(text(),'Hyundai')]").click(); 
		Thread.sleep(2000); 
		driver.findElementByXPath("//span[@class='model-txt' and contains(text(),'Creta')]").click(); 
		Thread.sleep(3000);
		
		js.executeScript("window.scrollBy(0, 400)");
		driver.findElementByXPath("//h3[contains(text(),'Fuel Type')]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//span[@class='filterText' and text()='Petrol']").click(); 
		Thread.sleep(3000);
		
		// Sorting the results 
		WebElement sortElement = driver.findElementById("sort"); 
		Select sortOptions = new Select(sortElement); 
		sortOptions.selectByValue("2"); 
		Thread.sleep(5000); 
		
		List<WebElement> kmElement = driver.findElementsByXPath("//span[@class='slkms vehicle-data__item']"); 
		int size = kmElement.size(); 
		
		List<Integer> kmList = new ArrayList<Integer>(); 
		
		for (int i = 0; i < size; i++) {
			
			String text = kmElement.get(i).getText(); 
			System.out.println(text); 
			int kmValues = Integer.parseInt(text.replaceAll("\\D", "")); 
			kmList.add(kmValues); 
			
		}
		
		// Validating the Integer List is sorted or not 
		boolean sorted = Ordering.natural().isOrdered(kmList); 
		
		if (sorted == true) {
			System.out.println("Cars are sorted correctly.");
		} else { 
			System.out.println("Sorting is incorrect.");
		}
		
		// Sorting the List 
		Collections.sort(kmList); 
		System.out.println("*****************************"); 
		System.out.println(kmList); 
		
		Integer lowestKm = kmList.get(0); 
	
		// Converting this integer value to String to pass it in the below Xpath 
		String str = Integer.toString(lowestKm);  
		str = new StringBuilder(str).insert(str.length()-3, ",").toString(); 
		System.out.println(str);
		
		// Selecting the lowest KMs car  
		Thread.sleep(3000); 
		WebElement favCar = driver.findElementByXPath("(//span[contains(text(),'"+str+"')]//ancestor::div[@class='stock-detail']//span[@class='shortlist-icon--inactive shortlist'])[1]");
		js.executeScript("window.scrollBy(0, 800)");
		//js.executeScript("window.scrollIntoView(true)", favCar);  
		favCar.click();
		
		// Navigating to Favourites page and viewing the details 
		driver.findElementByXPath("//li[@data-cat='UsedCarSearch']").click(); 
		driver.findElementByLinkText("More details »").click(); 
		
		driver.switchTo().window(hand.pickWindow()); 
		Thread.sleep(5000); 
		
		List<WebElement> eleAttribute = driver.findElementsByXPath("//div[@id='overview']//li//div[1]"); 
		List<WebElement> eleValue = driver.findElementsByXPath("//div[@id='overview']//li//div[2]");
		
		Map<String, String> carDetails = new LinkedHashMap<String, String>(); 
		
		for (int i=0; i < eleAttribute.size(); i++) { 
			String attributeStr = eleAttribute.get(i).getText(); 
			String valueStr = eleValue.get(i).getText(); 
			carDetails.put(attributeStr, valueStr); 
		} 
		
		System.out.println("\nDetails of the Favourite Car");
		for (Map.Entry<String, String> eachEntry : carDetails.entrySet()) { 
			System.out.println(eachEntry.getKey() + " ------- " + eachEntry.getValue());
		}
		
		driver.quit();

	}

}
