package com.kitaramedia.selenium;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private String baseUrl;
	private WebDriver driver;
	private ScreenshotHelper screenshotHelper;
	  
	  
	@Before
	  public void openBrowser() {
	  }
	  
	  @After
	  public void saveScreenshotAndCloseBrowser() throws IOException {
	    screenshotHelper.saveScreenshot("screenshot.png");
	    driver.quit();
	  }
	  
	  /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
	    baseUrl = System.getProperty("webdriver.base.url");
	    driver = new ChromeDriver();
	    driver.get(baseUrl);
	    screenshotHelper = new ScreenshotHelper();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     *  Test 
     */
    @SuppressWarnings("unchecked")
	public void testApp() throws IOException
    {
        try {
        	String search = "Kitara Media!"; 
            assertEquals("The page title should equal Google at the start of the test.", "Google", driver.getTitle());
            WebElement searchField = driver.findElement(By.name("q"));
            searchField.sendKeys(search);
            searchField.submit();
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(new ExpectedCondition<Boolean>(){
				@Override
				public Boolean apply(WebDriver d) {
					return ((WebDriver) d).getTitle().toLowerCase().startsWith("kitara media!");
				}
			});
            
          screenshotHelper.saveScreenshot("screenshot.png");
          driver.quit();                	
        }catch(IOException exc){
    	  System.out.println(exc.getMessage());
        }        

    }


    private class ScreenshotHelper
    {
    	  
        public void saveScreenshot(String screenshotFileName) throws IOException {
          File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
          FileUtils.copyFile(screenshot, new File(screenshotFileName));
        }
    }
}
