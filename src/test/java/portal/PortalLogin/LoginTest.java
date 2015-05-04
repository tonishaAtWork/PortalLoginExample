package portal.PortalLogin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver; 

	@BeforeSuite
	public void configurePortalStack() {
		driver = new ChromeDriver();
	}
	
	@AfterSuite
	public void cleanUp() {
		driver.close();
	}


	@Test
	public void login_succeeds_fromLandingPageHeader() {
		HomePage home = new HomePage(driver);
		home.open();
		home.startSignInFromHeader().enterCredentials().submitLogin(home);

		Assert.assertTrue(home.isSignOutDropDownVisible());
	}
	
	@Test 
	public void login_fails_incorrectPassword() {
		LoginPage login;
		HomePage home = new HomePage(driver);
		
		home.open();
		login = home.startSignInFromHeader()
		.enterUsername(home.CONFIG.getUsername())
		.enterPassword("fake password")
		.submitUnsuccessfulLogin();
		
		Assert.assertTrue(login.isLoginErrorDisplayed());
	}
	
	@DataProvider(name = "sqlInjectionPasswords")
	public Object[][] createSqlInjectionPasswords() {
		return new Object[][] {
				{"' or '1'='1"},
				{"' or 'x'='x"},
				{"' or 0=0 --"},
				{"\" or 0=0 --"},
				{"or 0=0 --"},
				{"' or 0=0 #"},
				{"\" or 0=0 #"},
				{"or 0=0 #"},
				{"' or 'x'='x"},
				{"\" or \"x\"=\"x"},
				{"') or ('x'='x"},
				{"' or 1=1--"},
				{"\" or 1=1--"},
				{"or 1=1--"},
				{"' or a=a--"},
				{"\" or \"a\"=\"a"},
				{"') or ('a'='a"},
				{"\") or (\"a\"=\"a"},
				{"hi\" or \"a\"=\"a"},
				{"hi\" or 1=1 --"},
				{"hi' or 1=1 --"},
				{"'or'1=1'"},
				{"=="},
				{"and 1=1--"},
				{"and 1=1"},
				{"' or 'one'='one--"},
				{"' or 'one'='one"},
				{"' and 'one'='one"},
				{"' and 'one'='one--"},
				{"1') and '1'='1--"},
				{"admin' --"},
				{"admin' #"},
				{"admin'/*"},
				{"or 1=1--"},
				{"or 1=1#"},
				{"or 1=1/*"},
				{") or '1'='1--"},
				{") or ('1'='1--"},
				{"' or '1'='1"},
				{"' or 'x'='x"},
				{"' or 0=0 --"},
				{"\" or 0=0 --"},
				{"or 0=0 --"},
				{"' or 0=0 #"},
				{"\" or 0=0 #"},
				{"or 0=0 #"},
				{"' or 'x'='x"},
				{"\" or \"x\"=\"x"},
				{"') or ('x'='x"},
				{"' or 1=1--"},
				{"\" or 1=1--"},
				{"or 1=1--"},
				{"' or a=a--"},
				{"\" or \"a\"=\"a"},
				{"') or ('a'='a"},
				{"\") or (\"a\"=\"a"},
				{"hi\" or \"a\"=\"a"},
				{"hi\" or 1=1 --"},
				{"hi' or 1=1 --"},
				{"'or'1=1’"}
		};
	}
	
	@Test(dataProvider="sqlInjectionPasswords")
	public void login_fails_sqlInjectionOnPassword(String password) {
        LoginPage login = new LoginPage(driver);
        login.open();
//        login.enterUsername(login.CONFIG.getUsername())
        login.enterUsername("admin")
        .enterPassword(password)
        .submitUnsuccessfulLogin();

		Assert.assertTrue(login.isLoginErrorDisplayed());
	}
} 