package com.codio.ide.idemenu;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.Button;

public class MarkAsCompletedWarningDialog extends BaseDialog {

    private final Button okButton = new Button(dialog, "Ok");
    private final SelenideElement confirmationStringInput = dialog.$x(".//input[@id='confirmation-code']");

    public MarkAsCompletedWarningDialog() {
        super("Warning");
    }

    @Step("click ok on mark as completed warning dialog")
    public void clickOkButton() {
        okButton.click();
    }

    @Step("type confirmation string on mark as completed warning dialog")
    public void setConfirmationString(String confirmationString) {
        confirmationStringInput.setValue(confirmationString);
    }

    @Step("is dialog existing")
    public boolean isDialogExisting() {
        return dialog.exists();
    }

    @Step("is confirmation string needed")
    public boolean isConfirmationStringNeeded() {
        return confirmationStringInput.exists();
    }
}
