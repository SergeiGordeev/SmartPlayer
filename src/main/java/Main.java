import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import driver.BrowserDriver;
import injector.InjectorModule;
import injector.annotations.Chrome;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

                if (title.equals(GAME_NAME_EN) || title.equals(GAME_NAME_RU))
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

    private void readMathesData () {
        List<WebElement> gameItems = driver.findElements(By.xpath("//div[contains(@class, 'c-events__item_col')]"));
        for (WebElement element : gameItems) {
            WebElement scoreboard;
            WebElement bets;
            try {
                scoreboard = element.findElement(By.className("c-events-scoreboard"));
                bets = element.findElement(By.className("c-bets"));
            }catch (NoSuchElementException nse)
            {
                continue;
            }
            WebElement math = scoreboard.findElement(By.className("c-events__teams"));
            String title = math.getAttribute("title");
            System.out.println("title: " + title);

            try {
                WebElement rcLite = bets.findElement(By.xpath("//a[contains(@class, 'rc_lite')]"));
                String dataCoef = rcLite.getAttribute("data-coef");
                System.out.println("dataCoef :" + dataCoef);
            }catch (NoSuchElementException nse)
            {
                continue;
            }
        }
    }
}
