package cl.company.ecommerce.service;

import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.repository.BillRepository;
import cl.company.ecommerce.repository.CustomerRepository;
import cl.company.ecommerce.service.impl.EcommerceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

}
