package com.codio.ide.idemenu.education;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class EducationMenu {

    private final SelenideElement educationMenuItem = $x("//li[contains(a/text(),'Education')]");
    private final SelenideElement educationMenuItemDisabled =
        $x("//li[@data-title='Education' and contains(@class,'disabled')]");

    @Step("Wait education menu item updated after opened")
    public void waitEducationMenuItemUpdatedAfterOpened() {
        if (educationMenuItem.exists()) {
            Driver.tryToWaitElementPresented(educationMenuItemDisabled, AppConfig.timeOutSec);
            educationMenuItemDisabled.shouldNot(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecMedium));
        }
    }
}
