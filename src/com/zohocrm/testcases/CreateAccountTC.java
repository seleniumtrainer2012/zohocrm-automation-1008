package com.zohocrm.testcases;
import java.util.HashMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zohocrm.basetest.BaseTest;
import com.zohocrm.util.DataReader;
import com.zohocrm.util.ExtentManager;

public class CreateAccountTC extends BaseTest{	
	String TCName="CreateAccountTestCase";
	
	@Test
	public void CreateAccountTestCase() {
		report=ExtentManager.getInstance();
		test=report.startTest(TCName);
		
		test.log(LogStatus.INFO, "Started excuting testcase -"+TCName);
		
		test.log(LogStatus.INFO, "Loading config file");
		loadConfig();
		
		HashMap<String,String> TCData=DataReader.getData(TCName);
		
		/*
		 * selenium  code
		 */
		test.log(LogStatus.INFO, "Launching execution browser");
		launchBrowser();
		
		test.log(LogStatus.INFO, "Navigating to url");
		navigateTo();		
		
		setText("FullName_ID",TCData.get("FullName"));
		setText("Email_NAME",TCData.get("Email"));
		setText("Password_XPATH",TCData.get("PWD"));
		
		
		test.log(LogStatus.PASS, "Account is successfully created");
		takeScreenshot();
		
	}	
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			driver.quit();
		}
		report.endTest(test);
		report.flush();
	}
	
	
	

}



