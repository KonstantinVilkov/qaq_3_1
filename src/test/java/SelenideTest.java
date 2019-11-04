import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @Test
    void successfulLaunchWithValidData() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }

    @Test
    void errorEnteringNonRussianName() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();
        SelenideElement name = $("[data-test-id=name]");
        name.shouldHave(cssClass("input_invalid"));
        name.$(By.className("input__sub")).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void errorEnteringDataWithoutName() {
        open("http://localhost:9999");

        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();
        SelenideElement name = $("[data-test-id=name]");
        name.shouldHave(cssClass("input_invalid"));
        name.$(By.className("input__sub")).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void ErrorEnteringDataWithoutPhone() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();
        SelenideElement phone = $("[data-test-id=phone]");
        phone.shouldHave(cssClass("input_invalid"));
        phone.$(By.className("input__sub")).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void errorWhileEnteringDataWithoutCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(By.tagName("button")).click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

}

