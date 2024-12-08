package com.searchingpro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleEngine {
    // Constants for CSS selectors
    private final String INPUT_SEARCH_GOOGLE = "#APjFqb";
    private final String VALID_SEARCH_BTN = "input.gNO89b";
    private final String FIRST_PAGE_SEARCH = "#rso .yuRUbf a";
    private final List<String> SOCIAL_DOMAINS = Arrays.asList(
            "facebook.com", "twitter.com", "x.com", "linkedin.com", "instagram.com"
    );

    private WebDriver driver;

    // Set up WebDriver
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/FRAFA-PC/automation/selenium-billing/driver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // Perform a Google search
    public void search(String query) {
        driver.get("https://google.com");
        WebElement searchBox = driver.findElement(By.cssSelector(INPUT_SEARCH_GOOGLE));
        searchBox.sendKeys(query);
        searchBox.sendKeys(Keys.RETURN);

        // WebElement searchButton = driver.findElement(By.cssSelector(VALID_SEARCH_BTN));
        // searchButton.click();
    }

    // Select the first result on the search page
    public void selectFirstResult() {
        List<WebElement> results = driver.findElements(By.cssSelector(FIRST_PAGE_SEARCH));
        if (!results.isEmpty()) {
            results.get(0).click();
        } else {
            System.out.println("No search results found.");
        }
    }

    // Track and print links leading to social media domains
    public void trackSocialLinks() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        List<String> socialLinks = new ArrayList<>();

        for (WebElement link : links) {
            @SuppressWarnings("deprecation")
            String url = link.getAttribute("href");
            if (url != null) {
                for (String domain : SOCIAL_DOMAINS) {
                    if (url.contains(domain)) {
                        socialLinks.add(url);
                        break;
                    }
                }
            }
        }

        if (!socialLinks.isEmpty()) {
            System.out.println("Social media links found:");
            socialLinks.forEach(System.out::println);
        } else {
            System.out.println("No social media links found on this page.");
        }
    }

    // Close the WebDriver
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        GoogleEngine googleEngine = new GoogleEngine();

        try {
            googleEngine.setup();
            googleEngine.search("jumia");
            googleEngine.selectFirstResult();
            googleEngine.trackSocialLinks();
        } finally {
            googleEngine.tearDown();
        }
    }
}
