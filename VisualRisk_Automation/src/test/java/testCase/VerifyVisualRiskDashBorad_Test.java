/**
 * @TCName:- VerifyVisualRiskDashBorad_Test
 * @Description:- This test case verify the E2E flow for an existing form
 * @author Deependra.Rajawat
 * @CreationDate:	10-Oct-2018
 */
package testCase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import library.BaseTest;
import library.CustomListener;

@Listeners(CustomListener.class)
public class VerifyVisualRiskDashBorad_Test extends BaseTest {

	static WebDriver driver;

	@Test
	public static void verifyVisualRiskDashBorad_Test() throws Exception {
		commonLib.waitUntilWidgetUnmask(2);
		dashboard_Lib.verifyFieldsOnDashborad("Submissions", "tile");
		dashboard_Lib.verifyFieldsOnDashborad("Quotes", "tile");
		dashboard_Lib.verifyFieldsOnDashborad("Bound", "tile");
		dashboard_Lib.verifyFieldsOnDashborad("Declined", "tile");
		dashboard_Lib.verifyFieldsOnDashborad("AVG.TIME TO QUOTE", "tile");
		dashboard_Lib.verifyFieldsOnDashborad("Start", "button");
		dashboard_Lib.verifyFieldsOnDashborad("New Submission", "button");
		dashboard_Lib.verifyFieldsOnDashborad("Submissions", "grid");
		commonLib.logOut();
	}

}
