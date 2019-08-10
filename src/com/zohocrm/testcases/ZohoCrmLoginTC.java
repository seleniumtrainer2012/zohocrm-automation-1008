package com.zohocrm.testcases;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zohocrm.basetest.BaseTest;
import com.zohocrm.util.ExtentManager;

public class ZohoCrmLoginTC extends BaseTest{	
	String TCName="ZohoCRMLoginWithValidCredentialsTestCase";
	
	@Test
	public void ZohoCRMLoginWithValidCredentials() {
		report=ExtentManager.getInstance();
		test=report.startTest(TCName);
		
		test.log(LogStatus.INFO, "Started excuting testcase -"+TCName);
		
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
