package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(id="success-home-note-link")
    private WebElement successHomeNoteLink;

    @FindBy(id="credential-success-home-link")
    private WebElement successHomeCredentialLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void noteLinkSuccess(){
        successHomeNoteLink.click();
    }

    public void credentialLinkSuccess(){
        successHomeCredentialLink.click();
    }


}
