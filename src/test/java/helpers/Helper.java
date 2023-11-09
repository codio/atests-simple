package helpers;

import io.qameta.allure.Step;

import com.codio.common.pageelements.TestStudentHeader;
import com.codio.common.users.Student;
import com.codio.common.users.Teacher;
import com.codio.common.users.User;
import com.codio.dashboard.NavigationMenu;
import com.codio.dashboard.auth.LoginPage;
import com.codio.dashboard.courses.student.CoursesPageOfStudent;
import com.codio.dashboard.courses.teacher.*;
import com.codio.dashboard.courses.teacher.course.CoursePageOfTeacher;
import com.codio.helpers.*;

public class Helper {

    @Step("Clone course by code")
    public static CoursePageOfTeacher cloneCourseByCode(String code, String courseName) {
        return new CoursesPageOfTeacher().open()
            .openAllTab()
            .clickNewCourseButton()
            .clickAddFromShareCodeMenuItem()
            .setCourseCode(code)
            .setCourseName(courseName)
            .clickCreateCourseButton();
    }

    @Step("Clone course by code and get student add token")
    public static String cloneCourseByCodeAndGetStudentAddToken(String code, String courseName) {
        return cloneCourseByCode(code, courseName).openStudentsPage().getStudentAddToken();
    }

    @Step("On Active tab open course by name and delete if exist")
    public static void deleteActiveCourseIfExist(String courseName) {
        CourseItem course = new CoursesPageOfTeacher().open().openAllTab().course(courseName);
        if (course.isPresented()) {
            course.open()
                .openCourseDetailsPage()
                .courseDetailsPanel()
                .clickDeleteCourseButton()
                .setConfirmCode()
                .clickDeleteCourseOnlyButton();
        }
    }

    public static boolean isCurrentUser(User user) {
        String currentUserLogin = "";
        if (!isDashboard()) {
            tryToOpenHomePage();
        }
        if (isDashboard()) {
            currentUserLogin = switchBackToTeacherAccountIfTestStudent().getUserLogin();
        }
        return currentUserLogin.equals(user.getLogin());
    }

    public static boolean isDashboard() {
        return new NavigationMenu().isPresented();
    }

    @Step("Join Course with Auth from Login page")
    public static CoursesPageOfStudent joinCourseWithAuthFromLoginPage(String token, Student student) {
        return new LoginPage().waitOpened()
            .clickJoinACourseLink()
            .setTokenFieldValue(token)
            .clickNextButton()
            .setEmailFieldValue(student.getEmail())
            .clickNextButton()
            .setFirstNameValueIfFieldAvailable(student.getFirstName())
            .setLastNameValueIfFieldAvailable(student.getLastName())
            .setPasswordFieldValue(student.getPassword())
            .clickJoinCourseButton();
    }

    @Step("Login as user")
    public static NavigationMenu login(User user) {
        return new LoginPage().open().login(user.getLogin(), user.getPassword());
    }

    @Step("Login by user if not current")
    public static NavigationMenu loginByUserIfNotCurrent(User user) {
        Driver.switchToDefaultContent(); // helps if exit with error within iframe
        if (!isCurrentUser(user)) {
            logout();
            return login(user);
        }
        if (!isDashboard()) {
            tryToOpenHomePage();
        }
        return new NavigationMenu();
    }

    @Step("Logout")
    public static LoginPage logout() {
        if (!isDashboard()) {
            tryToOpenHomePage();
        }
        if (isDashboard() || !new LoginPage().tryToOpen()) {
            return switchBackToTeacherAccountIfTestStudent().clickLogoutButton().clickYesButton();
        }
        return new LoginPage();
    }

    @Step("Open Course by teacher")
    public static CoursePageOfTeacher openCourseByTeacher(Teacher teacher, String courseName) {
        return loginByUserIfNotCurrent(teacher).clickCourses()
            .openAllTab()
            .searchCourse(courseName)
            .course(courseName)
            .open();
    }

    @Step("Re-login as user")
    public static NavigationMenu reLogin(User user) {
        logout();
        return new LoginPage().waitOpened().login(user.getLogin(), user.getPassword());
    }

    @Step("Switch back from teacher account if test student")
    public static NavigationMenu switchBackToTeacherAccountIfTestStudent() {
        TestStudentHeader testStudentHeader = new TestStudentHeader();
        if (testStudentHeader.isPresented()) {
            testStudentHeader.clickSwitchBackToTeacherAccountButton();
        }
        return new NavigationMenu();
    }

    @Step("Open Home page")
    public static boolean tryToOpenHomePage() {
        Driver.open(Driver.getBaseUrl() + "/home");
        Driver.waitOneOfConditionsIsTrue(Helper::isDashboard, LoginPage::isOpened, AppConfig.timeOutSecMedium);
        return isDashboard();
    }
}
