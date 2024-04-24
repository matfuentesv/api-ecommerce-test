package cl.company.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

@Entity(name = "Rol")
public class Rol extends RepresentationModel<Rol> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    @NotBlank(message = "No puede ingresar un nombre de rol vacio")
    @NotNull(message = "No puede ingresar un nombre de rol nulo")
    private String nombre;

    @Column(name = "descripcion")
    @NotBlank(message = "No puede ingresar una descripcion de rol vacio")
    @NotNull(message = "No puede ingresar una descripcion de rol nulo")
    private String descripcion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
