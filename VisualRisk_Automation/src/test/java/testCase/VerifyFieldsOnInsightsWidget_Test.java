/**
 * @TCName:- VerifyVisualRiskDashBorad_Test
 * @Description:- This test case verify the E2E flow for an existing form
 * @author Deependra.Rajawat
 * @CreationDate:	10-Oct-2018
 */
package testCase;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import library.BaseTest;
import library.CustomListener;

@Listeners(CustomListener.class)
public class VerifyFieldsOnInsightsWidget_Test extends BaseTest {

	static WebDriver driver;

	@Test
	public static void verifyFieldsOnInsightsWidget_Test() throws Exception {

		File filePath = new File(System.getProperty("user.dir")+ "\\src\\test\\java\\resourses\\FileUpload_BareBurger.exe");
		commonLib.waitUntilUnmask(30);
		commonLib.uploadFile(filePath.toString());
		commonLib.verifyAndSaveApplicationForm();
		commonLib.waitUntilUnmask(120);
		commonLib.waitUntilWidgetUnmask(3);
		commonLib.clickOnTopMenu("Insights");
		commonLib.getNoOfWidgetsOnThePage("Insights");
		commonLib.clickOnWidget("BAREBURGER.COM");
		commonLib.closeWidget();
		commonLib.logOut();
	}

}
