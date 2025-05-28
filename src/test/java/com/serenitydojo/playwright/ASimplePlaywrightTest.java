package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.*;

import java.util.Arrays;

@UsePlaywright(ASimplePlaywrightTest.CustomOptions.class)
public class ASimplePlaywrightTest {

    public static class CustomOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(false)
                    .setLaunchOptions(
                            new BrowserType.LaunchOptions()
                                    .setSlowMo(500)
                                    .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu"))
                    );
        }
    }

    @Test
    void shouldShowThePageTitle(Page page) {

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();
        Assertions.assertTrue( title.contains("Practice Software Testing"));

    }

    @Test
    void shouldSearchByKeyword(Page page) {

        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("#search-query")
                .fill("pliers");
        page.locator("button:has-text('Search')").click();

        int matchingSearchResults = page.locator(".card").count();
        Assertions.assertTrue(matchingSearchResults > 0);
        Locator cardTitles = page.locator(".card-title");
        int cardTitlesCount = cardTitles.count();
        for (int i = 0; i < cardTitlesCount; i++) {
            System.out.println("cardTitles = " + cardTitles.nth(i).textContent());
            Assertions.assertTrue(cardTitles.nth(i).textContent().contains("Pliers"));
        }

    }
}
