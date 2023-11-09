package com.codio.ide.tabs;

import io.qameta.allure.Step;

import com.codio.common.pageelements.IDEPanelTab;
import com.codio.common.pageelements.monaco.Monaco;

public class FileTab {

    private final IDEPanelTab tab;
    private final Monaco monaco;

    public FileTab(String fileName) {
        tab = new IDEPanelTab(fileName);
        monaco = new Monaco(tab.getTab().$x("../.."));
    }

    @Step
    public void close() {
        tab.close();
    }

    @Step
    public Monaco getMonaco() {
        tab.waitTabActive();
        return monaco.waitOpened();
    }

    @Step("Wait file tab opened")
    public FileTab waitOpened() {
        tab.waitTabActive();
        return this;
    }
}
