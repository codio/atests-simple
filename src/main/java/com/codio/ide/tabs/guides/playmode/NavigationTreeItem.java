package com.codio.ide.tabs.guides.playmode;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.Driver;

public class NavigationTreeItem {

    private final SelenideElement item;
    private final SelenideElement nameEl;

    NavigationTreeItem(SelenideElement item) {
        this.item = item;
        nameEl = item.$x("./a");
    }

    @Step("Click TOC item")
    public NavigationTreeItem click() {
        item.click();
        return this;
    }

    @Step("Get TOC item name")
    public String getName() {
        return nameEl.getText();
    }

    @Step("Open TOC item")
    public GuidesPlayMode open() {
        click();
        GuidesPlayMode guides = new GuidesPlayMode();
        if (!guides.tryToWaitContentOfExactItemOpened(getName())) {
            Driver.reload();
            guides.waitOpened().openBookNavigation();
            click();
        }
        return guides.waitContentOfExactItemOpened(getName());
    }
}
