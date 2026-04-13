import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    Login login = new Login();
    Registration reg = new Registration();

    @Test
    void testLoginUser_Successful() {
        reg.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean result = login.loginUser(reg, "kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    void testLoginUser_FailedUsername() {
        reg.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean result = login.loginUser(reg, "wrongUser", "Ch&&sec@ke99!");
        assertFalse(result);
    }

    @Test
    void testLoginUser_FailedPassword() {
        reg.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean result = login.loginUser(reg, "kyl_1", "wrongPassword");
        assertFalse(result);
    }

    @Test
    void testReturnLoginStatus_Success() {
        reg.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.returnLoginStatus(reg, "kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Smith it is great to see you again.", result);
    }

    @Test
    void testReturnLoginStatus_Failure() {
        reg.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.returnLoginStatus(reg, "wrongUser", "wrongPass");
        assertEquals("Username or password incorrect, please try again.", result);
    }
}