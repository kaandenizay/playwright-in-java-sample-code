package toolshop.catalog;

import io.qameta.allure.Feature;
import toolshop.catalog.pageobjects.*;
import toolshop.fixtures.PlaywrightBaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Searching for products")
@Feature("Product Catalog")
public class SearchForProductsTest extends PlaywrightBaseTest {

    SearchComponent searchComponent;
    ProductList productList;

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @BeforeEach
    void setUp() {
        searchComponent = new SearchComponent(page);
        productList = new ProductList(page);
    }

    @Nested
    @DisplayName("Searching by keyword")
    class SearchingByKeyword {

        @Test
        @DisplayName("When there are matching results")
        void whenSearchingByKeyword() {
            searchComponent.searchBy("tape");

            var matchingProducts = productList.getProductNames();

            Assertions.assertThat(matchingProducts).contains("Tape Measure 7.5m", "Measuring Tape", "Tape Measure 5m");
        }

        @Test
        @DisplayName("When there are no matching results")
        void whenThereIsNoMatchingProduct() {
            searchComponent.searchBy("unknown");

            var matchingProducts = productList.getProductNames();

            Assertions.assertThat(matchingProducts).isEmpty();
            Assertions.assertThat(productList.getSearchCompletedMessage()).contains("There are no products found.");
        }

        @Test
        @DisplayName("When the user clears a previous search results")
        void clearingTheSearchResults() {
            searchComponent.searchBy("saw");

            var matchingFilteredProducts = productList.getProductNames();
            Assertions.assertThat(matchingFilteredProducts).hasSize(2);

            searchComponent.clearSearch();

            var matchingProducts = productList.getProductNames();
            Assertions.assertThat(matchingProducts).hasSize(9);
        }
    }
}