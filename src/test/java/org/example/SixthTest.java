package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.Random;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SixthTest {
    public WebDriver driver;
    public WebDriverWait wait;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test //Задание 11. Сценарий регистрации пользователя
    public void registerAndLoginScenario() {
        String characterSet = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomWord = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(characterSet.length());
            randomWord.append(characterSet.charAt(index));
        }
        String email = randomWord + "@" + randomWord;
        String psswrd = "password";
        driver.get("http://localhost:80/litecart/en/");

        driver.findElement(By.cssSelector("td>a[href='http://localhost/litecart/en/create_account']")).click();
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys("First");
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys("Last");
        driver.findElement(By.cssSelector("input[name='address1']")).sendKeys("address1");
        driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("City");
        driver.findElement(By.cssSelector("span.select2")).click();
        driver.findElement(By.xpath("//li[text()='United States']")).click();
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+11111111");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(psswrd);
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys(psswrd);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.findElement(By.cssSelector("div[id='box-account'] a[href='http://localhost/litecart/en/logout']")).click();
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(psswrd);
        driver.findElement(By.cssSelector("button[name='login']")).click();
        driver.findElement(By.cssSelector("div[id='box-account'] a[href='http://localhost/litecart/en/logout']")).click();
    }

    @Test //Задание 12. Сценарий добавления товара
    public void addToCartScenario() {
        loginMethod();

        driver.findElement(By.cssSelector("a[href='http://localhost/litecart/admin/?app=catalog&doc=catalog']")).click();
        driver.findElement(By.cssSelector("a[href='http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product']")).click();
        String name = "newProductName";
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys(name);
        driver.findElement(By.cssSelector("input[name='code']")).sendKeys("Code");
        driver.findElement(By.cssSelector("input[type='checkbox'][value='1-1']")).click();
        driver.findElement(By.cssSelector("input[name='quantity']")).sendKeys("20");
        driver.findElement(By.cssSelector("input[type='date'][name='date_valid_from']")).click();
        driver.findElement(By.cssSelector("input[type='date'][name='date_valid_from']")).sendKeys("14042025");
        driver.findElement(By.cssSelector("input[type='date'][name='date_valid_to']")).click();
        driver.findElement(By.cssSelector("input[type='date'][name='date_valid_to']")).sendKeys("14042025");

        //блок с загрузкой изображения
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\org\\example\\beautiful-lake-5.jpg";
        driver.findElement(By.cssSelector("input[type='file'][name='new_images[]'][type='file'][name='new_images[]']")).sendKeys(new File(filePath).getAbsolutePath());

        driver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("select[name='manufacturer_id']")));
        driver.findElement(By.cssSelector("select[name='manufacturer_id']")).click();
        driver.findElement(By.xpath("//*[text()='ACME Corp.']")).click();
        driver.findElement(By.cssSelector("input[type='text'][name='keywords']")).sendKeys("Keyword");
        driver.findElement(By.cssSelector("input[type='text'][name='short_description[en]']")).sendKeys("Short Description");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Description");
        driver.findElement(By.cssSelector("input[type='text'][name='head_title[en]']")).sendKeys("Head Title");
        driver.findElement(By.cssSelector("input[type='text'][name='meta_description[en]']")).sendKeys("Meta Description");

        driver.findElement(By.cssSelector("a[href='#tab-data']")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("input[type='text'][name='sku']")));
        driver.findElement(By.cssSelector("input[type='text'][name='sku']")).sendKeys("SKU");
        driver.findElement(By.cssSelector("input[type='text'][name='gtin']")).sendKeys("GTIN");
        driver.findElement(By.cssSelector("input[type='text'][name='taric']")).sendKeys("TARIC");
        driver.findElement(By.cssSelector("input[type='number'][name='weight']")).sendKeys("20");
        driver.findElement(By.cssSelector("input[type='number'][name='dim_x']")).sendKeys("20");
        driver.findElement(By.cssSelector("input[type='number'][name='dim_y']")).sendKeys("20");
        driver.findElement(By.cssSelector("input[type='number'][name='dim_z']")).sendKeys("20");
        driver.findElement(By.cssSelector("textarea[name='attributes[en]']")).sendKeys("Attribute");
        driver.findElement(By.cssSelector("button[type='submit'][name='save']")).click();
        presenceOfElementLocated(By.xpath("//a[text()='" + name + "']"));
    }

    @After
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }

    private void loginMethod() {
        driver.get("http://localhost:80/litecart/admin/");
        driver.findElement(By.cssSelector("div.content tbody tr:first-child span.input-wrapper .fa.fa-user")).click();
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.content tbody tr:nth-child(2) span.input-wrapper .fa.fa-key")).click();
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.footer button[type='submit']")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("a[title='Logout']")));
        wait.until(presenceOfElementLocated(By.cssSelector("div.notice.success")));
    }
}
