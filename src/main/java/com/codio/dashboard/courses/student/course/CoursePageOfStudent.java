package com.codio.dashboard.courses.student.course;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class CoursePageOfStudent {

    private final SelenideElement course = $x("//div[@class='studentCourse']");
    private final ElementsCollection modules = course.$$x(".//div[@class='studentCourseUnit']");
    private final SelenideElement blockNotice = course.$x(".//div[@class='blockNotice']");

    public ModuleItemOfStudentCourse module(String moduleName) {
        return new ModuleItemOfStudentCourse(moduleName);
    }

    @Step("Try to wait student course page closed")
    public boolean tryToWaitClosed() {
        return Driver.tryToWaitConditionIsFalse(course::exists, AppConfig.timeOutSecLong);
    }

    @Step("Wait student course page opened")
    public CoursePageOfStudent waitOpened() {
        Driver.waitOneOfElementsPresented(modules.first(), blockNotice, AppConfig.timeOutSecLong);
        return this;
    }
}
