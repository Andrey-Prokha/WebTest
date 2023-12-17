import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainClass {

    @Test
    public void FirstTest() {

        System.setProperty("webdriver.chrome.driver","C:\\Users\\PC-55\\IdeaProjects\\TestProject\\drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        // Переход на сайт
        driver.get("https://habr.com/ru/articles/");
        driver.manage().window().setSize(new Dimension(1280, 1025));

        // Поиск поля поиска
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//*[@class='tm-header-user-menu__item tm-header-user-menu__search']")).click();

        // Ввод значения в поле поиска и выполненение поиска
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='q']")), driver.switchTo().activeElement());
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Selenium WebDriver");
        driver.findElement(By.xpath("//*[@class='tm-svg-img tm-svg-icon']")).click();

        // Поиск статьи
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.linkText("Что такое Selenium?")).click();

        // Проверка совпадения открытой статьи и ожидаемой
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertEquals("28 сен 2012 в 16:14", driver.findElement(By.xpath("//*[@title='2012-09-28, 16:14']")).getText());

        // Скролл в низ страницы и переход в статьи
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//a[@href='/ru/articles/' and @class='footer-menu__item-link router-link-active']")).click();

        driver.quit();
    }

    @Test
    public void TestMail() {

        System.setProperty("webdriver.chrome.driver","C:\\Users\\PC-55\\IdeaProjects\\TestProject\\drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        // Переход на сайт
        driver.get("https://mail.ru/");
        driver.manage().window().setSize(new Dimension(1280, 1025));

        // Поиск кнопки входа
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//button[contains(@class, 'resplash-btn resplash-btn_primary resplash-btn_mailbox-big')]")).click();

        // Форма входа подключается в iframe поэтому нужно перевести контекст туда
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']")));

        // Ввод логина
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='username']")), driver.switchTo().activeElement());
        driver.findElement(By.xpath("//input[@name='username']")).clear();
        // На почте, которая в лабе кто-то сменил пароль. Использую свою
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("professionaltester1");

        // Переход к вводу пароля
        driver.findElement(By.xpath("//button[@data-test-id='next-button']")).click();

        // Ввод пароля
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //Assert.assertEquals(driver.findElement(By.xpath("//input[@name='password']")), driver.switchTo().activeElement());
        driver.findElement(By.xpath("//*[@name='password']")).clear();
        driver.findElement(By.xpath("//*[@name='password']")).sendKeys("toptester123");

        // Вход в аккаунт
        driver.findElement(By.xpath("//button[@data-test-id='submit-button']")).click();

        // Ждем загрузки нужного элемента
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> driver.findElement(By.xpath("//*[contains(@class, 'ph-auth')]")).isDisplayed());

        // Проверка соответсвия пользователя ожидаемому
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//*[contains(@class, 'ph-auth')]")).click();
        Assert.assertEquals("Тестовый Тестич", driver.findElement(By.xpath("//*[contains(@class, 'ph-name')]")).getText());

        // Выйдем из профиля
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти')]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@class, 'footer_logged-out')]")).isDisplayed());

        driver.quit();
    }

}
