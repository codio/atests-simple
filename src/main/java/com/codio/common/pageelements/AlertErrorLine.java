package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class AlertErrorLine {

    private final SelenideElement alertError = $x("//div[@class='alert alert--error']");

    public SelenideElement getElement() {
        return alertError;
    }

    @Step("Get alert error message")
    public String getMessge() {
        return alertError.getText();
    }

    public boolean isPresented() {
        return alertError.exists();
    }
}
