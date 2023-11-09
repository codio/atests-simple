package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.*;

import java.time.Duration;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class NotificationDash {
    private final ElementsCollection notifications = $$x("//div[@class='notifications']/div/div");
    private final SelenideElement allertError =
        $x("//div[@class='notifications']/div/div[contains(@class,'alert-error')]");

    private SelenideElement closeButton(int notificationNumber) {
        return notifications.get(notificationNumber).$x(".//i[string()='close']");
    }

    private SelenideElement notificationByMessage(String message) {
        return $x("//div[@class='message' and string()='" + message + "']/../..");
    }

    private SelenideElement notificationMessage(int notificationNumber) {
        return notifications.get(notificationNumber).$x(".//div[@class='message']");
    }

    @Step
    public void closeAllIfExist() {
        int n = notifications.size();
        try {
            for (int i = n; i > 0; i--) {
                closeButton(i - 1).click();
            }
        } catch (AssertionError e) {
            Driver.logInfo("No more notifications");
        }
        notifications.shouldHave(CollectionCondition.size(0));
    }

    @Step
    public String getLastNotificationMessage() {
        waitPresented();
        return notificationMessage(notifications.size() - 1).getText();
    }

    @Step
    public SelenideElement getFirst() {
        return notifications.first();
    }

    @Step
    public boolean isPresented() {
        return !notifications.isEmpty();
    }

    @Step
    public boolean tryToWaitErrorNotificationPresented(int timeOutSec) {
        return Driver.tryToWaitElementPresented(allertError, timeOutSec);
    }

    @Step("Wait notification appears")
    public NotificationDash waitPresented() {
        notifications
            .shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(AppConfig.timeOutSecMedium));
        notifications.get(0).shouldHave(Condition.attributeMatching("class", ".*fade-enter-done"));
        return this;
    }

    @Step("Wait notification with message appears")
    public NotificationDash waitPresented(String message) {
        notificationByMessage(message).shouldHave(
            Condition.attributeMatching("class", ".*fade-enter-done"),
            Duration.ofSeconds(AppConfig.timeOutSecMedium)
        );
        return this;
    }
}
