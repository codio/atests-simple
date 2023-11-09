package com.codio.ide.idemenu;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.dialogs.BaseDialog;
import com.codio.common.pageelements.AlertErrorLine;
import com.codio.common.pageelements.CookieBanner;
import com.codio.common.pageelements.NotificationDash;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;
import com.codio.ide.CriticalErrorPage;
import com.codio.ide.idemenu.education.EducationMenu;

public class IDEMenu {

    private final AlertErrorLine alertError = new AlertErrorLine();
    private final CriticalErrorPage criticalError = new CriticalErrorPage();
    private final CookiePreviewConfirmationDialog cookieDialog = new CookiePreviewConfirmationDialog();
    private final ConvertGuidesDialog convertGuidesDialog = new ConvertGuidesDialog();
    private static final SelenideElement menu = $x("//div[@id='menu-items']");
    private final NotificationDash notification = new NotificationDash();

    public EducationMenu educationMenu() {
        return new EducationMenu();
    }

    public boolean isPresented() {
        return menu.exists();
    }

    public static boolean tryToWaitClosed() {
        return Driver.tryToWaitElementNotPresented(menu, 10);
    }

    private boolean checker() {
        return Driver.tryToWaitOneOfElementsPresented(
            new SelenideElement[] {alertError.getElement(), criticalError.getPanel(), BaseDialog.getDialogBaseEl(),
                menu},
            AppConfig.timeOutSecLong
        );
    }

    @Step("Check if there is notification and Reload page")
    private void repeater(int attempt) {
        if (notification.isPresented()) {
            Driver.logInfo("Got notification with message: " + notification.getLastNotificationMessage());
        }
        Driver.logInfo("Activating reopening project (" + attempt + ")");
        Driver.reload();
    }

    private void actionIfAttemptsFailed(int attempts) {
        Driver.exitTestWithError("After " + attempts + " attempts IDE menu still not presented");
    }

    @Step("Wait IDE menu opened and Convert Guides if needed")
    public IDEMenu waitOpened() {
        waitOpenedWithoutConvertGuidesDialog();
        if (convertGuidesDialog.isOpened()) {
            convertGuidesDialog.clickYesButton();
        }
        new CookieBanner().removeIfExists();
        return this;
    }

    @Step("Wait IDE menu opened")
    public IDEMenu waitOpenedWithoutConvertGuidesDialog() {
        Driver.repeatIfFailed(this::checker, this::repeater, this::actionIfAttemptsFailed);
        if (alertError.isPresented()) {
            Driver.logInfo("\n!!!!!!!\nHack to avoid Alert error\n" + alertError.getMessge());
            Driver.reload();
            if (checker() && alertError.isPresented()) {
                Driver.exitTestWithError("Alert error line appears: " + alertError.getMessge());
            }
        }
        if (criticalError.isOpened()) {
            Driver.logInfo(
                "\n!!!!!!!\nHack to avoid Critical error page\n" + criticalError.getLabelText() + "\n"
                    + criticalError.getContentText()
            );
            Driver.reload();
            if (checker() && criticalError.isOpened()) {
                Driver.exitTestWithError(
                    "Critical error page is opened\n" + criticalError.getLabelText() + "\n"
                        + criticalError.getContentText()
                );
            }
        }
        Driver.wait(500);
        if (cookieDialog.isOpened()) {
            cookieDialog.clickOkButton();
            if (!Driver.tryToWaitElementPresented(menu, AppConfig.timeOutSecLong)) {
                Driver.exitTestWithError("After " + AppConfig.timeOutSecLong + " seconds element still not presented");
            }
        }
        educationMenu().waitEducationMenuItemUpdatedAfterOpened();
        return this;
    }
}
