package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.Driver;

public class UiBlocker {

    private static final SelenideElement blocker = $x("//div[contains(@class,'ui-blocker')]");

    @Step("Wait UI blocker appear and disappear")
    public static void tryToWaitPerformed(int timeOutSec) {
        Driver.tryToWaitElementPresented(blocker, 1);
        Driver.waitElementNotPresented(blocker, timeOutSec);
    }
}
