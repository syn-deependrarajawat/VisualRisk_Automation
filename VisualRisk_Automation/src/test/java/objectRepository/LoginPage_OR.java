package objectRepository;

import org.openqa.selenium.By;

public class LoginPage_OR {
	public static final By byError = By.xpath("//div[@class='loginFormWrap']//label[contains(text(),'Please Enter Valid UserName and Password')]");
	public static final By byUserName = By.id("userid");
	public static final By byPassword = By.id("loginpsd");
	public static final By byLoginButton = By.xpath("//input[@value='Sign In']");
	public static final By byWelcomeText = By.xpath("//*[contains(text(),'Welcome')]"); 
	
	
	

}
