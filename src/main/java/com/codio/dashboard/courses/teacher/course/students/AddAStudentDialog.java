package com.codio.dashboard.courses.teacher.course.students;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.Button;

public class AddAStudentDialog extends BaseDialog {
    private final SelenideElement tokenInput = dialog.$x(".//div[@class='ModalAddUser-forStudent']/span[1]//input");
    private final Button doneButton = new Button(dialog, "Done");

    public AddAStudentDialog() {
        super("Add a Student");
    }

    @Step("Close dialog by click 'Done' button")
    public StudentsPage clickDoneButton() {
        doneButton.click();
        waitClosed();
        return new StudentsPage();
    }

    @Step("Get token input value")
    public String getStudentAddToken() {
        return tokenInput.getValue();
    }

    @Step("Wait Add a Student dialog opened")
    public AddAStudentDialog waitOpened() {
        waitOpenedBase();
        return this;
    }
}
