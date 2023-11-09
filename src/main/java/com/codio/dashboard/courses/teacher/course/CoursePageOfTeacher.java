package com.codio.dashboard.courses.teacher.course;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.NestedMenuItem;
import com.codio.common.pageelements.NotificationDash;
import com.codio.dashboard.courses.teacher.CoursesPageOfTeacher;
import com.codio.dashboard.courses.teacher.course.coursedetails.CourseDetailsPage;
import com.codio.dashboard.courses.teacher.course.overview.OverviewPageOfCourse;
import com.codio.dashboard.courses.teacher.course.students.StudentsPage;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class CoursePageOfTeacher {
    private static final SelenideElement activeLoader = $x("//div[@class='loadingBar teacherCourse-loading']");
    private final NestedMenuItem overviewItem = new NestedMenuItem("Overview");
    private final NestedMenuItem studentsItem = new NestedMenuItem("Students");
    private final NestedMenuItem courseDetailsItem = new NestedMenuItem("Course Details");
    private final NotificationDash notification = new NotificationDash();
    private String courseName;

    public CoursePageOfTeacher(String courseName) {
        this.courseName = courseName;
    }

    public CoursePageOfTeacher() {
    }

    @Step
    public CourseDetailsPage openCourseDetailsPage() {
        if (courseDetailsItem.clickIfNotSelected()) {
            return new CourseDetailsPage().waitOpened();
        }
        return new CourseDetailsPage();
    }

    @Step
    public OverviewPageOfCourse openOverviewPage() {
        if (overviewItem.clickIfNotSelected()) {
            return new OverviewPageOfCourse().waitOpened();
        }
        return new OverviewPageOfCourse();
    }

    @Step
    public StudentsPage openStudentsPage() {
        if (studentsItem.clickIfNotSelected()) {
            return new StudentsPage().waitOpened();
        }
        return new StudentsPage();
    }

    @Step("Wait loader")
    public static void waitLoader() {
        Driver.tryToWaitElementPresented(activeLoader, 1);
        Driver.tryToWaitElementNotPresented(activeLoader, AppConfig.timeOutSecMedium);
    }

    @Step
    public CoursePageOfTeacher reload() {
        Driver.reload();
        overviewItem.waitNotPresented();
        return waitOpened();
    }

    @Step
    private void repeater(int attempt) {
        if (notification.isPresented()) {
            Driver.logInfo("Got notification with message: " + notification.getLastNotificationMessage());
        }
        Driver.logInfo("Activating reopening course (" + attempt + ")");
        new CoursesPageOfTeacher().open().openAllTab().course(courseName).reopen();
    }

    @Step
    private void actionIfAttemptsFailed(int attempts) {
        Driver.exitTestWithError("After " + attempts + " attempts course " + courseName + " still not opened");
    }

    @Step("Wait course page opened")
    public CoursePageOfTeacher waitOpened() {
        if (courseName == null) {
            overviewItem.waitPresented();
        } else {
            Driver.repeatIfFailed(overviewItem::tryToWaitPresented, this::repeater, this::actionIfAttemptsFailed);
        }
        return this;
    }

    @Step()
    public CoursePageOfTeacher waitOverviewItemSelected() {
        overviewItem.waitSelected();
        return this;
    }
}
