package com.zohocrm.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.zohocrm.basetest.BaseTest;
import com.zohocrm.util.ExtentManager;

public class CreateAccountEmptyFieldsErrorsMessagesTC extends BaseTest{


	String TCName="CreateAccountEmptyFieldsErrorsMessagesTestCase";

	@Test
	public void CreateAccountTestCase() {
		report=ExtentManager.getInstance();
		test=report.startTest(TCName);

		test.log(LogStatus.INFO, "Started excuting testcase -"+TCName);

		test.log(LogStatus.INFO, "Loading config file");
		loadConfig();

		/*
		 * selenium  code
		 */
		test.log(LogStatus.INFO, "Launching execution browser");
		launchBrowser();

		test.log(LogStatus.INFO, "Navigating to url");
		navigateTo();		
		
		//click on get started button without entering any field values
		waitUntilEnabled("GetStarted_ID");
		click("GetStarted_ID");

		//Full name field error
		String fullNameErrorActualMessage=getText("NameFieldError_ID");
		verifyText("Please specify your Name", fullNameErrorActualMessage);

		//Email field error
		String emailFieldErrorActualMessage=getText("EmailFieldError_ID");
		verifyText("Please enter valid email address", emailFieldErrorActualMessage);


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
