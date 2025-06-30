package toolshop.cucumber.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import toolshop.catalog.pageobjects.NavBar;
import toolshop.catalog.pageobjects.ProductList;
import toolshop.catalog.pageobjects.SearchComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
