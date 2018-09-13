import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import driver.BrowserDriver;
import injector.InjectorModule;
import injector.annotations.Chrome;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

import java.util.List;

public class Main
{
    @Inject
    @Chrome
    private BrowserDriver chrome;
    private static RemoteWebDriver driver;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new InjectorModule());
        Main mainInstance = injector.getInstance(Main.class);

        driver = mainInstance.chrome.getDriver();
        driver.manage().window().setPosition(new Point(0, 950));
        driver.get(Constants.DOMAIN_PATH);
        driver.manage().deleteAllCookies();

        List<WebElement> elements = driver.findElements(By.className("fSports"));
        for(WebElement element : elements)
        {
            System.out.println(element.getAttribute("title"));
            System.out.println(element.getAttribute("data-href"));
            System.out.println("\n");
        }
        driver.quit();
    }
}
