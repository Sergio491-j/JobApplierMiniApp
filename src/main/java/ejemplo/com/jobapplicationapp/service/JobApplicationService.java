package ejemplo.com.jobapplicationapp.service;

import ejemplo.com.jobapplicationapp.repository.JobApplicationRepository;
import ejemplo.com.jobapplicationapp.model.JobApplication;
import ejemplo.com.jobapplicationapp.exceptions.NullListJobException;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
public class JobApplicationService {

    private JobApplicationRepository repository;

    private ArrayList<JobApplication> hola;

    public List<JobApplication> getList() {
        return repository.findAll();
    }


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

                    String url = "https://www.randstad.es/candidatos/ofertas-empleo/q-mozo/";

                    var newWindow = new RandStandScraperService();

                    hola = newWindow.getJobAppliesRT(url, applications);

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
                    salir = true;
                    break;
            }
        }
    }
}

