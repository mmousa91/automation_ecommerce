import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

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
public void Day4 () throws InterruptedException {

    //2-click on mobile menu
    driver.findElement(By.xpath("//a[@class=\"level0 \"]")).click();

    //3- click on add to compare of iphone mobile
     driver.findElement(By.xpath
            ("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[3]/div/div[3]/ul/li[2]/a")).click();

     String mainIphone = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[3]/div/h2/a")).getText();
    System.out.println("the main mobile 1 is : " + mainIphone);

    // 3-1 click on add to compare of xperia mobile
     driver.findElement(By.xpath
             ("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/ul/li[2]/a")).click();

     String mainXperia = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/h2/a")).getText();
    System.out.println("the main mobile 2 is : "+ mainXperia);
     //4- click on compare button
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[3]/div[1]/div[2]/div/button")).click();
        Thread.sleep(4000);

    // 5-1 switch to the new popupwindow
    for (String popupWindow : driver.getWindowHandles()){

        driver.switchTo().window(popupWindow);
    }
    // print the url of the new popup window
      System.out.println(driver.getCurrentUrl());

    // verify the popup window heading is "COMPARE PRODUCTS" with selected product in it
    String expheadTitle = "COMPARE PRODUCTS";
    String headTitle = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[1]/h1")).getText();
   //print heading title
    System.out.println("compare head title is : "+headTitle);
    Assert.assertEquals(expheadTitle,headTitle);

   //text capture is iphone
    String popupIphone = driver.findElement(By.xpath("//*[@id=\"product_comparison\"]/tbody[1]/tr[1]/td[2]/h2/a")).getText();
    System.out.println("the popup 1 = " + popupIphone);

    //text capture is iphone
    String popupXperia = driver.findElement(By.xpath("//*[@id=\"product_comparison\"]/tbody[1]/tr[1]/td[1]/h2/a")).getText();
    System.out.println("the popup 2 = " + popupXperia);

    // to check the item1 iphone  in main page is equal the item in the popup page
    try {
        Assert.assertEquals(mainIphone,popupIphone);

    }catch (Exception e){
        e.printStackTrace();
    }
    // to check the second item xperia in main page is equal the item in the popup page
    SoftAssert soft = new SoftAssert();
    soft.assertEquals(mainXperia,popupXperia,"the two items does'nt match");

    soft.assertAll();
    // close popup window
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/button/span/span")).click();

}

