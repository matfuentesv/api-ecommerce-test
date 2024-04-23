package cl.company.ecommerce.repository;

import cl.company.ecommerce.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Factura,Long> {


}
