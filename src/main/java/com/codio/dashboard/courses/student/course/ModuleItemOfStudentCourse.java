package com.codio.dashboard.courses.student.course;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class ModuleItemOfStudentCourse {

    private final SelenideElement item;

    ModuleItemOfStudentCourse(String name) {
        item = $x("//div[@class='studentCourseUnit-header-name' and string()='" + name + "']/../..");
    }

    @Step
    public AssignmentItemOfStudentCourse assignment(String assignmentName) {
        return new AssignmentItemOfStudentCourse(item, assignmentName);
    }
}
