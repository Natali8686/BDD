package ru.netology.test;

import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        loginPage.validLogin(authInfo).validVerification(verificationCode);

        int amount = 1000;
        var cardInfo = DataHelper.getFirstCardInfo();

        var dashboard = new DashboardPage();
        dashboard.changeCard(1).shouldMoneyInfo(cardInfo, amount);
        int balanceFirstCard = dashboard.getCardBalance("0");
        int balanceSecondCard = dashboard.getCardBalance("1");

        assertEquals(balanceFirstCard, balanceFirstCard);
        assertEquals(balanceSecondCard, balanceSecondCard);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        loginPage.validLogin(authInfo).validVerification(verificationCode);

        int amount = 3000;
        var cardInfo = DataHelper.getSecondCardInfo();

        var dashboard = new DashboardPage();
        dashboard.changeCard(0).shouldMoneyInfo(cardInfo, amount);
        int balanceFirstCard = dashboard.getCardBalance("1");
        int balanceSecondCard = dashboard.getCardBalance("0");

        assertEquals(balanceFirstCard, balanceFirstCard);
        assertEquals(balanceSecondCard, balanceSecondCard);
    }

    @Test
    void shouldNoTransferMoneyOverLimit() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        loginPage.validLogin(authInfo).validVerification(verificationCode);

        var cardInfoFirst = DataHelper.getFirstCardInfo();
        var cardInfoSecond = DataHelper.getSecondCardInfo();

        var dashboard = new DashboardPage();
        int amount = (dashboard.getCardBalance("1") + 150000);

        dashboard.changeCard(1).shouldMoneyInfo(cardInfoFirst, amount);
        dashboard.changeCard(1).shouldNoNegativeBalance(cardInfoFirst);
        int currentBalanceFirstCard = dashboard.getCardBalance("0");
        int currentBalanceSecondCard = dashboard.getCardBalance("1");

        assertTrue(currentBalanceFirstCard > 0 && currentBalanceSecondCard > 0);
        assertEquals(currentBalanceFirstCard, currentBalanceFirstCard);
        assertEquals(currentBalanceSecondCard, currentBalanceSecondCard);
    }
}