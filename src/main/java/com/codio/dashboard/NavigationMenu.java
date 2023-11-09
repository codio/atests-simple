package com.codio.dashboard;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import com.codio.common.pageelements.CookieBanner;
import com.codio.dashboard.courses.student.CoursesPageOfStudent;
import com.codio.dashboard.courses.teacher.CoursesPageOfTeacher;
import com.codio.helpers.AppConfig;
import com.codio.helpers.Driver;

public class NavigationMenu {

    private final SelenideElement panel = $x("//nav[contains(@class,'navigation')]");
    private final SelenideElement courses = panel.$x(".//span[string()='Courses']/../../..");
    private final SelenideElement logoutButton = panel.$x(".//a[@title='Logout']");
    private final SelenideElement userLogin = panel.$x(".//div[@class='navigation-avatar']/a[2]");

    public NavigationMenu() {
        new CookieBanner().removeIfExists();
    }

    @Step("Open Courses by teacher")
    public CoursesPageOfTeacher clickCourses() {
        courses.click();
        return new CoursesPageOfTeacher().waitOpened();
    }

    @Step("Open Courses by student")
    public CoursesPageOfStudent clickCoursesByStudent() {
        courses.click();
        return new CoursesPageOfStudent().waitOpened();
    }

    @Step("Open Sign out dialog by click Logout button")
    public SignOutDialog clickLogoutButton() {
        logoutButton.click();
        return new SignOutDialog().waitOpened();
    }

    @Step("Get user login")
    public String getUserLogin() {
        return userLogin.getOwnText();
    }

    public boolean isPresented() {
        return panel.exists();
    }

    public boolean isTeacher() {
        return Driver.getCurrentUrl().contains("/home/teacher");
    }

    @Step("Wait Navigation menu in dashboard opened")
    public NavigationMenu waitOpened() {
        panel.should(Condition.exist, Duration.ofSeconds(AppConfig.timeOutSecLong));
        new CookieBanner().removeIfExists();
        return this;
    }
}
