package com.codio.ide.tabs.guides.playmode;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.Button;
import com.codio.common.pageelements.UiBlocker;
import com.codio.dashboard.NavigationMenu;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;
import com.codio.ide.idemenu.MarkAsCompletedWarningDialog;
import com.codio.ide.tabs.guides.playmode.assessments.*;

public class GuidesPlayMode {

    private final SelenideElement toggleSectionsListButton = $x("//button[@aria-label='Toggle sections list']");
    private static final SelenideElement guidesPlayerIframe = $x("//iframe[@title='Guides Player']");
    private final SelenideElement pageContent = $x("//div[@class='guides']");
    private final SelenideElement pageTitle = pageContent.$x(".//h1[@id='page-title']");
    private final Button markAsCompleteButton = new Button("Mark as Completed");

    private SelenideElement codeBlockByNumber(int num) {
        return $x("//div[@class='code-block'][" + num + "]//code");
    }

    @Step("Answer and submit fill in the blanks assessment")
    public FillInTheBlanks answerFillInTheBlanksAssessment(String assessmentNameOrText, String[] answerItems) {
        FillInTheBlanks fillInTheBlanks = new FillInTheBlanks(assessmentNameOrText);
        fillInTheBlanks = fillInTheBlanks.isPresented() ? fillInTheBlanks
            : FillInTheBlanks.getFillInTheBlanksByPartOfContentText(assessmentNameOrText);
        for (int i = 0; i < answerItems.length; i++) {
            fillInTheBlanks.setAnswerInField(answerItems[i], i + 1);
        }
        return fillInTheBlanks.clickCheckItButton();

    }

    @Step("Answer and submit multiple choice assessment")
    public MultipleChoice answerMultipleChoiceAssessment(String assessmentNameOrText, String[] answerItems) {
        MultipleChoice multipleChoice = new MultipleChoice(assessmentNameOrText);
        multipleChoice = multipleChoice.isPresented() ? multipleChoice
            : MultipleChoice.getMultipleChoiceByPartOfContentText(assessmentNameOrText);
        for (int i = 0; i < answerItems.length; i++) {
            multipleChoice.clickOnAnswer(answerItems[i]);
        }
        return multipleChoice.clickCheckItButton();
    }

    @Step("Click codio button in guides play mode by name and number")
    public GuidesPlayMode clickButton(String name, int num) {
        switchToGuidesPlayerIframe();
        try {
            Button button = new Button(pageContent, name, num);
            button.waitPresented();
            button.click();
        } finally {
            switchToDefaultContent();
        }
        return this;
    }

    @Step("Get Code Block text")
    public String getCodeBlockText(int num) {
        switchToGuidesPlayerIframe();
        String result = "";
        try {
            result = codeBlockByNumber(num).getText();
        } finally {
            switchToDefaultContent();
        }
        return result;
    }

    @Step("Get codio button result")
    public String getCodioButtonResult(String name, int num) {
        switchToGuidesPlayerIframe();
        String result;
        try {
            Button button = new Button(pageContent, name, num);
            SelenideElement resultField = button.getElement().$x("..//pre[contains(@class,'codio-button-result-out')]");
            resultField.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
            Driver.tryToWaitConditionIsTrue(
                () -> resultField.getText().equals("Please wait while we check your code…"),
                1
            );
            Driver.tryToWaitConditionIsFalse(
                () -> resultField.getText().equals("Please wait while we check your code…"),
                AppConfig.timeOutSecLong
            );
            result = resultField.getText();
        } finally {
            switchToDefaultContent();
        }
        return result;
    }

    @Step("Mark as Completed")
    public NavigationMenu markAsCompleted() {
        MarkAsCompletedWarningDialog dialog = new MarkAsCompletedWarningDialog();
        switchToGuidesPlayerIframe();
        try {
            markAsCompleteButton.waitPresented(AppConfig.timeOutSecMedium).click();
        } finally {
            switchToDefaultContent();
        }
        dialog.waitOpenedBase();
        if (dialog.isDialogExisting()) {
            if (dialog.isConfirmationStringNeeded()) {
                dialog.setConfirmationString("yes");
            }
            dialog.clickOkButton();
        }
        UiBlocker.tryToWaitPerformed(3 * AppConfig.timeOutSecLong);
        return new NavigationMenu().waitOpened();
    }

    @Step("Expand guides page navigation tree")
    public CodioBookNavigationPlayPanel openBookNavigation() {
        if (toggleSectionsListButton.has(Condition.attribute("aria-expanded", "false"))) {
            toggleSectionsListButton.click();
        }
        return new CodioBookNavigationPlayPanel().waitOpened();
    }

    @Step("Open page of guides")
    public GuidesPlayMode openPage(String pageItemPath) {
        openBookNavigation().item(pageItemPath).open().closeBookNavigation();
        return this;
    }

    @Step("Hide guides page navigation tree")
    public void closeBookNavigation() {
        if (toggleSectionsListButton.has(Condition.attribute("aria-expanded", "true"))) {
            toggleSectionsListButton.click();
        }
    }

    @Step("Switch to guides player iframe")
    public static void switchToGuidesPlayerIframe() {
        Driver.switchToIframe(guidesPlayerIframe);
    }

    @Step("Switch to default content")
    public static void switchToDefaultContent() {
        Driver.switchToDefaultContent();
    }

    @Step("Wait Guides Player tab opened")
    public GuidesPlayMode waitOpened() {
        guidesPlayerIframe.shouldBe(Condition.visible, Duration.ofSeconds(AppConfig.timeOutSecLong));
        return this;
    }

    @Step("Try to wait Guides Player Iframe not presented")
    private GuidesPlayMode tryToWaitIframeNotPresented() {
        Driver.tryToWaitElementNotPresented(guidesPlayerIframe, 1);
        return this;
    }

    @Step("Try to wait exact page content loaded")
    public boolean tryToWaitContentOfExactItemOpened(String contentItemName) {
        tryToWaitIframeNotPresented();
        switchToGuidesPlayerIframe();
        boolean result;
        try {
            result = Driver.tryToWaitConditionIsTrue(
                () -> pageTitle.has(Condition.matchText(".*" + contentItemName)),
                AppConfig.timeOutSecMedium
            );
        } finally {
            switchToDefaultContent();
        }
        return result;
    }

    @Step("Wait exact page content loaded")
    public GuidesPlayMode waitContentOfExactItemOpened(String contentItemName) {
        switchToGuidesPlayerIframe();
        try {
            pageTitle.shouldHave(
                Condition.matchText(".*" + contentItemName),
                Duration.ofSeconds(AppConfig.timeOutSecMedium)
            );
        } finally {
            switchToDefaultContent();
        }
        return this;
    }
}
