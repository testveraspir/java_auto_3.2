package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {


    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");

    }


    @Test
    void validFiled() {
        Configuration.headless = true;
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+79115556644");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void invalidFiledCity() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("США");
        $(byText("Забронировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyFiledCity() {
        open("http://localhost:9999");
        $(byText("Забронировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidFileDate() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(2);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $(byText("Забронировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidFiledName() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Petrov Ivan");
        $(byText("Забронировать")).click();
        $("[data-test-id='name']").shouldBe(text("Имя и Фамилия указаные неверно."));

    }

    @Test
    void emptyFiledName() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void invalidFiledNumber() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+791155566444");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone']").shouldBe(text("Телефон указан неверно."));

    }

    @Test
    void emptyFiledNumber() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone']").shouldBe(text("Поле обязательно для заполнения"));

    }

    @Test
    void invalidCheckbox() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Казань");
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[type='tel']").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[name='phone']").setValue("+79115556644");
        $(byText("Забронировать")).click();
        $(".checkbox_checked").shouldBe(hidden);

    }
}



