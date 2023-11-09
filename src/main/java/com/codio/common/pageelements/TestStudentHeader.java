package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;

public class TestStudentHeader {

    private final SelenideElement trialNotificationBar = $x("//div[@class='trialNotificationBar']");
    private final SelenideElement switchBackToTeacherAccountButton =
        trialNotificationBar.$x(".//a[string()='Switch back to teachers account']");

    @Step("Switch back to teacher account")
    public void clickSwitchBackToTeacherAccountButton() {
        switchBackToTeacherAccountButton.click();
        new CoursePageOfTeacher().waitOpened();
    }

    public boolean isPresented() {
        return switchBackToTeacherAccountButton.exists();
    }
}
