package driver;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

public class InternetExplorerWebDriver implements BrowserDriver
{
    public RemoteWebDriver getDriver() {
        System.setProperty("webdriver.ie.driver", Constants.INTERNET_EXPLORER_WEB_DRIVER_LOCAL_PATH);
        return new InternetExplorerDriver();
    }
}
