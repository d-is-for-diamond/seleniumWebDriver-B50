package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class EighthTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public String newWindow;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(15000));
    }

    @Test //14. Открытие ссылок в новом окне
    public void addAndDeleteItemsFromCartTest() {
        loginMethod();
        driver.get("http://localhost:80/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("a[href='http://localhost/litecart/admin/?app=countries&doc=edit_country']")).click();
        List<WebElement> links = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
        for (int i = 0; i < links.size(); i++) {
            String oldWindow = driver.getWindowHandle();
            links.get(i).click();
            wait.until(numberOfWindowsToBe(2));
            // записывает в переменную newWindow id новой вкладки
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!Objects.equals(window, oldWindow)) {newWindow = window;}
            }
            driver.switchTo().window(newWindow);
            WebElement new_page = driver.findElement(By.tagName("html"));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            driver.close();
            driver.switchTo().window(oldWindow);
        }
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

    @After
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}
