package isbn_explorer;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverExtrator {
	
	private WebDriver driver;
	
	public DriverExtrator(boolean headless) throws IOException {
		WebDriverManager.chromedriver().setup();

		if(headless) {
		    ChromeOptions options = new ChromeOptions();
		    options.addArguments("--headless");
			driver = new ChromeDriver(options);
		}else {
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
	}
	
	public void waitForLoad() {
	    new WebDriverWait(driver, 600).until((ExpectedCondition<Boolean>) wd ->
	            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
	
	public WebDriver getDriver() {
		return driver;
	}

}
