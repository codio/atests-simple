package com.codio.dashboard.courses.teacher.course.overview.assignment.progress;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class StudentItem {

    private final SelenideElement item;
    private SelenideElement nameField;
    private SelenideElement grade;
    private SelenideElement points;
    private SelenideElement answered;

    private void init() {
        nameField = item.$x(".//a[contains(@class,'name-item')]");
        grade = item.$x(".//div[contains(@class,'grade')]//a");
        points = item.$x(".//div[contains(@class,'points')]//span");
        answered = item.$x(".//div[contains(@class,'answered')]//span");
    }

    StudentItem(SelenideElement item) {
        this.item = item;
        init();
    }

    StudentItem(String fullName) {
        item = $x(
            ".//a[contains(@class,'teacherUnitsStudentListItem-name-item') and contains(string(),'" + fullName
                + "')]/../../.."
        );
        init();
    }

    @Step("Get student Name")
    public String getName() {
        return nameField.getOwnText();
    }

    @Step("Get Grade")
    public int getGrade() {
        return Integer.parseInt(grade.getText());
    }

    @Step("Get Points")
    public int getPoints() {
        return Integer.parseInt(points.getText());
    }

    @Step("Get Answered")
    public int getAnswered() {
        return Integer.parseInt(answered.getText());
    }

    public boolean isPresented() {
        return item.exists();
    }
}
