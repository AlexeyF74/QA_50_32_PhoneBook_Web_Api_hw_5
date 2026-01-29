package pages;

import dto.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {

    // ====== LOCATORS ======

    @FindBy(css = "input#email, input[name='email'], input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input#password, input[name='password'], input[type='password']")
    private WebElement passwordInput;

    @FindBy(css = "input[type='checkbox']")
    private WebElement termsCheckbox;

    @FindBy(css = "button[type='submit']")
    private WebElement btnYalla;

    private WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        setDriver(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    // ====== WAITS ======
    public RegistrationPage waitForForm() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        return this;
    }

    // ====== FILL ======
    public RegistrationPage fillRegistrationForm(User user) {
        waitForForm();
        type(emailInput, user.getUsername());
        type(passwordInput, user.getPassword());
        return this;
    }


    public RegistrationPage fillRegistrationForm(String firstName, String lastName, String email, String password) {
        waitForForm();
        type(emailInput, email);
        type(passwordInput, password);
        return this;
    }

    // ====== TERMS ======

    public RegistrationPage clickTermsIfPresent() {
        try {
            if (termsCheckbox != null) {
                wait.until(ExpectedConditions.visibilityOf(termsCheckbox));
                if (!safeIsSelected(termsCheckbox)) {
                    wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
                }
            }
        } catch (Exception ignored) {

        }
        return this;
    }

//    public RegistrationPage setCheckBoxAgreeTermsOfUse() {
//        return clickTermsIfPresent();
//    }

    // ====== SUBMIT ======


    public RegistrationPage clickBtnYalla() {
        wait.until(ExpectedConditions.elementToBeClickable(btnYalla)).click();
        return this;
    }

//    public RegistrationPage clickSignUp() {
//        return clickBtnYalla();
//    }

    // ====== ASSERT HELPERS ======
    public String closeAlertReturnText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public boolean isYallaButtonStillVisible() {
        try {
            return btnYalla.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ====== PRIVATE HELPERS ======
    private void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    private boolean safeIsSelected(WebElement el) {
        try {
            return el.isSelected();
        } catch (StaleElementReferenceException e) {

            return false;
        }
    }


//    private boolean isAlertPresent() {
//        try {
//            driver.switchTo().alert();
//            return true;
//        } catch (NoAlertPresentException e) {
//            return false;
//        }
    }
//}
