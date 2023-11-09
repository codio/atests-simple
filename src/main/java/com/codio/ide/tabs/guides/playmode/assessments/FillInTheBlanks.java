package com.codio.ide.tabs.guides.playmode.assessments;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.Button;
import com.codio.common.pageelements.Input;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;
import com.codio.ide.tabs.guides.playmode.GuidesPlayMode;

public class FillInTheBlanks {

    private final SelenideElement panel;
    private Button checkItButton;
    private Button resetButton;
    private ElementsCollection answerInputs;
    private SelenideElement emptyAnswerInput;
    private ElementsCollection answerDropdowns;
    private SelenideElement selectPlaceholder;
    private SelenideElement answerItemsPanel;
    private SelenideElement correctAnswer;
    private SelenideElement wrongAnswer;
    private SelenideElement hiddenAnswer;

    private void init() {
        checkItButton = new Button(panel.$x(".//button[contains(string(),'Check It!')]"));
        resetButton = new Button(panel, "Reset");
        answerInputs = panel.$$x(".//div[@class='codio-fill-in-the-blanks']//input[@class='codio-fib-simple-input']");
        emptyAnswerInput = panel.$x(".//input[@class='codio-fib-simple-input' and @value='']");
        answerDropdowns = panel.$$x(
            ".//div[@class='codio-fill-in-the-blanks']//div[contains(@class, 'fib-react-select__value-container')]"
        );
        selectPlaceholder = panel.$x(".//div[contains(@class,'fib-react-select__placeholder')]");
        answerItemsPanel = $x("//div[contains(@class,'fib-react-select__menu-list')]/..");

        correctAnswer = panel.$x(".//span[@class='codio-assessment-fib-ok']");
        wrongAnswer = panel.$x(".//span[@class='codio-assessment-fib-wrong']");
        hiddenAnswer = panel.$x(".//span[@class='codio-fib-simple-input']");
    }

    public FillInTheBlanks(String assessmentName) {
        panel = $x("//h3[@class='codio-assessment-name' and string()='" + assessmentName + "']/..");
        init();
    }

    public FillInTheBlanks(SelenideElement assignmentEl) {
        panel = assignmentEl;
        init();
    }

    public static FillInTheBlanks getFillInTheBlanksByPartOfContentText(String text) {
        return new FillInTheBlanks(
            $x("//div[contains(@class,'codio-challenge-fill') and contains(string(),'" + text + "')]")
        );
    }

    @Step("Set answer in field")
    public FillInTheBlanks setAnswerInField(String answer, int fieldNumber) {
        GuidesPlayMode.switchToGuidesPlayerIframe();
        try {
            Input input = new Input(answerInputs.get(fieldNumber - 1));
            if (input.exists()) {
                input.setValue(answer);
            }
            if (answerDropdowns.get(fieldNumber - 1).exists()) {
                Driver.scrollElementToCenter(answerDropdowns.get((fieldNumber - 1)));
                answerDropdowns.get(fieldNumber - 1).click();
                answerItemsPanel.$x(".//div[contains(@class,'fib-react-select__option') and string()='" + answer + "']")
                    .click();
            }
        } finally {
            GuidesPlayMode.switchToDefaultContent();
        }
        return this;
    }

    @Step("Submit answer")
    public FillInTheBlanks clickCheckItButton() {
        GuidesPlayMode.switchToGuidesPlayerIframe();
        try {
            waitPresented();
            checkItButton.clickOrExitTestWithErrorIfDisabled();
            Driver.tryToWaitConditionIsTrue(() -> getStateWithinIframe() == FillInTheBlanksAssessmentState.PROGRESS, 1);
            Driver.waitConditionIsFalse(
                () -> getStateWithinIframe() == FillInTheBlanksAssessmentState.PROGRESS,
                AppConfig.timeOutSecMedium
            );
        } finally {
            GuidesPlayMode.switchToDefaultContent();
        }
        return this;
    }

    private boolean isFilled() {
        return (!answerInputs.isEmpty() && !emptyAnswerInput.exists())
            || (!answerDropdowns.isEmpty() && !selectPlaceholder.exists());
    }

    private boolean isNotFilled() {
        return selectPlaceholder.exists() || emptyAnswerInput.exists();
    }

    private FillInTheBlanksAssessmentState getStateWithinIframe() {
        if (checkItButton.isDisabled() && isNotFilled()) {
            return FillInTheBlanksAssessmentState.NOT_STARTED;
        }
        if (correctAnswer.exists() && !wrongAnswer.exists()) {
            return FillInTheBlanksAssessmentState.PASS;
        }
        if (wrongAnswer.exists()) {
            return FillInTheBlanksAssessmentState.FAIL;
        }
        if ((isFilled() || hiddenAnswer.exists()) && (checkItButton.isEnabled() || resetButton.isPresented())) {
            return FillInTheBlanksAssessmentState.HIDDEN;
        }
        if (checkItButton.isDisabled() && isFilled()) {
            return FillInTheBlanksAssessmentState.PROGRESS;
        }
        return FillInTheBlanksAssessmentState.OTHER;
    }

    public FillInTheBlanksAssessmentState getState() {
        GuidesPlayMode.switchToGuidesPlayerIframe();
        FillInTheBlanksAssessmentState result;
        try {
            result = getStateWithinIframe();
        } finally {
            GuidesPlayMode.switchToDefaultContent();
        }
        return result;
    }

    public boolean isPresented() {
        return panel.exists();
    }

    @Step("Wait Fill in the Blanks assessment presented")
    private void waitPresented() {
        checkItButton.waitPresented();
    }
}
