package com.codio.ide;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class CriticalErrorPage {

    private final SelenideElement panel = $x("//div[@id='critical-error']");
    private final SelenideElement label = panel.$x(".//h1");
    private final SelenideElement content = panel.$x(".//div[@class='critical-error-content']");

    @Step
    public String getContentText() {
        return content.getText();
    }

    @Step
    public SelenideElement getPanel() {
        return panel;
    }

    @Step
    public String getLabelText() {
        return label.getText();
    }

    public boolean isOpened() {
        return panel.exists();
    }
}
