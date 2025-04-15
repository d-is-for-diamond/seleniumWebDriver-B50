package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SeventhTest {
    public WebDriver driver;
    public WebDriverWait wait;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test //13. Сделайте сценарий работы с корзиной
    public void addAndDeleteItemsFromCartTest() {
        driver.get("http://localhost:80/litecart/en/");
        for (int i = 0; i < 3; i++) {
            WebElement e = driver.findElement(By.cssSelector(".listing-wrapper.products li.product"));
            if (Objects.equals(e.findElement(By.cssSelector("div.name")).getAttribute("textContent"), "Yellow Duck")) {
                e.click();
                driver.findElement(By.cssSelector("select[name='options[Size]']")).click();
                driver.findElement(By.xpath("//*[text()='Small']")).click();
            } else {e.click();}
            int cartCounter = parseInt(driver.findElement(By.xpath("/html/body/div[1]/div/header/div[3]/div/a[2]/span[1]")).getAttribute("textContent"));
            driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
            int newCartCounter = cartCounter+1;
            wait.until(presenceOfElementLocated(By.xpath("/html/body/div[1]/div/header/div[3]/div/a[2]/span[1][text()='" + newCartCounter + "']")));
            driver.get("http://localhost:80/litecart/en/");
        }
        driver.findElement(By.xpath("//*[@id='cart']/a[3][text()='Checkout »']")).click();
        List<WebElement> elementsForDelete = driver.findElements(By.xpath("//*[text()='Remove']"));
        List<WebElement> listE = driver.findElements(By.cssSelector("table.dataTable tr:not(.header):not(.footer) td.item"));
        for (int i = elementsForDelete.size(); i > 0; i--) {
            driver.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
            wait.until(numberOfElementsToBe(By.cssSelector("button[name='remove_cart_item']"), i-1));
            int elementOrder = elementsForDelete.size()-1;
            wait.until(stalenessOf(listE.get(elementOrder)));
            wait.until(refreshed(stalenessOf(listE.get(elementOrder))));
        }
    }

    @After
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}
