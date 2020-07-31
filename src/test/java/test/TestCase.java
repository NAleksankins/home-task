package test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase extends TestBase {

    static final String TEST_AUTOMATION_ENGINEER = "Test Automation Engineer";

    @Test
    public void skillsOnPageTest() {
        driver.findElement(menuBtn)
                .click();

        waitForElementToBeVisible(vacanciesBtn, 2)
                .click();

        openSpecificVacancy(TEST_AUTOMATION_ENGINEER);

        Assert.assertEquals(getSkillsNumber(), 5, "Actual amount of skills is wrong");
    }
}