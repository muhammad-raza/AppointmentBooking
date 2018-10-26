import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class LoginPage {

    private final WebDriver driver;

    @FindBy(id = "password")
    WebElement password;

    @FindBy(id = "submit")
    WebElement submit;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

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

    public void setPassword(String password){
        this.password.sendKeys(password);
    }

    public void submit() {
        submit.click();
    }

}
