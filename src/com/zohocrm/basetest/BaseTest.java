package com.zohocrm.basetest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	public WebDriver driver;
	public FileInputStream fis;
	public Properties prop;
	public ExtentReports report;
	public ExtentTest test;

	public void loadConfig() {
		try {
			fis=new FileInputStream("config.properties");
			prop=new Properties();
			prop.load(fis);
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}

	public void launchBrowser() {
		if(prop.getProperty("ExecutionBrowser").equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", 
					System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			driver=new ChromeDriver();

		}else if(prop.getProperty("ExecutionBrowser").equals("IE")) {
			System.setProperty("webdriver.ie.driver", 
					System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}else {
			System.err.println("Invalid browser specified!");
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
	}


	public void navigateTo() {
		driver.get(prop.getProperty("AppURL"));		
	}


	public WebElement getElement(String locatorKey) {
		WebElement elm;		
		if(locatorKey.endsWith("_ID")) {			
			elm=driver.findElement(By.id(prop.getProperty(locatorKey)));			
		}else if(locatorKey.endsWith("_NAME")) {
			elm=driver.findElement(By.name(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_XPATH")) {
			elm=driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		}else {
			System.out.println("invalid locator specified!");			
			elm=null;
		}		
		return elm;
	}

	public void setText(String locatorKey, String value) {
		WebElement elm=getElement(locatorKey);
		elm.sendKeys(value);
	}	

	public void click(String locatorKey) {
		WebElement elm=getElement(locatorKey);
		elm.click();
	}

	public String getAttriute(String locatorKey,String attributeName) {
		WebElement elm=getElement(locatorKey);
		return elm.getAttribute(attributeName);
	}
	public String getText(String locatorKey) {
		WebElement elm=getElement(locatorKey);
		return elm.getText();
	}

	public void isElementDisplayed(String locatorKey) {
		try {
			WebElement elm=getElement(locatorKey);
			if(elm.isDisplayed()) {
				test.log(LogStatus.PASS, "Element-"+locatorKey+" is not disoplayed");
			}else {
				test.log(LogStatus.FAIL, "Element-"+locatorKey+" is not disoplayed");
			}
		}catch(Exception e) {
			test.log(LogStatus.FAIL, "Element-"+locatorKey+" is not disoplayed");
		}
	}

	public boolean verifyText(String expectedText, String actualText) {		
		if(actualText.equals(expectedText)) {
			test.log(LogStatus.PASS, "Actual text -"+actualText+" does not match with expected text-"+expectedText);
			return true;
		}else {
			test.log(LogStatus.FAIL, "Actual text -"+actualText+" does not match with expected text-"+expectedText);
			return false;
		}		
	}

	public void waitUntilEnabled(String locatorKey) {		
		test.log(LogStatus.INFO, "Wait untill element "+locatorKey+" is clickable");
		WebDriverWait wait=new WebDriverWait(driver, 120);
		Boolean status=wait.until(ExpectedConditions.attributeToBe(getElement(locatorKey), "class", "signupbtn btn"));
		System.out.println("Get Started status:-"+status);
	}


	public void takeScreenshot() {		
		try {
			Date d=new Date();
			String TimeStamp=d.toString().replace(" ", "_").replace(":", "_");			
			String screenshotPath=System.getProperty("user.dir")+"//scrennshots//s_"+TimeStamp+".PNG";

			TakesScreenshot screenshot=(TakesScreenshot)driver;
			File f=screenshot.getScreenshotAs(OutputType.FILE);

			FileHandler.copy(f, new File(screenshotPath));
			
			test.log(LogStatus.INFO, test.addScreenCapture(screenshotPath));
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}




}




