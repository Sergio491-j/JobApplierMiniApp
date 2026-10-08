package ejemplo.com.jobapplicationapp.service;

import org.openqa.selenium.WebDriver;

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

            waitTime("Encontrando las cookies", 2);

            org.openqa.selenium.WebElement acceptCookiesBtn = web.findElement(org.openqa.selenium.By.id("onetrust-accept-btn-handler"));
            acceptCookiesBtn.click();

            System.out.println("Botón encontrado.");

        } catch (Exception e) {
            System.out.println("A ocurrido un error con el gestor de las cookies " + e.getMessage());
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

    void findFirstURL(WebDriver web,String province, String job);

    void botMenu();

}
