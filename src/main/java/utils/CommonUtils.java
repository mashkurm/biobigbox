package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CommonUtils {
	private final Logger logger = Logger.getLogger(CommonUtils.class);
    public WebDriver driver;

    public WebDriver openBrowser (String browserName, String baseURL) throws Exception {
        try{
            DesiredCapabilities capability = new DesiredCapabilities();
            logger.info("Browser: " + browserName + " without remote driver");
            if (browserName.toLowerCase().equals("firefox")) {
                capability = DesiredCapabilities.firefox();
                capability.setBrowserName("firefox");
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("dom.max_chrome_script_run_time", 1000);
                profile.setPreference("dom.max_script_run_time", 1000);
                profile.setPreference("browser.cache.disk.enable", false);
                capability.setCapability(FirefoxDriver.PROFILE, profile);
                driver = new FirefoxDriver(capability);
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/resources/driver/geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (browserName.toLowerCase().equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/resources/driver/chromedriver.exe");
                capability.setJavascriptEnabled(true);
                capability.setCapability("chrome.switches", "--start-maximized");
                capability.setCapability(ChromeOptions.CAPABILITY, new ChromeOptions());
                capability = DesiredCapabilities.chrome();
                capability.setBrowserName("chrome");
                driver = new ChromeDriver(capability);
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            logger.info("Driver setup is starting ......");
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.get(baseURL);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return driver;
    }
    
    public ArrayList<String> getListURLs() throws Exception{
        ArrayList<String> listURLs = new ArrayList<>();
        String excelData = getAbsolutePath("resources//data//data.xls");
        String[][] urlInfo = getTableArray(excelData,"data", "urldata");
        for (int i = 0; i < urlInfo.length; i++) {
            listURLs.add(urlInfo[i][0]);
        }
        return listURLs;
    }
    
    public String getAbsolutePath(String fileName){
        String path = "";
        try{
            File f;
            Boolean bool;
            f = new File(fileName);
            bool = f.exists();
            if (bool) {
                path = f.getAbsolutePath();
            }
        }
        catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return path;
    }

    public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
        String[][] tabArray = null;

        Workbook workbook;
        try {
            logger.info("File path: " + xlFilePath);
            workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow, startCol, endRow, endCol, ci, cj;
            Cell tableStart = sheet.findCell(tableName);
            startRow = tableStart.getRow();
            startCol = tableStart.getColumn();

            Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1,
                    100, 64000, false);

            endRow = tableEnd.getRow();
            endCol = tableEnd.getColumn();
            tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
            ci = 0;

            for (int i = startRow + 1; i < endRow; i++, ci++) {
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    tabArray[ci][cj] = sheet.getCell(j, i).getContents();
                }
            }
        } catch (BiffException | IOException ex) {
            logger.error("Error: " + ex.getMessage());
        }

        return (tabArray);
    }
 
    


}
