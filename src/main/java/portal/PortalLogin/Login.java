package portal.PortalLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.stream.JsonReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Log in to the portal.   
 *
 */
public class Login
{
    private WebDriver driver;

	private String username;
	private String password; 
	private String home; 

    private String devStackProperty = "dev_stack";
	private String devStackUserName = "username"; 
    private String devStackPassword = "password";
    private String devStackHome = "home";

    /**
     * Set up the username an password for logging into the portal on a dev stack account.
     */
	public Login() {
		// read the credentials for the dev stack from a config file
		try {

			// JsonReader is NOT a convenient Json parser.  Pick a different one!
			JsonReader jsonReader = new JsonReader(new InputStreamReader(
					new FileInputStream("src/main/config/settings.json"), "UTF-8"));
			jsonReader.beginObject();
			while (jsonReader.hasNext()){
				String jsonObjectName = jsonReader.nextName();
				if (jsonObjectName.equals(devStackProperty)){
					jsonReader.beginObject();
                    while (jsonReader.hasNext()){
                       String propertyName = jsonReader.nextName();	
                       if (propertyName.equals(devStackUserName)){
                    	   this.username = jsonReader.nextString();
                       }
                       else if (propertyName.equals(devStackPassword)){
                    	   this.password = jsonReader.nextString();
                       }
                       else if (propertyName.equals(devStackHome)){
                    	   this.home = jsonReader.nextString();
                       }
                       else {
                    	   jsonReader.skipValue();
                       }
                    }
                    jsonReader.endObject();
				}
				else {
					jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
            jsonReader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		// create new instance of chrome driver
		this.driver = new ChromeDriver();
		driver.get(this.home);
	}

    public WebDriver getDriver() {
		return driver;
	}
	
	public WebElement findSignInButton() {
		return driver.findElement(By.id("signInLink"));
	}
	
	private WebElement findUserNameField() {
		return (new WebDriverWait(driver, 5))
		  .until(ExpectedConditions.presenceOfElementLocated(By.id("emailid")));
	}
	
	private WebElement findPasswordField() {
		return driver.findElement(By.id("passwordid"));
	}

	private WebElement findSignInSubmitButton() {
		return driver.findElement(By.id("signInSubmit"));
	}
	
	public void enterCredentials(){
	   findUserNameField().sendKeys(username);	
	   findPasswordField().sendKeys(password);	
	}
	
	public WebElement findSignedInDropDownMenu(){
		return (new WebDriverWait(driver, 5))
		  .until(ExpectedConditions.presenceOfElementLocated(By.id("dropdownMenu1")));
	}

    public static void main( String[] args )
    {
        Login login = new Login();
        WebDriver driver = login.getDriver();
        
        login.findSignInButton().click();
        login.enterCredentials();
        login.findSignInSubmitButton().click();
		Assert.assertEquals(true, login.findSignedInDropDownMenu().isDisplayed());
        
        driver.close(); 
    }
}
