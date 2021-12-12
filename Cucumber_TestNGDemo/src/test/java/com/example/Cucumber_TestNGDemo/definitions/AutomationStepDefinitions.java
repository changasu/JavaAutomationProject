package com.example.Cucumber_TestNGDemo.definitions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.example.Cucumber_TestNGDemo.excelDataReader.excelDataReader;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
//import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomationStepDefinitions {
	WebDriver driver;
	ExtentReports extent;
	ExtentTest test;
	Sheet sheet;
	private ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();
	private ThreadLocal<ExtentReports> extentreport = new ThreadLocal<ExtentReports>();
	private ThreadLocal<ExtentTest> extenttest = new ThreadLocal<ExtentTest>();
	private ThreadLocal<Sheet> datasheet = new ThreadLocal<Sheet>();
	
    @Before
    public void setUp(Scenario scenario) {
    	
    	extent = new ExtentReports();
    	extentreport.set(extent);
    	ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\AutomationReports\\" + scenario.getName() + "_" + scenario.getLine() + ".html");
    	extentreport.get().attachReporter(spark);
    	test = extentreport.get().createTest(scenario.getName());
    	extenttest.set(test);
    	sheet = excelDataReader.getSheet(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\testdata.xlsx"), scenario.getName().split("_")[1]);
    	datasheet.set(sheet);
    	
    	//WebDriverManager.chromedriver().setup();
    	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\Vibha\\Software\\geckodriver-v0.26.0-win64\\geckodriver.exe");
    	
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("start-maximized");
    	options.addArguments("disable-infobars");
    	options.addArguments("disable-popup-blocking");
        driver = new ChromeDriver(options);
        webdriver.set(driver);
        webdriver.get().manage().window().maximize();
        webdriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @Given("^User launches Google page$")
    public void user_launches_google_page() {
    	webdriver.get().get("https://www.google.com");
        extenttest.get().pass("Launched Google home page");
    }
    
    @Then("^User searches for a text (.+) \"([^\"]*)\"$")
    public void user_searches_for_a_text_something(String rowid, String searchText) throws Exception, InvalidFormatException, IOException{
    	webdriver.get().findElement(By.xpath("//div[text()='I agree']")).click();
    	extenttest.get().pass("Clicks on I agree consent");
    	System.out.println("Column Name: " + searchText);
    	String inputText = excelDataReader.readByColumnName(datasheet.get(), searchText, Integer.parseInt(rowid));
    	webdriver.get().findElement(By.xpath("//input[@title='Search']")).sendKeys(inputText);
        extenttest.get().pass("input text " + inputText);
    }

    @And("^User clicks on Google search button$")
    public void user_clicks_on_google_search_button() throws Exception {
    	int cnt =0;
    	do
    	{
    		try {
    			cnt++;
        		List<WebElement> eles = webdriver.get().findElements(By.xpath("//input[@type='submit' and @value='Google Search']"));
            	for(int i=0;i<eles.size();i++)
            	{
            		int size = eles.get(i).getSize().width;
            		if (size >0)
            		{
            			eles.get(i).click();
            			extenttest.get().pass("Clicks on Google search button");
            			cnt =6;
            		}
            	}
            	if (cnt >=6)
            		break;
        	}
        	catch(StaleElementReferenceException e) {cnt = 0;}
        	catch(Exception e) {
        		cnt = 6;
        		extenttest.get().pass("Clicks on Google search button");
        	}
    	} while (cnt <=0);
    	
        System.out.println("Thread ID - " + Thread.currentThread().getId());
    } 

    @And("^User verifies the Google results$")
    public void user_verifies_the_google_results() throws Exception {
    	int cnt =0;
    	int available = 0;
    	do
    	{
    		Thread.sleep(500);
    		cnt++;
    		available = webdriver.get().findElements(By.xpath("//div[@class='IC1Ck']")).size();
    		if(cnt > 20)
    			break;
    	} while(available<=0);
    	
        if (available >= 1)
        {
        	System.out.println("Results are displayed");
        	extenttest.get().pass("Results are displayed");
        }
        else
        {
        	System.out.println("Results are not displayed");
        	extenttest.get().fail("Results are not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String("App Screenshot").build());
        }
    }
    
    @And("^User clicks on the available result$")
    public void user_clicks_on_the_available_result() {
    	webdriver.get().findElement(By.xpath("(//h2[text()='Web results'])[1]//following-sibling::div//a")).click();
        extenttest.get().pass("Clicks on Google result");
    }
    
    @After
    public void teardown() {
    	extentreport.get().flush();
    	webdriver.get().quit();
    }
}
