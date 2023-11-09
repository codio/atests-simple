package com.codio.common.pageelements.monaco;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import com.codio.common.HotKeys;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class Monaco {

    private final SelenideElement container;
    private final SelenideElement monacoLinesContainer;
    private final ElementsCollection monacoLines;
    private final SelenideElement overlays;
    private final ElementsCollection cursors;

    public Monaco(SelenideElement parentEl) {
        container = parentEl.$x(".//div[contains(@class,'monaco-editor-container')]");
        monacoLinesContainer = container.$x(".//div[@class='view-lines monaco-mouse-cursor-text']");
        monacoLines = monacoLinesContainer.$$x("./div");
        overlays = container.$x(".//div[contains(@class,'lines-content')]/div[contains(@class,'view-overlays')]");
        cursors = container.$$x(".//div[contains(@class,'cursors-layer')]/div");
    }

    @Step("Add new line from current position")
    public void addNewLine() {
        if (getSuggestWidget().isPresented()) {
            HotKeys.pressKeys(Keys.ESCAPE); // to avoid autocompletion
        }
        HotKeys.pressKeys(Keys.ENTER);
    }

    @Step("Add text to current cursor position")
    public Monaco addText(String text) {
        exitTestIfNotFocused();
        List<String> lines = convertTextToLinesList(text);
        HotKeys.pressKeys(lines.get(0));
        for (int i = 1; i < lines.size(); i++) {
            addNewLine();
            HotKeys.pressKeys(lines.get(i));
        }
        HotKeys.pressKeys(Keys.ESCAPE); // to avoid autocompletion
        return this;
    }

    private List<String> convertTextToLinesList(String text) {
        List<String> result = new ArrayList<>(List.of(text.split("\n")));
        if (text.lastIndexOf("\n") == text.length() - 1) {
            result.add("");
        }
        return result;
    }

    @Step("Delete all text")
    public Monaco deleteAll() {
        monacoLine().setCursor();
        HotKeys.deleteAll();
        Driver.waitConditionIsTrue(
            () -> getMLinesAmount() == 1 && Objects.equals(monacoLine().getText(), ""),
            AppConfig.timeOutSec
        );
        return this;
    }

    protected void exitTestIfNotFocused() {
        if (!isFocused()) {
            Driver.exitTestWithError("Monaco editor not focused");
        }
    }

    @Step("Focus on container")
    public Monaco focus() {
        if (!isFocused()) {
            monacoLine().setCursorToTheStart();
        }
        return this;
    }

    public MonacoCursor getCursor() {
        return getCursor(1);
    }

    public MonacoCursor getCursor(int number) {
        return new MonacoCursor(this, cursors.get(number - 1));
    }

    public int getMLinesAmount() {
        return monacoLines.size();
    }

    public SuggestWidget getSuggestWidget() {
        return new SuggestWidget(container);
    }

    public boolean isFocused() {
        return overlays.has(Condition.attributeMatching("class", ".*focused.*"));
    }

    public MonacoLine monacoLine() {
        return new MonacoLine(container);
    }

    @Step("Wait content loaded")
    public Monaco waitOpened() {
        monacoLines.first().should(Condition.exist);
        return this;
    }
}
