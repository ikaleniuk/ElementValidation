package core;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

@Title("First test suite")
@Description("In this suite we will do element's verification")
public class SignUp_POMTest {
	
	SignUpPage signUpPage;
	ConfirmationPage confirmationPage;
	String expectedTitle = "Welcome to Sign Up v1";
	String expectedConfirmationTitle = "Confirmation";
	
	WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		driver = new FirefoxDriver();
		signUpPage = new SignUpPage(driver);
		confirmationPage = new ConfirmationPage(driver);
		signUpPage.loadPage();
		
		PageFactory.initElements(driver, signUpPage);
	}
	
	@DataProvider(name = "elementIds")
	public static Object[][] elementIds() {
	   return new Object[][] {{"id_fname"}, {"id_lname"}, {"id_email"}, {"id_phone"}, {"id_state"}, {"id_quotes"}, 
		   {"id_f_title"}, {"id_current_location"},{"id_weather_icon"}, {"id_temperature"}, {"id_f_label_fn"}, 
		   {"id_f_label_ln"}, {"id_f_label_ea"}, {"id_f_label_pn"}, {"id_r_label_m"}, {"id_g_radio_01"}, 
		   {"id_r_label_f"}, {"id_g_radio_02"}, {"id_f_label_s"}, {"id_cb_label"}, {"id_checkbox"}, 
		   {"id_img_facebook"}, {"id_img_twitter"},{"id_img_flickr"}, {"id_img_youtube"}, {"id_reset_button"}, 
		   {"id_submit_button"}, {"timestamp"}, {"copyright"}, {"os_browser"}};
	}

	@Title("Check that element is present")
	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "elementIds")
	public void isElementPresentTest(String elementId) {
		Assert.assertTrue(signUpPage.isElementPresent(elementId), "Element is not present - " + elementId);
 	}
	
	@Title("Check that element is visible")
	@Test (dependsOnMethods = {"isElementPresentTest"}, dataProvider = "elementIds")
	public void isElementVisibleTest(String elementId) {
		Assert.assertTrue(signUpPage.isElementVisible(elementId), "Element is not visible");
	}
	
	@Title("Check that element is enabled")
	@Test (dependsOnMethods = {"isElementPresentTest"}, dataProvider = "elementIds")
	public void isElementEnabledTest(String elementId) {
		Assert.assertTrue(signUpPage.isElementEnabled(elementId), "Element is disabled");
	}
	
	@Title("Check element's dimension")
	@Severity(SeverityLevel.MINOR)
	@Test (dependsOnMethods = {"isElementPresentTest"}, dataProvider = "elementIds")
	public void getElementDimensionTest(String elementId) {
		System.out.println("Location X: " + signUpPage.elementLocation(elementId).getX());
		System.out.println("Location Y: " + signUpPage.elementLocation(elementId).getY());
	}
	
	@Title("Check element's size")
	@Test (dependsOnMethods = {"isElementPresentTest"}, dataProvider = "elementIds")
	public void getElementSizeTest(String elementId) {
		System.out.println("Size Width: " + signUpPage.elementSize(elementId).getWidth());
		System.out.println("Size Height: " + signUpPage.elementSize(elementId).getHeight());
	}
	
//	@AfterMethod 
//	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException { 
//		if (testResult.getStatus() == ITestResult.FAILURE) { 
//			String filePath = "/Users/ikaleniuk/Workspace/ElementValidation";
//			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
//			try {
//				FileUtils.copyFile(scrFile, new File(filePath + "test.png"));
//				System.out.println("***Placed screen shot in " + filePath + " ***");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			System.out.println(testResult.getStatus()); 
//		} 
//	}
	
	@AfterClass
	public void tearDown() {
		signUpPage.closeBrowser();
	}

}
