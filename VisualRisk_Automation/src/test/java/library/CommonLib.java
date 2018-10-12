package library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import objectRepository.Common_App_Objects_OR;
import objectRepository.HomePage_OR;
import objectRepository.LoginPage_OR;

public class CommonLib extends BaseTest {

	static WebDriver driver;
	static WebElement element;
	static String screenShotPath;
	static String screenShotPath1;
	static JavascriptExecutor jse;

	public void logIn(boolean isNegative) throws IOException {

		try {
			driver = BaseTest.getDriver("");
			File file = new File("environment.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fileInput);

			String userName = prop.getProperty("username");
			String password = prop.getProperty("password");
			driver.findElement(LoginPage_OR.byUserName).clear();
			driver.findElement(LoginPage_OR.byUserName).sendKeys(userName);
			driver.findElement(LoginPage_OR.byPassword).clear();
			driver.findElement(LoginPage_OR.byPassword).sendKeys(password);
			driver.findElement(LoginPage_OR.byLoginButton).click();
			logger.info("Entered user name, password and clicked on login button");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

			if (isNegative) {
				try {
					WebElement error = driver.findElement(LoginPage_OR.byError);
					Assert.assertTrue(error.isDisplayed(), "Able to login successfully");
					extentlogger.log(Status.INFO, MarkupHelper.createLabel("**Login test Passed**", ExtentColor.GREEN));

				} 
				catch (Exception e) {
					WebDriverWait wait = new WebDriverWait(driver, 120);
					wait.until(ExpectedConditions.elementToBeClickable(By.className("startBtn")));
					element = driver.findElement(LoginPage_OR.byWelcomeText);
					extentlogger.log(Status.INFO, MarkupHelper.createLabel("Error message not displayed", ExtentColor.RED));
					Assert.assertFalse(element.isDisplayed(), "Able to login");
				}
			} 
			else {
				driver.manage().timeouts().pageLoadTimeout(7, TimeUnit.SECONDS);
				WebDriverWait wait = new WebDriverWait(driver, 120);
				wait.until(ExpectedConditions.elementToBeClickable(By.className("startBtn")));
				element = driver.findElement(LoginPage_OR.byWelcomeText);
				Assert.assertTrue(element.isDisplayed(), "Unable to login");
				screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
				extentlogger.pass(
						Thread.currentThread().getStackTrace()[1].getMethodName()
						+ " method is passed:- Logged in successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath,screenShotPath1).build());
				//extent.flush();
			}
		} catch (Exception e) {
			extentlogger.fail(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " method is failed, Exception occured",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			e.printStackTrace();
		}
	}

	public void logOut() {
		boolean bFlag = false;
		try {
			element = driver.findElement(HomePage_OR.byLogOutImg);
			element.click();
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.info("clicked on logout image successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath,screenShotPath1).build());
			Thread.sleep(1500);
			String screenShotPath1 = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			element.findElement(HomePage_OR.byLogOut).click();
			driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
			bFlag = true;
			Assert.assertTrue(bFlag, "Could not logged out successfully");
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.pass(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " method is pass:- Logged out successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath,screenShotPath1).build());
			logger.info("Logged out successfully");
			//extent.flush();
		} catch (Exception e) {
			extentlogger.fail(e);
			e.printStackTrace();
		}

	}

	public void clickOnSaveButton() {
		try {
			List<WebElement> saveButtons = driver.findElements(Common_App_Objects_OR.bySaveButton);
			saveButtons.get(1).click();
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.pass(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " method is passed:- Clicked on Save Button successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitUntilUnmask(long timeinSeconds) {
		boolean bflag = false;
		try {
			WebElement mask = driver.findElement(Common_App_Objects_OR.byMaskImage);
			Date date = new Date();
			System.out.println("Time " + date);
			while (true) {
				if (mask.getAttribute("aria-hidden").equalsIgnoreCase("true")|| System.currentTimeMillis() > (date.getTime() + timeinSeconds * 1000)) {
					logger.info("Loading mask disappeared: " + mask.getAttribute("aria-hidden"));
					bflag = true;
					Assert.assertTrue(bflag, "page is masked");
					extentlogger.info( "page is un-masked successfully");
					break;
				}
				logger.info("Loading mask disappeared: " + mask.getAttribute("aria-hidden"));
				continue;
			}
			takeScreenShot("");
		} catch (Exception e) {
		}
	}

	public void waitUntilWidgetUnmask(long timeinMins) {
		boolean bflag = false;
		try {
			List<WebElement> masks = driver.findElements(Common_App_Objects_OR.byWidgetMaskImage);

			for(WebElement mask :masks){
				Date date = new Date();
				while (true) {
					if (!mask.isDisplayed()|| System.currentTimeMillis() > (date.getTime() + timeinMins * 1000*60)) {
						logger.info("Loading mask disappeared for all widgets: " + mask.getAttribute("aria-hidden"));
						bflag = true;
						Assert.assertTrue(bflag, "widget is masked");
						break;
					}
					continue;
				}
			}
			logger.info( "All widgets un-masked successfully");
			extentlogger.info( "All widgets un-masked successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void getNoOfWidgetsOnThePage() {

		try {
			List<WebElement> widgestHeaders = driver.findElements(Common_App_Objects_OR.byWidgetHeaders);
			for (WebElement widgetHeader : widgestHeaders) {
				logger.info("Widget Header is: " + widgetHeader.getText());
			}
			logger.info("Total widgets on the page are: " + widgestHeaders.size());
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.info(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " method is passed, Total widgets on the page are: " + widgestHeaders.size(),
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String uploadFile(String filePath) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.elementToBeClickable(HomePage_OR.byStartButton));
			driver.findElement(HomePage_OR.byStartButton).click();
			driver.findElement(HomePage_OR.byBrowseFileButton).click();
			Thread.sleep(1000);
			Runtime.getRuntime().exec(filePath);

			element = driver.findElement(HomePage_OR.byProceedButton);
			wait.until(ExpectedConditions.presenceOfElementLocated((HomePage_OR.byProceedButton)));
			wait.until(ExpectedConditions.elementToBeClickable(HomePage_OR.byProceedButton));
			element.click();
			logger.info("Form uploaded successfully");
			waitUntilUnmask(180);
			element = driver.findElement(Common_App_Objects_OR.bySaveButton);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((Common_App_Objects_OR.bySaveButton)));
			wait.until(ExpectedConditions.elementToBeClickable(Common_App_Objects_OR.bySaveButton));
			String appNo = driver.findElement(HomePage_OR.byApplicationNum).getText();
			driver.findElement(HomePage_OR.byApplicationNum).getAttribute("Value");
			Assert.assertNotNull(appNo, "Form not uploaded successfully and unable to fetch Application no");
			logger.info(appNo);
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.pass(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " method is passed:- uploaded form successfully with " + appNo,
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			logger.info("uploaded form successfully with " + appNo);

		} catch (Exception e) {
			e.printStackTrace();
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.fail(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ ": is failed, could not upload form successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());

		}
		return filePath;
	}

	public void verifyAndSaveApplicationForm() {
		try {

			List<WebElement> saveButtons = driver.findElements(Common_App_Objects_OR.bySaveButton);
			saveButtons.get(1).click();
			Thread.sleep(1000);
			try {
				if (driver.findElement(Common_App_Objects_OR.byErrorMessage).isDisplayed()) {
					extentlogger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
							+ "Error displayed while saving Application form " + "Error message: "
							+ driver.findElement(Common_App_Objects_OR.byErrorMessage).getText() + " is displayed",
							MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
					logger.info("Error message: "
							+ driver.findElement(Common_App_Objects_OR.byErrorMessage).getText() + " is displayed");
					driver.findElement(Common_App_Objects_OR.byErrorMessage).click();
					enterDataInBizApplication();
					saveButtons = driver.findElements(Common_App_Objects_OR.bySaveButton);
					saveButtons.get(1).click();
					waitUntilUnmask(120);
				}
				waitUntilUnmask(120);
				Assert.assertEquals(driver.findElement(Common_App_Objects_OR.byInsighTopMenu).isDisplayed(), true,
						"Application form not saved successfully");
				screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
				extentlogger.pass(
						Thread.currentThread().getStackTrace()[1].getMethodName()
						+ " method is passed:- Application form saved successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enterDataInBizApplication() {
		try {
			List<WebElement> textBoxes = driver.findElements(Common_App_Objects_OR.byInvalidFields);
			for (WebElement textBox : textBoxes) {
				String isInvalid = textBox.getAttribute("aria-invalid");
				if (Boolean.parseBoolean(isInvalid) == true) {
					String sField = driver.findElement(By.xpath("//div[contains(@class,'quoteRightWrap')]//*[ @aria-invalid='true']/../label")).getText();
					logger.info("Entering data into :" + sField + " field as its missing in form");
					if (sField.equalsIgnoreCase("State") || sField.equalsIgnoreCase("Country")) {
						WebElement field = driver.findElement(By.xpath("//select[@id='App" + sField + "']"));
						Select statedrpdwn = new Select(field);
						List<WebElement> options = statedrpdwn.getOptions();
						Random rand = new Random();
						int index = rand.nextInt() * options.size();
						statedrpdwn.selectByIndex(index);
					}
					textBox.sendKeys("Dummy data for " + sField + " field");
				}
			}
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.info("Entered missing data in Application form",MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clickOnTopMenu(String menuName) {
		boolean bFlag = false;
		try {
			List<WebElement> menuList = driver.findElements(Common_App_Objects_OR.byTopMenus);
			for (WebElement menu : menuList) {
				if (menu.getText().trim().toLowerCase().contains(menuName.toLowerCase())) {

					jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].scrollIntoView(true);",menu);
					menu.click();
					bFlag = true;
					waitUntilUnmask(10);
					Assert.assertEquals(bFlag, true, "Could not click on: " + menuName);

					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//img[@alt='logo']")));
					screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
					extentlogger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
							+ " method is passed:- Clicked on "+ menuName + " menu successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.fail(MarkupHelper.createLabel("In "+Thread.currentThread().getStackTrace()[1].getMethodName()+" method Exception occured"+"\n"+e.getMessage(), ExtentColor.RED));
		}
	}

	public void clickOnWidget(String headrName) throws IOException {
		try {
			List<WebElement> widgestHeaders = driver.findElements(Common_App_Objects_OR.byWidgetHeaders);
			for (WebElement widgetHeader : widgestHeaders) {
				if (widgetHeader.getText().trim().toLowerCase().contains(headrName.toLowerCase())) {
					jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].scrollIntoView(true);",widgetHeader);
					widgetHeader.click();
					waitUntilUnmask(60);
					String strFirstWindowHandle = driver.getWindowHandle();
					Set<String> setWindowHandles = driver.getWindowHandles();
					for (String WindowHandle : setWindowHandles) {
						if (!WindowHandle.equals(strFirstWindowHandle)) {
							driver.switchTo().window(WindowHandle);
							WebDriverWait wait = new WebDriverWait(driver, 90);
							wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("logoImage"))));
							Assert.assertTrue(driver.findElement(By.className("logoImage")).isDisplayed(), "Not clicked on the header"+ headrName );
							screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
							extentlogger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
									+ "method is passed:- Clicked on widget " + headrName + " successfully and navigated to: "+driver.getTitle(),
									MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
							driver.switchTo().window(strFirstWindowHandle);
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.fail(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ "Unable to click on " + headrName ,
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		}

	}

	public void closeWidget() throws IOException {
		try {
			String strFirstWindowHandle = driver.getWindowHandle();
			Set<String> setWindowHandles = driver.getWindowHandles();
			for (String strWindowHandle : setWindowHandles) {
				if (!strWindowHandle.equals(strFirstWindowHandle)) {
					driver.switchTo().window(strWindowHandle);
					String pageTitle = driver.getTitle();
					logger.info("successfully opened: " + pageTitle);
					screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
					driver.close();
					driver.switchTo().window(strFirstWindowHandle);
					jse = (JavascriptExecutor)driver;
					jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//img[@alt='logo']")));
					screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
					Assert.assertEquals(driver.getTitle(), "Visual Risk | Streamlining your Underwriting Process",
							"Could not closed widget or unable to navigate to main window");
					extentlogger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
							+ "method is passed:- Closed widget "+pageTitle+" and navigated to: " + driver.getTitle() + " Successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.fail(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ "Unable to close widget",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		}
	}

	public void clickOnSubmissionsNoLink(int rowNum) throws IOException {
		try {
			driver.findElement(By.xpath("//*[contains(text(),'Submissions')]/../following-sibling::div//table/tbody/tr[" + rowNum + "]//a")).click();
			waitUntilUnmask(120);
			Assert.assertEquals(driver.findElement(HomePage_OR.byApplicationNum).isDisplayed(), true,
					"Could not click on submission link row no:" + rowNum);
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.pass(Thread.currentThread().getStackTrace()[1].getMethodName()+ "method is passed:- Clicked on the submission no link successfully",MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());

		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.fail(
					Thread.currentThread().getStackTrace()[1].getMethodName()
					+ "Unable to clicke on the submission no link successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		}
	}

	public String fetchDataFromTableColumn() {
		try {
			Thread.sleep(4000);
			List<WebElement> tableRows = driver.findElements(Common_App_Objects_OR.byTableRows);
			List<WebElement> tableColumns = driver.findElements(Common_App_Objects_OR.byColumns);
			for (int i = 1; i <= tableRows.size(); i++) {
				for (int j = 1; j <= tableColumns.size(); j++) {
					System.out.print(
							driver.findElement(By.xpath("html//table/tbody/tr[" + i + "]/td[" + j + "]")).getText()+ "\t");
				}
				System.out.println();
			}
			getTableRowcount();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public String fetchDataFromWidget(String WidgetHeader, String field) {
		try {
			Thread.sleep(4000);
			// (//div[contains(@class,'ratingBlock
			// ')]//span[contains(text(),'Overall
			// rating')]/preceding-sibling::span)
			List<WebElement> widgets = driver.findElements(Common_App_Objects_OR.byWidgetHeaders);
			driver.findElements(Common_App_Objects_OR.byColumns);
			for (WebElement widget : widgets) {
				if (widget.getText().contains(WidgetHeader)) {

				}
			}
			getTableRowcount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public int getTableRowcount() {

		List<WebElement> tableRows = new ArrayList<WebElement>();
		try {
			tableRows = driver.findElements(Common_App_Objects_OR.byTableRows);
			System.out.println("No of rows in the table" + tableRows.size());
			Assert.assertEquals(tableRows.size(), tableRows.size() > 0, "no table row found");
			extentlogger.info("Fetched no of rows successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableRows.size();
	}

	public List<WebElement> waitForFormElementsOnPageToLoad(int iTimeInSecond) {
		List<WebElement> elements = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, iTimeInSecond);
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(new ByChained(By.xpath("//input[@type='checkbox']"),
							By.xpath("//input[@type='button']"), By.xpath("//input[@type='text']"))));
			elements = driver.findElements(new ByChained(By.xpath("//input[@type='checkbox']"),
					By.xpath("//input[@type='button']"), By.xpath("//input[@type='text']")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elements;
	}

	public static void tearDown() {
		driver.quit();
		System.out.println("Closed browser successfully");
	}

	public static String takeScreenShot(String sPath) {
		String path = "";
		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			path = "C:\\ScreenShot\\" + sPath + "_" + new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(new Date())+".png";
			File file = new File(path);
			FileUtils.copyFile(screenshotFile, file);
		} catch (IOException e) {
			logger.info("Capture failed " + e.getMessage());
		}
		return path;
	}

	public static WebElement clickOnButton(String buttonName) {
		try {
			driver = BaseTest.getDriver("");
			element = driver.findElement(
					By.xpath("//input[@type='submit' or @type='button' and contains(@value,'" + buttonName + "')]"));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	public void createQuote(String totalEstimatedValue) throws IOException {

		String user = "";

		try {
			WebDriverWait wait = new WebDriverWait(driver, 45);
			driver.findElement(Common_App_Objects_OR.byCreateQuote).click();
			WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Similar Quotes')]"));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			driver.findElement(Common_App_Objects_OR.byNextButton).click();
			element = driver.findElement(By.xpath("//*[contains(text(),'Location')]"));
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.findElement(Common_App_Objects_OR.byTotalEstimated).sendKeys(totalEstimatedValue);
			logger.info("Entered Total Estimated Annual amt: "+totalEstimatedValue);
			driver.findElement(Common_App_Objects_OR.byNextButton).click();

			element = driver.findElement(By.xpath("//*[contains(text(),'Questionnaire')]"));
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.findElement(Common_App_Objects_OR.byNextButton).click();
			element = driver.findElement(By.xpath("//*[contains(text(),'UNDERLYING COVERAGE')]"));
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.findElement(Common_App_Objects_OR.byNextButton).click();

			element = driver.findElement(By.xpath("//*[contains(text(),'Prior Policies')]"));
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.findElement(Common_App_Objects_OR.byNextButton).click();

			element = driver.findElement(By.xpath("//*[contains(text(),'Conditions')]"));
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.findElement(Common_App_Objects_OR.byNextButton).click();

			element = driver.findElement(By.xpath("//*[contains(text(),'Generated New Business Quote')]"));
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.findElement(Common_App_Objects_OR.byNextButton).click();

			element = driver.findElement(By.xpath("//*[contains(text(),'Ready to send Quote')]"));
			wait.until(ExpectedConditions.visibilityOf(element));

			Common_App_Objects_OR.clickOnButton("Send");
			Assert.assertTrue(driver.findElement(Common_App_Objects_OR.bySuccessMessage).isDisplayed(), "Quote not send successfully");
			user = driver.findElement(Common_App_Objects_OR.bySuccessMessage).getText();
			String []array = user.split(" ");
			screenShotPath = takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.pass(Thread.currentThread().getStackTrace()[1].getMethodName()+ " method is passed:- Successfully send the quotes to "+array[array.length-1], MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			logger.info("Successfully send the Quote to: "+array[array.length-1]);
		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.fail(e, MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		}
	}

}
