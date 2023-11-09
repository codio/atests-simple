package com.codio.common.dialogs;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.Input;

public class BaseCodeConfirmDashboardDialog extends BaseDialog {

    private final SelenideElement keyElement = $x("//label[@class='form-label form-confirmationTextInput-label']");
    private final Input confirmKeyInput = new Input("confirm");

    public BaseCodeConfirmDashboardDialog(String dialogName) {
        super(dialogName);
    }

    @Step("Get confirmation code")
    private String getKey() {
        return keyElement.getText().toLowerCase().replace("confirmation code: ", "");
    }

    @Step("Enter confirmation code")
    protected void enterConfirmCode() {
        confirmKeyInput.setValue(getKey());
    }
}
