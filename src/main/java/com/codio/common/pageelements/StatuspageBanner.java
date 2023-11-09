package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.Driver;

public class StatuspageBanner {

    private final SelenideElement statuspageIframePath = $x("//iframe[contains(@src,'statuspage')]");
    private final SelenideElement closeStatuspageBannerButton = $x("//div[@class='frame-close']/button");
    private final SelenideElement statuspageBannerOpened =
        $x("//iframe[contains(@src,'statuspage') and contains(@style,'left: 60px;')]");

    public boolean isStatuspageBannerPresented() {
        return statuspageBannerOpened.exists();
    }

    @Step("Remove cookie banner")
    public void removeIfExist() {
        if (isStatuspageBannerPresented()) {
            switchToStatusPageIframe();
            closeStatuspageBannerButton.click();
            switchToDefaultContent();
        }
    }

    @Step
    public void switchToStatusPageIframe() {
        Driver.currentDriver().switchTo().frame(statuspageIframePath);
    }

    @Step
    public void switchToDefaultContent() {
        Driver.currentDriver().switchTo().defaultContent();
    }
}
