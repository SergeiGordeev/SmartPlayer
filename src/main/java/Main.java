import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import driver.BrowserDriver;
import injector.InjectorModule;
import injector.annotations.Chrome;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Constants;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static utils.Constants.DOWNLOADED_IMAGES_LOCAL_PATH;
import static utils.Constants.JPG;

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
        firstTest(5);
        driver.quit();
    }

    public static void firstTest(int countOfImagesForDownload)
    {
        List<WebElement> imgElementsList  = driver.findElements(By.xpath("//li[@class='  js-content-li']/a/span[@class='image']/img"));
        for(WebElement img : imgElementsList)
        {
            downloadImage(img.getAttribute("data-src"), img.getAttribute("alt"), JPG);
            countOfImagesForDownload--;
            if(countOfImagesForDownload == 0)
            {
                return;
            }
        }
        System.out.println(imgElementsList.size());
    }

    public static void downloadImage(String url, String imageName, String imageType)
    {
        try
        {
            ImageIO.write(ImageIO.read
                            (new URL(StringUtils.substringBefore(url, imageType) + imageType)),
                            imageType,
                            new File(DOWNLOADED_IMAGES_LOCAL_PATH + imageName + "." + imageType));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
