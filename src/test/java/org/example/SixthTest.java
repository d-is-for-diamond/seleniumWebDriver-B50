package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textMatches;

public class SixthTest {
    public WebDriver driver;
    public WebDriverWait wait;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test //Задание 11. Сценарий регистрации пользователя
    public void checkCountriesSortedListTest() {
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

    @After
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }

    private void loginMethod() {
        driver.get("http://localhost:8080/litecart/admin/");
        driver.findElement(By.cssSelector("div.content tbody tr:first-child span.input-wrapper .fa.fa-user")).click();
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.content tbody tr:nth-child(2) span.input-wrapper .fa.fa-key")).click();
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.footer button[type='submit']")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("a[title='Logout']")));
        wait.until(presenceOfElementLocated(By.cssSelector("div.notice.success")));
    }
}
