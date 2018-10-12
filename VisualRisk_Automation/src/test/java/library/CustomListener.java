package library;

import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class CustomListener extends BaseTest implements ITestListener{

	CommonLib commonLib = new CommonLib();

	public void onTestStart(ITestResult result) {
		extentlogger.log(Status.INFO, "Test "+result.getMethod().getMethodName()+" is started");
	}

	public void onTestSuccess(ITestResult result) {
		CommonLib.takeScreenShot(result.getMethod().getMethodName());
//		extentlogger.log(Status.PASS, "Test "+result.getMethod().getMethodName()+" is Passed");
	}

	public void onTestFailure(ITestResult result) {
		CommonLib.takeScreenShot(result.getMethod().getMethodName());
		extentlogger.log(Status.FAIL, "Test "+result.getMethod().getMethodName()+" is Failed");
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
//		extentlogger.log(Status.INFO, "Execution is started");


	}

	public void onFinish(ITestContext context) {
//		extentlogger.log(Status.INFO, "Execution is Completed");

	}

}
