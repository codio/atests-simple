package com.codio.ide.idemenu;

import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.Button;

public class CookiePreviewConfirmationDialog extends BaseDialog {

    private final Button okButton = new Button(dialog, "Ok");

    public CookiePreviewConfirmationDialog() {
        super("Cookie preview confirmation");
    }

    @Step("Close 'Cookie preview confirmation' dialog by click 'Ok' button")
    public IDEMenu clickOkButton() {
        okButton.click();
        if (!tryToWaitClosed()) {
            okButton.click();
            waitClosed();
        }
        return new IDEMenu();
    }

    @Step("Wait 'Cookie preview confirmation' dialog opened")
    public CookiePreviewConfirmationDialog waitOpened() {
        waitOpenedBase();
        return this;
    }
}
