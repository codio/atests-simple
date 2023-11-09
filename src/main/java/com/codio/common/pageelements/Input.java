package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class Input {

    final SelenideElement inputField;

    public Input(SelenideElement input) {
        inputField = input;
    }

    public Input(SelenideElement parentEl, String nameAttribute) {
        inputField = parentEl.$x(".//input[@name='" + nameAttribute + "']");
    }

    public Input(String nameAttribute) {
        inputField = $x("//input[@name='" + nameAttribute + "']");
    }

    public boolean exists() {
        return inputField.exists();
    }

    @Step
    public SelenideElement getInputEl() {
        return inputField;
    }

    @Step
    public String getValue() {
        return inputField.getValue();
    }

    @Step
    public void setValue(String value) {
        int i = 0;
        while (!getValue().equals(value) && i < 10) { // hack to make more stable
            inputField.setValue(value);
            i++;
        }
    }

    @Step
    public boolean tryToWaitPresented() {
        return Driver.tryToWaitElementPresented(inputField, AppConfig.timeOutSecLong);
    }

    @Step
    public void waitPresented() {
        inputField.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
    }
}
