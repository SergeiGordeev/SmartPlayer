package driver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

public class FireFoxWebDriver implements BrowserDriver {

    public RemoteWebDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", Constants.FIRE_FOX_WEB_DRIVER_LOCAL_PATH);
        return new FirefoxDriver();
    }
}
