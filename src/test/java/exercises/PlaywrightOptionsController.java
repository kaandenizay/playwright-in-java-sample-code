package exercises;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.util.Arrays;

public class PlaywrightOptionsController implements OptionsFactory {

    @Override
    public Options getOptions() {
        return new Options().setLaunchOptions(
                        new BrowserType.LaunchOptions()
                                .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
                ).setHeadless(false)
                .setTestIdAttribute("data-test");
    }
}
