/**
 * @TCName:- Verify_QuoteCreationFlow_Test
 * @TestDescription:- This test case verify the E2E flow for creating and sending a quote
 * @author Deependra.Rajawat
 * @CreationDate:	09-Oct-2018
 */

package testCase;

import java.io.File;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import library.BaseTest;
import library.CustomListener;

@Listeners(CustomListener.class)
public class Verify_QuoteCreationFlow_Test extends BaseTest{

	@Test
	public void verify_QuoteCreationFlow_Test() throws Exception {

		File filePath = new File(System.getProperty("user.dir")+ "\\src\\test\\java\\resourses\\FileUpload_BareBurger.exe");
		commonLib.waitUntilUnmask(30);
		commonLib.uploadFile(filePath.toString());
		commonLib.verifyAndSaveApplicationForm();
		commonLib.waitUntilUnmask(120);
		commonLib.createQuote("1000");
		commonLib.logOut();
	}
}
