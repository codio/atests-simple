package com.codio.dashboard.auth;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.*;
import com.codio.dashboard.NavigationMenu;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class LoginPage {

    private final SelenideElement page = $x("//div[@class='auth auth-login']");
    private final Button signInButton = new Button("Sign In");
    private final CookieBanner cookieBanner = new CookieBanner();
    private static final Input loginField = new Input("user");
    private final PasswordInput passwordField =
        new PasswordInput($x("//input[contains(@class,'ReactPasswordStrength-input')]"));
    public static final String pageUrl = Driver.getBaseUrl() + "/p/login";
    private final StatuspageBanner statuspageBanner = new StatuspageBanner();
    private final SelenideElement joinACourseLink = page.$x(".//a[string()='Join a course.']");

    @Step("Open Join Course Token page by click link")
    public JoinCourseTokenPage clickJoinACourseLink() {
        joinACourseLink.click();
        return new JoinCourseTokenPage().waitOpened();
    }

    @Step("Open dashboard by click Sign In button")
    public NavigationMenu clickSignInButton() {
        cookieBanner.removeIfExists();
        statuspageBanner.removeIfExist();
        signInButton.clickOrExitTestWithErrorIfDisabled(
            "Sign In button disabled. Check that all required fields have correct values"
        );
        waitClosed();
        return new NavigationMenu().waitOpened();
    }

    public static boolean isOpened() {
        return loginField.exists();
    }

    @Step("Login by fill login and password fields and click Sign In button")
    public NavigationMenu login(String login, String password) {
        return new LoginPage().setLoginFieldValue(login).setPasswordFieldValue(password).clickSignInButton();
    }

    @Step("Open Login page")
    public LoginPage open() {
        if (!isOpened()) {
            Driver.open(pageUrl);
        }
        return waitOpened();
    }

    @Step("Try to open Login page")
    public boolean tryToOpen() {
        Driver.open(pageUrl);
        Driver.waitOneOfConditionsIsTrue(
            LoginPage::isOpened,
            new NavigationMenu()::isPresented,
            AppConfig.timeOutSecLong
        );
        return loginField.exists();
    }

    @Step("Fill login field")
    public LoginPage setLoginFieldValue(String login) {
        loginField.setValue(login);
        return this;
    }

    @Step("Fill password field")
    public LoginPage setPasswordFieldValue(String password) {
        passwordField.setValue(password);
        return this;
    }

    private boolean checker() {
        return Driver.tryToWaitElementNotPresented(page, AppConfig.timeOutSecLong);
    }

    @Step("Click Sign In button")
    private void closeRepeater(int attempt) {
        signInButton.clickOrExitTestWithErrorIfDisabled(
            "Sign In button disabled. Check that all required fields have correct values"
        );
    }

    private void actionIfAttemptsToBeClosedFailed(int attempts) {
        Driver.exitTestWithError("After " + attempts + " attempts to get login page closed, it still opened");
    }

    @Step("Get Login page closed and Dashboard opened")
    public NavigationMenu waitClosed() {
        Driver.repeatIfFailed(this::checker, this::closeRepeater, this::actionIfAttemptsToBeClosedFailed);
        return new NavigationMenu().waitOpened();
    }

    @Step("Reopen Login page")
    private void repeater(int attempt) {
        Driver.logInfo("Activating reopening Login page (" + attempt + ")");
        Driver.open(pageUrl);
    }

    private void actionIfAttemptsFailed(int attempts) {
        Driver.exitTestWithError("After " + attempts + " attempts Login page still not opened");
    }

    @Step("Wait Login page opened")
    public LoginPage waitOpened() {
        Driver.repeatIfFailed(loginField::tryToWaitPresented, this::repeater, this::actionIfAttemptsFailed);
        return this;
    }

    @Step("Wait flash hack")
    public LoginPage tryToWaitFlashHack() {
        Driver.tryToWaitElementPresented(
            $x("//div[contains(@class,'bodyLoading') and contains(@class,'u-fade')]"),
            AppConfig.timeOutSecMedium
        );
        return this;
    }
}
