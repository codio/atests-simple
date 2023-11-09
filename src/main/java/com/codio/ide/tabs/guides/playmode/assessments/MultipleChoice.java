package com.codio.ide.tabs.guides.playmode.assessments;

import static com.codeborne.selenide.Selenide.$x;

import java.util.Date;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.Button;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;
import com.codio.ide.tabs.guides.playmode.GuidesPlayMode;

public class MultipleChoice {

    private final SelenideElement panel;
    private Button checkItButton;
    private Button resetButton;
    private SelenideElement answers;
    private ElementsCollection radios;
    private ElementsCollection checkboxes;
    private Date progressTimeOutDate;

    private void init() {
        checkItButton = new Button(panel.$x(".//button[contains(string(),'Check It!')]"));
        resetButton = new Button(panel, "Reset");
        answers = panel.$x(".//div[contains(@class,'answers')]");
        radios = panel.$$x(".//div[contains(@class,'codio-button-type-assessment-multiple-radio')]//i");
        checkboxes = panel.$$x(".//div[contains(@class,'codio-button-type-assessment-multiple-checkbox')]//i");
        progressTimeOutDate = Driver.getDateWithShiftInSeconds(10000);
    }

    public MultipleChoice(String assessmentName) {
        panel = $x("//h3[@class='codio-assessment-name' and string()='" + assessmentName + "']/..");
        init();
    }

    public MultipleChoice(SelenideElement assignmentEl) {
        panel = assignmentEl;
        init();
    }

    public static MultipleChoice getMultipleChoiceByPartOfContentText(String text) {
        return new MultipleChoice(
            $x("//div[contains(@class,'codio-assessment-multiple-choice') and contains(string(),'" + text + "')]")
        );
    }

    @Step("Click check it button and wait checked")
    public MultipleChoice clickCheckItButton() {
        GuidesPlayMode.switchToGuidesPlayerIframe();
        try {
            checkItButton.clickOrExitTestWithErrorIfDisabled("Select answer before click button");
            progressTimeOutDate = Driver.getDateWithShiftInSeconds(3);
            Driver.tryToWaitConditionIsTrue(() -> getStateWithinIframe() == MultipleChoiceAssessmentState.PROGRESS, 1);
            Driver.waitConditionIsFalse(
                () -> getStateWithinIframe() == MultipleChoiceAssessmentState.PROGRESS,
                AppConfig.timeOutSecMedium
            );
        } finally {
            GuidesPlayMode.switchToDefaultContent();
        }
        return this;
    }

    @Step("Click on answer")
    public MultipleChoice clickOnAnswer(String answer) {
        GuidesPlayMode.switchToGuidesPlayerIframe();
        try {
            waitPresented();
            Driver.scrollElementToCenter(answers);
            String[] strings = answer.split("'");
            StringBuilder sb = new StringBuilder();
            String single = ", \"'\", ";
            for (String s : strings) {
                sb.append(single).append("'").append(s).append("'");
            }
            String result = sb.substring(single.length());
            answers
                .$x(
                    ".//div[@class='codio-button-type-assessment-multiple-answer' and normalize-space(.)=concat('', "
                        + result + ")]"
                )
                .click();
        } finally {
            GuidesPlayMode.switchToDefaultContent();
        }
        return this;
    }

    private boolean isCheckboxPresented() {
        return !checkboxes.isEmpty();
    }

    private boolean isCheckboxSelected() {
        for (SelenideElement checkbox : checkboxes) {
            if (checkbox.getCssValue("background-size").equals("contain")) {
                return true;
            }
        }
        return false;
    }

    private boolean isRadioPresented() {
        return !radios.isEmpty();
    }

    private boolean isRadioSelected() {
        for (SelenideElement radio : radios) {
            if (radio.pseudo(":before", "height").equals("0px")) {
                return true;
            }
        }
        return false;
    }

    private boolean isCorrectPresented() {
        for (SelenideElement radio : radios) {
            if (radio.pseudo(":before", "background-color").equals("rgb(113, 179, 128)")) {
                return true;
            }
        }
        for (SelenideElement checkbox : checkboxes) {
            if (checkbox.getCssValue("background-color").equals("rgba(113, 179, 128, 1)")) {
                return true;
            }
        }
        return false;
    }

    private boolean isWrongPresented() {
        for (SelenideElement radio : radios) {
            if (radio.pseudo(":before", "background-color").equals("rgb(218, 129, 129)")) {
                return true;
            }
        }
        for (SelenideElement checkbox : checkboxes) {
            if (checkbox.getCssValue("background-color").equals("rgba(218, 129, 129, 1)")) {
                return true;
            }
        }
        return false;
    }

    private MultipleChoiceAssessmentState getStateWithinIframe() {
        if (checkItButton.isDisabled()
            && ((isRadioPresented() && !isRadioSelected()) || (isCheckboxPresented() && !isCheckboxSelected()))) {
            return MultipleChoiceAssessmentState.NOT_STARTED;
        }
        if (isCorrectPresented() && !isWrongPresented()) {
            return MultipleChoiceAssessmentState.PASS;
        }
        if (isWrongPresented()) {
            return MultipleChoiceAssessmentState.FAIL;
        }
        if ((isRadioSelected() || isCheckboxSelected())
            && (checkItButton.isEnabled() || resetButton.isPresented() || Driver.isDateInPast(progressTimeOutDate))) {
            return MultipleChoiceAssessmentState.HIDDEN;
        }
        if (checkItButton.isDisabled() && (isRadioSelected() || isCheckboxSelected())) {
            return MultipleChoiceAssessmentState.PROGRESS;
        }
        return MultipleChoiceAssessmentState.OTHER;
    }

    public MultipleChoiceAssessmentState getState() {
        GuidesPlayMode.switchToGuidesPlayerIframe();
        MultipleChoiceAssessmentState result;
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

    @Step("Wait Multiple Choice assessment presented")
    private void waitPresented() {
        checkItButton.waitPresented();
    }
}
