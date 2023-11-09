package com.codio.dashboard.courses.student;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import com.codio.common.pageelements.CookieBanner;
import com.codio.common.pageelements.Search;

public class BaseTabOfStudentCourses {

    protected final Search search = new Search();
    protected final SelenideElement coursesList = $x("//div[@class='studentMain']//div[@class='infinite-list']");
    protected final CookieBanner cookieBanner = new CookieBanner();

    public CourseItem course(String courseName) {
        cookieBanner.removeIfExists();
        search.setValue(courseName);
        return new CourseItem(courseName);
    }
}
