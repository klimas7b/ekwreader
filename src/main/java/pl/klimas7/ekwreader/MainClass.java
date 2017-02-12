package pl.klimas7.ekwreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by bklimas on 10.11.15.
 */
public class MainClass {
    private static ChromeDriverService service;
    private static WebDriver driver;

    public static void main(String... args) throws IOException {
        String url = "https://przegladarka-ekw.ms.gov.pl/";


        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("/opt/tools/chromedriver/chromedriver"))
                .usingAnyFreePort()
                .build();
        service.start();


        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());

        driver.get(url);
        /*
        List<Integer> tmp = Arrays.asList(6504);
        for (Integer n : tmp) {
            getEKW(ekws, n);
        }
        */
        int begine = 10200;
        List<EKW> ekws = new ArrayList<>();
        for (int i = begine; i <= begine + 300; i++) {
            EKWNumber ekwNumber = new EKWNumber(Court.KR3I, i);
            if (ekwNumber.getChecksum().equals("7")) {
                getEKW(ekws, i);
            }
        }

        System.out.println("=======================================================================================================================");
        for (EKW ekw : ekws) {
            System.out.println(ekw.getEkwNumber().toString() + " " + ekw.getEkwHeader().getOwnersAsText() + " | " + ekw.getEkwHeader().getPosition());
        }
        System.out.println("=======================================================================================================================");

        //driver.findElement(By.id("przyciskWydrukZwykly")).click();
        //;


        //Nawigowanie po działach
        //driver.findElement(By.xpath("//input[@value='Dział III']")).click();


        //

        driver.quit();
        service.stop();

    }

    private static void getEKW(List<EKW> ekws, Integer n) {
        try {
            EKWNumber ekwNumber = new EKWNumber(Court.KR3I, n);
            EKW ekw = new EKW();

            ekw.setEkwNumber(ekwNumber);

            WebElement searchBox = driver.findElement(By.id("kodWydzialuInput"));
            searchBox.sendKeys(ekwNumber.getCourt().name());

            searchBox = driver.findElement(By.id("numerKsiegiWieczystej"));
            searchBox.sendKeys(ekwNumber.getNumber());


            searchBox = driver.findElement(By.id("cyfraKontrolna"));
            searchBox.sendKeys(ekwNumber.getChecksum());


            WebElement wyszukalButton = driver.findElement(By.id("wyszukaj"));
            sleepAndClick(wyszukalButton, 6000);

            String wynikWyszukiwania = driver.findElement(By.className("section")).getText();
            ekw.setEkwHeader(Utils.parseEKWHeader(wynikWyszukiwania));
            System.out.println(wynikWyszukiwania);
            ekws.add(ekw);
            //Powrót do wyszukiwania
            driver.findElement(By.id("powrotDoKryterii")).click();
        } catch (Exception e) {
            System.out.println("In Exception: getEKW");
            getEKW(ekws, n);
        }
    }

    private static void sleepAndClick(WebElement wyszukalButton, int sleepTime) {
        System.out.println("Przed sleep");
        try {
            Thread.sleep(sleepTime);
            System.out.println("Po sleep");
            wyszukalButton.click();
        } catch (Exception e) {
            System.out.println("In Exception: sleepAndClick");
            //System.err.println(e.getMessage());
            sleepAndClick(wyszukalButton, 2000);
        }
    }
}
