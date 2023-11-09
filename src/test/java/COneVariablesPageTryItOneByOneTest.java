
import base.BaseOneByOneTest;
import helpers.COneCreateCourseHelper;
import helpers.Helper;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.codio.common.users.Student;
import com.codio.common.users.Teacher;
import com.codio.ide.tabs.FileTab;
import com.codio.ide.tabs.guides.playmode.GuidesPlayMode;

@Tag("content")
public class COneVariablesPageTryItOneByOneTest extends BaseOneByOneTest {

    /**
     * Use {@link COneCreateCourseHelper} to recreate default course for this tests
     * if needed.
     */

    private static final Teacher teacherA = Teacher.getTeacher("teacher");
    private static final Student student1 = Student.getStudent("student1");
    private static final String course = "Course For Auto Tests";
    private static final String moduleName = "Basic Skills";
    private static final String assignment1 = "Variables";
    private static final String fileName = "playground-variables1.py";
    private static final GuidesPlayMode guides = new GuidesPlayMode();

    @BeforeAll
    static void init() {
        Helper.login(teacherA);
        Helper.openCourseByTeacher(teacherA, course)
            .openOverviewPage()
            .openAssignmentInModule(assignment1, moduleName)
            .openProgressTab()
            .getOverviewAssignmentPage()
            .resetForAllStudentsIfPossible();

        Helper.reLogin(student1)
            .clickCoursesByStudent()
            .openActiveTab()
            .course(course)
            .open()
            .module(moduleName)
            .assignment(assignment1)
            .open();
        guides.waitOpened().openPage("Fundamentals/Variables/Variables");
    }

    @Test
    @Description("Check the first Try It button show correct result")
    public void firstTryIt() {
        String code = guides.getCodeBlockText(1);
        new FileTab(fileName).getMonaco().deleteAll().addText(code);
        guides.clickButton("try it", 1);

        Assertions.assertEquals(
            "Hello world",
            guides.getCodioButtonResult("try it", 1),
            "Is the first TRY IT button result correct"
        );
    }

    @Test
    @Description("Check the second Try It button show correct result")
    public void secondTryIt() {
        String code = guides.getCodeBlockText(2);
        new FileTab(fileName).getMonaco().deleteAll().addText(code);
        guides.clickButton("try it", 2);

        Assertions.assertEquals(
            "Hello world\nmy_variable",
            guides.getCodioButtonResult("try it", 2),
            "Is the second TRY IT button result correct"
        );
    }
}
