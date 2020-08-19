package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestUtils {

    static final int PAGE_LOAD_TIMEOUT = 25;
    static final int IMPLICIT_WAIT = 25;

    public static WebDriver initDriver() {
        Properties prop = config();
        WebDriver driver = null;
        String browserName = prop.getProperty("browser");
        String osName = prop.getProperty("os.name");
        if (osName.contains("Window")) {
            if (browserName.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
                driver = new ChromeDriver();
            } else if (browserName.equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
                driver = new FirefoxDriver();
            } else {
                throw new RuntimeException("Unsupported webdriver: " + browserName);
            }
        } else if (osName.contains("Linux")) {
            if (browserName.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
                driver = new ChromeDriver();
            } else if (browserName.equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
                driver = new FirefoxDriver();
            } else {
                throw new RuntimeException("Unsupported webdriver: " + browserName);
            }
        } else {
            throw new RuntimeException("Unsupported os: " + osName);
        }
        return driver;
    }

    public static void runDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(config().getProperty("url"));
    }

    public static Properties config() {
        Properties prop = null;
        try {
            prop = new Properties();
            FileInputStream file = new FileInputStream(new File("./src/main/resources/config.properties"));
            prop.load(file);
        } catch (IOException e) {
            System.err.println("can't find properties file");
        }
        return prop;
    }

    public static void quitBrowser(WebDriver driver) {
        driver.quit();
    }
}
