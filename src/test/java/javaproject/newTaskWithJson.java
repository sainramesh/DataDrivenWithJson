package javaproject;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class newTaskWithJson {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://app.hubspot.com/login/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@DataProvider(name = "NewTask")
	public Object[][] passData() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		// return JsonReader.getdata(JSON_path, typeData, totalDataRow,
		// totalColumnEntry);
		return JsonReader.getdata(
				"/Users/ramesh/eclipse-workspace/DataDrivenWithJson/src/main/resource/" + "TaskTestData.json", "NewTask",
				2, 7);
	}

	@Test(dataProvider = "NewTask")
	public void login(String un, String pwd, String taskSub, String taskType, String QueueType, String Month, String Day) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get("https://app.hubspot.com/login/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.cssSelector("#username")).sendKeys(un);
			driver.findElement(By.cssSelector("#password")).sendKeys(pwd);
			driver.findElement(By.cssSelector("#loginBtn")).click();

			driver.findElement(By.cssSelector(".expandable #nav-primary-sales-branch[data-tracking='click hover']"))
					.click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driver.findElement(By.cssSelector(".expandable.active #nav-secondary-tasks")).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driver.findElement(By.cssSelector("[data-selenium-test=TasksHeaderView__add-task-btn]")).click();

			driver.findElement(By.cssSelector(".private-form__control[data-field=hs_task_subject]")).sendKeys(taskSub);

			driver.findElement(By.cssSelector("[data-selenium-test=\"property-input-hs_task_type\"]")).click();
			List<WebElement> TaskTypes = driver.findElements(By.cssSelector(".private-typeahead-result-label"));
			System.out.println("itesms are " + TaskTypes.size());
			for (WebElement stageList : TaskTypes) {
				if (stageList.getText().equalsIgnoreCase(taskType)) {
					stageList.click();
					break;
				}

			}
			driver.findElement(By.cssSelector("[title=\"None\"]")).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
			driver.findElement(By.cssSelector("[title=\"High\"]")).click();

			driver.findElement(By.cssSelector("[data-selenium-test=\"property-input-hs_queue_membership_ids\"]")).click();
			List<WebElement> queueList = driver.findElements(By.cssSelector(".private-typeahead-result-label"));
			System.out.println("itesms are " + queueList.size());
			for (WebElement stageList : queueList) {
				if (stageList.getText().equalsIgnoreCase("To-do")) {
					stageList.click();
				} else {
					driver.findElement(By.cssSelector("[data-key=\"taskFormsLib.addQueue.addQueueButton\"]")).click();
					driver.findElement(By.cssSelector(".private-modal__container .form-control")).sendKeys(QueueType);
					driver.findElement(By.cssSelector(".uiButton [data-key=\"taskFormsLib.addQueue.save\"]")).click();
				}
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(By.cssSelector("[data-key=\"customerDataRte.plugins.tooltips.more\"]")));

			driver.findElement(By.cssSelector(".gUstvr .uiDropdown__buttonContents")).click();
			List<WebElement> dueDateList = driver.findElements(By.cssSelector(".private-typeahead-result-label"));
			System.out.println("itesms are " + dueDateList.size());
			for (WebElement stageList : dueDateList) {
				if (stageList.getText().equalsIgnoreCase("Test")) {
					stageList.click();
				} else if (stageList.getText().equalsIgnoreCase("Custom Date")) {
					stageList.click();

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					driver.findElement(By.cssSelector(".jISQGO [data-format=\"MM/DD/YYYY\"]")).click();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					while (true) {
						String text = driver
								.findElement(By.cssSelector(".cxrywB"))
								.getText();
						if (text.equalsIgnoreCase(Month)) {
							break;
						} else {
							driver.findElement(By.cssSelector(".UIIcon__IconContent-sc-1ngbkfp-0.gxNdVV")).click();
						}
					}
					List<WebElement> days = driver.findElements(By.cssSelector(".Day__Cell-sc-1sul5rl-0:not(.fJaoZG)"));
					for (WebElement value : days) {
						if (value.getText().equalsIgnoreCase(Day)) {
							value.click();
							break;
						}
					}
				}
			}

			driver.findElement(By.cssSelector("[placeholder=\"HH:MM\"]")).click();
			List<WebElement> TimeList = driver.findElements(By.cssSelector(".private-typeahead-result-label"));
			System.out.println("itesms are " + TimeList.size());
			for (WebElement stageList : TimeList) {
				if (stageList.getText().equalsIgnoreCase("11:00 AM")) {
					stageList.click();
					break;
				}
			}

			driver.findElement(By.cssSelector("[data-selenium-test=\"CreateTaskSidebar__save-btn\"]")).click();

		}



	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
