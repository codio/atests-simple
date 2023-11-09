package com.codio.common.dialogs;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class BaseDialog {

    static final SelenideElement dialogBase = $x("//div[contains(@class,'modal-header')]/../..");
    protected final SelenideElement dialog;

    public BaseDialog(SelenideElement dialog) {
        this.dialog = dialog;
    }

    public BaseDialog(String dialogName) {
        this.dialog = $x("//h3[contains(string(),'" + dialogName + "')]/../..");
    }

    public static SelenideElement getDialogBaseEl() {
        return dialogBase;
    }

    public boolean isOpened() {
        return dialog.is(Condition.visible);
    }

    /**
     * Use this when instance of abstract standard dialog needed.
     *
     * @return instance of BaseDialog
     */
    public static BaseDialog create() {
        return new BaseDialog(dialogBase);
    }

    protected boolean tryToWaitClosed() {
        return Driver.tryToWaitElementNotPresented(dialog, AppConfig.timeOutSecMedium);
    }

    @Step("Wait dialog not presented")
    public void waitClosed() {
        dialog.shouldNot(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecMedium));
    }

    @Step("Wait dialog presented")
    public void waitOpenedBase() {
        Driver.waitElementPresented(dialogBase, AppConfig.timeOutSecMedium);
        Driver.waitElementCSSValueEquals(dialogBase, "opacity", "1", AppConfig.timeOutSecMedium);
        dialog.shouldBe(Condition.visible);
    }
}
