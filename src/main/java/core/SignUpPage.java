package core;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SignUpPage {
	
	private WebDriver driver;
	String baseUrl = "http://learn2test.net/qa/apps/sign_up/v1/";
	
	@FindBy(how = How.ID, using = "id_link_facebook")
	@CacheLookup
	List<WebElement> facebookLinks;
	
	@FindBy(how = How.ID, using = "id_link_twitter")
	@CacheLookup
	List<WebElement> twitterLinks;
	
	@FindBy(how = How.ID, using = "id_link_flickr")
	@CacheLookup
	List<WebElement> flickrLinks;
	
	@FindBy(how = How.ID, using = "id_link_youtube")
	@CacheLookup
	List<WebElement> youtubeLinks;
	
	@FindBy(how = How.ID, using = "id_submit_button")
	@CacheLookup
	WebElement submitButton;
	
	@FindBy(how = How.ID, using = "ErrorLine")
	@CacheLookup
	WebElement errorLine;
	
	@FindBy(how = How.ID, using = "id_fname")
	@CacheLookup
	WebElement first_name;
	
	@FindBy(how = How.ID, using = "id_lname")
	@CacheLookup
	WebElement last_name;
	
	@FindBy(how = How.ID, using = "id_email")
	@CacheLookup
	WebElement email_address;
	
	@FindBy(how = How.ID, using = "id_phone")
	@CacheLookup
	WebElement phone_number;
	
	@FindBy(how = How.ID, using = "id_g_radio_01")
	@CacheLookup
	WebElement male;
	
	@FindBy(how = How.ID, using = "id_g_radio_02")
	@CacheLookup
	WebElement female;
	
	@FindBy(how = How.ID, using = "id_state")
	@CacheLookup
	WebElement current_state;
	
	@FindBy(how = How.ID, using = "id_checkbox")
	@CacheLookup
	WebElement privacy_checkbox;
	
	@FindBy(how = How.ID, using = "id_reset_button")
	@CacheLookup
	WebElement resetButton;
	
	@FindBy(how = How.ID, using = "id_quotes")
	WebElement quotes;
	
	@FindBy(how = How.ID, using = "id_current_location")
	@CacheLookup
	WebElement currentLocation;
	
	@FindBy(how = How.ID, using = "id_weather_icon")
	@CacheLookup
	WebElement weatherIcon;
	
	@FindBy(how = How.ID, using = "timestamp")
	@CacheLookup
	WebElement timestamp;
	
	@FindBy(how = How.ID, using = "os_browser")
	@CacheLookup
	WebElement osBrowser;
	
	public SignUpPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean verifyTitle(String expectedTitle) {
		String actualTitle = driver.getTitle();
		return expectedTitle.equals(actualTitle);
	}
	
	public void loadPage() {
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public void closeBrowser() {
		driver.quit();
	}

	public boolean verifyLink(String linkType) {
		boolean result = false;
		switch (linkType) {
		case "facebook":
			result = facebookLinks.size() > 0;
			break;
		case "twitter":
			result = twitterLinks.size() > 0;
			break;
		case "flickr":
			result = flickrLinks.size() > 0;
			break;
		case "youtube":
			result = youtubeLinks.size() > 0;
			break;
		default:
			break;
		}
		return result;
	}

	public boolean verifyErrorHandling(String errorType, String expectedErrorText) {
		boolean result = false;
		switch (errorType) {
		case "firstName":
			submitButton.click();
			String firstNameError = errorLine.getText();
			result = firstNameError.equals(expectedErrorText);
			break;
		case "lastName":
			first_name.sendKeys("Iryna");
			submitButton.click();
			String lastNameError = errorLine.getText();
			result = lastNameError.equals(expectedErrorText);
			break;
		case "emailAddress":
			first_name.sendKeys("Iryna");
			last_name.sendKeys("Kaleniuk");
			submitButton.click();
			String emailAddressError = errorLine.getText();
			result = emailAddressError.equals(expectedErrorText);
			break;
		case "phoneNumber":
			first_name.sendKeys("Iryna");
			last_name.sendKeys("Kaleniuk");
			email_address.sendKeys("oseznotest4@gmail.com");
			submitButton.click();
			String phoneNumber = errorLine.getText();
			result = phoneNumber.equals(expectedErrorText);
			break;
		default:
			break;
		}
		return result;
	}
	
	public void submitForm(String firstName, String lastName, String emailAddress, String phoneNumber, String gender,
			String state, boolean checkTerms) {
		resetButton.click();
		first_name.sendKeys(firstName);
		last_name.sendKeys(lastName);
		email_address.sendKeys(emailAddress);
		phone_number.sendKeys(phoneNumber);
		if (gender.equals("Female")) {
			male.click();
		} else {
			female.click();
		}
		current_state.sendKeys("CA");
		privacy_checkbox.click();
		if (checkTerms) {
			submitButton.click();
		}
	}

	public boolean verifyContent(String contentType, String contentText) {
		boolean result = false;
		switch (contentType) {
		case "quotes":
			quotes.getText();
			String quote = quotes.getText();
			//refresh the page and verify that quote has changed
			driver.navigate().refresh();
			String quoteAfterRefresh = quotes.getText();
			return !quote.equals(quoteAfterRefresh);
		case "currentCity":
			String actualCity = currentLocation.getText();
			return actualCity.equals(contentText);
		case "currentWeather":
			String actualWeather = weatherIcon.getAttribute("src");
			return actualWeather.contains(contentText);
		case "currentDate":
			String currentDate = timestamp.getText().trim();
			return currentDate.equals(contentText);
		case "currentOs":
			String currentOs = osBrowser.getText();
			return currentOs.startsWith("OS X 10.10 Yosemite");
		case "currentBrowser":
			String currentBrowser = osBrowser.getText();
			return currentBrowser.startsWith("Firefox");
		default:
			break;
		}
		return result;
	}
	
	public boolean isElementPresent(String elementId) {
		Boolean element = driver.findElements(By.id(elementId)).size() > 0;
		return element;
	}
	
	public boolean isElementVisible(String elementId) {
		Boolean element = driver.findElement(By.id(elementId)).isDisplayed();
		return element;
	}
	
	public boolean isElementEnabled(String elemebtId) {
		Boolean element = driver.findElement(By.id(elemebtId)).isEnabled();
		return element;
	}
	
	public Dimension elementSize(String elemebtId) {
		Dimension element = driver.findElement(By.id(elemebtId)).getSize();
		return element;
	}
	
	public Point elementLocation(String elemebtId) {
		Point element = driver.findElement(By.id(elemebtId)).getLocation();
		return element;
	}
 }