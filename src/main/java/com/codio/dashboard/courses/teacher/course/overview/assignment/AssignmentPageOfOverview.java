package com.codio.dashboard.courses.teacher.course.overview.assignment;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.*;
import com.codio.dashboard.courses.teacher.course.overview.assignment.progress.ProgressTabOfAssignment;
import com.codio.helpers.Driver;

public class AssignmentPageOfOverview {

    private final SelenideElement actionsPanel = $x("//div[contains(@class,'actionsPanel')]");
    private final Button resetButton = new Button(actionsPanel.$x(".//a[string()='Reset']"));
    private final Tab progressTab = new Tab("Progress");
    private final Tab settingsTab = new Tab("Settings");

    @Step("Open Reset Assignment For All Students confirmation dialog by click Reset button")
    public ResetAssignmentForAllStudentsConfirmDialog clickResetButton() {
        resetButton.clickOrExitTestWithErrorIfDisabled();
        return new ResetAssignmentForAllStudentsConfirmDialog().waitOpened();
    }

    @Step("Open Progress tab")
    public ProgressTabOfAssignment openProgressTab() {
        progressTab.open();
        return new ProgressTabOfAssignment().waitOpened();
    }

    @Step("Reset Assignment For All Students")
    public AssignmentPageOfOverview resetForAllStudentsIfPossible() {
        if (resetButton.isEnabled()) {
            Driver.logInfo("reset");
            clickResetButton().setConfirmCode().clickYesButton();
        }
        return this;
    }

    @Step("Wait Course Assignment page opened")
    public AssignmentPageOfOverview waitOpened() {
        progressTab.waitPresented();
        settingsTab.waitPresented();
        return this;
    }
}
