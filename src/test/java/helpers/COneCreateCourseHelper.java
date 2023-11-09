package helpers;

import java.util.List;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import com.codio.common.users.Student;
import com.codio.common.users.Teacher;

public class COneCreateCourseHelper extends BaseTest {

    private final Teacher teacherA = Teacher.getTeacher("teacher");
    private final List<Student> students = List.of(
        Student.getStudent("student1"),
        Student.getStudent("student2"),
        Student.getStudent("student3"),
        Student.getStudent("student4")
    );

    private final String code = "ZY9E-EC3H-4WH9";
    private final String course = "Course For Auto Tests";

    /**
     * This helper should be run once, just to create example test course and join
     * it by students. Before run be sure that credentials for teacher and 4
     * students are added as described in README.md file
     */
    @Test
    public void recreateDefaultCourse() {
        Helper.login(teacherA);
        Helper.deleteActiveCourseIfExist(course);
        String token = Helper.cloneCourseByCodeAndGetStudentAddToken(code, course);
        Helper.logout();

        for (Student student : students) {
            Helper.joinCourseWithAuthFromLoginPage(token, student);
            Helper.logout();
        }
    }
}
