package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import library.BaseTest;

public class Common_App_Objects_OR extends BaseTest {
	static WebDriver driver;
	static WebElement element;

	public static final By bySaveButton = By.xpath("//input[@class='saveBtn comBtn']");

	public static final By byMaskImage = By.xpath("//div[contains(@class,'greyOverlay overLeyFullPage')]");
	public static final By byWidgetHeaders = By.xpath("html/body/div[1]/div/div[4]/div/div/div[2]/div/div//h2");

	public static final By byTopMenus = By.xpath("//div[@class='container']//ul[@class='topMenu']//li");
	public static final By byTableRows = By.xpath("html//table/tbody/tr");
	public static final By byColumns = By.xpath("html//table/tbody/tr[1]/td");
	public static final By byErrorMessage = By.xpath("//div[@class='toast-message']");
	public static final By byInsighTopMenu = By.xpath("//*[@class='topMenu']//a[contains(text(),'INSIGHT')]");
	public static final By byInvalidFields = By.xpath("//div[contains(@class,'quoteRightWrap')]//*[@aria-invalid='true']");
	public static final By byTotalEstimated = By.name("totalEstimated");
	public static final By byNextButton = By.xpath("//input[@value='Next']");
	public static final By byCreateQuote = By.xpath("//a[contains(text(),'CREATE QUOTE')]");
	public static final By bySimilarQuote = By.xpath("//a[@class='active' and contains(text(),'Similar Quotes')]");
	public static final By bySuccessMessage = By.xpath("//p[contains(text(),'Quote has been sent successfully to')]");
	public static final By byWidgetMaskImage = By.xpath("//div[@class='greyOverlay boxOverlay']//div[@class='gif']");
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;
	//
	// public static final By byBrowseFileButton = ;
	// public static final By byBrowseFileButton = ;

	public static WebElement clickOnButton(String buttonName) {
		try {
			driver = BaseTest.getDriver("");
			element = driver.findElement(
					By.xpath("//input[@type='submit' or @type='button' and contains(@value,'" + buttonName + "')]"));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	public static WebElement clickOnTopMenu(String menuName) {
		try {
			driver = BaseTest.getDriver("");
			element = driver.findElement(
					By.xpath("//*[@class='topMenu']//li//a[contains(text(),'" + menuName.toUpperCase() + "')]"));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

}
