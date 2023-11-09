package com.codio.dashboard.courses.teacher;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;
import com.codio.helpers.Driver;

public class CourseItem {

    protected SelenideElement courseNameEl;
    protected SelenideElement item;
    protected SelenideElement courseItemCreatedDate;

    private void init() {
        courseNameEl = item.$x(".//button[@class='btn defaultCourseListItem-title']");
        courseItemCreatedDate = item.$x(".//div[@title='Created date']");
    }

    CourseItem(SelenideElement item) {
        this.item = item;
        init();
    }

    CourseItem(String name) {
        item = $x(".//button[string()='" + name + "']/../../..");
        init();
    }

    @Step
    protected String getName() {
        return courseNameEl.getText();
    }

    @Step
    public boolean isPresented() {
        return courseNameEl.exists();
    }

    @Step
    public CoursePageOfTeacher open() {
        if (!isPresented()) {
            Driver.exitTestWithError("There is no '" + getName() + "' course in list");
        }
        String name = getName();
        courseNameEl.click();
        return new CoursePageOfTeacher(name).waitOpened();
    }

    @Step
    public CoursePageOfTeacher reopen() {
        String name = getName();
        courseNameEl.click();
        return new CoursePageOfTeacher(name);
    }
}
