package plainseleniumscripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class HondaClass { 
	
	public static RemoteWebDriver driver;  
	
	public String pickWindow() { 
		Set<String> windowHandles = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(windowHandles); 
		int size = winList.size(); 
		return winList.get(size-1);
	}

	public static void main(String[] args) throws InterruptedException { 
		
		// Creating Object to handle multiple windows 
		HondaClass hand = new HondaClass(); 
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");  
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		
		driver = new ChromeDriver(options);
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		try {
			driver.findElementByXPath("//button[@data-dismiss='modal']").click();
		} catch (Exception e) {
			System.out.println("Lockdown popup not displayed.");
			e.printStackTrace();
		}
		
		// Clicking the Scooter Dio 
		driver.findElementByLinkText("Scooter").click(); 
		Thread.sleep(3000);
		driver.findElementByXPath("//img[@src='/assets/images/thumb/dioBS6-icon.png']").click(); 
		Thread.sleep(5000);
		
		driver.findElementByLinkText("Specifications").click(); 
		Thread.sleep(3000); 
		
		Actions act = new Actions(driver); 
		
		act.moveToElement(driver.findElementByLinkText("ENGINE")).build().perform(); 
		
		// Getting the Displacement value of Dio 
		String dioDispStr = driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span").getText(); 
		// dioDispStr = dioDispStr.replaceAll("[\\s+a-zA-Z :]", "");  // Another regular expression for Double 
		dioDispStr = dioDispStr.replace("cc", ""); 
		System.out.println(dioDispStr);
		double dioDispValue = Double.parseDouble(dioDispStr);  
		System.out.println("Displacement value for Scooter Dio: " + dioDispValue);
		
		// Navigating to Activa 125 scooter details  
		driver.findElementByLinkText("Scooter").click(); 
		driver.findElementByXPath("//img[@src='/assets/images/thumb/activa-125new-icon.png']").click(); 
		Thread.sleep(5000);
		
		driver.findElementByLinkText("Specifications").click(); 
		Thread.sleep(5000); 
		
		act.moveToElement(driver.findElementByLinkText("ENGINE")).build().perform(); 
		
		// Getting the Displacement value here 
		String activaDispStr = driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span").getText(); 
		activaDispStr = activaDispStr.replaceAll("\\D", ""); 
		double activaDispValue = Double.parseDouble(activaDispStr);
		System.out.println("Displacement value for Scooter Activa 125: " + activaDispValue); 
		
		if (dioDispValue < activaDispValue) {
			System.out.println("Scooter Activa125 has better Displacement value."); 
		} else if (dioDispValue == activaDispValue) { 
			System.out.println("Both Scooters have same Displacement value.");
		} else { 
			System.out.println("Scooter Dio has better Displacement value.");
		}
		
		// Moving to FAQ 
		driver.findElementByLinkText("FAQ").click(); 
		
		// Selecting Activa 125 BS-VI under Browser product suggestions 
		driver.findElementByLinkText("Activa 125 BS-VI").click(); 
		Thread.sleep(3000); 
		driver.findElementByXPath("//a[contains(text(),'Vehicle Price')]").click(); 
		
		WebElement eleModel = driver.findElementByName("ModelID"); 
		Select modelOptions = new Select(eleModel); 
		modelOptions.selectByValue("31"); 
		
		driver.findElementByXPath("(//button[text()='Submit'])[6]").click(); 
		driver.findElementByLinkText("Click here to know the price of Activa 125 BS-VI.").click(); 
		Thread.sleep(5000); 
		
		// Switching to the new window 
		driver.switchTo().window(hand.pickWindow()); 
		
		// Selecting State Tamilnadu and City Chennai in the new page 
		WebElement stateElement = driver.findElementByName("StateID"); 
		Select stateOptions = new Select(stateElement); 
		stateOptions.selectByValue("28"); 
		
		WebElement cityElement = driver.findElementByName("CityID"); 
		Select cityOptions = new Select(cityElement); 
		cityOptions.selectByValue("1524"); 
		
		driver.findElementByXPath("//button[text()='Search']").click(); 
		Thread.sleep(3000);
		
		// Printing all the available models in the selected city. 
		/* 
		 * The table rows of this application does not have consistent column values. 
		 * So this table approach not working properly. 
		 * 
		 * WebElement table = driver.findElementById("gvshow"); List<WebElement> rows =
		 * table.findElements(By.tagName("tr"));
		 * 
		 * for (WebElement eachRow : rows) { List<WebElement> col =
		 * eachRow.findElements(By.tagName("td")); String values = col.get(0).getText();
		 * System.out.println(values); }
		 */
		
		// Printing all the Models and Price. 
		System.out.println("**********************************************************");
		System.out.println("Available Models and Price details for the selected city.");
		
		String activaModel1 = driver.findElementByXPath("(//table[@id='gvshow']//td[2])[1]").getText(); 
		String activaModel1Price = driver.findElementByXPath("//table[@id='gvshow']//td[3]").getText(); 
		System.out.println(activaModel1 + "-" + activaModel1Price);
		
		String activaModel2 = driver.findElementByXPath("(//table[@id='gvshow']//td[1])[2]").getText(); 
		String activaModel2Price = driver.findElementByXPath("(//table[@id='gvshow']//td[2])[2]").getText(); 
		System.out.println(activaModel2 + "-" + activaModel2Price); 
		
		String activaModel3 = driver.findElementByXPath("(//table[@id='gvshow']//td[1])[3]").getText(); 
		String activaModel3Price = driver.findElementByXPath("(//table[@id='gvshow']//td[2])[3]").getText(); 
		System.out.println(activaModel3 + "-" + activaModel3Price); 
		
		driver.quit();

	}

}
