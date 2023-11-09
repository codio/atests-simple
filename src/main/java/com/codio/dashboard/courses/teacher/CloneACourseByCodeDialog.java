package com.codio.dashboard.courses.teacher;

import io.qameta.allure.Step;

import com.codio.common.pageelements.Input;

public class CloneACourseByCodeDialog extends CreateCourseBaseDialog {

    private final Input courseCodeInput = new Input(dialog, "code");

    public CloneACourseByCodeDialog() {
        super("Clone a Course by code");
    }

    @Step("Set value in course code input")
    public CloneACourseByCodeDialog setCourseCode(String token) {
        courseCodeInput.setValue(token);
        return this;
    }

    @Step("Set value in Course Name input field")
    public CloneACourseByCodeDialog setCourseName(String courseName) {
        baseSetCourseName(courseName);
        return this;
    }

    @Step("Wait 'Clone a Course by code' dialog opened")
    public CloneACourseByCodeDialog waitOpened() {
        baseWaitOpened();
        return this;
    }
}
