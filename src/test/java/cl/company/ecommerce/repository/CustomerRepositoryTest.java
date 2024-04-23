package cl.company.ecommerce.repository;

import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.Factura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

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

    

}
