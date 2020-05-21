package plainseleniumscripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class JustDialClass { 
	
	public static RemoteWebDriver driver; 
	
	public static void main(String[] args) throws InterruptedException { 
		
		JustDialClass obj = new JustDialClass(); 

		// Launching the application
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);

		driver = new ChromeDriver(options);
		driver.get("https://www.justdial.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		Actions act = new Actions(driver); 
		
		// Selecting City 
		driver.findElementById("city").clear();
		driver.findElementById("city").sendKeys("Chennai"); 
		driver.findElementById("Chennai").click(); 
		
		driver.findElementByXPath("//span[text()='Auto care']").click(); 
		Thread.sleep(3000); 
		
		driver.findElementByXPath("//span[text()='Car Repair']").click(); 
		Thread.sleep(3000); 
		
		// Selecting Car brand and model 
		driver.findElementByXPath("//span[@class='meditle2 lng_commn' and text()='Hyundai']").click(); 
		Thread.sleep(3000); 
		driver.findElementByXPath("//span[@class='meditle2 lng_commn' and text()='Hyundai Xcent']").click(); 
		Thread.sleep(3000); 
		
		// Selecting Location 
		driver.findElementByLinkText("Location").click(); 
		driver.findElementById("sortbydist").sendKeys("Porur"); 
		driver.findElementByLinkText("Porur").click(); 
		Thread.sleep(3000); 
		
		// Setting the distance 
		driver.findElementById("distance").click(); 
		driver.findElementByLinkText("1 km").click(); 
		
		// Fetching the desired result from the list 
		List<WebElement> rawList = driver.findElementsByXPath("//li[@class='cntanr']"); 
		
		// Separate List for Center Names 
		List<String> centerName = new ArrayList<String>(); 
		
		// Separate List for Phone numbers 
		List<String> phoneList = new ArrayList<String>(); 
		
		// To get the appended phone number 
		String appendPhone = ""; 
		
		// Created Map to store the final Service Center details with Phone number 
		Map<String, String> serviceCenterDetails = new LinkedHashMap<String, String>(); 
		
		for(int i = 1; i <= rawList.size(); i++) {
			
			String ratingStr = driver.findElementByXPath("//span[@class='green-box']").getText(); 
			double rating = Double.parseDouble(ratingStr); 
			
			String voteStr = driver.findElementByXPath("//span[@class='green-box']/parent::a//span[@class='rt_count lng_vote']").getText(); 
			int vote = Integer.parseInt(voteStr.replaceAll("[\\s+a-zA-Z ]", "")); 
			
			if (rating >= 4.5 && vote >= 50) { 
				String servNameStr = driver.findElementByXPath("//span[text()='"+rating+"']/ancestor::li//span[@class='lng_cont_name']").getText();
				centerName.add(servNameStr);  
				
				// Fetching the phone number here 
				List<WebElement> phoneIconElementList = driver.findElementsByXPath("//span[text()='"+rating+"']/ancestor::li//p[@class='contact-info ']//span[contains(@class,'mobile')]");
				
				for (int j = 0; j <= phoneIconElementList.size(); j++) { 
					String iconCode = phoneIconElementList.get(j).getAttribute("class");
					if (obj.phoneNumber().containsKey(iconCode)) {
						appendPhone = appendPhone + obj.phoneNumber().get(iconCode); 
						phoneList.add(appendPhone); 
					}
				}
				
				
			}
			
		}
		
		
		// Adding the Center Names and corresponding Phone numbers in the final Map 
		serviceCenterDetails.put(centerName, appendPhone); 
		

		// Writing the Map in the excel 
		obj.writeExcel(serviceCenterDetails);
		
		
	}

	// Writing a separate method to fetch the encoded phone number 
	public Map<String, String> phoneNumber() {  
		
		Map<String, String> phoneMap = new LinkedHashMap<String, String>(); 
		
		phoneMap.put("dc", "+"); 
		phoneMap.put("fe", "("); 
		phoneMap.put("hg", ")"); 
		phoneMap.put("ba", "-"); 
		phoneMap.put("yz", "1"); 
		phoneMap.put("wx", "2"); 
		phoneMap.put("vu", "3");
		phoneMap.put("ts", "4");
		phoneMap.put("rq", "5");
		phoneMap.put("po", "6");
		phoneMap.put("nm", "7");
		phoneMap.put("lk", "8");
		phoneMap.put("ji", "9");
		phoneMap.put("acb", "0");
		
		return phoneMap; 
		
	} 
	
	public void writeExcel(Map<String, String> map) throws IOException { 
		File doc = new File("./dataOutput/JustDial_ServiceDetails.xlsx"); 
		XSSFWorkbook wBook = new XSSFWorkbook(); 
		XSSFSheet wSheet = wBook.createSheet("Output"); 
		
		for (Entry<String, String> eachEntry : map.entrySet()) {
			for (int s = 0; s < map.size(); s++) {
				wSheet.createRow(0).createCell(0).setCellValue("Service Center Name"); 
				wSheet.createRow(0).createCell(1).setCellValue("Phone Number"); 
				wSheet.createRow(1).createCell(0).setCellValue(eachEntry.getKey());
				wSheet.createRow(1).createCell(1).setCellValue(eachEntry.getValue());
			}
		}
		
		FileOutputStream opFile = new FileOutputStream(doc); 
		wBook.write(opFile); 
		wBook.close();  
		
	}
	
}
