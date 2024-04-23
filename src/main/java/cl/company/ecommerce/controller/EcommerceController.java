package cl.company.ecommerce.controller;


import cl.company.ecommerce.exception.ErrorResponse;
import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.Product;
import cl.company.ecommerce.model.Rol;
import cl.company.ecommerce.service.LoginService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EcommerceController {

    private static final Logger log = LoggerFactory.getLogger(EcommerceController.class);

    @Autowired
    LoginService loginService;


    //Interceptor
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Error de violación de restricción única: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
    }

    // Endpoint que Busca un usuario
    @GetMapping("/findUser")
    public ResponseEntity<Object> findUser(@RequestHeader(value = "user",required = false) String user,
                                           @RequestHeader(value = "password",required = false) String password) {
        if ((user == null || password == null) || (user.isEmpty() || password.isEmpty())) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findUser(user,password));
    }

    // Endpoint que busca todos roles
    @GetMapping("/findAllRoles")
    public ResponseEntity<Object> findAllRoles(@RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {
        if ((user == null || password == null) || (user.isEmpty() || password.isEmpty())) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findAllRoles(user,password));
    }

    // Endpoint que busca un rol por su nombre
    @GetMapping("/findRolByName/{name}")
    public ResponseEntity<Object> findRolByName(@PathVariable String name,
                                             @RequestHeader(value = "user",required = false) String user,
                                             @RequestHeader(value = "password",required = false) String password) {

        if (StringUtils.containsWhitespace(name) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findRolByName(user, password, name));
    }

    //Crea un nuevo rol
    @PostMapping("/createRol")
    public ResponseEntity<Object> createRol(@Valid @RequestBody Rol rol,
                                                @RequestHeader(value = "user",required = false) String user,
                                                @RequestHeader(value = "password",required = false) String password,
                                                BindingResult bindingResult) throws MethodArgumentNotValidException {

        if ((user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        return ResponseEntity.ok(loginService.createRol(user,password,rol));
    }

    //Actualiza un rol
    @PutMapping("/updateRol/{id}")
    public ResponseEntity<Object> updateRol(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password,
                                               @Valid @RequestBody Rol rol,
                                               BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return ResponseEntity.ok(loginService.updateRol(user, password, Long.parseLong(id), rol));
    }

    //Elimina un rol
    @DeleteMapping("/deleteRol/{id}")
    public ResponseEntity<Object> deleteRol(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        return ResponseEntity.ok(loginService.deleteRol(user, password, Long.parseLong(id)));
    }

    // Endpoint que busca todos los clientes
    @GetMapping("/findAllCustomer")
    public ResponseEntity<Object> findAllCustomer(@RequestHeader(value = "user",required = false) String user,
                                                  @RequestHeader(value = "password",required = false) String password) {
        if ((user == null || password == null) || (user.isEmpty() || password.isEmpty())) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findAllCustomer(user,password));
    }


    // Endpoint que busca un cliente por su id
    @GetMapping("/findCustomer/{customerId}")
    public ResponseEntity<Object> findCustomer(@PathVariable String customerId,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {



        if (StringUtils.containsWhitespace(customerId) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findCustomer(user, password, customerId));
    }


    //Crea un nuevo customer
    @PostMapping("/createCustomer")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer,
                                                 @RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password,
                                                 BindingResult bindingResult) throws MethodArgumentNotValidException {

        if ((user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        return ResponseEntity.ok(loginService.createCustomer(user,password,customer));
    }

    //Actualiza un customer
    @PutMapping("/updateCustomer")
    public ResponseEntity<Object>updateCustomer(@RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password,
                                               @Valid @RequestBody Customer customer,
                                               BindingResult bindingResult) throws MethodArgumentNotValidException {

        if ((user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return ResponseEntity.ok(loginService.updateCustomer(user, password,customer));
    }

    //Elimina un customer
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        return ResponseEntity.ok(loginService.deleteCustomer(user, password,id));
    }


    // Endpoint que busca todos los productos
    @GetMapping("/findAllProduct")
    public ResponseEntity<Object> findAllProduct(@RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password) {
        if ((user == null || password == null) || (user.isEmpty() || password.isEmpty())) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findAllProduct(user,password));
    }

    // Endpoint que busca un producto por su id
    @GetMapping("/findProduct/{id}")
    public ResponseEntity<Object> findProduct(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {



        if (StringUtils.containsWhitespace(id) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findProduct(user, password, Long.parseLong(id)));
    }

    //Crea un nuevo producto
    @PostMapping("/createProduct")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product,
                                                 @RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password,
                                                 BindingResult bindingResult) throws MethodArgumentNotValidException {

        if ((user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        return ResponseEntity.ok(loginService.createProduct(user,password,product));
    }

    //Actualiza un producto
    @PutMapping("/updateProduct")
    public ResponseEntity<Object>updateProduct(@RequestHeader(value = "user",required = false) String user,
                                                @RequestHeader(value = "password",required = false) String password,
                                                @Valid @RequestBody Product product,
                                                BindingResult bindingResult) throws MethodArgumentNotValidException {

        if ((user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return ResponseEntity.ok(loginService.updateProduct(user, password,product));
    }

    //Elimina un customer
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object>deleteProduct(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        return ResponseEntity.ok(loginService.deleteProduct(user, password,Long.parseLong(id)));
    }


    // Endpoint que busca todos las facturas
    @GetMapping("/findAllInvoice")
    public ResponseEntity<Object> findAllInvoice(@RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password) {
        if ((user == null || password == null) || (user.isEmpty() || password.isEmpty())) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findAllInvoice(user,password));
    }

    // Endpoint que busca un producto por su id
    @GetMapping("/findInvoice/{id}")
    public ResponseEntity<Object> findBill(@PathVariable String id,
                                              @RequestHeader(value = "user",required = false) String user,
                                              @RequestHeader(value = "password",required = false) String password) {



        if (StringUtils.containsWhitespace(id) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(loginService.findInvoice(user, password, Long.parseLong(id)));
    }


    //Elimina una factura
    @DeleteMapping("/deleteInvoice/{id}")
    public ResponseEntity<Object>deleteInvoice(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || (user == null || password == null) || user.isEmpty() || password.isEmpty()) {
            log.error("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Algunos de los parámetros no se ingresaron"));
        }

        return ResponseEntity.ok(loginService.deleteInvoice(user, password,Long.parseLong(id)));
    }

}
