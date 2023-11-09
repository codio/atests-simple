package com.codio.dashboard.courses.student;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.dashboard.courses.student.course.CoursePageOfStudent;
import com.codio.helpers.Driver;

public class CourseItem {

    private final SelenideElement item;
    private final SelenideElement nameEl;

    CourseItem(String name) {
        item = $x(".//div[contains(@class,'studentCourseListItem-title') and string()='" + name + "']");
        nameEl = item.$x(".//div[contains(@class,'studentCourseListItem-title')]");
    }

    @Step
    public String getName() {
        return nameEl.getOwnText();
    }

    @Step
    public boolean isPresented() {
        return item.exists();
    }

    @Step
    public CoursePageOfStudent open() {
        if (!isPresented()) {
            Driver.exitTestWithError("There is no '" + getName() + "' course in list");
        }
        item.click();
        return new CoursePageOfStudent().waitOpened();
    }
}
