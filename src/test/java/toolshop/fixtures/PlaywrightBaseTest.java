package toolshop.fixtures;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;

public abstract class PlaywrightBaseTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    protected Page page;

    @BeforeAll
    static void setUpBrowser() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
    }

    @BeforeEach
    void setUpBrowserContext() {
        browserContext = browser.newContext();
        page = browserContext.newPage();

        browserContext.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true));
    }


    @AfterEach
    void closeContext(TestInfo testInfo) {
        String testName = testInfo.getTestMethod().get().getName();
        browserContext.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get( "target/traces/" + testName + "-trace.zip"))
        );
        browserContext.close();

    }

    @AfterAll
    static void tearDown() {
        browser.close();
        playwright.close();
    }

}