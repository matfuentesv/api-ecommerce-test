package cl.company.ecommerce.service.impl;

import cl.company.ecommerce.exception.ErrorResponse;
import cl.company.ecommerce.model.Factura;
import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.Product;
import cl.company.ecommerce.model.Rol;
import cl.company.ecommerce.repository.UserRepository;
import cl.company.ecommerce.service.EcommerceService;
import cl.company.ecommerce.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EcommerceService ecommerceService;

    @Override
    public ResponseEntity<Object> findUser(String user, String password) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findUser(user));
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findAllRoles(String user, String password) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findAllRoles());
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findRolByName(String user, String password, String value) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findRolByName(value));
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> createRol(String user, String password, Rol rol) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            final Rol createdRol = ecommerceService.createRol(rol);
            if(createdRol == null){
                log.error("Algunos de los parámetros no se ingresaron");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
            }else {
                return ResponseEntity.ok(rol);
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> updateRol(String user, String password, Long id, Rol rol) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            if(ecommerceService.existsRolById(id)){
                rol.setId(id);
                return ResponseEntity.ok(ecommerceService.updateRol(rol));
            }   else {
                log.error("No se puedo actualizar el rol,no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se puedo actualizar el rol,no existe"));
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteRol(String user, String password, Long id) {
        // Verificar la autenticación del usuario
        boolean userValid = userRepository.findByUserPassword(user, password).isPresent();
        if (!userValid) {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }

        // Verificar si el rol existe
        if (!ecommerceService.existsRolById(id)) {
            log.error("No se puede eliminar el rol, no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El rol no existe"));
        }

        // Eliminar al rol
        ecommerceService.deleteRol(id);
        return ResponseEntity.ok("Eliminación exitosa");
    }

    @Override
    public ResponseEntity<Object> findCustomer(String user, String password, String id) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findCustomer(id));
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findAllCustomer(String user, String password) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findAllCustomer());
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> createCustomer(String user, String password, Customer customer) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            if(!ecommerceService.existsCustomerById(customer.getId())){
                final Customer createdcustomer = ecommerceService.createCustomer(customer);
                if(createdcustomer == null){
                    log.error("Algunos de los parámetros no se ingresaron");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
                }else {
                    return ResponseEntity.ok(customer);
                }
            }   else {
                log.error("No se puedo crear el cliente,ya existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se puedo crear el cliente,ya existe"));
            }

        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> updateCustomer(String user, String password, Customer customer) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            if(ecommerceService.existsCustomerById(customer.getId())){
                return ResponseEntity.ok(ecommerceService.updateCustomer(customer));
            }   else {
                log.error("No se puedo actualizar el customer,no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se puedo actualizar el customer,no existe"));
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteCustomer(String user, String password, String id) {
        // Verificar la autenticación del usuario
        boolean userValid = userRepository.findByUserPassword(user, password).isPresent();
        if (!userValid) {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }

        // Verificar si el customer existe
        if (!ecommerceService.existsCustomerById(id)) {
            log.error("No se puede eliminar el customer, no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El customer no existe"));
        }

        // Eliminar al customer
        ecommerceService.deleteCustomer(id);
        return ResponseEntity.ok("Eliminación exitosa");
    }

    @Override
    public ResponseEntity<Object> findProduct(String user, String password, Long id) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findProduct(id));
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findAllProduct(String user, String password) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findAllProduct());
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> createProduct(String user, String password, Product product) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
                final Product createdProduct = ecommerceService.createProduct(product);
                if(createdProduct == null){
                    log.error("Algunos de los parámetros no se ingresaron");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
                }else {
                    return ResponseEntity.ok(product);
                }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(String user, String password, Product product) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            if(ecommerceService.existsProductById(product.getId())){
                return ResponseEntity.ok(ecommerceService.updateProduct(product));
            }   else {
                log.error("No se puedo actualizar el product,no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No se puedo actualizar el product,no existe"));
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String user, String password, Long id) {
        // Verificar la autenticación del usuario
        boolean userValid = userRepository.findByUserPassword(user, password).isPresent();
        if (!userValid) {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }

        // Verificar si el customer existe
        if (!ecommerceService.existsProductById(id)) {
            log.error("No se puede eliminar el product, no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El product no existe"));
        }

        // Eliminar al rol
        ecommerceService.deleteProduct(id);
        return ResponseEntity.ok("Eliminación exitosa");
    }

    @Override
    public ResponseEntity<Object> findInvoice(String user, String password, Long id) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findInvoice(id));
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findAllInvoice(String user, String password) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(ecommerceService.findAllInvoice());
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> createInvoice(String user, String password, Factura factura) {
        final boolean userValid = userRepository.findByUserPassword(user,password).isPresent();
        if(userValid){
            final Factura createdFactura = ecommerceService.createinvoice(factura);
            if(createdFactura == null){
                log.error("Algunos de los parámetros no se ingresaron");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
            }else {
                return ResponseEntity.ok(factura);
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteInvoice(String user, String password, Long id) {
        // Verificar la autenticación del usuario
        boolean userValid = userRepository.findByUserPassword(user, password).isPresent();
        if (!userValid) {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }

        // Verificar si el customer existe
        if (!ecommerceService.existsBillById(id)) {
            log.error("No se puede eliminar el product, no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("la factura no existe"));
        }

        // Eliminar al rol
        ecommerceService.deleteInvoice(id);
        return ResponseEntity.ok("Eliminación exitosa");
    }
}
