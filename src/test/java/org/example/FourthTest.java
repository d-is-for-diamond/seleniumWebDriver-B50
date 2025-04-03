package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FourthTest {
    public WebDriver driver;
    public WebDriverWait wait;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test //Задание 7. Сценарий, проверяющий наличие стикеров у товаров
    public void myThirdTest() {
        driver.get("http://localhost:8080/litecart/en/rubber-ducks-c-1/");
        List<WebElement> products = driver.findElements(By.cssSelector(".listing-wrapper.products li.product"));
        for (WebElement product : products) {
            Assert.assertEquals(1, product.findElements(By.cssSelector("a div.sticker")).size());
        }
    }

    @After
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}
