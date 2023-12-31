package pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.DbInteraction;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void login(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }
    public void changePassword(String password) {
        passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        passwordField.setValue(password);
        loginButton.click();
    }

    public VerificationPage validLogin(DbInteraction.AuthInfo info) {
        login(info.getLogin(),info.getPassword());
        return new VerificationPage();
    }

    public LoginPage invalidLogin() {
        login(DbInteraction.generateUser().getLogin(), DbInteraction.generateUser().getPassword());
        loginErrorNotification();
        return new LoginPage();
    }

    public LoginPage getUserWithDifferentPassword() {
        login (DbInteraction.getAuthInfoFromTestData().getLogin(),DbInteraction.generateUser().getPassword());
        loginErrorNotification();
        return new LoginPage();
    }

    public LoginPage threeTimesLoginDifferentPassword() {
        login(DbInteraction.getAuthInfoFromTestData().getLogin(),DbInteraction.generateUser().getPassword());
        new LoginPage();
        loginErrorNotification();
        changePassword(DbInteraction.generateUser().getPassword());
        loginErrorNotification();
        blockedUserErrorNotification();
        return new LoginPage();
    }

    public void errorNotificationVisible() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(20));
    }
    public void loginErrorNotification() {
        errorNotificationVisible();
        errorNotification.shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
    public void blockedUserErrorNotification() {
        errorNotificationVisible();
        errorNotification.shouldHave(text("Исчерпаны попытки входа в личный кабинет. Пользователь заблокирован"));
    }
}