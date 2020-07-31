package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;

public class TestBase {

    protected WebDriver driver;
    protected static final String url = "https://ctco.lv/en";

    By menuBtn = By.id("menu-item-127");
    By vacanciesBtn = By.cssSelector(".show-tablet .vacancies-item");
    By vacanciesTable = By.className("table-display");
    By vacanceSelector = By.cssSelector("#menu-main-1 .menu-item a");
    By jobPostingWrapper = By.className("vacancies-second-contents-wrap");
    By skillTextBlock = By.cssSelector(".vacancies-second-contents .text-block");

    @BeforeClass
    public void prepare() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        this.driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    public void openSpecificVacancy(String vacancyName) {
        waitForElementToBeVisible(vacanciesTable, 2);
        List<WebElement> vacancies = driver.findElements(vacanceSelector);
        vacancies.stream()
                .filter(vacancy -> vacancy.getText().equals(vacancyName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Vacancy: " + vacancyName + " not found"))
                .click();
    }

    public int getSkillsNumber() {
        waitForElementToBeVisible(jobPostingWrapper, 4);
        WebElement vacancyText = driver
                .findElements(skillTextBlock)
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No vacancy text visible"));

        List<WebElement> allParagraphs = vacancyText.findElements(By.tagName("p"));

        for (int i = 0; i < allParagraphs.size(); i++) {
            if (allParagraphs.get(i).getText().equals("Professional skills and qualification:")) {
                WebElement skillsParagraph = allParagraphs.get(i + 1);
                return skillsParagraph.findElements(By.tagName("br")).size() + 1;
            }
        }
        return 0;
    }

    protected WebElement waitForElementToBeVisible(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
