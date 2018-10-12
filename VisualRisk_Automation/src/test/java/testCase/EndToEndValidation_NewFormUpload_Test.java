/**
 * @TCName:- EndToEndValidation_NewFormUpload_Test
 * @TestDescription:- This test case verify the E2E flow for a new form
 * @author Deependra.Rajawat
 * @CreationDate:	01-Oct-2018
 */

package testCase;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import library.BaseTest;
import library.CommonLib;
import library.CustomListener;

@Listeners(CustomListener.class)
public class EndToEndValidation_NewFormUpload_Test extends BaseTest {

	static WebDriver driver ;
	static WebElement element ;

	@Test
	public static void endToEndValidation_NewFormUpload_Test() throws Exception  {
			File RootPath = new File (System.getProperty("user.dir"));
			File filePath = new File(RootPath, "\\src\\test\\java\\resourses\\FileUpload_BareBurger.exe");
			commonLib.waitUntilUnmask(120);
			commonLib.uploadFile(filePath.toString());
			commonLib.verifyAndSaveApplicationForm();
			commonLib.waitUntilUnmask(120);
			commonLib.getNoOfWidgetsOnThePage();
			commonLib.clickOnTopMenu( "Location");
			commonLib.clickOnTopMenu( "Insights");
			commonLib.clickOnWidget("BAREBURGER.COM");
			commonLib.closeWidget();
			commonLib.waitUntilUnmask(120);
			commonLib.logOut();

	}
}











