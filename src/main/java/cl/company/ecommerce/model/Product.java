package cl.company.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;


@Entity(name = "Producto")
public class Product extends RepresentationModel<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    @NotBlank(message = "No puede ingresar una descripcion vacia")
    @NotNull(message = "No puede ingresar una descripcion nula")
    private String nombre;

    @Column(name = "descripcion")
    @NotBlank(message = "No puede ingresar una descripcion vacia")
    @NotNull(message = "No puede ingresar una descripcion nula")
    private String descripcion;

    @Column(name = "PRECIOUNITARIO")
    @Positive(message = "El precioUnitario debe ser mayor a cero")
    private int precioUnitario;


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

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


}
