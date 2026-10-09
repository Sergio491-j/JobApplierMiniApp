package ejemplo.com.jobapplicationapp.service;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public interface JobApplicationServiceContract {

    default void waitTime(String textInConsole, int seconds) {
            for(int i = 0; i <= seconds; i++) {

                try {

                    System.out.print("\r" + textInConsole);
                    Thread.sleep(250);
                    System.out.print("\r" + textInConsole + ".");
                    Thread.sleep(250);
                    System.out.print("\r" + textInConsole + "..");
                    Thread.sleep(250);
                    System.out.print("\r" + textInConsole + "...");
                    Thread.sleep(250);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println();
    }

    default void findCookies(WebDriver web) {
        try {
            // Espera explícita de hasta 5 segundos a que el botón sea interaccionable
            WebDriverWait wait = new WebDriverWait(web, Duration.ofSeconds(5));

            WebElement acceptCookiesBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler"))
            );

            acceptCookiesBtn.click();
            System.out.println("Cookies aceptadas correctamente.");

        } catch (TimeoutException e) {
            // Ocurre si el banner no apareció (por ejemplo, si las cookies ya se aceptaron)
            System.out.println("El banner de cookies no apareció a tiempo.");
        } catch (Exception e) {
            System.err.println("Error al gestionar las cookies: " + e.getMessage());
        }
    }

    default void ntfWindow(WebDriver web, String notisButtonText) {

        waitTime("Esperando a las notificaciones", 3);

        try {
            web.findElement(org.openqa.selenium.By.xpath("//button[contains(text(), '" + notisButtonText + "')]")).click();
            System.out.println("Notificaciones quitadas.");

        } catch (Exception e) {
            System.out.println("Ignorando la ventana de notificaciones debido a que no se encontró.");
        }

    }

    void rmSpamWindows(WebDriver web);
}
