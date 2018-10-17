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
public class VerifyFieldsOnLocationWidget_Test extends BaseTest {

	static WebDriver driver;

	@Test
	public static void verifyVisualRiskDashBorad_Test() throws Exception {
		File RootPath = new File (System.getProperty("user.dir"));
		File filePath = new File(RootPath, "\\src\\test\\java\\resourses\\FileUpload_BareBurger.exe");
		commonLib.waitUntilUnmask(120);
		commonLib.uploadFile(filePath.toString());
		commonLib.verifyAndSaveApplicationForm();
		commonLib.waitUntilUnmask(120);
		commonLib.waitUntilWidgetUnmask(3);
		commonLib.getNoOfWidgetsOnThePage( "Insights");
		commonLib.clickOnTopMenu("Location");
		commonLib.getNoOfWidgetsOnThePage("Location");
		commonLib.logOut();
	}

}
