package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {
    private SelenideElement amountForTransfer = $("[data-test-id='amount'] .input__control");
    private SelenideElement cardNumberSelector = $("[data-test-id='from'] .input__control");
    private SelenideElement buttonTransfer = $("[data-test-id='action-transfer']");
    private SelenideElement error = $("[data-test-id=error-notification]");


    public DashboardPage shouldMoneyInfo(DataHelper.MoneyTransfer moneyTransfer, int amount) {
        amountForTransfer.setValue(String.valueOf(amount));
        cardNumberSelector.setValue(moneyTransfer.getCardNumber());
        buttonTransfer.click();
        return new DashboardPage();
    }

    public void shouldNoNegativeBalance(DataHelper.MoneyTransfer moneyTransfer) {
        error.shouldHave(exactText("На карте " + moneyTransfer.getCardNumber() + " недостаточно средств. Пополните счет и повторите попытку")).shouldBe(visible);
    }

}
