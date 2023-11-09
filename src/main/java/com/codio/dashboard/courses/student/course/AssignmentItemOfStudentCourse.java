package com.codio.dashboard.courses.student.course;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.NotificationDash;
import com.codio.helpers.Driver;
import com.codio.ide.idemenu.IDEMenu;

public class AssignmentItemOfStudentCourse {

    private final SelenideElement item;
    private final SelenideElement grade;
    private final SelenideElement answered;
    private final SelenideElement status;
    private final NotificationDash notification = new NotificationDash();

    AssignmentItemOfStudentCourse(SelenideElement module, String name) {
        item = module.$x(".//td[@class='studentAssignment-name' and string()='" + name + "']/..");
        grade = item.$x(".//div[@class='studentAssignmentGrade-grade']");
        answered = item.$x(".//td[@class='studentAssignment-answered']//span");
        status = item.$x(".//td[@class='studentAssignment-status']/div");
    }

    @Step("Get Grade value")
    public int getGrade() {
        return Integer.parseInt(grade.getText());
    }

    @Step("Get Answered value")
    public String getAnswered() {
        return answered.getText();
    }

    private boolean checker() {
        return new CoursePageOfStudent().tryToWaitClosed();
    }

    @Step("Try to click Create button again")
    private void repeater(int attempt) {
        Driver.logInfo("Activating reopening project (" + attempt + ")");
        if (notification.isPresented()) {
            String notificationMessage = notification.getLastNotificationMessage();
            Driver.logInfo("Got notification with message: " + notificationMessage);
        }
        Driver.reload();
        new CoursePageOfStudent().waitOpened();
        status.click();
    }

    private void actionIfAttemptsFailed(int attempts) {
        Driver.exitTestWithError("After " + attempts + " attempts to get assignment start opening, it still doesn't");
    }

    @Step("Open assignment")
    public IDEMenu open() {
        status.click();
        Driver.repeatIfFailed(this::checker, this::repeater, this::actionIfAttemptsFailed);
        return new IDEMenu().waitOpened();
    }
}
