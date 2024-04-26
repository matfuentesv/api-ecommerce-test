package cl.company.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

@Entity(name = "Usuario")
public class User extends RepresentationModel<User> {

    @Id
    private Long id;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "email")
    private String email;
    @Column(name = "fecha_registro")
    private String fechaRegistro;
    @Column(name = "ultima_conexion")
    private String ultimaConexion;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsuario() {
        return usuario;
    }

    public User setUsuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public String getContrasena() {
        return contrasena;
    }

    public User setContrasena(String contrasena) {
        this.contrasena = contrasena;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public User setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public String getUltimaConexion() {
        return ultimaConexion;
    }

    public User setUltimaConexion(String ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
        return this;
    }
}
