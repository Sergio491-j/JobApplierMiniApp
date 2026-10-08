package ejemplo.com.jobapplicationapp.service;

import ejemplo.com.jobapplicationapp.repository.JobApplicationRepository;
import ejemplo.com.jobapplicationapp.model.JobApplication;
import ejemplo.com.jobapplicationapp.exceptions.NullListJobException;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class JobApplicationService implements JobApplicationServiceContract {

    private JobApplicationRepository repository;

    public List<JobApplication> getList() {
        return repository.findAll();
    }

    @Override
    public void botMenu() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("======");
            System.out.println("MENU");
            System.out.println("======");
            System.out.println("Opción 1: extraer vacantes de empleo.");
            System.out.println("Opción 2: abrir el navegador y ver las vacantes registradas");
            System.out.println("Opción 3: salir del programa.");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.print("¿Cuantos puestos desea extraer en RandStand? ");
                    Integer applications = sc.nextInt();

                    sc.nextLine();

                    System.out.println("Diga para que puesto de trabajo desea extraer la información.");
                    String jobFilter = sc.nextLine();

                    System.out.println("Diga en que provincia de España desea encontrar estos puestos de trabajo.");
                    String provinceFilter = sc.nextLine();

                    // TODO : Arrancamos las opciones de Chrome
                    ChromeOptions options = new ChromeOptions();


                    // TODO : Arrancamos el navegador
                    WebDriver web = new ChromeDriver(options);
                    selectJobApplications(web, applications, jobFilter, provinceFilter);
                    web.quit();
                    break;
                case 2:
                    // TODO : Arrancamos las opciones de Chrome
                    ChromeOptions options1 = new ChromeOptions();

                    // TODO : Arrancamos el navegador
                    WebDriver web1 = new ChromeDriver(options1);

                    web1.get("http://localhost:8080/api"); // Cambio de la url a la que te manda el servidor
                                                            // una vez se quiera consultar
                                                            // las vacantes de trabajo.
                    break;
                case 3:
                    waitTime("Cerrando el programa", 2);
                    salir = true;
                    break;
            }
        }
    }

    // TODO: Este método lo que hace es llevar al bot a la URL CON FILTROS, luego quita las cookies.
    @Override
    public void findFirstURL(WebDriver web, String province, String job) {
        try {
            web.get("https://www.randstad.es/candidatos/ofertas-empleo/p-" + province + "/jb-" + job + "/");
            findCookies(web);
            ntfWindow(web, "gracias"); // TODO : Este método se encarga de quitar cookies
        } catch (RuntimeException e) {
            System.out.println("No se encontró resultado para la URL recibida.");
        }
    }

    private void selectJobApplications(WebDriver web, Integer applications, String jobFilter, String provinceFilter) {
        findFirstURL(web, provinceFilter, jobFilter);

        ArrayList<String> tarjeta = new ArrayList<>();

        try {
            List<WebElement> jobApplicationsURL = web.findElements(By.cssSelector(
                    "a[href*='/empleo/'], a[href*='/oferta/']"
            ));

            if (jobApplicationsURL.isEmpty()) {
                throw new NullListJobException("Lista de trabajos nula, introduzca otros filtros.");
            }

            int limite = Math.min(applications, jobApplicationsURL.size());

            for (int i = 0; i < limite; i++) {

                String url = jobApplicationsURL.get(i).getDomAttribute("href");

                if (url != null && !url.isEmpty()) {
                    if (!url.startsWith("http")) {
                        url = "https://www.randstad.es" + url;
                    }

                    if (!tarjeta.contains(url)) {
                        tarjeta.add(url);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer las url de RandStad: " + e.getMessage());
        }

        waitTime("Esperando URLs", 4);
        for (String url : tarjeta) {
            try {
                System.out.println("URL guardada con éxito: " + url);
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Error en el tiempo de espera de las URLs" + e.getMessage());
            }
        }

        // TODO: A partir de aquí ya nos metemos en UNA OFERTA EN CONCRETO.
        tarjeta.forEach(s -> {

            try {
                web.get(s);

                Thread.sleep(3000);

                WebElement salario = web.findElement(By.xpath("//*[contains(text(), '/año') or contains(text(), '/mes')]"));
                WebElement fechaPublicacion = web.findElement(By.xpath("//*[contains(text(), 'publicada el')]"));
                WebElement etiquetaLocalidad = web.findElement(By.xpath("//h3[contains(text(), 'localidad')]/following-sibling::span"));
                String textoLocalidad = etiquetaLocalidad.getDomProperty("textContent").trim();

                repository.save(new JobApplication(
                        s,
                        textoLocalidad,
                        salario.getText(),
                        fechaPublicacion.getText(),
                        jobFilter
                ));

                Thread.sleep(5000);

            } catch (Exception e) {
                System.out.println("Error al procesar la oferta " + s + ": " + e.getMessage());
            }

        });

        web.get("http://localhost:8080/api/listaURLs");

    }
}
