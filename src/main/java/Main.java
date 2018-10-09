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

        goToGamePage();
        printGameItemNames();

        driver.quit();
    }

    private static void goToGamePage()
    {
        List<WebElement> games = driver.findElements(By.className(CLASS_NAME_SELECTOR));
        for(WebElement game : games)
        {
            if(null != game.getAttribute("title"))
            {
                String title = game.getAttribute("title");
                String gameUrl = game.getAttribute("data-href");

                if (title.equals(VOLLEYBALL_GAME_NAME_EN) || title.equals(VOLLEYBALL_GAME_NAME_RU))
                {
                    driver.get(driver.getCurrentUrl() + gameUrl);
                    return;
                }
            }
        }
    }

    //TODO change method name like 'getGame' and return all block with game name, koeff and fora.
    private static void printGameItemNames()
    {
        List<WebElement> gameItems = driver.findElements(By.className("c-events__teams"));
        for(WebElement gameItem : gameItems)
        {
            String gameTitle = gameItem.getAttribute("title");
            Game game = new Game();
            game.setName(gameTitle);

            new JSONDataSender(game).sendData();
        }
    }
}
