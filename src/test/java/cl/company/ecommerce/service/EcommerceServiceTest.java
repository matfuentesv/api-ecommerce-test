package cl.company.ecommerce.service;

import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.Rol;
import cl.company.ecommerce.repository.BillRepository;
import cl.company.ecommerce.repository.CustomerRepository;
import cl.company.ecommerce.service.impl.EcommerceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EcommerceServiceTest {

    @InjectMocks
    private EcommerceServiceImpl ecommerceService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void findCustomerTest() {
        Customer customer = new Customer();
        customer.setId("cmundaca");
        customer.setNombre("Constanza");
        when(customerRepository.findById("cmundaca")).thenReturn(Optional.of(customer));
        Optional<Customer> foundCustomer = ecommerceService.findCustomer("cmundaca");
        assertTrue(foundCustomer.isPresent());
        assertEquals("cmundaca", foundCustomer.get().getId());
    }

    @Test
    void findCusomerAllTest() {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer().setId("cmundaca"));
        customers.add(new Customer().setId("mperez"));
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> foundCustomers = ecommerceService.findAllCustomer();
        assertEquals(2, foundCustomers.size());
    }

    @Test
    void saveCustomerTest() {

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
        when(customerRepository.save(any())).thenReturn(customer);
        Customer savedCustomer = ecommerceService.createCustomer(customer);
        assertEquals("mperez", savedCustomer.getId());
    }

}
