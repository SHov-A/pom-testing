package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilize methods for project
 */
public class TestUtils {

    private static final int PAGE_LOAD_TIMEOUT = 25;
    private static final int IMPLICIT_WAIT = 25;

    /**
     * Creates relevant web driver depends on Operating System and also browser
     * which is defined in config file
     *
     * @return the web driver {@link WebDriver}
     */
    public static WebDriver initDriver() {
        Properties prop = config();
        WebDriver driver = null;
        String browserName = prop.getProperty("browser");
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
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

    public static int convertStringToInt(String text){
        Pattern compile = Pattern.compile("([0-9]+)");
        Matcher matcher = compile.matcher(text);
        int totalCount = 0;
        if (matcher.find()) {
            totalCount = Integer.parseInt(matcher.group());
        }
        return totalCount;
    }

    private static String convertToDollar(String el) {
        String s = removeSpaces(el);
        return String.valueOf(Integer.parseInt(s) / 480);
    }

    private static String removeSpaces(String str) {
        return str.substring(1).replaceAll(" ", "");
    }

    public static List<Integer> checkList(List<String> list) {
        List<Integer> prices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str.charAt(0) == '֏') {
                list.set(i, convertToDollar(str));
            } else {
                list.set(i, removeSpaces(str));
            }
            prices.add(Integer.parseInt(list.get(i)));
        }
        return prices;
    }

    public static void quitBrowser(WebDriver driver) {
        driver.quit();
    }

    public static void main(String[] args) {
        System.getProperties();
    }
}
