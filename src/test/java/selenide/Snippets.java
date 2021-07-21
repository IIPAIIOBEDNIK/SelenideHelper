package selenide;



import com.codeborne.selenide.AuthenticationType;
import com.codeborne.selenide.Selenide;

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

        Selenide.switchTo().window(""); //Открытие нового окна






    }

}
