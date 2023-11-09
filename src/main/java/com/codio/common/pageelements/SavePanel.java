package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class SavePanel {

    @Step
    public static SelenideElement getByName(String name) {
        return $x("//*[@class='savePanel-div' and legend/text()='" + name + "']");
    }
}
