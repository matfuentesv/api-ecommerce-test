package cl.company.ecommerce.service.impl;

import cl.company.ecommerce.model.*;
import cl.company.ecommerce.repository.*;
import cl.company.ecommerce.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public boolean findUser(String user, String password) {
        return userRepository.findByUserPassword(user,password).isPresent();
    }

    @Override
    public Optional<User> findUser(String user) {
        return userRepository.findUser(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
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

}
