package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.TestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  Main class for home page actions (search with brand name and price)
 */
public class HomePage {

    private final WebDriver driver;

    @FindBy(xpath = "//select[@id='filter-make']")
    private WebElement brand;

    @FindBy(xpath = "//select[@name='usdprice[lt]']")
    private WebElement price_end;

    @FindBy(xpath = "//*[@id='search-btn']")
    private WebElement search;

    @FindBy(xpath = "//div[@class='search-result-info alert alert-success large']")
    private WebElement count_of_finding_items;

    @FindBy(xpath = ".//div[@class='price bold blue-text']/span")
    private List<WebElement> price_of_finding_items;

    @FindBy(xpath = "//*[@id='search-result']")
    List<WebElement> search_result_div;

    @FindBy(css = "div.price.bold.blue-text")
    private List<WebElement> price_of_finding_items_div;

    @FindBy(xpath = "//i[contains(text(),'chevron_right')]")
    private WebElement right_click_finding_item_page_change;

    /**
     * Inits webDriver and also PageFactory elements {@link PageFactory#initElements(WebDriver, Object)}
     *
     * @param driver the webDriver
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public String verifyHomePageTitle() {
        return driver.getTitle();
    }

    public void searchByEndPrice(String price) {
        price_end.sendKeys(price);
        search.click();
    }

    public void searchByBrandName(String name) {
        brand.sendKeys(name);
        search.click();
    }

    /**
     * Taking finding items price
     *
     * @return  list of price {@link List<Integer>}
     */
    public List<Integer> takePriceCount() {
        List<WebElement> elements = price_of_finding_items;
        List<String> texts = new ArrayList<>();
        for (WebElement element : elements) {
            texts.add(element.getText());
        }
        return TestUtils.checkList(texts);
    }

    /**
     * Count of total finding items
     *
     * @return total count of finding items
     */
    public int takeCountOfTotalFindingItems() {
        String text = count_of_finding_items.getText();
       return TestUtils.convertStringToInt(text);
    }

    /**
     * Count of actual finding items
     *
     * @return actual count
     */
    public int takeCountOfActualFindingItems() {
        int actualCount = 0;
        final int ITEMS_PER_PAGE = 50;
        int countOfPages = takeCountOfTotalFindingItems() <= ITEMS_PER_PAGE ? 1 : takeCountOfTotalFindingItems() / ITEMS_PER_PAGE + 1;
        for (int i = 0; i < countOfPages; i++) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(right_click_finding_item_page_change)).click();
            actualCount += price_of_finding_items_div.size();
        }
        return actualCount;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
