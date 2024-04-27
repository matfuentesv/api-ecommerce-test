package cl.company.ecommerce.repository;

import cl.company.ecommerce.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Rol,Long> {
    @Query("SELECT u FROM Rol u WHERE u.nombre = :nombre")
    Optional<Rol> findRolByName(@Param("nombre") String nombre);
}
