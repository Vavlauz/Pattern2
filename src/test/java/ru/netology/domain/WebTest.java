package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.openqa.selenium.Keys.chord;

public class WebTest {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");

    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSendForm() {
        String planningDate = generateDate(5);
        $("[data-test-id='city'] input").val("Новосибирск");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    void fieldTownEmpty() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Поле обязательно')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldsAllEmpty() {
        $("[data-test-id='city'] input").val("");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("");
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Поле обязательно')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldTownWrong() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Вадим");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Доставка в выбранный город недоступна')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldTownWrong2() {
        String planningDate = generateDate(3);
        $("[data-test-id='city'] input").val("Novosibirsk");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Доставка в выбранный город недоступна')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataEmpty() {
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Неверно введена дата')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataCurrent() {
        String planningDate = generateDate(0);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataWrong() {
        String planningDate = generateDate(-1);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataTrue() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameEmpty() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Поле обязательно')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameTrue() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Вадим");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameTrue2() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameWrong() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков_Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameWrong2() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Vadim");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldPhoneEmpty() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Поле обязательно для заполнения')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldPhoneWrong() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldPhoneWrong2() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+7999333666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldCheckBoxNotValue() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").val("Кемерово");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").val("+79993336666");
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Я соглашаюсь с условиями обработки и использования моих персональных данных')]").should(appear, ofSeconds(15));

    }

    @Test
    void shouldSubmitComplexRequest() {
        $("[data-test-id='city'] input").val("ке");
        $(".input__menu").find(withText("Кемерово")).click();
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);
        String dateOfMeetingFormatted = dateOfMeeting.format(ofPattern("dd.MM.yyyy"));
        if (startDate.getMonthValue() != dateOfMeeting.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(String.valueOf(dateOfMeeting.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").val("Ужакин Вадим");
        $("[data-test-id='phone'] input").val("+79993336666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $("button>.button__content").click();
        $("[class='notification__content']").shouldHave(text("Встреча успешно забронирована на " + dateOfMeetingFormatted), ofSeconds(15));

    }

}
