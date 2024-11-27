package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.codio.helpers.Driver;

public class BaseTest {

    @BeforeEach
    public void setUp() {
        Driver.exitTestWithErrorIfIncorrectKeyboardLayout();
        Driver.initDriver();
    }

    @AfterEach
    public void tearDown() {
        Driver.quit();
    }
}
