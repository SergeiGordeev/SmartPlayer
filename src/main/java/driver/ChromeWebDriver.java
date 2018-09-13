package driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

public class ChromeWebDriver implements BrowserDriver {

    public RemoteWebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_WEB_DRIVER_LOCAL_PATH);
        return new ChromeDriver();
    }
}
