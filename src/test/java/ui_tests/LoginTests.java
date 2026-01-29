package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;

public class LoginTests extends ApplicationManager {

    // -------------------- LOGIN (positive) --------------------

    @Test
    public void loginPositiveTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm("family@mail.ru", "Family123!");
        loginPage.clickBtnLoginForm();

        Assert.assertTrue(
                new ContactPage(getDriver()).isTextInBtnAddPresent("ADD"),
                "Login failed: ADD button not found"
        );
    }

    @Test
    public void loginPositiveTestWithUser() {
        User user = new User("family@mail.ru", "Family123!");

        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertTrue(
                new ContactPage(getDriver()).isTextInBtnSignOutPresent("Sign Out"),
                "Login failed: Sign Out not found"
        );
    }

    // -------------------- LOGIN (negative) --------------------

    @Test
    public void loginNegativeTest_WrongEmailFormat() {
        User user = new User("familymail.ru", "Family123!");

        new HomePage(getDriver()).clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertEquals(
                loginPage.closeAlertReturnText(),
                "Wrong email or password",
                "Unexpected alert text for wrong email format"
        );
    }

    @Test
    public void loginNegativeTest_WrongPassword() {
        User user = new User("family@mail.ru", "WrongPassword123!");

        new HomePage(getDriver()).clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertEquals(
                loginPage.closeAlertReturnText(),
                "Wrong email or password",
                "Expected 'Wrong email or password' for wrong password"
        );
    }

    @Test
    public void loginNegativeTest_EmptyEmail() {
        User user = new User("", "Family123!");

        new HomePage(getDriver()).clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertEquals(
                loginPage.closeAlertReturnText(),
                "Wrong email or password",
                "Expected error for empty email"
        );
    }

    @Test
    public void loginNegativeTest_EmptyPassword() {
        User user = new User("family@mail.ru", "");

        new HomePage(getDriver()).clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertEquals(
                loginPage.closeAlertReturnText(),
                "Wrong email or password",
                "Expected error for empty password"
        );
    }

    // -------------------- REGISTRATION (negative) --------------------

    @Test
    public void registrationNegativeTest_InvalidEmail() {
        new HomePage(getDriver()).clickBtnSignUp();

        RegistrationPage regPage = new RegistrationPage(getDriver());

        regPage.fillRegistrationForm(new User("testmail.ru", "Password123!"))
                .clickTermsIfPresent();
                //      .clickBtnYalla();

        String alert = regPage.closeAlertReturnText();

        Assert.assertTrue(
                alert.toLowerCase().contains("email") || alert.toLowerCase().contains("wrong"),
                "Expected email validation message, got: " + alert
        );
    }

    @Test
    public void registrationNegativeTest_WeakPassword() {
        new HomePage(getDriver()).clickBtnSignUp();

        RegistrationPage regPage = new RegistrationPage(getDriver());

        regPage.fillRegistrationForm(new User("test_user_1@mail.ru", "12345"))
                .clickTermsIfPresent()
                .clickBtnYalla();

        String alert = regPage.closeAlertReturnText();

        Assert.assertTrue(
                alert.toLowerCase().contains("password") || alert.toLowerCase().contains("weak"),
                "Expected password validation message, got: " + alert
        );
    }

    @Test
    public void registrationNegativeTest_NoTermsCheckbox() {
        new HomePage(getDriver()).clickBtnSignUp();

        RegistrationPage regPage = new RegistrationPage(getDriver());

        regPage.fillRegistrationForm(new User("test_user_2@mail.ru", "Password123!"));
        // НЕ кликаем чекбокс (если он есть)
        regPage.clickBtnYalla();

        Assert.assertTrue(
                regPage.isYallaButtonStillVisible(),
                "Expected registration NOT to proceed without terms checkbox"
        );
    }

    @Test
    public void registrationNegativeTest_EmptyRequiredFields() {
        new HomePage(getDriver()).clickBtnSignUp();

        RegistrationPage regPage = new RegistrationPage(getDriver());

        regPage.fillRegistrationForm(new User("", ""))
                .clickTermsIfPresent()
                .clickBtnYalla();

        boolean stillOnForm = regPage.isYallaButtonStillVisible();

        String alertText = "";
        try {
            alertText = regPage.closeAlertReturnText();
        } catch (Exception ignored) {
        }

        Assert.assertTrue(
                stillOnForm || (alertText != null && !alertText.trim().isEmpty()),
                "Expected validation error OR staying on registration page, but no validation detected."
        );
    }
}
