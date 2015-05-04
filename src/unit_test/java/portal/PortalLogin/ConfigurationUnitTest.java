package portal.PortalLogin;

import org.testng.Assert;
import org.testng.annotations.*;

import portal.PortalLogin.Configuration.Stack;

/**
 * Unit tests for setting up portal configuration. 
 */
public class ConfigurationUnitTest {
	@BeforeClass
	public void oneTimeSetUp(){
		System.out.println("@BeforeClass -oneTimeSetUp");
        
	}


    @Test
    public void testConfigurationFileReturnsURLMap()
    {
    	Configuration c = new Configuration(Stack.DEV);
        Assert.assertNotNull(c);
    }
}
