package cl.company.ecommerce.service;

import cl.company.ecommerce.model.Factura;
import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.Product;
import cl.company.ecommerce.model.Rol;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<Object>findUser(String user,String password);
    ResponseEntity<Object>findAllRoles(String user,String password);
    ResponseEntity<Object>findRolByName(String user,String password,String value);
    ResponseEntity<Object> createRol(String user, String password, Rol rol);
    ResponseEntity<Object> updateRol(String user,String password,Long id,Rol rol);
    ResponseEntity<Object> deleteRol(String user,String password,Long id);
    ResponseEntity<Object>findCustomer(String user,String password,String id);
    ResponseEntity<Object>findAllCustomer(String user,String password);
    ResponseEntity<Object> createCustomer(String user, String password, Customer customer);
    ResponseEntity<Object> updateCustomer(String user,String password,Customer customer);
    ResponseEntity<Object> deleteCustomer(String user,String password,String id);

    ResponseEntity<Object>findProduct(String user,String password,Long id);
    ResponseEntity<Object>findAllProduct(String user,String password);
    ResponseEntity<Object> createProduct(String user, String password,Product product);
    ResponseEntity<Object> updateProduct(String user,String password,Product product);
    ResponseEntity<Object> deleteProduct(String user,String password,Long id);


    ResponseEntity<Object>findInvoice(String user,String password,Long id);
    ResponseEntity<Object>findAllInvoice(String user,String password);
    ResponseEntity<Object> createInvoice(String user, String password, Factura factura);
    ResponseEntity<Object> deleteInvoice(String user,String password,Long id);


}
