package portal.PortalLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.stream.JsonReader;


/**
 * Log in to the portal.   
 *
 */
public class Login
{
	private String username;
	private String password; 
    private String devStackProperty = "dev_stack";
    private String devStackUserName = "username"; 
    private String devStackPassword = "password";

    /**
     * Set up the username an password for logging into the portal on a dev stack account.
     */
	public Login() {
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
	}
	
	public 

    public static void main( String[] args )
    {
        Login l = new Login();
    }
}
