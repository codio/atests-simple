package com.codio.helpers;

import java.awt.im.InputContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.codeborne.selenide.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;

import com.codio.dashboard.auth.LoginPage;

public class Driver {

    private static Logger log;
    private static String baseUrl;
    private static final HashMap<Integer, ArrayList<SelenideElement>> iframeStacks = new HashMap<>();

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void close() {
        iframeStacks.remove(currentDriver().hashCode());
        if (WebDriverRunner.hasWebDriverStarted()) {
            currentDriver().close();
            try { // hack to avoid WebDriverException
                currentDriver().quit();
            } catch (WebDriverException e) {
                logInfo("\n!!!!!!!\nRun hack for WebDriverException\nWebDriverException message: " + e.getMessage());
                if (WebDriverRunner.hasWebDriverStarted()) {
                    currentDriver().quit();
                }
            }
        }
    }

    public static WebDriver currentDriver() {
        return WebDriverRunner.getWebDriver();
    }

    public static void executeJs(String script, SelenideElement element) {
        JavascriptExecutor js = (JavascriptExecutor) currentDriver();
        try {
            js.executeScript(script, element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exitTestWithError(String errorMessage) {
        Assertions.fail(errorMessage);
    }

    public static void exitTestWithErrorIfIncorrectKeyboardLayout() {
        InputContext context = InputContext.getInstance();
        if (!context.getLocale().toString().equals("en_US") && OsValidator.isMac()) {
            Driver.exitTestWithError(
                "Incorrect keyboard layout\n" + "Switch keyboard layout to en_US before run tests on macOs\n"
                    + "It is needed to make shortcuts work"
            );
        }
    }

    public static String getCurrentUrl() {
        return currentDriver().getCurrentUrl();
    }

    public static String getDateFormatted(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateTimeMsFormated() {
        return getDateFormatted("yy.MM.dd HH:mm:ss SSS");
    }

    public static Date getDateWithShiftInSeconds(int secondsToAdd) {
        return new Date(new Date().getTime() + (1000L * secondsToAdd));
    }

    public static void initDriver() {

        // Initialize logger
        initLogger();

        // Get settings from command line
        TestConfig.initConfig();

        // Set settings for selenide browser
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1280x853";
        Configuration.holdBrowserOpen = false;
        Configuration.screenshots = false;
        Configuration.headless = TestConfig.isHeadless();
        Configuration.timeout = AppConfig.timeOutSec * 1000L;

        switch (TestConfig.browser) {
            case "chrome":
                Configuration.browser = Browsers.CHROME;
                break;
            case "firefox":
                Configuration.browser = Browsers.FIREFOX;
                break;
            default:
                exitTestWithError(
                    "browser = \"" + TestConfig.browser + "\" is unknown value. Use one from the list: chrome, firefox"
                );
                break;
        }

        if (AppConfig.environment.containsKey(TestConfig.baseUrl)) {
            baseUrl = AppConfig.environment.get(TestConfig.baseUrl);
        } else {
            exitTestWithError(
                "baseUrl = \"" + TestConfig.baseUrl + "\" is unknown environment. Use one from the list: prod"
            );
        }

        logInfo(
            "\n------------------------------------------" + "\nRuning on \"" + baseUrl + "\"" + "\nbrowser = "
                + TestConfig.browser + "\nheadless = " + TestConfig.headless
                + "\n------------------------------------------"
        );

        open(LoginPage.pageUrl);
        iframeStacks.put(currentDriver().hashCode(), new ArrayList<>());
    }

    public static void initLogger() {
        log = LogManager.getLogger("");
        DOMConfigurator.configure("src/test/resources/log4j.xml");
    }

    public static void logInfo(String str) {
        logInfoWithTime(getDateTimeMsFormated(), str);
    }

    private static void logInfoWithTime(String time, String str) {
        String infoStr = time + " INFO: " + str;
        log.info(infoStr);
    }

    public static void open(String url) {
        Selenide.open(url);
    }

    public static void reload() {
        Selenide.refresh();
        switchToRefreshedContent();
    }

    public static void repeatIfFailed(
        Supplier<Boolean> checker, Consumer<Integer> repeater, Consumer<Integer> actionIfAttemptsFailed
    ) { // hack to make tests more stable
        int attempts = 3;
        for (int i = 1; i <= attempts + 1; i++) {
            if (!checker.get()) {
                if (i <= attempts) {
                    repeater.accept(i);
                } else {
                    actionIfAttemptsFailed.accept(attempts);
                }
            } else {
                break;
            }
        }
    }

    public static void scrollElementToCenter(SelenideElement element) {
        executeJs("arguments[0].scrollIntoView({block: 'center', inline: 'center'})", element);
    }

    private static ArrayList<SelenideElement> iframeStack() {
        return iframeStacks.get(currentDriver().hashCode());
    }

    public static void switchToDefaultContent() {
        currentDriver().switchTo().defaultContent();
        iframeStack().clear();
    }

    public static void switchToRefreshedContent() {
        currentDriver().switchTo().defaultContent();
        for (SelenideElement iframe : iframeStack()) {
            iframe.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
            currentDriver().switchTo().frame(iframe);
        }
    }

    public static void switchToIframe(SelenideElement iframe) {
        iframe.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
        iframeStack().add(iframe);
        currentDriver().switchTo().frame(iframe);
    }

    public static boolean tryToWaitConditionIsFalse(Supplier<Boolean> condition, int timeOutSec) {
        try {
            waitConditionIsFalse(condition, timeOutSec);
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    public static boolean tryToWaitConditionIsTrue(Supplier<Boolean> condition, int timeOutSec) {
        try {
            waitConditionIsTrue(condition, timeOutSec);
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    public static boolean tryToWaitElementNotPresented(SelenideElement element, int timeOutSec) {
        try {
            element.shouldNot(Condition.exist, Duration.ofSeconds(timeOutSec));
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    public static boolean tryToWaitElementPresented(SelenideElement element, int timeOutSec) {
        try {
            element.should(Condition.visible, Duration.ofSeconds(timeOutSec));
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    public static boolean tryToWaitOneOfElementsPresented(SelenideElement[] elements, int timeOutSec) {
        try {
            waitOneOfElementsPresented(elements, timeOutSec);
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitElementCSSValueEquals(SelenideElement element, String value, String equals, int timeOutSec) {
        element.shouldHave(Condition.cssValue(value, equals), Duration.ofSeconds(timeOutSec));
    }

    public static void waitElementNotPresented(SelenideElement element, int timeOutSec) {
        element.shouldNot(Condition.exist, Duration.ofSeconds(timeOutSec));
    }

    public static void waitElementPresented(SelenideElement element, int timeOutSec) {
        element.should(Condition.exist, Duration.ofSeconds(timeOutSec));
    }

    public static void waitOneOfElementsPresented(SelenideElement element1, SelenideElement element2, int timeOutSec) {
        long end = new Date().getTime() + timeOutSec * 1000L;
        while (new Date().getTime() < end) {
            if (element1.exists() || element2.exists()) {
                return;
            }
            wait(100);
        }
        exitTestWithError("After " + timeOutSec + " seconds elements still not presented");
    }

    public static void waitOneOfElementsPresented(SelenideElement[] elements, int timeOutSec) {
        long end = new Date().getTime() + timeOutSec * 1000L;
        while (new Date().getTime() < end) {
            for (SelenideElement el : elements) {
                if (el.exists()) {
                    return;
                }
            }
            wait(100);
        }
        exitTestWithError("After " + timeOutSec + " seconds elements still not presented");
    }

    public static void waitOneOfConditionsIsTrue(
        Supplier<Boolean> condition1, Supplier<Boolean> condition2, int timeOutSec
    ) {
        boolean result = false;
        long end = new Date().getTime() + timeOutSec * 1000L;
        while (new Date().getTime() < end) {
            if (condition1.get() || condition2.get()) {
                result = true;
                break;
            }
            wait(100);
        }
        if (!result) {
            exitTestWithError("After " + timeOutSec + " seconds both conditions are still FALSE");
        }
    }

    public static void waitConditionIsFalse(Supplier<Boolean> condition, int timeOutSec) {
        boolean result = false;
        long end = new Date().getTime() + timeOutSec * 1000L;
        while (new Date().getTime() < end) {
            if (!condition.get()) {
                result = true;
                break;
            }
            wait(100);
        }
        if (!result) {
            exitTestWithError("After " + timeOutSec + " seconds condition is still TRUE");
        }
    }

    public static void waitConditionIsTrue(Supplier<Boolean> condition, int timeOutSec) {
        boolean result = false;
        long end = new Date().getTime() + timeOutSec * 1000L;
        while (new Date().getTime() < end) {
            if (condition.get()) {
                result = true;
                break;
            }
            wait(100);
        }
        if (!result) {
            exitTestWithError("After " + timeOutSec + " seconds condition is still FALSE");
        }
    }

    public static boolean isDateInPast(Date dateToCheck) {
        return new Date().getTime() > dateToCheck.getTime();
    }

    public static void clearCookies() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
}
