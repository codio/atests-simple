package com.codio.dashboard;

import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.Button;
import com.codio.dashboard.auth.LoginPage;
import com.codio.helpers.Driver;

public class SignOutDialog extends BaseDialog {

    private final Button yesButton = new Button(dialog, "Yes");

    public SignOutDialog() {
        super("Sign Out");
    }

    @Step("Confirm sign out by click 'Yes' button")
    public LoginPage clickYesButton() {
        yesButton.click();
        Driver.clearCookies();
        return new LoginPage().tryToWaitFlashHack().waitOpened();
    }

    @Step("Wait 'Sign Out' confirmation dialog opened")
    public SignOutDialog waitOpened() {
        waitOpenedBase();
        return this;
    }
}
