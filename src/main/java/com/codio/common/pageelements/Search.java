package com.codio.common.pageelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.HotKeys;

public class Search {

    private SelenideElement inputField;
    private SelenideElement deleteButton;

    private void init() {
        deleteButton = inputField.$x("../../../div[@class='btn searcher-reset']");
    }

    public Search(SelenideElement parentEl) {
        inputField = parentEl.$x(".//input[@aria-label='Search']");
        init();
    }

    public Search() {
        inputField = $x("//input[@aria-label='Search']");
        init();
    }

    @Step
    public void clear() {
        if (deleteButton.is(Condition.visible)) {
            deleteButton.click();
        } else {
            inputField.click();
            HotKeys.deleteAll();
        }
        deleteButton.shouldNot(Condition.exist);
    }

    @Step
    public String getValue() {
        return inputField.getValue();
    }

    public boolean isEmpty() {
        return !deleteButton.exists();
    }

    @Step
    public void setValue(String value) {
        if (!getValue().equals(value)) {
            if (!isEmpty()) {
                clear();
            }
            int n = 0;
            while (!getValue().equals(value) && n < 10) { // hack to make more stable
                inputField.setValue(value);
                n++;
            }
        }
        deleteButton.should(Condition.exist);
    }
}
