package com.codio.common.pageelements;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class PasswordInput extends Input {

    private final SelenideElement showPasswordIcon;
    private final SelenideElement hidePasswordIcon;
    private final SelenideElement passwordChecking;
    private final SelenideElement passwordChecked;

    public PasswordInput(SelenideElement input) {
        super(input);
        showPasswordIcon = inputField.$x("../..//i[@title='Show password']");
        hidePasswordIcon = inputField.$x("../..//i[@title='Hide password']");
        passwordChecking = inputField
            .$x("../../../div[@class='form-labeledPasswordInput--hint' and string()='Checking password security...']");
        passwordChecked = inputField.$x("../../../div[@class='form-labeledPasswordInput--hint' and string()='']");
    }

    @Step
    public void hidePassword() {
        hidePasswordIcon.click();
    }

    @Step
    public void setValue(String value) {
        if (showPasswordIcon.exists()) {
            showPassword();
        }
        super.setValue(value);
        if (hidePasswordIcon.exists()) {
            hidePassword();
        }
    }

    @Step
    public void showPassword() {
        showPasswordIcon.click();
    }

    @Step
    public void waitChecked() {
        Driver.tryToWaitElementPresented(passwordChecking, AppConfig.timeOutSec);
        passwordChecked.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecMedium));
    }
}
