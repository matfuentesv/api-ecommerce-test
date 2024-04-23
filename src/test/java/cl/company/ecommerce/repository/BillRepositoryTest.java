package cl.company.ecommerce.repository;

import cl.company.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BillRepositoryTest {

    @Autowired
    private BillRepository billRepository;


    @Test
    void testFindInvoiceById() {
        Factura invoice = new Factura();
        invoice.setId(10L);
        Optional<Factura> findInvoice = billRepository.findById(invoice.getId());
        Assertions.assertTrue(findInvoice.isPresent());
        assertEquals("mperez", findInvoice.get().getCliente().getId());
    }

    @Test
    void testFindInvoiceAll() {


        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");


        Customer cliente1 = new Customer();
        cliente1.setId("mperez");
        cliente1.setNombre("Nombre Cliente 1");
        cliente1.setApellido("Apellido Cliente 1");
        cliente1.setEdad(30);
        cliente1.setTelefono("123456789");
        cliente1.setDireccion("Dirección Cliente 1");
        cliente1.setRoles(rol1);

        // Crear dos productos
        Product producto1 = new Product();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setPrecioUnitario(50);

        // Crear dos detalles de factura
        Detalle_Factura detalle1 = new Detalle_Factura();
        detalle1.setId(6L);
        detalle1.setCantidad(2);
        detalle1.setProducto(producto1);

        // Crear lista de detalles de factura para cada factura
        List<Detalle_Factura> detallesFactura1 = new ArrayList<>();
        detallesFactura1.add(detalle1);

        // Crear dos facturas
        Factura factura1 = new Factura();
        factura1.setId(7L);
        factura1.setFechaFactura("2024-04-22");
        factura1.setCliente(cliente1);
        factura1.setDetalleFactura(detallesFactura1);
        factura1.setTotal(100);
        factura1.setDescuento(10.0);
        factura1.setTotalDescuento(90);

        billRepository.save(factura1);

        List<Factura> allInvoice = billRepository.findAll();
        assertEquals(2, allInvoice.size());
        Assertions.assertTrue(allInvoice.stream().anyMatch(movie -> movie.getFechaFactura().equals("2024-04-22")));
   }

    @Test
    void saveInvoice() {

        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");
        Customer cliente1 = new Customer();
        cliente1.setId("mperez");
        cliente1.setNombre("Nombre Cliente 1");
        cliente1.setApellido("Apellido Cliente 1");
        cliente1.setEdad(30);
        cliente1.setTelefono("123456789");
        cliente1.setDireccion("Dirección Cliente 1");
        cliente1.setRoles(rol1);

        // Crear dos productos
        Product producto1 = new Product();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setPrecioUnitario(50);

        // Crear dos detalles de factura
        Detalle_Factura detalle1 = new Detalle_Factura();
        detalle1.setId(6L);
        detalle1.setCantidad(2);
        detalle1.setProducto(producto1);

        // Crear lista de detalles de factura para cada factura
        List<Detalle_Factura> detallesFactura1 = new ArrayList<>();
        detallesFactura1.add(detalle1);

        // Crear dos facturas
        Factura factura1 = new Factura();
        factura1.setId(7L);
        factura1.setFechaFactura("2024-04-22");
        factura1.setCliente(cliente1);
        factura1.setDetalleFactura(detallesFactura1);
        factura1.setTotal(100);
        factura1.setDescuento(10.0);
        factura1.setTotalDescuento(90);

        final Factura f = billRepository.save(factura1);
        assertNotNull(f.getId());
        assertEquals("2024-04-22", f.getFechaFactura());
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void updateInvoice() {

        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");
        Customer cliente1 = new Customer();
        cliente1.setId("mperez");
        cliente1.setNombre("Nombre Cliente 1");
        cliente1.setApellido("Apellido Cliente 1");
        cliente1.setEdad(30);
        cliente1.setTelefono("123456789");
        cliente1.setDireccion("Dirección Cliente 1");
        cliente1.setRoles(rol1);

        // Crear dos productos
        Product producto1 = new Product();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setPrecioUnitario(50);

        // Crear dos detalles de factura
        Detalle_Factura detalle1 = new Detalle_Factura();
        detalle1.setId(6L);
        detalle1.setCantidad(2);
        detalle1.setProducto(producto1);

        // Crear lista de detalles de factura para cada factura
        List<Detalle_Factura> detallesFactura1 = new ArrayList<>();
        detallesFactura1.add(detalle1);

        // Crear dos facturas
        Factura factura1 = new Factura();
        factura1.setId(7L);
        factura1.setFechaFactura("2024-04-22");
        factura1.setCliente(cliente1);
        factura1.setDetalleFactura(detallesFactura1);
        factura1.setTotal(100);
        factura1.setDescuento(10.0);
        factura1.setTotalDescuento(90);
        Factura savedInvoice = billRepository.save(factura1);

        savedInvoice.setFechaFactura("2024-04-23");
        savedInvoice.setCliente(cliente1);
        savedInvoice.setDetalleFactura(detallesFactura1);
        savedInvoice.setTotal(100);
        savedInvoice.setDescuento(10.0);
        savedInvoice.setTotalDescuento(90);

        Factura updatedInvoice = billRepository.save(savedInvoice);
        assertNotNull(updatedInvoice.getId());
        assertEquals("2024-04-23", updatedInvoice.getFechaFactura());
        assertEquals(100, updatedInvoice.getTotal());
    }

    @Test
    void deleteInvoice() {
        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");
        Customer cliente1 = new Customer();
        cliente1.setId("mperez");
        cliente1.setNombre("Nombre Cliente 1");
        cliente1.setApellido("Apellido Cliente 1");
        cliente1.setEdad(30);
        cliente1.setTelefono("123456789");
        cliente1.setDireccion("Dirección Cliente 1");
        cliente1.setRoles(rol1);

        // Crear dos productos
        Product producto1 = new Product();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setPrecioUnitario(50);

        // Crear dos detalles de factura
        Detalle_Factura detalle1 = new Detalle_Factura();
        detalle1.setId(6L);
        detalle1.setCantidad(2);
        detalle1.setProducto(producto1);

        // Crear lista de detalles de factura para cada factura
        List<Detalle_Factura> detallesFactura1 = new ArrayList<>();
        detallesFactura1.add(detalle1);

        // Crear dos facturas
        Factura factura1 = new Factura();
        factura1.setId(7L);
        factura1.setFechaFactura("2024-04-22");
        factura1.setCliente(cliente1);
        factura1.setDetalleFactura(detallesFactura1);
        factura1.setTotal(100);
        factura1.setDescuento(10.0);
        factura1.setTotalDescuento(90);
        Factura savedInvoice = billRepository.save(factura1);

        savedInvoice.setFechaFactura("2024-04-23");
        savedInvoice.setCliente(cliente1);
        savedInvoice.setDetalleFactura(detallesFactura1);
        savedInvoice.setTotal(100);
        savedInvoice.setDescuento(10.0);
        savedInvoice.setTotalDescuento(90);

        Factura saveInvoice = billRepository.save(savedInvoice);


        assertTrue(billRepository.findById(saveInvoice.getId()).isPresent());


        billRepository.deleteById(saveInvoice.getId());


        Optional<Factura> deletedInvoice = billRepository.findById(saveInvoice.getId());
        assertFalse(deletedInvoice.isPresent());
    }
}
