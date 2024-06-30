package com._6bitcampers.nangman_doctor.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class SeleniumCrawlerService {

    public String getHospitalUrl(String hospitalName) {
        // Use Selenium Manager to automatically manage the WebDriver binaries
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode if needed
        WebDriver driver = new ChromeDriver(options);
        String hospitalUrl = "";

        try {
            driver.get("https://www.naver.com");

            WebElement searchBox = driver.findElement(By.name("query"));
            searchBox.sendKeys(hospitalName);
            searchBox.submit();

            WebElement result = driver.findElement(By.cssSelector("div.source_box a.txt"));
            hospitalUrl = result.getAttribute("href");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return hospitalUrl;
    }
}
