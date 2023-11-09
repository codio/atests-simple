package com.codio.dashboard.courses.student;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;

public class ActiveTab extends BaseTabOfStudentCourses {

    @Step("Wait Active tab of student Courses page opened")
    public ActiveTab waitOpened() {
        coursesList.shouldBe(Condition.visible, Duration.ofSeconds(AppConfig.timeOutSecLong));
        return this;
    }
}
