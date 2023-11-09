package com.codio.dashboard.auth;

import static com.codeborne.selenide.Selenide.$x;

import io.qameta.allure.Step;

import com.codio.common.HotKeys;
import com.codio.common.pageelements.*;
import com.codio.dashboard.courses.student.CoursesPageOfStudent;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class JoinCourseCredentialsPage {

    private final Button joinCourseButton = new Button("Join Course");
    private final Input firstNameField = new Input("firstName");
    private final Input lastNameField = new Input("lastName");
    private final PasswordInput passwordField =
        new PasswordInput($x("//input[contains(@class,'ReactPasswordStrength-input')]"));
    private final StatuspageBanner statuspageBanner = new StatuspageBanner();
    private final CookieBanner cookieBanner = new CookieBanner();
    private final NotificationDash notification = new NotificationDash();

    @Step("Click Join course button")
    public CoursesPageOfStudent clickJoinCourseButton() {
        notification.closeAllIfExist();
        cookieBanner.removeIfExists();
        statuspageBanner.removeIfExist();
        joinCourseButton.clickOrExitTestWithErrorIfDisabled(
            "Join Course button disabled. Check that all required fields have correct values"
        );
        if (notification.tryToWaitErrorNotificationPresented(AppConfig.timeOutSec)) {
            Driver.exitTestWithError("Got error notification: " + notification.getLastNotificationMessage());
        }
        return new CoursesPageOfStudent().waitOpened();
    }

    @Step("Set First name")
    public JoinCourseCredentialsPage setFirstNameValueIfFieldAvailable(String firstName) {
        if (firstNameField.exists()) {
            firstNameField.setValue(firstName);
        }
        return this;
    }

    @Step("Set Last name")
    public JoinCourseCredentialsPage setLastNameValueIfFieldAvailable(String lastName) {
        if (lastNameField.exists()) {
            lastNameField.setValue(lastName);
        }
        return this;
    }

    @Step("Set password")
    public JoinCourseCredentialsPage setPasswordFieldValue(String password) {
        passwordField.setValue(password);
        if (firstNameField.exists()) {
            passwordField.waitChecked();
            // Hack to make field send full password string =====
            passwordField.getInputEl().click();
            HotKeys.deleteAll();
            for (int i = 0; i < password.length(); i++) {
                String letter = password.substring(i, i + 1);
                HotKeys.pressKeys(letter);
                Driver.wait(100);
            }
            passwordField.waitChecked();
            passwordField.showPassword();
            // ==================================================
        }
        return this;
    }

    @Step("Wait Join Course (Credentials) page opened")
    public JoinCourseCredentialsPage waitOpened() {
        passwordField.waitPresented();
        return this;
    }
}
