package ejemplo.com.jobapplicationapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {

    @Id
    @Column(length = 500)
    private String urlVacante;

    private String localidad;
    private String salario;
    private String fechaPublicacion;
    private String estado = "SIN ENTREGAR";
    private String trabajo;

    private LocalDate fechaAplicacion;
    private LocalTime horaAplicacion;

    public JobApplication(String urlVacante, String localidad, String salario, String fechaPublicacion, String trabajo) {
        this.urlVacante = urlVacante;
        this.localidad = localidad;
        this.salario = salario;
        this.fechaPublicacion = fechaPublicacion;
        this.trabajo = trabajo;
    }

    @PrePersist
    public void singApplicationTimes() {
        if (fechaAplicacion == null) fechaAplicacion = LocalDate.now();
        if (horaAplicacion == null) horaAplicacion = LocalTime.now();
    }

}