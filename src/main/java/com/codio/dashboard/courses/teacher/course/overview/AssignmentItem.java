package com.codio.dashboard.courses.teacher.course.overview;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.dashboard.courses.teacher.course.overview.assignment.AssignmentPageOfOverview;

public class AssignmentItem {

    private final SelenideElement item;
    private SelenideElement assignmentEnableField;
    private SelenideElement assignmentNameEl;
    private SelenideElement assignmentPinIcon;
    private SelenideElement assignmentCompletedField;

    private void init() {
        assignmentNameEl = item.$x(".//td[contains(@class,'name')]/button");
        assignmentEnableField = item.$x(".//td[contains(@class,'enable')]/button");
        assignmentPinIcon = item.$x(".//td[contains(@class,'pin')]/button");
        assignmentCompletedField = item.$x(".//td[@title='Students Completed']//span");
    }

    AssignmentItem(SelenideElement item) {
        this.item = item;
        init();
    }

    AssignmentItem(SelenideElement module, String name) {
        item = module.$x(".//td[contains(@class,'name')]/button[string()='" + name + "']/../..");
        init();
    }

    @Step
    public int getCompleted() {
        return Integer.parseInt(assignmentCompletedField.getText());
    }

    @Step
    public String getName() {
        return assignmentNameEl.getText();
    }

    @Step
    public boolean isDisabled() {
        return assignmentEnableField.has(Condition.attribute("title", "Enable assignment"));
    }

    @Step
    public boolean isPinned() {
        return assignmentPinIcon.has(Condition.attribute("title", "Unpin assignment"));
    }

    @Step
    public boolean isPresented() {
        return assignmentNameEl.exists();
    }

    @Step
    public AssignmentPageOfOverview open() {
        assignmentNameEl.click();
        return new AssignmentPageOfOverview().waitOpened();
    }
}
