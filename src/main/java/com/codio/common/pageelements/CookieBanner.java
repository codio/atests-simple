package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class CookieBanner {

    private final SelenideElement cookieBannerButton = $x("//button[contains(string(),'Got it!')]");

    private final SelenideElement cookieBanner = cookieBannerButton.parent().parent();

    @Step("Remove cookie banner")
    public void removeIfExists() {
        if (cookieBanner.has(Condition.cssValue("opacity", "1"))) {
            cookieBannerButton.click();
            cookieBanner.shouldHave(Condition.attribute("style", "display: none;"));
        }
    }
}
