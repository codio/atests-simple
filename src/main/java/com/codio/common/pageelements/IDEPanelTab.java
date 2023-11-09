package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;

public class IDEPanelTab {

    private final SelenideElement tab;
    private SelenideElement closeButton;

    public IDEPanelTab(String tabName) {
        tab = $x("//div[contains(@class,'tabbar')]//a[string()='" + tabName + "']/../..");
        closeButton = tab.$x(".//button[@class='close-button']");
    }

    @Step
    public void click() {
        tab.click();
        waitTabActive();
    }

    @Step
    public void close() {
        closeButton.click();
        waitTabClosed();
    }

    @Step
    public SelenideElement getTab() {
        return tab;
    }

    @Step
    public IDEPanelTab waitTabActive() {
        tab.shouldHave(
            Condition.attributeMatching("class", ".*active"),
            Duration.ofSeconds(AppConfig.timeOutSecMedium)
        );
        return this;
    }

    @Step
    private void waitTabClosed() {
        tab.shouldNot(Condition.exist);
    }
}
