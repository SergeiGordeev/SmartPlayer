package injector;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import driver.BrowserDriver;
import driver.ChromeWebDriver;
import driver.FireFoxWebDriver;
import driver.InternetExplorerWebDriver;
import injector.annotations.Chrome;
import injector.annotations.FireFox;
import injector.annotations.InternetExplorer;

public class InjectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BrowserDriver.class).annotatedWith(Chrome.class).to(ChromeWebDriver.class).in(Singleton.class);
        bind(BrowserDriver.class).annotatedWith(FireFox.class).to(FireFoxWebDriver.class).in(Singleton.class);
        bind(BrowserDriver.class).annotatedWith(InternetExplorer.class).to(InternetExplorerWebDriver.class).in(Singleton.class);
    }
}
