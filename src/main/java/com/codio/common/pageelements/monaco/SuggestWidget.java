package com.codio.common.pageelements.monaco;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class SuggestWidget {
    private final SelenideElement suggestWidget;

    public SuggestWidget(SelenideElement parentEl) {
        suggestWidget = parentEl.$x(".//div[@widgetid='editor.widget.suggestWidget']");
    }

    public boolean isPresented() {
        return suggestWidget.has(Condition.attribute("monaco-visible-content-widget", "true"));
    }
}
