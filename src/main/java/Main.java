
import java.io.Console;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        Console console = System.console();

        String url = console.readLine("Login page URL: ");
        char[] password = console.readPassword("password: ");

        Credentials.url = url;
        Credentials.password = String.valueOf(password);

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new AppointmentBooking(), 0, 5, TimeUnit.MINUTES);

    }

}
