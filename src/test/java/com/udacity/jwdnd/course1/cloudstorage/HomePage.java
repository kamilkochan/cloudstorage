package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "newNoteButton")
    private WebElement newNoteButton;

    @FindBy(id = "noteSave")
    private WebElement noteSave;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(className = "homeNoteTitle")
    private WebElement homeNoteTitle;

    @FindBy(className = "homeNoteDescription")
    private WebElement homeNoteDescription;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "newCredentialButton")
    private WebElement newCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialURLInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(id = "saveCredentialButton")
    private WebElement saveCredentialButton;

    @FindBy(className = "credentialURL")
    private WebElement credentialURL;

    @FindBy(className = "credentialUsername")
    private WebElement credentialUsername;

    @FindBy(className = "credentialPassword")
    private WebElement credentialPassword;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(id = "deleteCredentialButton")
    private WebElement deleteCredentialButton;



    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver,this);

    }

    public void createNote(String title,String desc)throws InterruptedException{
        Thread.sleep(1000);
        notesTab.click();
        Thread.sleep(1000);
        newNoteButton.click();
        Thread.sleep(1000);
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(desc);
        noteSave.click();
    }

    public void editNote(String editedDescription)throws InterruptedException{
        Thread.sleep(1000);
        editNoteButton.click();
        Thread.sleep(1000);
        noteDescription.clear();
        noteDescription.sendKeys(editedDescription);
        noteSave.click();
    }

    public void deleteNote()throws InterruptedException{
        Thread.sleep(1000);
        deleteNoteButton.click();
    }


    public Note getNote(){
        try {
            Note note = new Note();
            note.setNoteTitle(homeNoteTitle.getText());
            note.setNoteDescription(homeNoteDescription.getText());
            return note;
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public void  createCredential(String url,String username,String pwd) throws InterruptedException{
        Thread.sleep(1000);
        navCredentialsTab.click();
        Thread.sleep(1000);
        newCredentialButton.click();
        Thread.sleep(1000);
        credentialURLInput.sendKeys(url);
        credentialUsernameInput.sendKeys(username);
        credentialPasswordInput.sendKeys(pwd);
        saveCredentialButton.click();
    }
    public Credential getCredential(WebElement row){
        Credential credential = new Credential();
        credentialURL = row.findElement(By.className("credentialURL"));
        credentialUsername = row.findElement(By.className("credentialUsername"));
        credentialPassword = row.findElement(By.className("credentialPassword"));
        credential.setUrl(credentialURL.getText());
        credential.setUsername(credentialUsername.getText());
        credential.setPassword(credentialPassword.getText());
            return credential;
    }

    public String getDecryptedPassword(WebElement row) throws InterruptedException{
        Thread.sleep(1000);
        row.findElement(By.id("editCredentialButton")).click();
        return credentialPasswordInput.getAttribute("value");
    }

    public void editCredentialUsername(String username)throws InterruptedException{
        Thread.sleep(1000);
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(username);
        saveCredentialButton.click();
    }

    public void deleteCredential(WebElement row)throws InterruptedException{
        Thread.sleep(1000);
        row.findElement(By.id("deleteCredentialButton")).click();
    }

    public WebElement getCredentialTableRow(int position) {
        try {
            WebElement body = credentialTable.findElement(By.tagName("tbody"));
            if (body != null) {
                List<WebElement> rows = body.findElements(By.tagName("tr"));
                if (rows != null && !rows.isEmpty() && rows.size()>=position+1) {
                    return rows.get(position);
                }
            return null;
            }


        } catch (NoSuchElementException e) {
                System.out.println("Element not found!");
        }
        return null;
    }

}
