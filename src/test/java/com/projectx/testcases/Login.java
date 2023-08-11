package com.projectx.testcases;

import base.Base;
import com.projectx.pageobjects.AccountPage;
import com.projectx.pageobjects.HomePage;
import com.projectx.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.projectx.utils.Utilities.generateTimeStamp;
import static com.projectx.utils.Utilities.getTestDataFromExcel;

public class Login extends Base {

    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    AccountPage accountPage;

    public Login() throws IOException {

    }

    @BeforeMethod
    public void preSetup() throws IOException {
        driver = initilizeBrowserAndOpenApp(properties.getProperty("browserName"));
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        homePage.clickOnMyAccount();
        homePage.selectLoginOption();
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyLoginWithValidCredentialsPropData() {
        loginPage.sendKeysEmail(properties.getProperty("validEmail"));
        loginPage.sendKeysPassword(properties.getProperty("validPassword"));
        loginPage.clickOnLoginButton();
        Assert.assertTrue(accountPage.getDisplayOptionOfEditYourAccountInformationOption());
    }

    @Test(dataProvider = "validCredsDataProvider")
    public void verifyLoginWithValidCredentialsDataProvider(String email, String password) {
        loginPage.sendKeysEmail(email);
        loginPage.sendKeysPassword(password);
        loginPage.clickOnLoginButton();
        Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed());
    }

    //Excel data provider
    @DataProvider(name = "validCredsDataProvider")
    public Object[][] feedTestData() throws IOException {
        Object[][] data = getTestDataFromExcel(dataProperties.getProperty("testSheetName"));
        return data;
    }

    @Test
    public void verifyLoginWithInValidCredentials() {
        loginPage.sendKeysEmail("test" + generateTimeStamp() + "@gmail.com1");
        loginPage.sendKeysPassword(dataProperties.getProperty("invalidPassword"));
        loginPage.clickOnLoginButton();
        String actualEmailWarningMessage = loginPage.emailWarningMessageOnLoginPageGetText();
        Assert.assertTrue(true, actualEmailWarningMessage);
        String expectedWarningNoEmailpass = dataProperties.getProperty("expectedWarningNoEmailpass");
        Assert.assertTrue(actualEmailWarningMessage.contains(expectedWarningNoEmailpass), "No Expected Warning Message is Displayed");
    }

    @Test
    public void verifyLoginWithoutCredentials() {
        loginPage.clickOnLoginButton();
        String actualEmailWarningMessage = loginPage.emailWarningMessageOnLoginPageGetText();
        String expectedWarningNoEmailpass = dataProperties.getProperty("expectedWarningNoEmailpass");
        Assert.assertTrue(actualEmailWarningMessage.contains(expectedWarningNoEmailpass), "No Expected Warning Message is Displayed");
    }


}
