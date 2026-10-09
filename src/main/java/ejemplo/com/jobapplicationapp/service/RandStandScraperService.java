package ejemplo.com.jobapplicationapp.service;

import ejemplo.com.jobapplicationapp.exceptions.NullListJobException;
import ejemplo.com.jobapplicationapp.model.JobApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.*;

public class RandStandScraperService implements JobApplicationServiceContract {

    public RandStandScraperService() {}

    public ArrayList<JobApplication> getJobAppliesRT(String url, Integer applications) {
        var driver = startChrome();
        driver.get(url);
        rmSpamWindows(driver);
        return selectOffers(driver, applications);
    }


    private WebDriver startChrome() {
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    // TODO: Este método lo que hace es llevar al bot a la URL CON FILTROS, luego quita las cookies.
    @Override
    public void rmSpamWindows(WebDriver web) {
        try {
            findCookies(web);
        } catch (RuntimeException e) {
            System.out.println("No se encontró resultado para la URL recibida.");
        }
    }

    private ArrayList<JobApplication> selectOffers(WebDriver web, Integer applications) {
        HashSet<String> tarjeta = new HashSet<>();

        try {
            List<WebElement> jobApplicationsURL = web.findElements(By.cssSelector(
                    "a[href*='/empleo/'], a[href*='/oferta/']"
            ));

            if (jobApplicationsURL.isEmpty()) {
                throw new NullListJobException("Lista de trabajos nula, introduzca otros filtros.");
            }

            int limite = Math.min(applications, jobApplicationsURL.size());

            for (int i = 0; i < limite; i++) {

                String link = jobApplicationsURL.get(i).getDomAttribute("href");

                if (link != null && !link.isEmpty()) {
                    if (!link.startsWith("http")) {
                        link = "https://www.randstad.es" + link;
                    }

                    if (!tarjeta.contains(link)) {
                        tarjeta.add(link);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer las url de RandStad: " + e.getMessage());
        }

        waitTime("Esperando URLs", 4);
        for (String s : tarjeta) {
            try {
                System.out.println("URL guardada con éxito: " + s);
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Error en el tiempo de espera de las URLs" + e.getMessage());
            }
        }
        return obtainData(web, tarjeta);
    }

    private ArrayList<JobApplication> obtainData(WebDriver web, Set<String> urlList) {
        ArrayList<JobApplication> jobList = new ArrayList<>();

        urlList.forEach(url -> {
            web.get(url);

            ntfWindow(web, "gracias"); // TODO : Este método se encarga de quitar cookies

            List<WebElement> data = web.findElements(By.cssSelector("ul[class*='rel_flex rel_flex-col rel_gap-1'] div[class*='rel_text-paragraph-default']"));

            String localidad = data.get(0).getText();
            String modalidad = data.get(1).getText();
            String salario = data.get(2).getText();
            String tipo_contrato = data.get(3).getText();
            String tipo_jornada = data.get(4).getText();

            jobList.add(new JobApplication(localidad, modalidad, salario, tipo_contrato, tipo_jornada));

        });
        return jobList;
    }
}
