package com.projectx.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage {

    WebDriver driver;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Edit your account information")
    WebElement editYourAccountInformationOption;

    //Actions
    public boolean getDisplayOptionOfEditYourAccountInformationOption() {
        boolean displayStatus = editYourAccountInformationOption.isDisplayed();
        return displayStatus;
    }

}
