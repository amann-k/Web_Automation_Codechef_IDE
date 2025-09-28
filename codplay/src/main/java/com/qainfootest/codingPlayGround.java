package com.qainfootest;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class codingPlayGround {
    public static void main(String[] args)  throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\amans\\projects\\javasel2\\codplay\\src\\test\\resources\\driver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.codechef.com/ide");
        Thread.sleep(10000);
        changeLanguage(driver, "JavaScript");
        appendCodeToAce(driver, "submit-ide-v2", "System.out.println(\"Hello World\");");
        changeLanguage(driver, "SQL");
        appendCodeToAce(driver, "submit-ide-v2", "SELECT * FROM users;");
        Thread.sleep(5000);
        driver.quit();
    }

    public static void changeLanguage(WebDriver driver, String language) throws InterruptedException {

        // Click the language dropdown
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement langDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("language-select"))));
        langDropdown.click();

        Thread.sleep(3000); // Wait for the dropdown to populate

        // Find the language option dynamically and scroll to it
        System.out.println("Selecting language: " + language);
        WebElement languageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath(("//ul[@class='MuiList-root MuiList-padding MuiMenu-list css-ubifyk']//li[text()='"+ language +"']"))
        ));
        languageElement.click();
        System.out.println("Language changed to: " + language);
        Thread.sleep(3000); // Wait for the selection to take effect
    }

    public static void appendCodeToAce(WebDriver driver, String editorId, String code) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Click visible area to focus
        WebElement editorContent = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("#" + editorId + " .ace_content")));
        editorContent.click();

        // Step 2: Locate the hidden textarea
        WebElement aceInput = driver.findElement(
            By.cssSelector("#" + editorId + " .ace_text-input"));

        // Step 3: Move cursor to end and type
        aceInput.sendKeys(Keys.chord(Keys.CONTROL, Keys.END));
        aceInput.sendKeys(Keys.ENTER + code);
    }
}