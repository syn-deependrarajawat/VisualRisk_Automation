/**
 * 
 */
package library;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * @author Deependra.Rajawat
 *
 */
public class Insights_lib extends BaseTest{

	static WebDriver driver;
	static WebElement element;

	public void verifyFieldsOnDashborad(String fieldName, String fieldType) throws Exception {
		boolean bflag = false;
		String sceenshotPath ="";
		try {
			driver = CommonLib.getDriver("");
			List<WebElement> tiles = driver.findElements(By.xpath("//div[contains(@class,'taBoardTile tile')]//a//div//span"));

			switch (fieldType.toLowerCase()) {

			case "right mainmenu":

				if(driver.findElement(By.xpath("//a[contains(text(),'"+fieldName.toUpperCase()+"')]")).isDisplayed())
					break;

			case "button":
				if (fieldName.contains("New Submission")) {
					if (driver.findElement(By.xpath("//*[@class='newSub']")).isDisplayed()
							&& driver.findElement(By.xpath("//*[@class='newSub']")).isEnabled())
						bflag = true;
					break;
				} 
				else if (fieldName.contains("Start")) {
					if (driver.findElement(By.xpath("//*[@class='startBtn']")).isDisplayed()
							&& driver.findElement(By.xpath("//*[@class='startBtn']")).isEnabled())
						bflag = true;
					break;
				}
			case "tile":


				try {
					try {

					} catch (Exception e) {
						// TODO: handle exception
					}
				} catch (Exception e) {
					// TODO: handle exception
				}


			default: 
				logger.info("invalid field type");
			}

			Assert.assertTrue(bflag, fieldName + " " + fieldType + " is not available on dashborad,Please check the provided name");
			sceenshotPath = CommonLib.takeScreenShot(Thread.currentThread().getStackTrace()[1].getMethodName());
			extentlogger.log(Status.PASS, Thread.currentThread().getStackTrace()[1].getMethodName()+" method is passed:- "+fieldName+" "+ fieldType+" is available on dashborad", MediaEntityBuilder.createScreenCaptureFromPath(sceenshotPath).build());
			logger.info(fieldName + " " + fieldType + " is available on dashborad");
		} catch (Exception e) {
			e.printStackTrace();
			extentlogger.log(Status.PASS, e, MediaEntityBuilder.createScreenCaptureFromPath(sceenshotPath).build());
		}
	}
}
