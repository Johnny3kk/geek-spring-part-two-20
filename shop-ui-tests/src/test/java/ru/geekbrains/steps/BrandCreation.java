package ru.geekbrains.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.geekbrains.DriverInitializer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandCreation {

    private WebDriver webDriver = null;

    @Given("^I reopen web browser$")
    public void iOpenFFBrowser() throws Throwable {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to brands page$")
    public void iNavigateToBrands() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("brands.url"));
    }

    @When("^I login, providing username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void iProvideUsernameAsAndPasswordAs(String username, String password) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("inp-username"));
        webElement.sendKeys(username);
        Thread.sleep(2000);
        webElement = webDriver.findElement(By.id("inp-password"));
        webElement.sendKeys(password);
        Thread.sleep(2000);
    }

    @When("^I click on login$")
    public void iClickOnLogin() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-login"));
        webElement.click();
    }

    @When("^I click create new button$")
    public void iMoveToCreateNewForm() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("new brand"));
        webElement.click();
    }

    @When("^i enter brand \"([^\"]*)\"$")
    public void iEnterBrandName(String name) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(name);
        Thread.sleep(2000);
    }

    @When("^i click submit button$")
    public void iClickSubmit() {
        WebElement webElement = webDriver.findElement(By.id("submit"));
        webElement.click();
    }

    @Then("^New brand with \"([^\"]*)\" should be in list showing on the brands page$")
    public void isNewBrandCreated(String name) {
        List<WebElement> webElements = webDriver.findElements(By.xpath("/html/body/div/div[2]/div/div/table"));
        String foundName = "test";

        for (int i = 1; i <= webElements.size(); i++) {
            String addedName = webElements.get(i).findElement(By.xpath("/html/body/div/div[2]/div/div/table/tbody/tr[i]/td[1]")).getText();
            if (addedName.equals(name)) {
                foundName = addedName;
                break;
            }
        }

        assertThat(foundName.equals(name));
    }

    @Given("^Close web browser again$")
    public void quitFFBrowser() {
        webDriver.quit();
    }

}
