/*
 *Base test is the class file which is  
 * 
 */
package library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import objectRepository.LoginPage_OR;

public class BaseTest {

	static WebDriver driver = null;
	static WebElement element = null;


	public static CommonLib commonLib;
	public static LoginPage_OR loginPage;
	public static Dashboard_Lib dashboard_Lib;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest extentlogger ;
	public static String userDir ;
	public static String fileName ;
	public static Logger logger;

	@BeforeSuite
	public void beforeSuite() {
		initialize();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy-hh_mm_ss");

		//Setup for Extent reporting
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\src\\test\\java\\htmlReport\\HTMLReport-" + formater.format(calendar.getTime()) + ".html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		//Setup for log4j logging
		userDir = System.getProperty("user.dir");
		fileName =userDir+"\\log4j.properties";
		logger = Logger.getLogger(Logger.class.getName());
		PropertyConfigurator.configure(fileName);
	}


	@Parameters({"browser", "testName", "isNegative"})
	@BeforeMethod
	public static WebDriver setUp(String browser, String testName, boolean isNegative) {
		try {

			extentlogger = extent.createTest(testName, testName+"- description");
			extentlogger.info("Reporting started");
			getDriver(browser);
			extentlogger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" method is passed");
			commonLib.logIn(isNegative);
		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.info("Exception occured in "+Thread.currentThread().getStackTrace()[1].getMethodName()+" method");

		}
		return driver;
	}


	@AfterMethod
	public void afterMethod(ITestResult result) {

		try {
			if(result.getStatus()==ITestResult.FAILURE ){
				extentlogger.info(MarkupHelper.createLabel(result.getMethod().getMethodName()+" is completed and its failed ", ExtentColor.RED));
				extentlogger.fail(result.getThrowable().getMessage());
			}
			else if(result.getStatus()==ITestResult.SUCCESS){
				extentlogger.pass(MarkupHelper.createLabel(result.getMethod().getMethodName()+" is completed and its passed", ExtentColor.GREEN));
			}
			else if(result.getStatus()==ITestResult.SKIP){
				extentlogger.pass(MarkupHelper.createLabel(result.getMethod().getMethodName()+" is Skipped", ExtentColor.GREY));
			}
			extent.flush();
			driver.quit();
			logger.info("Closed the browser");
			driver= null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}



	@AfterSuite//(alwaysRun = true)
	public void afterSuite() {
		extent.flush();
		extent= null;
	}

	/** @Methodname :- getDriver
	 * @param browser
	 * @throws 
	 * @Description :- This method is used to launch a browser and returns the webdriver driver object
	 * @Author :- Deependra Rajawat
	 * @Creation_Date:- 25-Sep-18
	 * @Modified_By :-
	 * @Modification_date :-
	 */
	public static WebDriver getDriver(String browser) {
		try {
			File file = new File("environment.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fileInput);
			String url = prop.getProperty("url");
			if (driver == null) {
				if (browser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\java\\resourses\\chromedriver.exe");
					driver = new ChromeDriver();
					extentlogger.info("Launched chrome browser");
				}
				if (browser.equalsIgnoreCase("firefox")) {
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\java\\resourses\\geckodriver.exe");
					driver = new FirefoxDriver();
					extentlogger.info("Launched firefox browser");
				}
				if (browser.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\java\\resourses\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					extentlogger.info("Launched IE browser");
				}
				driver.get(url);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}

		} catch (Exception e) {
			System.out.println("exception occured");
			e.printStackTrace();
		}
		return driver;

	}
	
	public static void initialize() {
		commonLib = new CommonLib();
		dashboard_Lib = new Dashboard_Lib();
		loginPage = new LoginPage_OR();

	}

}
