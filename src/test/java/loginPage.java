import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class loginPage {

    WebDriver driver = null;
@BeforeTest
public void openBrowser () {

String chromePath = System.getProperty("user.dir")+"\\src\\main\\resources\\chromedriver.exe";
System.setProperty("WebDriver.chrome.driver",chromePath);

driver = new ChromeDriver();

driver.manage().window().maximize();

//1- go to http://live.techpanda.org/index.php/
    driver.navigate().to("http://live.techpanda.org/index.php/");

}

@Test
public void Case1  () throws InterruptedException, IOException {

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
    soft.assertTrue(driver.findElement(By.xpath("//option[@value=\"http://live.techpanda.org/index.php/mobile.html?dir=asc&order=name\"]")).isDisplayed(),"not displayed");
    File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    String day1 = "day1";
    String png = ( "C:\\Users\\MMousa\\Downloads" +day1+".png");
    FileUtils.copyFile(scrFile,new File(png));






    soft.assertAll();
}

@Test
public void Day2 (){

    //2-click on mobile menu
    driver.findElement(By.xpath("//a[@class=\"level0 \"]")).click();

    // 3- read the cost of sony mobile and note this value
    String value1=driver.findElement(By.id("product-price-1")).getText();
    System.out.println(value1);

    // 4 - click on sony xperia mobile
    driver.findElement(By.linkText("SONY XPERIA")).click();

    // 5- read the sony xperia mobile from details
    String value2 = driver.findElement(By.cssSelector("span[class=\"price\"]")).getText();
    System.out.println(value2);

    // 6 - compare value 3 with value 5
    SoftAssert soft = new SoftAssert();
    soft.assertEquals(value1,value2,"the cost doesn't match");
    soft.assertAll();



}

@Test
public void Day3 (){


    //2-click on mobile menu
    driver.findElement(By.xpath("//a[@class=\"level0 \"]")).click();

    //3- click on add to cart
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/button")).click();

    //4- change qty value to 1000
    driver.findElement(By.xpath("//input[@type=\"text\"]")).clear();
    driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys("1000");
    //4-1 click update button
    driver.findElement(By.xpath("//button[@class=\"button btn-update\"]")).click();

    //5- verify the error msd
    String errorMsg=driver.findElement(By.className("error-msg")).getText();
    String expectederrorMsg = "Some of the products cannot be ordered in requested quantity.";
    Assert.assertTrue(errorMsg.contains(expectederrorMsg),"error msg does'nt apper");

    // 6- click on empty card link
    driver.findElement(By.id("empty_cart_button")).click();
    // 7- verify card empty
    String actualcardEmpty=  driver.findElement(By.className("page-title")).getText();
    System.out.println("you have no item msg " + actualcardEmpty);
    String expectedcardEmpty="SHOPPING CART IS EMPTY";
    Assert.assertTrue(actualcardEmpty.contains(expectedcardEmpty),"error in verify card empty");
}

@Test
public void Day4 () {

    //2-click on mobile menu
    driver.findElement(By.xpath("//a[@class=\"level0 \"]")).click();

    //3- click on add to compare of iphone mobile
     driver.findElement(By.xpath
            ("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[3]/ul/li[2]/a")).click();
    // 3-1 click on add to compare of xperia mobile
     driver.findElement(By.xpath
             ("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[3]/div/div[3]/ul/li[2]/a")).click();

     String
     //4- click on compare button
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[3]/div[1]/div[2]/div/button")).click();

    //
      //driver.switchTo().newWindow(WindowType.WINDOW).findElement(By.className("page-title title-buttons")).getText();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[1]/h1"))));

  String newWindow = driver.findElement(By.className("page-title title-buttons")).getText();
  System.out.println(newWindow);




}

@AfterTest
public  void closeBrowser () throws InterruptedException {
Thread.sleep(3000);
    driver.quit();

}
}
