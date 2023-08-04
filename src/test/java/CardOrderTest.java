import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {

    String city = "Пермь";
    String name = "Иван Иванов";
    String phone = "+79000000000";
    int plusDays = 5;
    String currentDate = generateDate(plusDays, "dd.MM.yyyy");

    @BeforeEach
    void setupUp() {
        open("http://localhost:9999/");
    }

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldTestForm() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldTestEmptyForm() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithoutAgreement() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidCityV1() {
        $("[data-test-id=city] input").setValue("Gthvm");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidCityV2() {
        $("[data-test-id=city] input").setValue("///////");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithEmptyCity() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidNameV1() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("Bdfy Bdfyjd");
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidNameV2() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("1234");
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidNameV3() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("*&^%&$");
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithEmptyName() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue(phone);
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidPhoneV1() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("+7900000000");
        $("button.button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidPhoneV2() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("+790000000000");
        $("button.button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidPhoneV3() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("+(?**%*%*)");
        $("button.button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidPhoneV4() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("иван иванов");
        $("button.button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithInvalidPhoneV5() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("Bdfy Bdfyjd");
        $("button.button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormWithEmptyPhone() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("");
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldTestFormV2() {
        int plusMoreDays = 7;
        String currentDate = generateDate(plusMoreDays, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Пе");
        $$(".menu-item__control").findBy(text(city)).click();
        $("[data-test-id=date] input").click();
        if (!generateDate(plusDays, "MM").equals(generateDate(plusMoreDays, "MM"))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(generateDate(plusMoreDays, "d"))).click();
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
}