@Test
public void Day5 (){
    //2- click on my account link
   driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[3]/div/div[4]/ul/li[1]/a")).click();
    //switch to new window
    for (String handle : driver.getWindowHandles() ){
        driver.switchTo().window(handle);
    }

    //3- click on create account
    driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[1]/div[2]/a")).click();

    //switch to new window
    for (String handle : driver.getWindowHandles()){
        driver.switchTo().window(handle);
    }

    //fill new user information
    driver.findElement(By.id("firstname")).clear();
    driver.findElement(By.id("firstname")).sendKeys("mohamed95");
    driver.findElement(By.id("middlename")).clear();
    driver.findElement(By.id("middlename")).sendKeys("mostafa");
    driver.findElement(By.id("lastname")).clear();
    driver.findElement(By.id("lastname")).sendKeys("hafez");
    driver.findElement(By.id("email_address")).clear();
    driver.findElement(By.id("email_address")).sendKeys("h1995@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("confirmation")).clear();
    driver.findElement(By.id("confirmation")).sendKeys("123456");

    //4- click register
    driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button")).click();

    // switch to new window
    for (String handle : driver.getWindowHandles()){
        driver.switchTo().window(handle);
    }

    //5- verify registration done
    String expectedverifyMsg = "Thank you for registering with Main Website Store.";
    String actualverifyMsg = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div/ul/li")).getText();

    try {
        Assert.assertTrue(actualverifyMsg.contains(expectedverifyMsg));

    }catch (Exception e){
        e.printStackTrace();
    }

    //6- go to tv menu
    driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[2]/a")).click();

    //7- click on add to wish list -add lg lcd tv
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[2]/ul/li[1]/div/div[3]/ul/li[1]/a")).click();

    //8- click on share wishlist
    driver.findElement(By.xpath("//*[@id=\"wishlist-view-form\"]/div/div/button[1]")).click();

    //9- enter email and message
    driver.findElement(By.id("email_address")).sendKeys("hala@gmail.com");

    driver.findElement(By.id("message")).sendKeys("hello");

    //
    driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button")).click();

    //
   String actualwishMsg= driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div[1]/ul/li")).getText();
    String expectwishMsg = "Your Wishlist has been shared.";

    try {
        Assert.assertEquals(actualwishMsg , expectwishMsg);
    }catch (Exception e){
        e.printStackTrace();
    }

}

@Test
public void Day6 () throws InterruptedException {
    //2- click on my account link
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[3]/div/div[4]/ul/li[1]/a")).click();

    // 3- login using created credential
    driver.findElement(By.id("email")).sendKeys("h1997@gmail.com");
    driver.findElement(By.id("pass")).sendKeys("123456");

    driver.findElement(By.id("send2")).click();

    //4- click on my wish list
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[1]/div/div[2]/ul/li[8]/a")).click();

    //5- click on add to cart link
    driver.findElement(By.xpath("//*[@class=\"first last odd\"]/td[5]/div/button")).click();

    //6- click on proceed to checkout
    driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div/div[1]/ul/li/button")).click();

    //7- enter shipping info
    driver.findElement(By.id("billing:street1")).clear();
    driver.findElement(By.id("billing:street1")).sendKeys("ABC");
    driver.findElement(By.id("billing:city")).clear();
    driver.findElement(By.id("billing:city")).sendKeys("New York");

    Select state = new Select(driver.findElement(By.id("billing:region_id")));
    state.selectByVisibleText("New York");

    driver.findElement(By.id("billing:postcode")).clear();
    driver.findElement(By.id("billing:postcode")).sendKeys("542896");
    driver.findElement(By.id("billing:telephone")).clear();
    driver.findElement(By.id("billing:telephone")).sendKeys("12345678");


    //8- click estimate
    driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/button")).click();
    Thread.sleep(4000);

    //9- scroll up to shipping method
    JavascriptExecutor js = (JavascriptExecutor) driver ;
    WebElement flateRate = driver.findElement(By.xpath("//*[@id=\"opc-shipping_method\"]/div[1]/h2"));
    js.executeScript("arguments[0].scrollIntoView();",flateRate);

    //9-1 verify shipping cost generate
    String actualflatRate = driver.findElement(By.xpath("//*[@id=\"checkout-shipping-method-load\"]/dl/dd/ul/li/label/span")).getText();
    String expectedflateRate = "$5.00";
    try {
        Assert.assertEquals(actualflatRate,expectedflateRate);
    }
    catch (Exception e){
        e.printStackTrace();
    }

    //9-2 click on continue
    driver.findElement(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/button")).click();
     Thread.sleep(4000);

    // select payment information

  //  WebElement radio2 = driver.findElement(By.id("p_method_checkmo"));
  //  radio2.click();


    WebElement radiobutton = driver.findElement(By.id("p_method_checkmo"));
    boolean isenabled = radiobutton.isEnabled();
    boolean isselected = radiobutton.isSelected();
    if (isenabled == true){
       if (isselected == false){
            radiobutton.click();
       }
   }else System.out.println("the radiobutton is disabled");

    // click continue
    driver.findElement(By.xpath("//*[@id=\"payment-buttons-container\"]/button")).click();
    Thread.sleep(3000);

    // click place order
    driver.findElement(By.xpath("//*[@id=\"review-buttons-container\"]/button")).click();


    // switch to new window
    for (String handle : driver.getWindowHandles()){
        driver.switchTo().window(handle);
    }
Thread.sleep(2000);
    //17- verify order is generated
    String actualverifyorderMsd = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div[1]/h1")).getText();
    String expectedverifyorderMsg = "YOUR ORDER HAS BEEN RECEIVED.";

    try {
        Assert.assertEquals(actualverifyorderMsd , expectedverifyorderMsg);
    }catch (Exception e){
        e.printStackTrace();
    }
    // note the order number
    String orderNo = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/p[1]/a")).getText();
    System.out.println("the order no is : "+ orderNo);

}
@Test
public void Day7(){


}

@AfterTest
public  void closeBrowser () throws InterruptedException {
Thread.sleep(3000);
    driver.quit();

}
}
