package com.qainfootest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


import java.net.MalformedURLException;

public class CodingPlaygroundTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 15);
    }

    @Test
    public void testLanguageSwitchAndCodeEntry() throws InterruptedException {
        driver.get("https://www.codechef.com/ide");

        changeLanguage("JavaScript");
        Thread.sleep(2000); // Wait for the editor to update
        appendCodeToAce("submit-ide-v2", "function whatDoesFilteredDo(){ return \"Filtered Rocks!!!\"; }\nconsole.log(whatDoesFilteredDo());");
        Thread.sleep(2000); // Wait for the code to be entered
        changeLanguage("Python3");
        Thread.sleep(2000); // Wait for the editor to update
        appendCodeToAce("submit-ide-v2", "def whatDoesFilteredDo():\n    return \"Filtered Rocks!!!\"\n\nprint(whatDoesFilteredDo())");
        Thread.sleep(2000); // Wait for the code to be entered
    }

    private void changeLanguage(String language) {
        WebElement langDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("language-select")));
        langDropdown.click();

        WebElement languageElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(@class,'MuiMenuItem-root') and text()='" + language + "']")));
        languageElement.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("language-select"), language));
        System.out.println("Language changed to: " + language);
    }

    private void appendCodeToAce(String editorId, String code) {
        WebElement editorContent = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#" + editorId + " .ace_content")));
        editorContent.click();

        WebElement aceInput = driver.findElement(By.cssSelector("#" + editorId + " .ace_text-input"));

        aceInput.sendKeys(Keys.CONTROL + "a");
        aceInput.sendKeys(Keys.DELETE);
        aceInput.sendKeys(code);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
