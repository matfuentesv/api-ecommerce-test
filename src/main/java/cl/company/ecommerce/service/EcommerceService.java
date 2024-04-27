package cl.company.ecommerce.service;


import cl.company.ecommerce.model.*;

import java.util.List;
import java.util.Optional;

public interface EcommerceService {

    boolean findUser(String user, String password);
    Optional<User> findUser(String user);
    List<User> findAllUser();
    List<Rol>findAllRoles();
    Optional<Rol>findRolByName(String value);
    boolean existsRolById(Long id);
    Rol createRol(Rol rol);
    Rol updateRol(Rol rol);
    void deleteRol(Long id);
    Optional<Customer> findCustomer(String id);
    boolean existsCustomerById(String id);
    List<Customer>findAllCustomer();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(String id);
    boolean existsProductById(Long id);
    Optional<Product> findProduct(Long id);
    List<Product>findAllProduct();
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);


    boolean existsBillById(Long id);
    List<Factura>findAllInvoice();
    Optional<Factura> findInvoice(Long id);


}
