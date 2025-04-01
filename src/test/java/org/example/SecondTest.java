package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.Driver;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SecondTest {
    private WebDriver driver;
    public WebDriverWait wait;


    @Before
    public void start() {
//        driver = new ChromeDriver();
//        driver = new InternetExplorerDriver();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));

//        WebDriver chromeDriver = new ChromeDriver();
//        WebDriver ieDriver = new InternetExplorerDriver();
//        WebDriver firefoxDriver = new FirefoxDriver();
    }

    @Test
    public void mySecondTest() {
        driver.get("http://localhost:8080/litecart/admin/");
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
