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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class FifthTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public List<String> countriesWithGeoZonesList;
    public List<String> countriesCodesWithGeoZonesList;
    public List<String> zonesList;


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test //Задание 8.a Сценарий проверяет, что страны расположены в алфавитном порядке
    public void checkCountriesSortedListTest() {
        loginMethod();
        driver.get("http://localhost:8080/litecart/admin/?app=countries&doc=countries");
        List<WebElement> countries = driver.findElements(By.cssSelector("table.dataTable tr.row td:nth-child(5) a"));
        countriesWithGeoZonesList = new ArrayList<>();
        for (WebElement country : countries) {
            String countryName = country.getAttribute("textContent");
            countriesWithGeoZonesList.add(countryName);
        }
        List<String> sortedCountriesList = countriesWithGeoZonesList.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(countriesWithGeoZonesList, sortedCountriesList);
    }

    @Test //Задание 8.б Сценарий проверяет, что для тех стран, у которых количество зон отлично от нуля -- открывает страницу этой страны и там проверяет,
    // что геозоны расположены в алфавитном порядке
    public void checkCountriesGeoZonesSortedListTest() {
        loginMethod();
        driver.get("http://localhost:8080/litecart/admin/?app=countries&doc=countries");
        List<WebElement> rows = driver.findElements(By.cssSelector("table.dataTable tr.row"));
        countriesCodesWithGeoZonesList = new ArrayList<>();
        for (WebElement row : rows) {
            Assert.assertTrue(row.isDisplayed());
            if (!Objects.equals(row.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent"), "0")) {
                String countryName = row.findElement(By.cssSelector("td:nth-child(4)")).getAttribute("textContent");
                countriesCodesWithGeoZonesList.add(countryName);
            }
        }
        for (String countryCodeWithGeoZones : countriesCodesWithGeoZonesList) {
            driver.get("http://localhost:8080/litecart/admin/?app=countries&doc=edit_country&country_code=" + countryCodeWithGeoZones);
            List<WebElement> zones = driver.findElements(By.cssSelector("table.dataTable tr:not(.header) td:nth-child(3) input:not([type='text'])"));
            zonesList = new ArrayList<>();
            for (WebElement zone : zones) {
                String zoneName = zone.findElement(By.xpath("..")).getAttribute("textContent");
                zonesList.add(zoneName);
            }
            List<String> sortedZonesList = zonesList.stream().sorted().collect(Collectors.toList());
            Assert.assertEquals(zonesList, sortedZonesList);
        }
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
