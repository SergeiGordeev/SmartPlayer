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

import java.util.List;

import static utils.Constants.*;

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
        driver.manage().window().setPosition(new Point(0, 920));
        driver.get(DOMAIN_PATH);
        driver.manage().deleteAllCookies();

        List<WebElement> elements = driver.findElements(By.className(CLASS_NAME_SELECTOR));

        goToGamePage(elements);
        driver.quit();
    }

    private static void goToGamePage(List<WebElement> elements)
    {
        for(WebElement element : elements)
        {
            if(null != element.getAttribute("title"))
            {
                String title = element.getAttribute("title");
                String gameUrl = element.getAttribute("data-href");

                if (title.equals(GAME_NAME_EN) || title.equals(GAME_NAME_RU))
                {
                    driver.get(driver.getCurrentUrl() + gameUrl);
                    return;
                }
            }
        }
    }
}
