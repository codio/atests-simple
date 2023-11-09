package com.codio.dashboard.courses.teacher;

import io.qameta.allure.Step;

import com.codio.common.pageelements.AlertErrorLine;
import com.codio.common.pageelements.CookieBanner;
import com.codio.common.pageelements.Tab;
import com.codio.helpers.Driver;

public class CoursesPageOfTeacher {

    private final AlertErrorLine alertError = new AlertErrorLine();
    private final String pageUrl = Driver.getBaseUrl() + "/home/teacher";
    private final Tab allTab = new Tab("All");
    private final CookieBanner cookieBanner = new CookieBanner();

    public boolean isOpened() {
        return allTab.isPresented();
    }

    @Step("Open Courses page by URL")
    public CoursesPageOfTeacher open() {
        Driver.open(pageUrl);
        return waitOpened();
    }

    @Step("Open All tab by click it")
    public AllTabOfCourses openAllTab() {
        allTab.open();
        return new AllTabOfCourses().waitOpened();
    }

    @Step("Reopen Courses page of teacher")
    private void repeater(int attempt) {
        if (alertError.isPresented()) {
            Driver.logInfo("Got error alert on Courses page: " + alertError.getMessge());
        }
        Driver.logInfo("Activating reloading Courses page (" + attempt + ")");
        Driver.open(pageUrl);
    }

    private void actionIfAttemptsFailed(int attempts) {
        Driver
            .exitTestWithError("After " + attempts + " attempts Courses page still not opened (All tab not presented)");
    }

    @Step("Wait teacher Courses page opened")
    public CoursesPageOfTeacher waitOpened() {
        Driver.repeatIfFailed(allTab::tryToWaitPresented, this::repeater, this::actionIfAttemptsFailed);
        cookieBanner.removeIfExists();
        return this;
    }
}
