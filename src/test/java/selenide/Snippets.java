package selenide;



import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

//This's not a full list, just the most common
public class Snippets {

    void browser_command_examples(){
        Selenide.open("https://google.com"); //Открывает в браузере указаннную скобках страницу(Абсолютный url)
        open("customers/orders");   //Открывает относительный url. при использовании Configuration.baseUrl="http://test.yandex.ru"; Открыласьбы страница "http://test.yandex.ru/customer/orders"
        open("/", AuthenticationType.BASIC, "user", "password");

        Selenide.back();
        Selenide.refresh();

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();

        Selenide.confirm(); // OK in alert dialogs
        Selenide.dismiss(); // Cancel in alert dialogs

        Selenide.closeWindow(); // Close active tab
        Selenide.closeWebDriver(); // Close browser completely

        Selenide.switchTo().frame("new"); //Ищет фрейм по имени, по id и еще чему-то
        Selenide.switchTo().defaultContent(); //Для выхода из фрейма

        Selenide.switchTo().window(""); //Переход из одного окна в другое
    }

    void selectors_examples(){
        $("div").click();
        element("div").click();

        $("div", 2).click(); // Клик по 3-му элементу

        $x("//h1/div").click();
        $(byXpath("//h1/div")).click();

        $(byText("full text")).click(); // по полному совпадению текста
        $(withText("ull tex")).click(); // по частичному совпадению текста

        $("").parent();
        $("").sibling(1); //идет вниз от якорного элемента
        $("").preceding(1); //идет вверх от якорного элемента

        $("div").$("h1").find(byText("abc")).click();

        //very optional
        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();

        $(byId("mytext")).click();
        $("#mytext").click();

        $(byClassName("red")).click();
        $(".red").click();
    }

    void actions_example(){
        $("").click();
        $("").doubleClick();
        $("").contextClick(); //Клик правой клавишей

        $("").hover(); //Навести мышку на элемент, но не кликать

        $("").setValue("text"); //Очищает поле и пишет текст
        $("").append("text"); //Добавляет текст в указанное поле
        $("").clear();

        //
        // perform() - не забывать, нужен для отправки всех команд одновременно и их последовательного выполнения
        $("div").sendKeys("c"); //hotkey c on element
        actions().sendKeys("c").perform(); //hotkey c on whole application. Нажимаем на
        // горячую клавишу в окне браузера, без воздействя на конкретный элемент
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // CTRL + F
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f")); // Привязываемся ко всему документу и ищем в нем

        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();

        //complex actions with keybord and mouse, example
        actions().moveToElement($("div")).clickAndHold().moveByOffset(300, 200).release().perform();
        //actions() - переход в режим команд, moveToElement - двигаем мышку к элементу, clickAndHold() - нажать на ЛКМ и не отпускать,
        // moveByOffset(300, 200) - сдвинуть мышку на 300 пиксилей вправо и на 200 вниз), release() - отпустить кнопку

        //old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_options"); //Выбор из выпадающего списка
        $("").selectRadio("radio_options");
    }

    void assertions_examples(){
        $("").shouldBe(visible);
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc"));
        $("").shouldNotHave(text("abc"));
        $("").should(appear); //appear - появляться
        $("").shouldNot(appear);

        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30)); //Жди появление указанного элемента до 30 секунд
        $("").waitUntil(visible, 30000); // Устаревший, все есть в shouldBe
    }

    void conditions_examples(){
        $("").shouldBe(visible); //visible - видимость элемента
        $("").shouldBe(hidden); //hidden - скрытность элемента

        $("").shouldHave(text("abc")); //не чувствителен к ригистру
        $("").shouldHave(exactText("abc")); //
        $("").shouldHave(textCaseSensitive("abc"));
        $("").shouldHave(exactTextCaseSensitive("abc"));
        $("").should(matchText("[0-9]abc$"));

        $("").shouldHave(cssClass("red")); //внутри скобок можно использовать перечисление
        $("").shouldHave(cssValue("font-size", "12")); //физическое свойство элемента

        //Используется для проверки в поисковой строке
        $("").shouldHave(value("25"));
        $("").shouldHave(exactValue("25"));
        $("").shouldBe(Condition.empty);

        $("").shouldHave(attribute("disabled"));
        $("").shouldHave(attribute("name", "example"));
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));

        $("").shouldBe(checked); // for checkboxes

        //Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist);

        //Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled);
        $("").shouldBe(enabled);
    }

    void collections_examples(){
        $$("div"); //does nothing!

        //selections
        $$("div").filterBy(text("123")).shouldHave(size(1));
        $$("div").excludeWith(text("123")).shouldHave(size(1)); //Ищем всё кроме "123"

        $$("div").first().click();
        elements("div").first().click();
        // $("div").click();
        $$("div").last().click();

        $$("div").get(1).click(); //the second! (start with 0)
        $("div", 1).click(); //same as previous

        $$("div").findBy(text("123")).click(); //finds first

        //assertions
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma"));
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma"));

        $$("").shouldHave(textsInAnyOrder("Alfa", "Beta", "Gamma"));
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Alfa", "Beta", "Gamma"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text

        $$("").shouldHave(sizeGreaterThan(0));
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3));
        $$("").shouldHave(sizeLessThanOrEqual(2));
    }

    void file_operation_examples() throws FileNotFoundException {
        File file1 = $("a.fileLink").download(); //only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER));
        //more common options, but may have problems with Grid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        //don't forget to submit!
        $("UploadButton").click();
    }

    void javascript_examples(){
        executeJavaScript("alert(selenide)");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fartytwo = executeJavaScript("return arguments[0]+arguments[1]", 6, 7);
    }

}
