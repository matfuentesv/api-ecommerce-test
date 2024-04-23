package cl.company.ecommerce.service.impl;

import cl.company.ecommerce.model.*;
import cl.company.ecommerce.repository.*;
import cl.company.ecommerce.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class EcommerceServiceImpl implements EcommerceService {


    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BillRepository billRepository;


    @Override
    public Optional<User> findUser(String user) {
        return userRepository.findUser(user);
    }

    @Override
    public List<Rol> findAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Optional<Rol> findRolByName(String value) {
        return rolesRepository.findRolByName(value);
    }

    @Override
    public boolean existsRolById(Long id) {
        return rolesRepository.existsById(id);
    }

    @Override
    public Rol createRol(Rol rol) {
        return rolesRepository.save(rol);
    }

    @Override
    public Rol updateRol(Rol rol) {
        return rolesRepository.save(rol);
    }

    @Override
    public void deleteRol(Long id) {
        rolesRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> findCustomer(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public boolean existsCustomerById(String id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existsProductById(Long id) {
        return productRepository.existsById(id);
    }


    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
       productRepository.deleteById(id);
    }

    @Override
    public boolean existsBillById(Long id) {
        return billRepository.existsById(id);
    }

    @Override
    public List<Factura> findAllInvoice() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Factura> findInvoice(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public Factura createinvoice(Factura factura) {
        return billRepository.save(factura);
    }

    @Override
    public void deleteInvoice(Long id) {
        billRepository.deleteById(id);
    }


    public BigDecimal calculateDiscount(Customer customer, DayOfWeek dayOfWeek) {
        // Verificar si el cliente está registrado
        if (customer != null) {
            // Aplicar un descuento según el día de la semana
            return switch (dayOfWeek) {
                case MONDAY -> new BigDecimal("0.10"); // 10% de descuento los lunes
                case TUESDAY -> new BigDecimal("0.08"); // 8% de descuento los martes
                case WEDNESDAY -> new BigDecimal("0.06"); // 6% de descuento los miércoles
                case THURSDAY -> new BigDecimal("0.05"); // 5% de descuento los jueves
                case FRIDAY -> new BigDecimal("0.04"); // 4% de descuento los viernes
                case SATURDAY -> new BigDecimal("0.03"); // 3% de descuento los viernes
                default -> BigDecimal.ZERO; // Sin descuento por defecto para otros días
            };
        } else {
            return BigDecimal.ZERO; // Sin descuento si el cliente no está registrado
        }
    }
}
