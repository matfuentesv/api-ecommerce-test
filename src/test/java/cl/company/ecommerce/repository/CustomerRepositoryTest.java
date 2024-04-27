package cl.company.ecommerce.repository;

import cl.company.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setId("mperez");
        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());
        Assertions.assertTrue(findCustomer.isPresent());
        assertEquals("mperez", findCustomer.get().getId());
    }

    @Test
    void testFindCustomerAll() {

        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");

        Customer customer = new Customer();
        customer.setId("mperez");
        customer.setNombre("Nombre Cliente 1");
        customer.setApellido("Apellido Cliente 1");
        customer.setEdad(30);
        customer.setTelefono("123456789");
        customer.setDireccion("Dirección Cliente 1");
        customer.setRoles(rol1);

        customerRepository.save(customer);

        List<Customer> allCustomer = customerRepository.findAll();
        Assertions.assertTrue(allCustomer.stream().anyMatch(x -> x.getId().equalsIgnoreCase("mperez")));
        Assertions.assertTrue(allCustomer.stream().anyMatch(x -> x.getId().equalsIgnoreCase("carriagada")));
        Assertions.assertTrue(allCustomer.stream().anyMatch(x -> x.getId().equalsIgnoreCase("mfuentes")));
    }
    @Test
    void saveCustomer() {

        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");
        Customer customer = new Customer();
        customer.setId("mperez");
        customer.setNombre("Nombre Cliente 1");
        customer.setApellido("Apellido Cliente 1");
        customer.setEdad(30);
        customer.setTelefono("123456789");
        customer.setDireccion("Dirección Cliente 1");
        customer.setRoles(rol1);
        final Customer c = customerRepository.save(customer);
        assertNotNull(c.getId());
        assertEquals("123456789", c.getTelefono());
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void updateCustomer() {

        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");
        Customer customer = new Customer();
        customer.setId("mperez");
        customer.setNombre("Nombre Cliente 1");
        customer.setApellido("Apellido Cliente 1");
        customer.setEdad(30);
        customer.setTelefono("123456789");
        customer.setDireccion("Dirección Cliente 1");
        customer.setRoles(rol1);


        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setId("mperez");
        savedCustomer.setNombre("Nombre Cliente 1");
        savedCustomer.setApellido("Apellido Cliente 1");
        savedCustomer.setEdad(30);
        savedCustomer.setTelefono("9999999");
        savedCustomer.setDireccion("Dirección Cliente 1");
        savedCustomer.setRoles(rol1);

        Customer updatedCustomer = customerRepository.save(savedCustomer);
        assertNotNull(updatedCustomer.getId());
        assertEquals("mperez", updatedCustomer.getId());
        assertEquals(30, updatedCustomer.getEdad());
    }

    @Test
    void deleteCustomer() {
        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");
        Customer customer = new Customer();
        customer.setId("mperez");
        customer.setNombre("Nombre Cliente 1");
        customer.setApellido("Apellido Cliente 1");
        customer.setEdad(30);
        customer.setTelefono("123456789");
        customer.setDireccion("Dirección Cliente 1");
        customer.setRoles(rol1);


        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setId("mperez");
        savedCustomer.setNombre("Nombre Cliente 1");
        savedCustomer.setApellido("Apellido Cliente 1");
        savedCustomer.setEdad(25);
        savedCustomer.setTelefono("123456789");
        savedCustomer.setDireccion("Dirección Cliente 1");
        savedCustomer.setRoles(rol1);

        Customer saveCudtomer = customerRepository.save(savedCustomer);


        assertTrue(customerRepository.findById(saveCudtomer.getId()).isPresent());


        customerRepository.deleteById(saveCudtomer.getId());


        Optional<Customer> deletedCustomer = customerRepository.findById(saveCudtomer.getId());
        assertFalse(deletedCustomer.isPresent());
    }
    

}
