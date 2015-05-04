package portal.PortalLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import portal.PortalLogin.Configuration.Stack;

/**
 * The base class for portal page objects. 
 * @author twhyte
 *
 */
public abstract class Page {
    protected final WebDriver DRIVER;
    protected final Configuration CONFIG;
    
    /**
     * Creates a page object with a specified web driver and by default uses the 
     * dev stack configuration.
     * @param driver
     */
    public Page (WebDriver driver){
        this.DRIVER = driver;
        CONFIG = new Configuration(Stack.DEV);
    }
    
    /**
     * Creates a page object.
     * @param driver
     * @param stack The configuration stack.
     */
    public Page (WebDriver driver, Stack stack) {
        this.DRIVER = driver;
        CONFIG = new Configuration(stack);
    }
   
    /** 
     * Navigate to this page using the web driver. 
     */
    public void open(){};

    /**
     * Find out whether an element
     */
    protected boolean isElementDisplayed(By locator){
    	return new WebDriverWait(DRIVER, 5)
    	.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
    }

}
