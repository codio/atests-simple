package com.codio.dashboard.courses.teacher.course.coursedetails.coursedetailspanel;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.*;
import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;
import com.codio.dashboard.courses.teacher.course.coursedetails.CourseDetailsPage;
import com.codio.helpers.AppConfig;

public class CourseDetailsPanel {

    private final SelenideElement panel = SavePanel.getByName("Course Details");
    private final SelenideElement organization = $x("//input[@class='courseDetails-details-org-name']");
    private final SelenideElement descriptionTextarea = $x("//textarea[@name='description']");
    private final Button deleteCourseButton = new Button("Delete Course");

    @Step("Open 'Delete course confirmation' dialog by lick 'Delete Course' button")
    public DeleteCourseConfirmationDialog clickDeleteCourseButton() {
        deleteCourseButton.click();
        return new DeleteCourseConfirmationDialog().waitOpened();
    }

    @Step("Get value of Description textarea field")
    public String getDescription() {
        return descriptionTextarea.getValue();
    }

    @Step("Get value of Organization field")
    public String getOrganization() {
        return organization.getValue();
    }

    public CourseDetailsPage page() {
        return new CourseDetailsPage();
    }

    @Step("Set value in Description textarea field")
    public CourseDetailsPanel setDescription(String description) {
        descriptionTextarea.setValue(description);
        return this;
    }

    @Step("Wait Course Details panel presented")
    public CourseDetailsPanel waitPresented() {
        panel.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
        CoursePageOfTeacher.waitLoader();
        return this;
    }
}
