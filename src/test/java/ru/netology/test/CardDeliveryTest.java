package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    public String changedDate(int numberDays) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(numberDays);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return inputDate;
    }

    @Test
    void validFiledNewVersion() {
        $("[placeholder='Город']").setValue("Ка");
        $$(".input__menu span").findBy(text("Калуга")).click();
        LocalDate date = LocalDate.now();
        date = date.plusDays(7);
        Locale l = Locale.forLanguageTag("ru");
        int day = date.getDayOfMonth();
        String dayString = String.valueOf(day);
        String monthYear = date.format(DateTimeFormatter.ofPattern("LLL yyyy", l));
        $(".icon_name_calendar").click();
        while (!($(".calendar__name").getText().equalsIgnoreCase(monthYear))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(dayString)).click();
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+79115556644");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(text("Встреча успешно забронирована на "), Duration.ofSeconds(15));
        $(".notification__content").shouldBe(text(changedDate(7)), Duration.ofSeconds(15));
    }

    @Test
    void validFiled() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(3));
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+79115556644");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(text("Встреча успешно забронирована на "), Duration.ofSeconds(15));
        $(".notification__content").shouldBe(text(changedDate(3)), Duration.ofSeconds(15));
    }

    @Test
    void invalidFiledCity() {
        $("[placeholder='Город']").setValue("США");
        $(byText("Забронировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyFiledCity() {
        $(byText("Забронировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidFileDate() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(2));
        $(byText("Забронировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidFiledName() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(3));
        $("[data-test-id='name'] input").setValue("Petrov Ivan");
        $(byText("Забронировать")).click();
        $("[data-test-id='name']").shouldBe(text("Имя и Фамилия указаные неверно."));

    }

    @Test
    void emptyFiledName() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(3));
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void invalidFiledNumber() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(3));
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+791155566444");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone']").shouldBe(text("Телефон указан неверно."));

    }

    @Test
    void emptyFiledNumber() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(3));
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone']").shouldBe(text("Поле обязательно для заполнения"));

    }

    @Test
    void invalidCheckbox() {
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue(changedDate(3));
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+79115556644");
        $(byText("Забронировать")).click();
        $(".checkbox_checked").shouldBe(hidden);

    }
}



