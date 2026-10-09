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
    private String modalidad;
    private String salario;
    private String tipo_contrato;
    private String tipo_jornada;
    private String fechaPublicacion;
    private String estado = "SIN ENTREGAR";

    private LocalDate fechaAplicacion = LocalDate.now();
    private LocalTime horaAplicacion = LocalTime.now();

    public JobApplication(String localidad, String modalidad, String salario,
                          String tipo_contrato, String tipo_jornada) {
        this.localidad = localidad;
        this.modalidad = modalidad;
        this.salario = salario;
        this.tipo_contrato = tipo_contrato;
        this.tipo_jornada = tipo_jornada;
    }

    @PrePersist
    public void singApplicationTimes() {
        if (fechaAplicacion == null) fechaAplicacion = LocalDate.now();
        if (horaAplicacion == null) horaAplicacion = LocalTime.now();
    }

}