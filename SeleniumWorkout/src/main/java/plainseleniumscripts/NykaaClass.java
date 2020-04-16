package plainseleniumscripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class NykaaClass { 
	
	public static RemoteWebDriver driver; 
	
	/*** 
	 * 
	 * This method is used to handle the multiple windows 
	 * and tries to deal with the current working window 
	 * Not used here.  
	 * Just need to call this method driver.switchTo().window(windowmethod()); 
	 *   
	 *  
	 * public String windowmethod() { Set<String> windowsid =
	 * driver.getWindowHandles(); List<String> windowsidlist=new ArrayList<String>
	 * (windowsid); int size= windowsidlist.size(); return
	 * windowsidlist.get(size-1); }
	 */

	public static void main(String[] args) throws InterruptedException { 

		// Launching the Nykaa application 
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe"); 
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("--disable-notifications"); 
		driver = new ChromeDriver(options); 
		driver.get("https://www.nykaa.com/"); 
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		
		
		// Selection the L'Oreal Paris brand 
		Thread.sleep(5000);
		
		Actions act = new Actions(driver); 
		act.moveToElement(driver.findElementByXPath("//a[text()='brands']")).build().perform(); 
		
		WebDriverWait wait = new WebDriverWait(driver, 30); 
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@class='BrandsCategoryHeading']")));
		
		act.moveToElement(driver.findElementByXPath("//a[text()='Popular']")).build().perform(); 
		
		driver.findElementByXPath("(//li[@class='brand-logo menu-links'])[5]").click(); 
		
		// Handling multiple windows and fetching the Title of new window 
		Set<String> windowHandles = driver.getWindowHandles(); 
		List<String> winList = new ArrayList<String>(windowHandles); 
		driver.switchTo().window(winList.get(1)); 
		
		if (driver.getTitle().contains("L'Oreal Paris")) {
			System.out.println("L'Oreal Paris page.");
		} else {
			System.out.println("Other Brand page.");
		}
		
		// Sorting by Customer Top Rated 
		Thread.sleep(5000);
		driver.findElementByXPath("(//span[text()='popularity'])[1]").click();
		driver.findElementByXPath("//span[text()='customer top rated']").click(); 
		
		// Selecting Shampoo Category 
		Thread.sleep(5000);
		driver.findElementByXPath("//div[text()='Category']").click(); 
		driver.findElementByXPath("(//span[contains(text(), 'Shampoo')])[1]").click(); 
		
		// Verifying Shampoo Filter is applied 
		String filterText = driver.findElementByXPath("(//ul[@class='pull-left applied-filter-lists']//li)[1]").getText(); 
		if (filterText.contains("Shampoo")) {
			System.out.println("Shampoo filters applied.");
		} else {
			System.out.println("Filters not applied with Shampoo.");
		}

		// Choosing a product and proceeding 
		driver.findElementByXPath("//span[contains(text(),'Oreal Paris Colour Protect Shampoo')]").click(); 
		
		// Again handling with the new window 
		Set<String> windowHandles2 = driver.getWindowHandles(); 
		List<String> secondWinList = new ArrayList<String>(windowHandles2); 
		driver.switchTo().window(secondWinList.get(2)); 
		
		driver.findElementByXPath("//span[text()='175ml']").click();
		String productMRP = driver.findElementByXPath("//div[@class='clearfix product-des__details']//span[@class='post-card__content-price-offer']").getText();
		//String realMRP = productMRP.replaceAll("\\D", "");
		System.out.println("MRP of the product: " + productMRP); 
		
		// Adding to Bag and proceeding 
		driver.findElementByXPath("(//button[text()='ADD TO BAG'])[1]").click(); 
		Thread.sleep(3000); 
		driver.findElementByClassName("AddBagIcon").click(); 
		
		String grandTotalPrice = driver.findElementByXPath("//div[text()='Grand Total']/following::div[1]").getText(); 
		//String realGrandPrice = grandTotalPrice.replaceAll("\\D", ""); 
		System.out.println("Grand Total price: " + grandTotalPrice);
		
		// Proceeding to checkout 
		driver.findElementByXPath("//button//span[text()='Proceed']").click(); 
		Thread.sleep(3000); 
		driver.findElementByXPath("//button[text()='CONTINUE AS GUEST']").click(); 
		
		System.out.println("Warning message: " + driver.findElementByXPath("//div[@class='message']").getText());
		
		driver.quit();
		
	}

}
