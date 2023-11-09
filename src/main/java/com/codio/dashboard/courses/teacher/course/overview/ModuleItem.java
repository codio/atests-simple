package com.codio.dashboard.courses.teacher.course.overview;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.helpers.AppConfig;

public class ModuleItem {

    private final SelenideElement item;
    private SelenideElement nameEl;
    private SelenideElement expandModuleButton;
    private SelenideElement assignmentsList;
    private ElementsCollection assignmentItems;

    private void init() {
        nameEl = item.$x(".//span[@class='unitHeader-rename-unit--title']");
        expandModuleButton = item.$x(".//button[@title='Expand']");
        assignmentsList = item.$x(".//table[@class='assignmentsTeachList']");
        assignmentItems = assignmentsList.$$x(".//tr[contains(@class,'assignmentsTeachListItem')]");
    }

    ModuleItem(SelenideElement item) {
        this.item = item;
        init();
    }

    ModuleItem(String name) {
        item = $x(".//div[@class='unitHeader-name']/span[string()='" + name + "']/../../..");
        init();
    }

    @Step
    public AssignmentItem assignment(String assignmentName) {
        expandModuleIfCollapsed();
        return new AssignmentItem(item, assignmentName);
    }

    @Step
    public ModuleItem expandModule() {
        expandModuleButton.click();
        assignmentsList.shouldBe(Condition.visible);
        return this;
    }

    @Step
    public ModuleItem expandModuleIfCollapsed() {
        waitPresented();
        if (isModuleCollapsed()) {
            expandModule();
        }
        return this;
    }

    @Step
    public AssignmentItem[] getAssignments() {
        expandModuleIfCollapsed();
        return assignmentItems.stream().map(AssignmentItem::new).toArray(AssignmentItem[]::new);
    }

    @Step
    public String getName() {
        return nameEl.getText();
    }

    @Step
    public boolean isModuleCollapsed() {
        return expandModuleButton.exists();
    }

    @Step
    public boolean isPresented() {
        return item.exists();
    }

    @Step("Wait module presented")
    public ModuleItem waitPresented() {
        item.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecMedium));
        return this;
    }
}
