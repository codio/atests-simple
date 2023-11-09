package com.codio.dashboard.auth;

import io.qameta.allure.Step;

import com.codio.common.pageelements.Button;
import com.codio.common.pageelements.Input;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class JoinCourseTokenPage {

    private final Button nextButton = new Button("Next");
    private final Input tokenField = new Input("token");
    private final String pageUrl = Driver.getBaseUrl() + "/p/join-course";

    @Step("Click Next button, exit with error if disabled")
    public JoinCourseEmailPage clickNextButton() {
        nextButton.clickOrExitTestWithErrorIfDisabled(
            "Next button disabled. Check that all required fields have correct values"
        );
        return new JoinCourseEmailPage().waitOpened();
    }

    @Step("Open Join Course page")
    public JoinCourseTokenPage open() {
        Driver.open(pageUrl);
        return this;
    }

    @Step("Set token in field")
    public JoinCourseTokenPage setTokenFieldValue(String token) {
        tokenField.setValue(token);
        return this;
    }

    @Step("Wait Join Course token page opened")
    public JoinCourseTokenPage waitOpened() {
        if (!Driver.tryToWaitElementPresented(tokenField.getInputEl(), AppConfig.timeOutSecMedium)) {
            open();
        }
        tokenField.waitPresented();
        return this;
    }

}
