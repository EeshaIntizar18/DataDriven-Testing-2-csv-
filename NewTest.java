package web1;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.opencsv.CSVReader;

public class NewTest {
	public static WebDriver driver;
	List<WebElement> linkElements = new ArrayList<WebElement>();
	ListIterator<WebElement> itr = null;
	ListIterator<WebElement> itr1 = null;
	boolean flag = false;
	int pageNumber = 1;
	boolean found = false;

	@Test()
	public void csvDataRead() throws Exception {

		// This will load csv file
		CSVReader reader = new CSVReader(new FileReader("/home/rlt/eclipse-workspace/Website/Web/Data/Untitled 1.csv"));
		// This will load csv file2
		CSVReader reader2 = new CSVReader(new FileReader("/home/rlt/eclipse-workspace/Website/Web/Data/Untitled2.csv"));
		// this will load keyword content into list
		List<String[]> li = reader.readAll();
		// this will load url content into list
		List<String[]> li2 = reader2.readAll();

		System.out.println("Total rows which we have in keywords==" + li.size());
		System.out.println("Total rows which we have is url==" + li2.size());
		 

		// create Iterator reference for keywords
		Iterator<String[]> i1 = li.iterator();
		Iterator<String[]> i2 = li2.iterator();
		
		
		// Iterate all values for keywords

		while (i1.hasNext()) {
			String[] str = i1.next();
			
			 
			System.out.print(" Keywords ");

			for (int i = 0; i < str.length; i++) {
				
				System.out.print(" " + str[i]);
			
				////////////// Enter keyword///////////////

				driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
						.sendKeys(str);
				Thread.sleep(8000);
				///////////////// Enter search////////////
				WebElement ar = driver.findElement(By.name("q"));
				ar.sendKeys(Keys.RETURN);

				Thread.sleep(7000);

				// Iterate all values for url
				// int linkCount = list.size();
				while (i1.hasNext()) {
					String[] str1 = i2.next();
					
					System.out.print(" url");
					for (int j = 0; j < str1.length; j++) {
						System.out.print(" " + str1[j]);
						// System.out.println("/nTotal number of page on the webpage:" + linkCount);
						while (!flag) {

							List<WebElement> list = driver.findElements(By.xpath("//*[@href or @src]"));
							for (WebElement eachResult : list) {
								String link = eachResult.getAttribute("href");
								if (null == link)
									link = eachResult.getAttribute("src");
								if (link.contains(str1[j])) {

									eachResult.findElement(By.xpath("//a[@href='" + link + "']")).click();
									System.out.println("Link found");
									flag = true;
									found = true;
									break;
								}
							} // End of foreach-loop

							if (!flag) {
								driver.findElement(By.xpath("//a[@id='pnnext']/span[1]")).click();
								Thread.sleep(5000);
								pageNumber++;

							}

						}
						flag = false; // while
						Thread.sleep(5000);
						driver.get("https://www.google.com/?gl=us&hl=en&pws=0&gws_rd=cr");

					}
					break;

				}
				break;

			}

		}
	}

	@BeforeTest
	public void f1() throws Exception {

		// Setting property for Chrome Driver
		System.setProperty("webdriver.chrome.driver", "/home/rlt/Downloads/chromedriver");
//		 System.setProperty("webdriver.gecko.driver",
//		 "/home/rlt/Downloads/geckodriver");

	//	String chromeProfilePath = "/home/codebind/.config/google-chrome/Profile 2/";
		// Instance of ChromeOption
		ChromeOptions options = new ChromeOptions();

		// Disable notification PopUp
		options.addArguments("--disable-notifications");
//		options.addArguments("chrome.switches", "--disable-extensions");
//		
//
//	//	options.addArguments("--user-data-dir=/home/rlt/.config/google-chrome/");
//		// load default profile
//		 options.addArguments("--profile-directory=/home/rlt/.config/google-chrome/");
//	//options.addArguments("--profile-directory=i2");
//		Thread.sleep(1000);
		
		// Instance of ChromeDriver
		
		driver = new ChromeDriver(options);
		// Calling Google Page
		driver.get("https://www.google.com/?gl=us&hl=en&pws=0&gws_rd=cr");
		Thread.sleep(1000);
		driver.manage().deleteAllCookies();

		// Maximize the browser
		driver.manage().window().maximize();
		Thread.sleep(4500);

	}

}
