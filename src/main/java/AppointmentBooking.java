import org.openqa.selenium.chrome.ChromeDriver;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBooking implements Runnable {
    private static final Map<String, String> PSC = new HashMap<>();
    private ChromeDriver driver;

    static {
        PSC.put("centreId_PEBE", "Belfast");
        PSC.put("centreId_PEBI", "Birmingham");
        PSC.put("centreId_PECA", "Cardiff");
        PSC.put("centreId_PCCR", "Croydon");
        PSC.put("centreId_PEGL", "Glasgow");
        PSC.put("centreId_PELI", "Liverpool");
        PSC.put("centreId_PESH", "Sheffield");

    }

    @Override
    public void run() {
        try {
//           System.setProperty("webdriver.chrome.driver", "chromedriver");
            System.setProperty("webdriver.chrome.driver", new File("chromedriver").getAbsolutePath());
            this.driver = new ChromeDriver();
            checkAppointAvailability();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    private void checkAppointAvailability() throws InterruptedException, IOException, LineUnavailableException {
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.navigate(Credentials.url);
        loginPage.setPassword(Credentials.password);
        loginPage.submit();

        searchBooking();
    }

    private boolean searchBooking() throws InterruptedException, IOException, LineUnavailableException {
        System.out.println("Searching for booking: " + new Date());
        boolean found = false;
        for (String pscId : PSC.keySet()) {
            BookingPage bookingPage = new BookingPage(this.driver);
            bookingPage.navigate("https://visas-immigration.service.gov.uk/edit/payment.appointmentCentreSelection");
            bookingPage.selectPSE(pscId);
            bookingPage.submit();

            found = bookingPage.found();
            if (found) {
                System.out.println("***** Booking found - PSC: " + PSC.get(pscId) + " - time:" + new Date() + " *****");
                playSound();
            }
        }
        return found;
    }

    private void playSound() throws IOException, LineUnavailableException {
        AudioInputStream inputStream = null;
        Clip clip = AudioSystem.getClip();
        try {
            InputStream audioResource = getClass().getClassLoader().getResourceAsStream("alert.wav");
            inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioResource));
            clip.open(inputStream);
            clip.start();
            Thread.sleep(10000);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (clip != null) {
                clip.close();
            }
        }
    }
}
