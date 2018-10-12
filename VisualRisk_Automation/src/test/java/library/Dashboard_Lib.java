package library;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class Dashboard_Lib extends BaseTest{

	static WebDriver driver;
	static WebElement element;

	public void verifyFieldsOnDashborad(String fieldName, String fieldType) throws Exception {
		boolean bflag = false;
		String sceenshotPath ="";
		try {
			driver = CommonLib.getDriver("");
			List<WebElement> tiles = driver.findElements(By.xpath("//div[contains(@class,'taBoardTile tile')]//a//div//span"));

			switch (fieldType.toLowerCase()) {

			case "tile":

				if(fieldName.contains("AVG.TIME TO QUOTE")){
					if(driver.findElement(By.xpath("//div[@class= 'graphWrap']//*[contains(text(),'AVG.TIME TO QUOTE')]")).isDisplayed())
						bflag =true;
					break;
				}
				else{

					for (WebElement tile : tiles) {
						String sName = tile.getText();
						if (sName.toLowerCase().contains(fieldName.toLowerCase())) {
							bflag = true;
							break;
						}
					}
				}
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
			case "grid":

				if (driver.findElement(By.xpath("//div[@class= 'taBoardGridWrap']//*[contains(text(),'" + fieldName + "')]")).isDisplayed())
					bflag = true;
				break;

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
