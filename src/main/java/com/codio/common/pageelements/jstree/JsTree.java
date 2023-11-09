package com.codio.common.pageelements.jstree;

import java.util.ArrayList;
import java.util.List;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class JsTree {

    private final SelenideElement container;

    public JsTree(SelenideElement jsTreeContainer) {
        container = jsTreeContainer;
    }

    private String xpathArgument(String itemName, boolean isFull) {
        return (isFull) ? "a/text()='" + itemName + "'" : "contains(a/text(),'" + itemName + "')";
    }

    private List<SelenideElement> getItemsElementsArrayFromTreePath(String treePath, boolean isFull) {
        String[] names = TreePathConstructor.getTreePathStringArray(treePath);
        List<SelenideElement> result = new ArrayList<>();
        SelenideElement first = container.$x(".//li[a/@aria-level='1' and " + xpathArgument(names[0], isFull) + "]");
        if (first.exists()) {
            result.add(first);
        } else {
            result.add(container.$x(".//li[a/@aria-level='2' and " + xpathArgument(names[0], isFull) + "]"));
        }
        for (int i = 1; i < names.length; i++) {
            result.add(result.get(i - 1).$x("./ul/li[" + xpathArgument(names[i], isFull) + "]"));
        }
        return result;
    }

    @Step("Get item element")
    public SelenideElement getItemElementByTreePath(String treePath, boolean isFull) {
        List<SelenideElement> items = getItemsElementsArrayFromTreePath(treePath, isFull);
        return items.get(items.size() - 1);
    }
}
