package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    private SelenideElement logIn = $("[data-test-id='login'] input");
    private SelenideElement password = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");
    private SelenideElement errorMessage = $("[data-test-id=error-notification] .notification__content");

    public void openPage() {
        open("http://localhost:9999/");
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        logIn.setValue(info.getLogin());
        password.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void shouldErrorMessage() {
        errorMessage.shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}