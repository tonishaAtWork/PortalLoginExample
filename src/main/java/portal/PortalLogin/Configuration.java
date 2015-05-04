package portal.PortalLogin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Configuration {
	private final String username; 
	private final String password;
	private Map<String, String> urls;
	
    private static final String HOME_URL_KEY = "home";
    private static final String LOGIN_URL_KEY = "login";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String URL_LIST_KEY = "urls"; 

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * Portal stacks.
	 * @author twhyte
	 *
	 */
	public enum Stack {
	    DEV("dev_stack"), 
	    PROD("prod_stack");	

	    private String value;

	    private Stack(String value){
	    	this.value = value;
	    }

	    public String toString() {
	    	return value;
	    }
	}

	/**
	 * Set user name, password and URLS for a specified portal stack.
	 * @param stack a portal stack.
	 */
	public Configuration (Stack stack) {
		 JSONParser configParser = new JSONParser();
		 JSONObject config = null; 
		 JSONObject portalStack = null;
		 

		  try {
			  config = (JSONObject)configParser.parse(new InputStreamReader(
					  new FileInputStream("src/main/config/config.json"), "UTF-8"));
			  portalStack = (JSONObject)config.get(stack.toString());
		  } catch (IOException e) {
			  e.printStackTrace();
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }

		  this.username = (String)portalStack.get(USERNAME_KEY);
		  this.password = (String)portalStack.get(PASSWORD_KEY);
		  this.urls = (Map<String, String>)portalStack.get(URL_LIST_KEY);
	}
	
	public String getHomeURL(){
		return (String)urls.get(HOME_URL_KEY);
	}

	public String getLoginURL(){
		return (String)urls.get(LOGIN_URL_KEY);
	}
}