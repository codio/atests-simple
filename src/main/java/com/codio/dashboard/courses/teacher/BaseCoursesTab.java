package com.codio.dashboard.courses.teacher;

import static com.codeborne.selenide.Selenide.*;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.*;
import com.codio.helpers.AppConfig;

public class BaseCoursesTab {

    protected final SelenideElement controls = $x("//div[@class='advancedList-controls-container']");
    protected final Search search = new Search(controls);
    protected final SelenideElement coursesList = $x("//div[@class='teacherMain']//div[@class='infinite-list']");
    protected final ElementsCollection courseItems = $$x("//div[@class='teacherCourseListItem defaultCourseListItem']");
    protected final CookieBanner cookieBanner = new CookieBanner();

    public CourseItem course(String courseName) {
        cookieBanner.removeIfExists();
        search.setValue(courseName);
        return new CourseItem(courseName);
    }

    public CourseItem course(int courseNumberInList) {
        return new CourseItem(courseItems.get(courseNumberInList));
    }

    @Step
    public BaseCoursesTab searchCourse(String nameOrTag) {
        search.setValue(nameOrTag);
        return this;
    }

    @Step("Wait custom tab of teacher Courses page opened")
    public BaseCoursesTab waitOpened() {
        coursesList.shouldBe(Condition.visible, Duration.ofSeconds(AppConfig.timeOutSecLong));
        return this;
    }
}
