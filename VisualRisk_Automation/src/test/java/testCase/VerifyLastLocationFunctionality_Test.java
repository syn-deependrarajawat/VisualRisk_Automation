/**
 * @TCName:- VerifyLastLocationFunctionality_Test
 * @Description:- This test case verify the functionality of storing last location of an entity
 * @author Deependra.Rajawat
 * @CreationDate:	18-Oct-2018
 */

package testCase;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import library.BaseTest;
import library.CustomListener;

@Listeners(CustomListener.class)
public class VerifyLastLocationFunctionality_Test extends BaseTest {

	static WebDriver driver;
	static WebElement element;
	static String applicationNumber = "";
	static String tabName = "EXPERIAN";

	@Test
	public static void verifyLastLocationFunctionality() throws Exception {

		File RootPath = new File (System.getProperty("user.dir"));
		File filePath = new File(RootPath, "\\src\\test\\java\\resourses\\FileUpload_BareBurger.exe");
		commonLib.waitUntilUnmask(120);
		applicationNumber = commonLib.uploadFile(filePath.toString());
		commonLib.verifyAndSaveApplicationForm();
		commonLib.waitUntilUnmask(120);
		commonLib.clickOnTopMenu(tabName);
		commonLib.waitUntilWidgetUnmask(3);
		commonLib.logOut();

		commonLib.logIn(false);
		commonLib.waitUntilUnmask(80);
		String [] array = applicationNumber.split(" ");

		commonLib.waitUntilWidgetUnmask(1);
		commonLib.clickOnSubmissionsNoLink(array[array.length-1]);//
		commonLib.waitUntilWidgetUnmask(1);
		String tabname1 = commonLib.getCurrentTab();
		commonLib.compareValues(tabname1, tabName, true);
		commonLib.logOut();
	}
}
