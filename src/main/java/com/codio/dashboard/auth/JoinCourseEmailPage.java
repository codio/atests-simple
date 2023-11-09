package com.codio.dashboard.auth;

import io.qameta.allure.Step;

import com.codio.common.pageelements.Button;
import com.codio.common.pageelements.Input;

public class JoinCourseEmailPage {

    private final Button nextButton = new Button("Next");
    private final Input emailField = new Input("email");

    @Step
    public JoinCourseCredentialsPage clickNextButton() {
        nextButton.clickOrExitTestWithErrorIfDisabled(
            "Next button disabled. Check that all required fields have correct values"
        );
        return new JoinCourseCredentialsPage().waitOpened();
    }

    @Step
    public JoinCourseEmailPage setEmailFieldValue(String email) {
        emailField.setValue(email);
        return this;
    }

    @Step("Wait Join Course (Email) page opened")
    public JoinCourseEmailPage waitOpened() {
        emailField.waitPresented();
        return this;
    }
}
