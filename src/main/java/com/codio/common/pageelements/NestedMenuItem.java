package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;
import java.util.Objects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class NestedMenuItem {

    private final String classSelected = "--active";
    private final SelenideElement item;
    private SelenideElement name;

    private void init() {
        name = item.$x("./a");
    }

    public NestedMenuItem(String itemName) {
        item = $x("//li[contains(@class,'nestedNavigation-navigationItem') and a/text()='" + itemName + "']");
        init();
    }

    @Step
    public boolean isPresented() {
        return item.exists();
    }

    @Step
    public boolean isSelected() {
        return Objects.requireNonNull(item.getAttribute("class")).contains(classSelected);
    }

    @Step
    public void click() {
        item.click();
        item.shouldHave(Condition.attributeMatching("class", ".*" + classSelected));
    }

    @Step
    public boolean clickIfNotSelected() { // to save about 1 sec if selected
        if (isSelected()) {
            return false;
        }
        item.click();
        item.shouldHave(Condition.attributeMatching("class", ".*" + classSelected));
        return true;
    }

    @Step
    public String getName() {
        return name.getText();
    }

    @Step
    public boolean tryToWaitPresented() {
        return Driver.tryToWaitElementPresented(item, AppConfig.timeOutSecLong);
    }

    @Step
    public void waitNotPresented() {
        item.shouldNot(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
    }

    @Step
    public void waitPresented() {
        item.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
    }

    @Step
    public void waitSelected() {
        item.shouldHave(
            Condition.attributeMatching("class", ".*" + classSelected + ".*"),
            Duration.ofSeconds(AppConfig.timeOutSecLong)
        );
    }
}
