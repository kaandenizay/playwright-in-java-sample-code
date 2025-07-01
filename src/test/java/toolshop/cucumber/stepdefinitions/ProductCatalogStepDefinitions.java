package toolshop.cucumber.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import toolshop.catalog.pageobjects.NavBar;
import toolshop.catalog.pageobjects.ProductList;
import toolshop.catalog.pageobjects.SearchComponent;

import java.util.*;

public class ProductCatalogStepDefinitions{

    NavBar navBar;
    SearchComponent searchComponent;
    ProductList productList;
    @Before
    public void setUpPageObjects() {
        navBar = new NavBar(PlaywrightCucumberHooks.getPage());
        searchComponent = new SearchComponent(PlaywrightCucumberHooks.getPage());
        productList = new ProductList(PlaywrightCucumberHooks.getPage());
    }
    @Given("Sally is on the home page")
    public void sally_is_on_the_home_page() {
        navBar.openHomePage();
    }
    @When("She searches for {string}")
    public void she_searches_for(String searchTerm) {
        searchComponent.searchBy(searchTerm);
    }
    @Then("The {string} product should be displayed")
    public void the_product_should_be_displayed(String productName) {
        var matchingProducts = productList.getProductNames();
        Assertions.assertThat(matchingProducts).contains(productName);
    }

//    @Then("The following products should be displayed:")
//    public void theFollowingProductsShouldBeDisplayed(List<String> productNames) {
//        var matchingProducts = productList.getProductNames();
//        Assertions.assertThat(matchingProducts).isEqualTo(productNames);
//    }

    @Then("The following products should be displayed:")
    public void theFollowingProductsShouldBeDisplayed(DataTable productNames) {

        var matchingProductsNames = productList.getProductNames();
        var matchingProductsPrices = productList.getProductPrices();
        List<Map<String,String>> actualProductsMapList = new ArrayList<>();
        List<Map<String,String>> expectedProducts = productNames.asMaps();
        for (int i = 0; i < expectedProducts.size(); i++) {
            actualProductsMapList.add(Map.of("Product", matchingProductsNames.get(i),
                                            "Price", matchingProductsPrices.get(i)));
        }
        Assertions.assertThat(actualProductsMapList).isEqualTo(expectedProducts);
    }

    @Then("No products should be displayed")
    public void noProductsShouldBeDisplayed() {
        var matchingProducts = productList.getProductNames();
        Assertions.assertThat(matchingProducts).isEmpty();
    }

    @And("The message {string} should be displayed")
    public void theMessageShouldBeDisplayed(String message) {
        String completionMessage = productList.getSearchCompletedMessage();
        Assertions.assertThat(completionMessage).isEqualTo(message);
    }

    @And("She filters by {string}")
    public void sheFiltersBy(String filterName) {
        searchComponent.filterBy(filterName);
    }

    @When("She sorts by {string}")
    public void sheSortsBy(String sortCriteria) {
        searchComponent.sortBy(sortCriteria);
    }

    @Then("The first product displayed should be {string}")
    public void theFirstProductDisplayedShouldBe(String productName) {
        List<String> productNames = productList.getProductNames();
        Assertions.assertThat(productNames).startsWith(productName);
    }

    @And("Products should be sorted correctly as {string}")
    public void productsShouldBeSortedCorrectlyAs(String sortType) {
        List<String> productNames = productList.getProductNames();
        List<String> productsPrices = productList.getProductPrices();
        ArrayList<String> originalListProductNames;
        ArrayList<Double> originalListProductPrices;
        ArrayList<Double> willBeSortedProductPrices;
        switch (sortType) {
            case "alphabetic":
                originalListProductNames = new ArrayList<>(productNames);
                Collections.sort(productNames);
                Assertions.assertThat(originalListProductNames).isEqualTo(productNames);
                break;
            case "alphabetic-reversed":
                originalListProductNames = new ArrayList<>(productNames);
                Collections.sort(productNames, Collections.reverseOrder());
                Assertions.assertThat(originalListProductNames).isEqualTo(productNames);
                break;
            case "numeric":
                originalListProductPrices = new ArrayList<>(
                        productsPrices.stream().map(price -> Double.valueOf(price.replace("$", ""))).toList());
                willBeSortedProductPrices = new ArrayList<>(originalListProductPrices);
                Collections.sort(willBeSortedProductPrices);
                Assertions.assertThat(originalListProductPrices).isEqualTo(willBeSortedProductPrices);
                break;
            case "numeric-reversed":
                originalListProductPrices = new ArrayList<>(
                        productsPrices.stream().map(price -> Double.valueOf(price.replace("$", ""))).toList());
                willBeSortedProductPrices = new ArrayList<>(originalListProductPrices);
                Collections.sort(willBeSortedProductPrices, Collections.reverseOrder());
                Assertions.assertThat(originalListProductPrices).isEqualTo(willBeSortedProductPrices);
                break;
        }
    }
}
