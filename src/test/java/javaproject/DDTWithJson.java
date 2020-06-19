package javaproject;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonParser;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DDTWithJson {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(dataProvider="dp")
	public void login(String data) {
		String users[]=data.split(",");
		driver.get("https://app.hubspot.com/login/");
		driver.findElement(By.cssSelector("#username")).sendKeys(users[0]);
		driver.findElement(By.cssSelector("#password")).sendKeys(users[1]);
		driver.findElement(By.cssSelector("#loginBtn")).click();
		System.out.println(driver.getTitle());

	}

	@DataProvider(name = "dp")
	public String[] readJson() throws IOException {

		JsonParser jsonParser = new JsonParser();
		FileReader reader = new FileReader("/Users/ramesh/eclipse-workspace/DataDrivenWithJson/testData.json");

		Object obj =jsonParser.parse(reader);

		JSONObject userLoginsJsonObj = (org.json.simple.JSONObject) obj;
		JSONArray userLoginsJsonArray = (org.json.simple.JSONArray) userLoginsJsonObj.get("userlogins");

		String arr[] = new String[userLoginsJsonArray.size()];
		for (int i = 0; i < userLoginsJsonArray.size(); i++) {
			JSONObject users = (JSONObject) userLoginsJsonArray.get(i);
			String un = (String) users.get("username");
			String pwd = (String) users.get("password");
			arr[i] = un + " , " + pwd;
		}
		return arr;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
