package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;
import static com.codio.helpers.Driver.exitTestWithError;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class Button {

    private final SelenideElement button;
    private final WebElementCondition disabledCondition =
        Condition.or("disabled", Condition.attribute("disabled"), Condition.attributeMatching("class", ".*disabled.*"));

    public Button(SelenideElement button) {
        this.button = button;
    }

    public Button(SelenideElement parentEl, String buttonName) {
        button = parentEl.$x(".//button[string()='" + buttonName + "']");
    }

    public Button(SelenideElement parentEl, String buttonName, int num) {
        button = parentEl.$$x(".//button[string()='" + buttonName + "']").get(num - 1);
    }

    public Button(String buttonName) {
        button = $x("//button[string()='" + buttonName + "']");
    }

    @Step("Click button or exit test if button disabled")
    public void clickOrExitTestWithErrorIfDisabled() {
        clickOrExitTestWithErrorIfDisabled("Button is disabled");
    }

    @Step("Click button or exit test if button disabled")
    public void clickOrExitTestWithErrorIfDisabled(String errorMessage) {
        if (!tryToWaitEnabled()) {
            exitTestWithError(errorMessage);
        }
        Driver.scrollElementToCenter(button);
        button.click();
    }

    @Step("Click button")
    public void click() {
        Driver.scrollElementToCenter(button);
        button.click();
    }

    public SelenideElement getElement() {
        return button;
    }

    public boolean isDisabled() {
        return button.has(disabledCondition);
    }

    public boolean isEnabled() {
        return button.has(Condition.not(disabledCondition));
    }

    public boolean isPresented() {
        return button.exists();
    }

    public boolean tryToWaitEnabled() {
        try {
            button.shouldNotHave(disabledCondition, Duration.ofSeconds(AppConfig.timeOutSecMedium));
            return true;
        } catch (AssertionError error) {
            return false;
        }
    }

    @Step("Wait button presented")
    public Button waitPresented() {
        return waitPresented(AppConfig.timeOutSecLong);
    }

    @Step("Wait button presented")
    public Button waitPresented(int timeOutSec) {
        button.shouldBe(Condition.visible, Duration.ofSeconds(timeOutSec));
        return this;
    }
}
