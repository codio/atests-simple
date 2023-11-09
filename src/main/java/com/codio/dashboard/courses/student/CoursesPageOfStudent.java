package com.codio.dashboard.courses.student;

import io.qameta.allure.Step;

import com.codio.common.pageelements.CookieBanner;
import com.codio.common.pageelements.Tab;
import com.codio.helpers.Driver;

public class CoursesPageOfStudent {

    private final String pageUrl = Driver.getBaseUrl() + "/home/student";
    private final Tab activeTab = new Tab("Active");
    private final CookieBanner cookieBanner = new CookieBanner();

    @Step("Open Active tab by click on it")
    public ActiveTab openActiveTab() {
        waitOpened();
        activeTab.open();
        return new ActiveTab().waitOpened();
    }

    @Step("Open student Courses page by URL")
    public CoursesPageOfStudent open() {
        Driver.open(pageUrl);
        return waitOpened();
    }

    @Step("Reload student's Courses page")
    private void repeater(int attempt) {
        Driver.logInfo("Activating reloading student Courses page (" + attempt + ")");
        if (Driver.getCurrentUrl().contains(pageUrl)) {
            Driver.reload();
        } else {
            Driver.exitTestWithError("StudentCoursesPage still not opened");
        }
    }

    private void actionIfAttemptsFailed(int attempts) {
        Driver.exitTestWithError("After " + attempts + " attempts student Courses page still not opened");
    }

    @Step("Wait student Courses page opened")
    public CoursesPageOfStudent waitOpened() {
        Driver.repeatIfFailed(activeTab::tryToWaitPresented, this::repeater, this::actionIfAttemptsFailed);
        cookieBanner.removeIfExists();
        return this;
    }
}
