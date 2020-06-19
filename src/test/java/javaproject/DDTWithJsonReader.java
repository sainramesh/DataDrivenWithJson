package javaproject;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DDTWithJsonReader {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://app.hubspot.com/login/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@DataProvider(name = "userlogins")
	public Object[][] passData() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		// return JsonReader.getdata(JSON_path, typeData, totalDataRow,
		// totalColumnEntry);
		return JsonReader.getdata(
				"/Users/ramesh/eclipse-workspace/DataDrivenWithJson/src/main/resource/" + "testData.json", "userlogins",
				2, 2);
	}

	@Test(dataProvider = "userlogins")
	public void login(String un, String pwd) {
		driver.findElement(By.cssSelector("#username")).sendKeys(un);
		driver.findElement(By.cssSelector("#password")).sendKeys(pwd);
		driver.findElement(By.cssSelector("#loginBtn")).click();
		System.out.println(driver.getTitle());

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
