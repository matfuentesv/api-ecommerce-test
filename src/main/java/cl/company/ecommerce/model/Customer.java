package cl.company.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Entity(name = "Cliente")
public class Customer {

    @Id
    @Column(name = "id")
    @NotBlank(message = "No puede ingresar un id vacio")
    @NotNull(message = "No puede ingresar un id nulo")
    private String id;

    @Column(name = "nombre")
    @NotBlank(message = "No puede ingresar un nombre vacio")
    @NotNull(message = "No puede ingresar un nombre nulo")
    private String nombre;

    @Column(name = "apellido")
    @NotBlank(message = "No puede ingresar un apellido vacio")
    @NotNull(message = "No puede ingresar un apellido nulo")
    private String apellido;
    @Column(name = "edad")
    @Positive(message = "La edad debe ser mayor a cero")
    private Integer edad;

    @Column(name = "telefono")
    @NotBlank(message = "No puede ingresar un telefono vacio")
    @NotNull(message = "No puede ingresar un telefono nulo")
    private String telefono;

    @Column(name = "direccion")
    @NotBlank(message = "No puede ingresar una direccion vacio")
    @NotNull(message = "No puede ingresar una direccion nulo")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Rol getRoles() {
        return rol;
    }

    public void setRoles(Rol rol) {
        this.rol = rol;
    }
}
