package base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.codio.helpers.Driver;

public class BaseOneByOneTest {

    @BeforeAll
    public static void setUp() {
        Driver.exitTestWithErrorIfIncorrectKeyboardLayout();
        Driver.initDriver();
    }

    @AfterAll
    static void tearDown() {
        Driver.quit();
    }
}
