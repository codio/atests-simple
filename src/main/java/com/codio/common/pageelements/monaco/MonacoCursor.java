package com.codio.common.pageelements.monaco;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import com.codio.helpers.Driver;

public class MonacoCursor {

    private final Monaco monaco;
    private final SelenideElement cursor;

    public MonacoCursor(Monaco monaco, SelenideElement cursorEl) {
        this.monaco = monaco;
        cursor = cursorEl;
    }

    /**
     * Returns cursor X position (visible X px position in monacoLine).
     */
    public int getX() {
        return Integer.parseInt(cursor.getCssValue("left").replaceAll("px", ""));
    }

    public boolean isActive() {
        return Driver.tryToWaitConditionIsTrue(
            () -> cursor.has(Condition.attributeMatching("style", ".*visibility: inherit;.*")),
            1
        );
    }
}
