package com.codio.dashboard.courses.teacher.course.overview.assignment.progress;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.*;
import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;
import com.codio.dashboard.courses.teacher.course.overview.assignment.AssignmentPageOfOverview;

public class ProgressTabOfAssignment {

    private final SelenideElement tab = $x("//div[@class='assignmentProgress-tab']");

    @Step
    public AssignmentPageOfOverview getOverviewAssignmentPage() {
        return new AssignmentPageOfOverview();
    }

    public boolean isOpened() {
        return tab.exists();
    }

    @Step
    public StudentItem student(String name) {
        return new StudentItem(name);
    }

    @Step
    public StudentItem student(SelenideElement item) {
        return new StudentItem(item);
    }

    @Step("Wait assignment Progress tab opened")
    public ProgressTabOfAssignment waitOpened() {
        tab.should(Condition.exist);
        CoursePageOfTeacher.waitLoader();
        return this;
    }
}
