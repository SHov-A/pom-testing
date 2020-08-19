package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.HomePage;
import util.TestUtils;

import java.util.Collections;

public class HomePageSteps {

    private HomePage homePage = new HomePage(TestUtils.initDriver());

    @Given("User open browser")
    public void user_open_browser() {
        TestUtils.runDriver(homePage.getDriver());
    }

    @Then("User in homepage")
    public void user_in_homepage() {
        String title = homePage.verifyHomePageTitle();
        Assert.assertEquals("Ավտոմեքենաների վաճառք Հայաստանում - Auto.am", title);
    }

    @When("User selecting price")
    public void user_selecting_price() {
        homePage.searchByEndPrice(TestUtils.config().getProperty("price$"));
    }

    @Then("All prices in result are smaller than selected price")
    public void all_prices_in_result_are_smaller_than_selected_price() {
        int price = Integer.parseInt(TestUtils.config().getProperty("price"));
        Assert.assertTrue(price >= Collections.max(homePage.takePriceCount()));
    }

    @When("User selecting brand of car")
    public void user_selecting_brand_of_car() {
        homePage.searchByBrandName(TestUtils.config().getProperty("brand"));
    }

    @Then("Actual items count matches with the number in green box")
    public void actual_items_count_matches_with_the_number_in_green_box() {
        Assert.assertEquals(homePage.takeCountOfTotalFindingItems(), homePage.takeCountOfActualFindingItems());
    }

    @After
    public void close() {
        TestUtils.quitBrowser(homePage.getDriver());
    }
}
