/**
 * @TCName:- EndToEndValidation_ExistingForm_Test
 * @Description:- This test case verify the E2E flow for an existing form
 * @author Deependra.Rajawat
 * @CreationDate:	01-Oct-2018
 */

package testCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import library.BaseTest;
import library.CustomListener;

@Listeners(CustomListener.class)
public class EndToEndValidation_ExistingForm_Test extends BaseTest {

	static WebDriver driver;
	static WebElement element;

	@Test
	public static void endToEndValidation_ExistingForm_Test() throws Exception {

		commonLib.waitUntilUnmask(120);
		commonLib.clickOnSubmissionsNoLink(2);
		commonLib.waitUntilUnmask(120);
		commonLib.clickOnSaveButton();
		commonLib.waitUntilUnmask(120);
		commonLib.waitUntilWidgetUnmask(3);
		commonLib.getNoOfWidgetsOnThePage();
		commonLib.clickOnTopMenu("Location");
		commonLib.getNoOfWidgetsOnThePage();
		commonLib.clickOnTopMenu("Insights");
		commonLib.clickOnWidget("BAREBURGER.COM");
		commonLib.closeWidget();
		commonLib.logOut();
	}
}
