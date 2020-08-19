package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<Integer> takePriceCount() {
        List<WebElement> elements = price_of_finding_items;
        List<String> texts = new ArrayList<>();
        for (WebElement element : elements) {
            texts.add(element.getText());
        }
        return checkList(texts);
    }

    public int takeCountOfTotalFindingItems() {
        String text = count_of_finding_items.getText();
        Pattern compile = Pattern.compile("([0-9]+)");
        Matcher matcher = compile.matcher(text);
        int totalCount = 0;
        if (matcher.find()) {
            totalCount = Integer.parseInt(matcher.group());
        }
        return totalCount;
    }

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


    private String convertToDollar(String el) {
        String s = removeSpaces(el);
        return String.valueOf(Integer.parseInt(s) / 480);
    }

    private String removeSpaces(String str) {
        return str.substring(1).replaceAll(" ", "");
    }

    private List<Integer> checkList(List<String> list) {
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

    public WebDriver getDriver() {
        return driver;
    }
}
