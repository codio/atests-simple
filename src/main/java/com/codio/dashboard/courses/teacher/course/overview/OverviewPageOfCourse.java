package com.codio.dashboard.courses.teacher.course.overview;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;
import com.codio.dashboard.courses.teacher.course.overview.assignment.AssignmentPageOfOverview;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class OverviewPageOfCourse {

    private final SelenideElement modulesList =
        $x("//div[@class='teacherUnits']//div[contains(@class,'unitsScrollable')]");
    private final SelenideElement blockNotice = $x("//div[@class='teacherUnits is-empty']//div[@class='blockNotice']");

    public boolean isOpened() {
        return blockNotice.exists() || modulesList.exists();
    }

    public ModuleItem module(String moduleName) {
        return new ModuleItem(moduleName);
    }

    @Step("Open assignment in exact module")
    public AssignmentPageOfOverview openAssignmentInModule(String assignmentName, String moduleName) {
        return module(moduleName).assignment(assignmentName).open();
    }

    @Step("Wait course Overview page opened")
    public OverviewPageOfCourse waitOpened() {
        Driver.waitOneOfElementsPresented(modulesList, blockNotice, AppConfig.timeOutSecLong);
        CoursePageOfTeacher.waitLoader();
        return this;
    }
}
