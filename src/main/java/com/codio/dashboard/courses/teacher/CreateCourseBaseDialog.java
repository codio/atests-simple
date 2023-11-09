package com.codio.dashboard.courses.teacher;

import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.*;
import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;

public class CreateCourseBaseDialog extends BaseDialog {

    private final Input courseNameInput = new Input(dialog, "name");
    private final Button createCourseButton = new Button(dialog, "Create Course");
    private String courseName;

    public CreateCourseBaseDialog(String dialogName) {
        super(dialogName);
    }

    @Step("Click Create Course button and wait it course opened")
    public CoursePageOfTeacher clickCreateCourseButton() {
        createCourseButton.clickOrExitTestWithErrorIfDisabled(
            "Create Course button disabled. Check that all required fields have correct values"
        );
        waitClosed();
        return new CoursePageOfTeacher(courseName).waitOpened().waitOverviewItemSelected();
    }

    @Step("Set value in Course Name input field")
    protected void baseSetCourseName(String courseName) {
        courseNameInput.setValue(courseName);
        this.courseName = courseName;
    }

    @Step("Wait dialog opened")
    protected void baseWaitOpened() {
        waitOpenedBase();
    }
}
