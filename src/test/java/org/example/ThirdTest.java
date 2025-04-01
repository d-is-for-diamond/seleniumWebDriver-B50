package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ThirdTest {
    public WebDriver driver;
    public WebDriverWait wait;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test //Задание 6. Сценарий, проходящий по всем разделам админки
    public void myThirdTest() {
        driver.get("http://localhost:8080/litecart/admin/");
        driver.findElement(By.cssSelector("div.content tbody tr:first-child span.input-wrapper .fa.fa-user")).click();
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.content tbody tr:nth-child(2) span.input-wrapper .fa.fa-key")).click();
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.footer button[type='submit']")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("a[title='Logout']")));
        wait.until(presenceOfElementLocated(By.cssSelector("div.notice.success")));
        int mainElements = driver.findElements(By.cssSelector(".list-vertical li[id='app-']")).size();
        for (int i = 1; i <= mainElements; i++) {//48 элементов всего, из них 17 элементов первого уровня
            driver.findElement(By.cssSelector(".list-vertical li[id='app-']:nth-child(" + i + ")>a")).click();
            wait.until(presenceOfElementLocated(By.cssSelector("h1")));
            if (!driver.findElements(By.cssSelector("ul.docs")).isEmpty()) {
                int subElementsSize = driver.findElements(By.cssSelector("ul.docs>li")).size();
                for (int j = 2; j <= subElementsSize; j++) {
                    driver.findElement(By.cssSelector("ul.docs li:nth-child(" + j + ") a")).click();
                    wait.until(presenceOfElementLocated(By.cssSelector("h1")));
                }
            }
        }

    }

    @After
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}
