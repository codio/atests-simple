package com.codio.common.pageelements.monaco;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

public class MonacoLine {

    private SelenideElement linesContent;
    private SelenideElement textEl;
    private SelenideElement horisontalScroll;
    private final SelenideElement line;

    private void init(SelenideElement monacoContainer) {
        linesContent = monacoContainer.$x(".//div[contains(@class,'lines-content')]");
        textEl = linesContent.$x("./div[@class='view-lines monaco-mouse-cursor-text']");
        horisontalScroll = monacoContainer
            .$x(".//div[@class='invisible scrollbar horizontal fade' or @class='visible scrollbar horizontal']");
    }

    public MonacoLine(SelenideElement monacoContainer) {
        init(monacoContainer);
        line = textEl.$x("./div");
    }

    @Step("Get line text")
    public String getText() {
        return line.getText();
    }

    @Step("Scroll to the beginning by mouse click")
    public MonacoLine scrollToTheBeginning() {
        if (horisontalScroll.exists()) {
            horisontalScroll.click(ClickOptions.withOffset(-horisontalScroll.getSize().width / 2, 0));
        }
        return this;
    }

    @Step("Set cursor to the line")
    public MonacoLine setCursor() {
        SelenideElement firstSpan = line.$x("./span/span");
        scrollToTheBeginning();
        if (firstSpan.isDisplayed()) {
            firstSpan.click();
        } else {
            line.click();
        }
        return this;
    }

    @Step("Set cursor to the start of line")
    public MonacoLine setCursorToTheStart() {
        setCursor();
        Selenide.actions().sendKeys(Keys.HOME).perform();
        return this;
    }
}
