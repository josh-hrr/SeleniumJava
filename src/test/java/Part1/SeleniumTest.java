/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Part1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test; 

import com.aventstack.extentreports.ExtentReports; 
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;

import java.io.File;
import org.apache.commons.io.FileUtils; 
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import java.util.HashMap;
import java.util.Map; 


/**
 *
 * @author Josue
 */
public class SeleniumTest {
    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    
    @BeforeClass
    public void setUp() { 
        //Driver setup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        
        /*
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        */
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        
        //Report setup
        htmlReporter = new ExtentHtmlReporter("extent.html"); 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }
     
    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
    
    @Test
    public void testLogin() throws Exception{
        ExtentTest test = extent.createTest("MyFirstTest", "Sample description");
        // log(Status, details)
        test.log(Status.INFO, "This step shows usage of log(status, details)");
        
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("login-button"));
        
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        submitBtn.click();
        
        
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html"); 
        Thread.sleep(2000);
        // log with snapshot
        
        String screenshotPath = takeScreenshot("screenshot");
        test.pass("Login successful",
            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }
    
    public String takeScreenshot(String fileName) throws Exception {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destPath = System.getProperty("user.dir") + "/" + fileName + ".png";
        FileUtils.copyFile(srcFile, new File(destPath));
        return destPath;
    }
    
}
