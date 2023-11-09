package com.codio.dashboard.courses.teacher.course.coursedetails;

import io.qameta.allure.Step;

import com.codio.dashboard.courses.teacher.course.coursedetails.coursedetailspanel.CourseDetailsPanel;
import com.codio.helpers.Driver;

public class CourseDetailsPage {

    @Step
    public CourseDetailsPanel courseDetailsPanel() {
        return new CourseDetailsPanel();
    }

    @Step("Reload page")
    public CourseDetailsPage reload() {
        Driver.reload();
        waitOpened();
        return this;
    }

    @Step("Wait Course Details page opened")
    public CourseDetailsPage waitOpened() {
        courseDetailsPanel().waitPresented();
        return this;
    }
}
