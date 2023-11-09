package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;
import java.util.Objects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class Tab {

    private final String classSelected = "react-tabs__tab react-tabs__tab--selected";
    private final SelenideElement tabEl;

    public Tab(String tabName) {
        tabEl = $x("//li[text()='" + tabName + "']");
    }

    @Step
    public void remove() {
        tabEl.$x("./i").click();
        waitNotPresented();
    }

    public boolean isPresented() {
        return tabEl.exists();
    }

    public boolean isSelected() {
        return Objects.equals(tabEl.getAttribute("class"), classSelected);
    }

    @Step
    public void open() {
        tabEl.click();
        tabEl.shouldHave(Condition.attribute("class", classSelected));
    }

    @Step
    public boolean tryToWaitPresented() {
        return Driver.tryToWaitElementPresented(tabEl, AppConfig.timeOutSecLong);
    }

    @Step
    public void waitPresented() {
        tabEl.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
    }

    @Step
    private void waitNotPresented() {
        tabEl.shouldNot(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecMedium));
    }
}
