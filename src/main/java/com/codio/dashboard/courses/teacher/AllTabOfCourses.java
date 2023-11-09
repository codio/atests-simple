package com.codio.dashboard.courses.teacher;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class AllTabOfCourses extends BaseCoursesTab {
    private final SelenideElement addFromShareCodeMenuItem = $x("//li[contains(string(),'Add From Share Code')]");
    private final SelenideElement newCourseButton = $x("//button[@title='Add course']");

    @Step("Open New Course button menu")
    public AllTabOfCourses clickNewCourseButton() {
        newCourseButton.click();
        return this;
    }

    @Step("Open Clone A Course By Code dialog by click Author Your Own Course in New Course button menu")
    public CloneACourseByCodeDialog clickAddFromShareCodeMenuItem() {
        addFromShareCodeMenuItem.click();
        return new CloneACourseByCodeDialog().waitOpened();
    }

    @Step("Search course by name or tag")
    public AllTabOfCourses searchCourse(String nameOrTag) {
        super.searchCourse(nameOrTag);
        return this;
    }

    @Step("Wait All tab of teacher Courses page opened")
    public AllTabOfCourses waitOpened() {
        super.waitOpened();
        return this;
    }
}
