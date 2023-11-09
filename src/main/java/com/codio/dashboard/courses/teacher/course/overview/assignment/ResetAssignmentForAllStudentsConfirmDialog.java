package com.codio.dashboard.courses.teacher.course.overview.assignment;

import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseCodeConfirmDashboardDialog;
import com.codio.common.pageelements.Button;
import com.codio.dashboard.courses.teacher.course.overview.assignment.progress.ProgressTabOfAssignment;

public class ResetAssignmentForAllStudentsConfirmDialog extends BaseCodeConfirmDashboardDialog {

    private final Button yesButton = new Button(dialog, "Yes");

    public ResetAssignmentForAllStudentsConfirmDialog() {
        super("Reset assignment confirm");
    }

    @Step("Confirm reset assignment for all students by click 'Yes' button")
    public ProgressTabOfAssignment clickYesButton() {
        yesButton.clickOrExitTestWithErrorIfDisabled("Button disabled. Check that confirmation code is correct");
        return new ProgressTabOfAssignment();
    }

    @Step("Set confirm code")
    public ResetAssignmentForAllStudentsConfirmDialog setConfirmCode() {
        enterConfirmCode();
        return this;
    }

    @Step("Wait 'Reset assignment confirm' for all students dialog opened")
    public ResetAssignmentForAllStudentsConfirmDialog waitOpened() {
        waitOpenedBase();
        return this;
    }
}
