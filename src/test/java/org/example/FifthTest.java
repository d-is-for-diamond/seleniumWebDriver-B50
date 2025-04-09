package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
    public List<String> countryGeoZonesLinks;
    public List<String> zonesList;


    @Before
    public void start() {
//        driver = new FirefoxDriver();
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
        countryGeoZonesLinks = new ArrayList<>();
        for (WebElement row : rows) {
            Assert.assertTrue(row.isDisplayed());
            if (!Objects.equals(row.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent"), "0")) {
                String countryName = row.findElement(By.cssSelector("td:nth-child(4)")).getAttribute("textContent");
                countryGeoZonesLinks.add(countryName);
            }
        }
        for (String countryCodeWithGeoZones : countryGeoZonesLinks) {
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

    @Test //Задание 9 Проверить сортировку геозон на странице геозон
    public void checkGeoZonesSortedListTest() {
        loginMethod();
        driver.get("http://localhost:8080/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> rows = driver.findElements(By.cssSelector("tr.row td:nth-child(3) a"));
        countryGeoZonesLinks = new ArrayList<>();
        for (WebElement row : rows) {
            String link = row.getAttribute("href");
                countryGeoZonesLinks.add(link);
        }
        for (String countryGeoZonesLink : countryGeoZonesLinks) {
            driver.get(countryGeoZonesLink);
            List<WebElement> zones = driver.findElements(By.cssSelector("table.dataTable tr td:nth-child(3) option[selected='selected']"));
            zonesList = new ArrayList<>();
            for (WebElement zone : zones) {
                String zoneName = zone.getAttribute("textContent");
                zonesList.add(zoneName);
            }
            List<String> sortedZonesList = zonesList.stream().sorted().collect(Collectors.toList());
            Assert.assertEquals(zonesList, sortedZonesList);
        }
    }

    @Test //Задание 10 Проверить, что открывается правильная страница товара
    public void checkCorrectProductPageTest() {
        driver.get("http://localhost:8080/litecart/en/");
        WebElement product = driver.findElement(By.cssSelector("div.box[id='box-campaigns'] a.link"));
        String name = product.findElement(By.cssSelector("div.name")).getAttribute("textContent");

        WebElement regularPrice = product.findElement(By.cssSelector("div.price-wrapper .regular-price"));
        String regularPriceValue = regularPrice.getAttribute("textContent");
        regularPriceValue = regularPriceValue.replaceAll("\\$","");
        int regularPriceValueInt = Integer.parseInt(regularPriceValue);

        String regularPriceColor = regularPrice.getCssValue("color");
        String regularPriceTextDecoration = regularPrice.getCssValue("text-decoration-line");

        WebElement campaignPrice = product.findElement(By.cssSelector("div.price-wrapper .campaign-price"));
        String campaignPriceValue = campaignPrice.getAttribute("textContent");
        campaignPriceValue = campaignPriceValue.replaceAll("\\$","");
        int campaignPriceValueInt = Integer.parseInt(campaignPriceValue);
        String campaignPriceColor = campaignPrice.getCssValue("color");
        String campaignPriceFontWeight = campaignPrice.getCssValue("font-weight");

        product.click();
        wait.until(presenceOfElementLocated(By.cssSelector("div.content div[id='box-product']")));
        WebElement regularPriceProductPage = driver.findElement(By.cssSelector("div.price-wrapper .regular-price"));
        WebElement campaignPriceProductPage = driver.findElement(By.cssSelector("div.price-wrapper .campaign-price"));

        //а. на главной странице и на странице товара совпадает текст названия товара
        Assert.assertEquals(name, driver.findElement(By.cssSelector("h1.title")).getAttribute("textContent"));

        //б. на главной странице и на странице товара совпадают цены (обычная и акционная)
        Assert.assertEquals(regularPriceValue, regularPriceProductPage.getAttribute("textContent").replaceAll("\\$",""));
        Assert.assertEquals(campaignPriceValue, campaignPriceProductPage.getAttribute("textContent").replaceAll("\\$",""));

        //в. обычная цена зачёркнутая и серая
        Assert.assertEquals("line-through", regularPriceTextDecoration);
        Assert.assertEquals("line-through", regularPriceProductPage.getCssValue("text-decoration-line"));

        regularPriceColor = regularPriceColor.replaceAll("rgba\\(","");
        regularPriceColor = regularPriceColor.replaceAll("rgb\\(","");
        regularPriceColor = regularPriceColor.replaceAll("\\)","");
        String[] regularPriceRgbaSplit = regularPriceColor.split(", ");
        String r = regularPriceRgbaSplit[0];
        String g = regularPriceRgbaSplit[1];
        String b = regularPriceRgbaSplit[2];
        Assert.assertTrue(Objects.equals(r, g) && Objects.equals(r, b));

        String regularPriceProductPageColor = regularPriceProductPage.getCssValue("color").replaceAll("rgba\\(","");
        regularPriceProductPageColor = regularPriceProductPageColor.replaceAll("rgb\\(","");
        regularPriceProductPageColor = regularPriceProductPageColor.replaceAll("\\)","");
        String[] regularPriceProductPageRgbaSplit = regularPriceProductPageColor.split(", ");
        r = regularPriceProductPageRgbaSplit[0];
        g = regularPriceProductPageRgbaSplit[1];
        b = regularPriceProductPageRgbaSplit[2];
        Assert.assertTrue(Objects.equals(r, g) && Objects.equals(r, b));

        //г. акционная цена жирная и красная
        Assert.assertEquals("900", campaignPriceFontWeight);
        Assert.assertEquals("700", campaignPriceProductPage.getCssValue("font-weight"));

        String campaignColor = campaignPriceColor.replaceAll("rgba\\(","");
        campaignColor = campaignColor.replaceAll("rgb\\(","");
        campaignColor = campaignColor.replaceAll("\\)","");
        String[] campaignPriceRgbaSplit = campaignColor.split(", ");
        g = campaignPriceRgbaSplit[1];
        b = campaignPriceRgbaSplit[2];
        Assert.assertTrue(Objects.equals(g, "0") && Objects.equals(b, "0"));

        String campaignPriceProductPageColor = campaignPriceProductPage.getCssValue("color").replaceAll("rgba\\(","");
        campaignPriceProductPageColor = campaignPriceProductPageColor.replaceAll("rgb\\(","");
        campaignPriceProductPageColor = campaignPriceProductPageColor.replaceAll("\\)","");
        String[] campaignPriceProductPageRgbaSplit = campaignPriceProductPageColor.split(", ");
        g = campaignPriceProductPageRgbaSplit[1];
        b = campaignPriceProductPageRgbaSplit[2];
        Assert.assertTrue(Objects.equals(g, "0") && Objects.equals(b, "0"));


        //д. акционная цена крупнее, чем обычная
        Assert.assertTrue(regularPriceValueInt>campaignPriceValueInt);
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
