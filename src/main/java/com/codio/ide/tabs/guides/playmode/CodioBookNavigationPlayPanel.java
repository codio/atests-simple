package com.codio.ide.tabs.guides.playmode;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.jstree.JsTree;
import com.codio.helpers.AppConfig;

public class CodioBookNavigationPlayPanel {

    private final SelenideElement navigation = $x("//div[@id='codio-book-navigation']");
    private final JsTree jsTree = new JsTree(navigation.$x(".//div[contains(@class,'codio-book-tree jstree')]"));

    @Step("Get item by full name (opens parent folders)")
    public NavigationTreeItem item(String itemTreePath) {
        return new NavigationTreeItem(jsTree.getItemElementByTreePath(itemTreePath, true));
    }

    @Step("Wait Codio Book Navigation Play panel opened")
    public CodioBookNavigationPlayPanel waitOpened() {
        navigation.shouldBe(Condition.visible, Duration.ofSeconds(AppConfig.timeOutSecLong));
        return this;
    }
}
