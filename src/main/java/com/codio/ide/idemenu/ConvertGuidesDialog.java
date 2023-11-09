package com.codio.ide.idemenu;

import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.Button;

public class ConvertGuidesDialog extends BaseDialog {

    private final Button yesButton = new Button(dialog, "Yes");

    public ConvertGuidesDialog() {
        super("Convert guides?");
    }

    @Step("Confirm guides converting by click 'Yes' button")
    public IDEMenu clickYesButton() {
        yesButton.click();
        waitClosed();
        IDEMenu.tryToWaitClosed();
        return new IDEMenu().waitOpenedWithoutConvertGuidesDialog();
    }

    @Step("Wait 'Convert guides?' dialog opened")
    public ConvertGuidesDialog waitOpened() {
        waitOpenedBase();
        return this;
    }
}
