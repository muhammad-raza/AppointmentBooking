import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by raza on 22/10/2018.
 */
public class BookingPage {

    private final WebDriver driver;

    @FindBy(id = "submit")
    WebElement submit;

    public void navigate(String url) throws InterruptedException {
        driver.navigate().to(url);
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> ((JavascriptExecutor)driver).executeScript("return jQuery.active").toString().equals("0");

        // wait for Javascript to load
//        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").toString().equals("complete");

        wait.until(jQueryLoad);

        PageFactory.initElements(driver, this);

    }

    public BookingPage(WebDriver driver) {
        this.driver = driver;
    }


    public void selectPSE(String pse) {
        this.driver.findElement(By.id(pse)).click();
    }

    public void submit() {
        submit.click();
    }

    public boolean found() {
        try {
            WebElement appointmentBooking = this.driver.findElement(By.id("AppointmentBooking"));
            return !appointmentBooking.getText().contains("We do not have any appointments in the next 45 business days at your selected location.");
        } catch (Exception e) {
            return true;
        }
    }

}
