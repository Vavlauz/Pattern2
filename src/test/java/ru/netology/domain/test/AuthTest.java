package ru.netology.domain.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.UserGenerator;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.domain.data.UserGenerator.getActiveRegisteredUser;
import static ru.netology.domain.data.UserGenerator.getWrongNameUser;

public class AuthTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void happyPath() {
        UserGenerator.UserInfo validUser = getActiveRegisteredUser();
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginWithWrongNameUser() {
        var wrongNameUser = getWrongNameUser();
        $("[data-test-id=login] input").setValue(wrongNameUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongNameUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }
}
