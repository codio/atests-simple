import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import base.BaseOneByOneTest;
import helpers.COneCreateCourseHelper;
import helpers.Helper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.codio.common.users.Student;
import com.codio.common.users.Teacher;
import com.codio.dashboard.courses.teacher.course.overview.assignment.progress.StudentItem;
import com.codio.ide.tabs.guides.playmode.GuidesPlayMode;

@Tag("assignment")
public class COneAssignmentParametrizedOneByOneTest extends BaseOneByOneTest {

    /**
     * Use {@link COneCreateCourseHelper} to recreate default course for this tests
     * if needed.
     */

    private static final Teacher teacherA = Teacher.getTeacher("teacher");
    private static final String course = "Course For Auto Tests";
    private static final String moduleName = "Basic Skills";
    private static final String assignment1 = "Printing";
    private final String assessment1option1 = "Missing \" \" around Hello world";
    private final String assessment1option2 = "Capitalize print";
    private final String assessment2option1 = "True";
    private final String assessment2option2 = "False";
    private final String assessment3option1 = "single-line";
    private final String assessment3option2 = "multi-line";
    private final String assessment4option1 = "Sends your output to the screen";
    private final String assessment4option2 = "Sends your output to the printer";
    private final String assessment5option1 = "print(\"Hi!\", end='')";
    private final String assessment5option2 = "print(\"Hi!\")";
    private final String answersAreCorrect = "Answers are correct";
    private final String answersAreWrong = "Answers are wrong";
    private final String correct3Wrong2 = "3 correct, 2 wrong";
    private final String correct2 = "2 correct, 3 not started";

    /**
     * Here new cases to check can be added. After this case's keyName should be
     * added as argument in @ValueSource of 'answersCorrect' parametrised test
     */
    private final HashMap<String, Case> cases = new HashMap<>() {
        {
            put(
                answersAreCorrect,
                new Case(
                    Student.getStudent("student1"),
                    new String[] {assessment1option1},
                    new String[] {assessment2option1},
                    new String[] {assessment3option1, assessment3option2},
                    new String[] {assessment4option1},
                    new String[] {assessment5option1},
                    100,
                    5
                )
            );
            put(
                answersAreWrong,
                new Case(
                    Student.getStudent("student2"),
                    new String[] {assessment1option2},
                    new String[] {assessment2option2},
                    new String[] {assessment3option2, assessment3option1},
                    new String[] {assessment4option2},
                    new String[] {assessment5option2},
                    0,
                    5
                )
            );
            put(
                correct3Wrong2,
                new Case(
                    Student.getStudent("student3"),
                    new String[] {assessment1option1},
                    new String[] {assessment2option2},
                    new String[] {assessment3option1, assessment3option2},
                    new String[] {assessment4option2},
                    new String[] {assessment5option1},
                    60,
                    5
                )
            );
            put(
                correct2,
                new Case(
                    Student.getStudent("student4"),
                    new String[] {assessment1option1},
                    new String[] {assessment2option1},
                    new String[] {},
                    new String[] {},
                    new String[] {},
                    40,
                    2
                )
            );
        }
    };

    private static class Case {
        final Student student;
        final String[] answer1;
        final String[] answer2;
        final String[] answer3;
        final String[] answer4;
        final String[] answer5;
        final int grade;
        final int answered;

        public Case(
            Student student, String[] answer1, String[] answer2, String[] answer3, String[] answer4, String[] answer5,
            int grade, int answered
        ) {
            this.student = student;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.answer4 = answer4;
            this.answer5 = answer5;
            this.grade = grade;
            this.answered = answered;
        }
    }

    @BeforeAll
    static void init() {
        Helper.login(teacherA);
        Helper.openCourseByTeacher(teacherA, course)
            .openOverviewPage()
            .openAssignmentInModule(assignment1, moduleName)
            .openProgressTab()
            .getOverviewAssignmentPage()
            .resetForAllStudentsIfPossible();
    }

    @ParameterizedTest(name = "Check assignment completion results. {0}")
    @ValueSource(strings = {answersAreCorrect, answersAreWrong, correct3Wrong2, correct2})
    public void answersCorrect(String caseName) {
        Student student = cases.get(caseName).student;
        String[] answer1 = cases.get(caseName).answer1;
        String[] answer2 = cases.get(caseName).answer2;
        String[] answer3 = cases.get(caseName).answer3;
        String[] answer4 = cases.get(caseName).answer4;
        String[] answer5 = cases.get(caseName).answer5;
        int grade = cases.get(caseName).grade;
        int answered = cases.get(caseName).answered;

        Helper.reLogin(student)
            .clickCoursesByStudent()
            .openActiveTab()
            .course(course)
            .open()
            .module(moduleName)
            .assignment(assignment1)
            .open();
        GuidesPlayMode guides = new GuidesPlayMode().waitOpened();

        if (answer1.length > 0) {
            guides.openPage("Fundamentals/Printing/Printing to the Console");
            guides.answerMultipleChoiceAssessment("What is wrong with the code snippet below?", answer1);
        }

        if (answer2.length > 0) {
            guides.openPage("Fundamentals/Printing/Printing");
            guides.answerMultipleChoiceAssessment(
                "True or False: Python automatically inserts a newline character when you use the print command.",
                answer2
            );
        }

        if (answer3.length > 0) {
            guides.openPage("Fundamentals/Printing/Comments");
            guides.answerFillInTheBlanksAssessment("Fill in the blanks below about comments in Python.", answer3);
        }

        if (answer4.length > 0) {
            guides.openPage("Fundamentals/Formative Assessment - Printing/Formative Assessment 1");
            guides.answerMultipleChoiceAssessment("What does the print statement do?", answer4);
        }

        if (answer5.length > 0) {
            guides.openPage("Fundamentals/Formative Assessment - Printing/Formative Assessment 2");
            guides.answerMultipleChoiceAssessment(
                "Which statement below will print Hi! without a newline character?",
                answer5
            );
        }

        guides.openPage("Fundamentals/Formative Assessment - Printing/Formative Assessment 2");
        guides.markAsCompleted();

        Helper.reLogin(teacherA);
        StudentItem studentItem = Helper.openCourseByTeacher(teacherA, course)
            .openOverviewPage()
            .openAssignmentInModule(assignment1, moduleName)
            .openProgressTab()
            .student(student.getFullName());

        Assertions.assertAll(
            () -> assertEquals(grade, studentItem.getGrade(), "Is Grade correct"),
            () -> assertEquals(answered, studentItem.getAnswered(), "Is Answered correct")
        );
    }
}
