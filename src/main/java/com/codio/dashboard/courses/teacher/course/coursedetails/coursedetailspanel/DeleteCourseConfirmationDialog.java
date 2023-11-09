package com.codio.dashboard.courses.teacher.course.coursedetails.coursedetailspanel;

import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseCodeConfirmDashboardDialog;
import com.codio.common.pageelements.Button;
import com.codio.dashboard.courses.teacher.CoursesPageOfTeacher;

public class DeleteCourseConfirmationDialog extends BaseCodeConfirmDashboardDialog {
    private final Button deleteCourseOnlyButton = new Button(dialog, "Delete Course Only");

    public DeleteCourseConfirmationDialog() {
        super("Delete course confirmation");
    }

    @Step("Click 'Delete Course Only' button")
    public CoursesPageOfTeacher clickDeleteCourseOnlyButton() {
        deleteCourseOnlyButton.clickOrExitTestWithErrorIfDisabled(
            "Delete Course Only button disabled. Check that confirmation code is correct"
        );
        return new CoursesPageOfTeacher().waitOpened();
    }

    @Step("Enter confirm code")
    public DeleteCourseConfirmationDialog setConfirmCode() {
        enterConfirmCode();
        return this;
    }

    @Step("Wait 'Delete course confirmation' dialog opened")
    public DeleteCourseConfirmationDialog waitOpened() {
        waitOpenedBase();
        return this;
    }
}
