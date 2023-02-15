import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.*;

import java.io.File;

public class loginPage {

    WebDriver driver = null;
@BeforeTest
public void openBrowser () {

String chromePath = System.getProperty("user.dir")+"\\src\\main\\resources\\chromedriver.exe";
System.setProperty("WebDriver.chrome.driver",chromePath);

driver = new ChromeDriver();

driver.manage().window().maximize();

}

@Test
public void Case1  () throws InterruptedException, IOException {
//1- go to http://live.techpanda.org/index.php/
    driver.navigate().to("http://live.techpanda.org/index.php/");

//2 - verify title of the page "this is demo site"
    String actualResult = driver.findElement(By.className("page-title")).getText();
    System.out.println(actualResult);
    String expectedResult = "THIS IS DEMO SITE";
    SoftAssert soft = new SoftAssert();
   soft.assertTrue(actualResult.contains(expectedResult),"wrong return msg");


   Thread.sleep(3000);

   //3-click on mobile menu
    driver.findElement(By.xpath("//a[@class=\"level0 \"]")).click();

    Thread.sleep(2000);

    //4- verify title "mobile" is shown on mobile list page
   String actualResult2= driver.findElement(By.xpath("//div[@class=\"page-title category-title\"]")).getText();
   System.out.println(actualResult2);
   String expectedResult2 = "MOBILE";
   soft.assertTrue(actualResult2.contains(expectedResult2),"wrong return msg 2");

//5-select "sort-by" dropdown as name
    Select sortMenu = new Select(driver.findElement(By.xpath("//select[@title=\"Sort By\"]")));
    sortMenu.selectByVisibleText("Name");

    //6- verify all product sorted by name
   // soft.assertTrue(driver.findElement(By.xpath("//option[@value=\"http://live.techpanda.org/index.php/mobile.html?dir=asc&order=name\"]")).isDisplayed(),"not displayed");
    File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    String day1 = "day1";
    String png = ("C:\\Users\\moham\\Downloads\\"+ day1 +".png");
    FileUtils.copyFile(scrFile,new File(png));






    soft.assertAll();
}

@AfterTest
public  void closeBrowser () throws InterruptedException {
Thread.sleep(3000);
    driver.quit();

}
}
