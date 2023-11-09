package com.codio.dashboard.courses.teacher.course.students;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.Button;
import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class StudentsPage {

    private final SelenideElement page = $x("//div[@class='teacherStudents']");
    private final Button addAStudentButton = new Button(page, "Add a student");
    private final SelenideElement studentsList = page.$x(".//div[@class='scrollable']");
    private final SelenideElement emptyMessage = page.$x(".//span[contains(@class,'emptyMessage')]");

    @Step
    public AddAStudentDialog clickAddAStudentButton() {
        addAStudentButton.click();
        return new AddAStudentDialog().waitOpened();
    }

    @Step
    public String getStudentAddToken() {
        AddAStudentDialog dialog = clickAddAStudentButton();
        String token = dialog.getStudentAddToken();
        dialog.clickDoneButton();
        return token;
    }

    @Step("Wait course Students page opened")
    public StudentsPage waitOpened() {
        Driver.waitOneOfElementsPresented(studentsList, emptyMessage, AppConfig.timeOutSecLong);
        CoursePageOfTeacher.waitLoader();
        return this;
    }
}
