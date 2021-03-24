package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private ResultPage resultPage;
	public  String baseURL;
	private String username = "testuser";
	private String password = "qwerty";
	private static WebDriver driver;
	private CredentialService credentialService;


	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public  void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@AfterAll
	public static void afterAll(){
		driver.quit();
		driver = null;
	}

	@Test
	public void testUnauthorizedUser() {
		//Test if unauthorized user can access the login and signup pages
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		//If Unauthorized user enters home or result page he should be redirected to login page
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginLogout() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 500);
		signupUser();
		loginUser();
		//verify that user was redirected to home page
		Assertions.assertEquals("Home", driver.getTitle());
        //logout
		Thread.sleep(1000);  //works only with sleep. I don't know why WebDriverWait does not work.
		WebElement logoutButton = wait.until(webDriver  -> webDriver.findElement(By.id("logout-button")));
		logoutButton.click();
		Assertions.assertEquals("Login", driver.getTitle());
		//verify that the home page is no longer accessible
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testNoteCreateEditAndDelete() throws InterruptedException{
		signupUser();
		loginUser();
		resultPage = new ResultPage(driver);
		homePage = new HomePage(driver);
		//CREATE test
		String title = "Test title";
		String desc="Test desc";
		homePage.createNote(title,desc);
		Thread.sleep(1000);
		resultPage.noteLinkSuccess();
		Thread.sleep(1000);
		Note note = homePage.getNote();
		Assertions.assertEquals(title, note.getNoteTitle());
		Assertions.assertEquals(desc, note.getNoteDescription());

		//EDIT test
		String editedDescription = "Edited desc";
		homePage.editNote(editedDescription);
		Thread.sleep(1000);
		resultPage.noteLinkSuccess();
		Thread.sleep(1000);
		note = homePage.getNote();
		Assertions.assertEquals(editedDescription, note.getNoteDescription());

		//DELETE test
		homePage.deleteNote();
		Thread.sleep(1000);
		resultPage.noteLinkSuccess();
		Thread.sleep(1000);
		note = homePage.getNote();
		Assertions.assertNull(note);
	}

	@Test
	public void testNoteCredentialEditAndDelete() throws InterruptedException{
		signupUser();
		loginUser();
		String[] url={"URL1","URL2","URL3"};
		String[] credentialUsername = {"user1","user2","user3"};
		String[] pwd = {"pwd1","pwd2","pwd3"};
		int testPosition = 2;
		resultPage = new ResultPage(driver);
		homePage = new HomePage(driver);

		//CREATE test
		for(int i=0;i<url.length;i++) {
			homePage.createCredential(url[i], credentialUsername[i], pwd[i]);
			Thread.sleep(1000);
			resultPage.credentialLinkSuccess();
			Thread.sleep(1000);
		}
		WebElement row = homePage.getCredentialTableRow(testPosition);
		Credential credential = homePage.getCredential(row);
		Assertions.assertEquals(url[testPosition], credential.getUrl());
		Assertions.assertEquals(credentialUsername[testPosition], credential.getUsername());
		Assertions.assertNotEquals(pwd[testPosition], credential.getPassword());
		Thread.sleep(1000);

		//DecryptedPassword test
		Assertions.assertEquals(pwd[testPosition], homePage.getDecryptedPassword(row));
		String editedUsername = "EditedUser";

		//EDIT test
		homePage.editCredentialUsername(editedUsername);
		Thread.sleep(1000);
		resultPage.credentialLinkSuccess();
		Thread.sleep(1000);
		row= homePage.getCredentialTableRow(testPosition);
		Assertions.assertEquals(editedUsername, homePage.getCredential(row).getUsername());

		//DELETE test
		Thread.sleep(1000);
		homePage.deleteCredential(row);
		Thread.sleep(1000);
		resultPage.credentialLinkSuccess();
		Thread.sleep(1000);
		Assertions.assertNull(homePage.getCredentialTableRow(testPosition));



	}

	private void signupUser(){
		// signup new User
		driver.get(baseURL + "/signup");
		signupPage=new SignupPage(driver);
		signupPage.signup("Firstname","Lastname",username,password);

	}

	private void loginUser(){
		//login new user
		driver.get(baseURL + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login(username,password);
	}


}
