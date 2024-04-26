package cl.company.ecommerce.controller;


import cl.company.ecommerce.exception.EcommerceNotFoundException;
import cl.company.ecommerce.exception.ErrorResponse;
import cl.company.ecommerce.model.*;
import cl.company.ecommerce.service.EcommerceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class EcommerceController {

    private static final Logger log = LoggerFactory.getLogger(EcommerceController.class);


    @Autowired
    EcommerceService ecommerceService;


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

    @ExceptionHandler(EcommerceNotFoundException.class)
    public ResponseEntity<String> handleRequestException(EcommerceNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }


    // Endpoint que Busca todos los usuarios
    @GetMapping("/findAllUsers")
    public CollectionModel<EntityModel<User>> findAllUsers(@RequestHeader(value = "user", required = false) String user,
                                                           @RequestHeader(value = "password", required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            throw new EcommerceNotFoundException(new ErrorResponse("Algunos de los parámetros no se ingresaron"), HttpStatus.BAD_REQUEST);
        }

        // Verificar la validez del usuario
        boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            throw new EcommerceNotFoundException(new ErrorResponse("No está autorizado para ejecutar esta petición"), HttpStatus.UNAUTHORIZED);
        }

        final List<User> users = ecommerceService.findAllUser();
        final List<EntityModel<User>> userModels = users.stream()
                .map(u -> EntityModel.of(u,
                        linkTo(methodOn(EcommerceController.class).findUser(user, password)).withSelfRel(),
                        linkTo(methodOn(EcommerceController.class).findAllUsers(user, password)).withRel("usuarios")))
                .collect(Collectors.toList());
        return CollectionModel.of(userModels, linkTo(methodOn(EcommerceController.class).findAllUsers(user, password)).withSelfRel());
    }

    // Endpoint que Busca un usuario
    @GetMapping("/findUser")
    public EntityModel<?> findUser(@RequestHeader(value = "user", required = false) String user,
                                   @RequestHeader(value = "password", required = false) String password) {
        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        // Verificar la validez del usuario
        boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            return EntityModel.of(new ErrorResponse("No está autorizado para ejecutar esta petición"));
        }

        // Buscar al usuario en el servicio de ecommerce
        Optional<User> optionalUser = ecommerceService.findUser(user);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            log.info("Usuario encontrado: {}", foundUser.getUsuario());
            return EntityModel.of(EntityModel.of(foundUser,
                    linkTo(methodOn(this.getClass()).findUser(user, password)).withSelfRel()));
        } else {
            log.warn("No se encontró información para el usuario: {}", user);
            return EntityModel.of(new ErrorResponse("No se encontró información para el usuario: " + user));
        }
    }

    // Endpoint que busca todos roles
    @GetMapping("/findAllRoles")
    public CollectionModel<EntityModel<Rol>> findAllRoles(@RequestHeader(value = "user",required = false) String user,
                                                          @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            throw new EcommerceNotFoundException(new ErrorResponse("Algunos de los parámetros no se ingresaron"), HttpStatus.BAD_REQUEST);
        }

        // Verificar la validez del usuario
        boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            throw new EcommerceNotFoundException(new ErrorResponse("No está autorizado para ejecutar esta petición"), HttpStatus.UNAUTHORIZED);
        }

        final List<Rol> roles = ecommerceService.findAllRoles();
        final List<EntityModel<Rol>> rolModels = roles.stream()
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(EcommerceController.class).findRolByName(r.getNombre(),user,password)).withSelfRel(),
                        linkTo(methodOn(EcommerceController.class).findAllUsers(user, password)).withRel("usuarios")))
                .collect(Collectors.toList());
        return CollectionModel.of(rolModels, linkTo(methodOn(EcommerceController.class).findAllRoles(user,password)).withSelfRel());

    }

    // Endpoint que busca un rol por su nombre
    @GetMapping("/findRolByName/{name}")
    public EntityModel<?> findRolByName(@PathVariable String name,
                                        @RequestHeader(value = "user", required = false) String user,
                                        @RequestHeader(value = "password", required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        // Verificar la validez del usuario
        final boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            return EntityModel.of(new ErrorResponse("No está autorizado para ejecutar esta petición"));
        }

        final Optional<Rol> optionalRol = ecommerceService.findRolByName(name);
        if (optionalRol.isPresent()) {
            Rol foundRol = optionalRol.get();
            log.info("Usuario encontrado: {}", foundRol.getNombre());
            return EntityModel.of(EntityModel.of(foundRol,
                    linkTo(methodOn(this.getClass()).findUser(user, password)).withSelfRel()));
        } else {
            log.warn("No se encontró información para el usuario: {}", user);
            return EntityModel.of(new ErrorResponse("No se encontró información para el usuario: " + user));
        }

    }

    //Crea un nuevo rol
    @PostMapping("/createRol")
    public EntityModel<?> createRol(@Valid @RequestBody Rol rol,
                                            @RequestHeader(value = "user",required = false) String user,
                                            @RequestHeader(value = "password",required = false) String password,
                                            BindingResult bindingResult) throws MethodArgumentNotValidException {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        final Rol createdRol = ecommerceService.createRol(rol);
        return EntityModel.of(createdRol,
                linkTo(methodOn(this.getClass()).findRolByName(createdRol.getNombre(),user,password)).withSelfRel(),
                linkTo(methodOn(this.getClass()).findAllRoles(user,password)).withRel("all-roles"));

    }

    //Actualiza un rol
    @PutMapping("/updateRol/{id}")
    public EntityModel<?>updateRol(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password,
                                               @Valid @RequestBody Rol rol,
                                               BindingResult bindingResult) throws MethodArgumentNotValidException {

        if(StringUtils.containsWhitespace(id)) {
            log.error("Algunos de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Debe ingresar el id"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        final Rol updatedRol = ecommerceService.updateRol(rol);
        return EntityModel.of(updatedRol,
                linkTo(methodOn(this.getClass()).findRolByName(updatedRol.getNombre(),user,password)).withSelfRel(),
                linkTo(methodOn(this.getClass()).findAllRoles(user,password)).withRel("all-roles"));
    }

    //Elimina un rol
    @DeleteMapping("/deleteRol/{id}")
    public EntityModel<?>deleteRol(@PathVariable String id,
                                    @RequestHeader(value = "user",required = false) String user,
                                    @RequestHeader(value = "password",required = false) String password) {

        if(StringUtils.containsWhitespace(id)) {
            log.error("Algunos de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Debe ingresar el id"));
        }

        final boolean existRol = ecommerceService.existsRolById(Long.parseLong(id));
        if(existRol){
            ecommerceService.deleteRol(Long.parseLong(id));
            return EntityModel.of(new ErrorResponse("Rol eliminado exitosamente"));
        }
        return EntityModel.of(new ErrorResponse("No se pudo eliminar el rol con id:"+id));
    }

    // Endpoint que busca todos los clientes
    @GetMapping("/findAllCustomer")
    public CollectionModel<EntityModel<Customer>>findAllCustomer(@RequestHeader(value = "user",required = false) String user,
                                                                 @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            throw new EcommerceNotFoundException(new ErrorResponse("Algunos de los parámetros no se ingresaron"), HttpStatus.BAD_REQUEST);
        }

        // Verificar la validez del usuario
        boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            throw new EcommerceNotFoundException(new ErrorResponse("No está autorizado para ejecutar esta petición"), HttpStatus.UNAUTHORIZED);
        }

        final List<Customer> customers = ecommerceService.findAllCustomer();
        final List<EntityModel<Customer>> customerModels = customers.stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(EcommerceController.class).findCustomer(customer.getId(), user, password)).withSelfRel(),
                        linkTo(methodOn(EcommerceController.class).findAllCustomer(user, password)).withRel("customers")))
                .collect(Collectors.toList());
        return CollectionModel.of(customerModels, linkTo(methodOn(EcommerceController.class).findAllCustomer(user, password)).withSelfRel());


    }




    // Endpoint que busca un cliente por su id
    @GetMapping("/findCustomer/{customerId}")
    public EntityModel<?>findCustomer(@PathVariable String customerId,
                                      @RequestHeader(value = "user",required = false) String user,
                                      @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        // Verificar la validez del usuario
        final boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            return EntityModel.of(new ErrorResponse("No está autorizado para ejecutar esta petición"));
        }

        final Optional<Customer> optionalCustomer = ecommerceService.findCustomer(customerId);
        if (optionalCustomer.isPresent()) {
            Customer foundCustomer = optionalCustomer.get();
            log.info("Customer encontrado: {}", foundCustomer.getNombre());
            return EntityModel.of(EntityModel.of(foundCustomer,
                    linkTo(methodOn(this.getClass()).findCustomer(customerId,user, password)).withSelfRel()));
        } else {
            log.warn("No se encontró información para el customer: {}", user);
            return EntityModel.of(new ErrorResponse("No se encontró información para el customer: " + customerId));
        }
    }


    //Crea un nuevo customer
    @PostMapping("/createCustomer")
    public EntityModel<?> createCustomer(@Valid @RequestBody Customer customer,
                                                 @RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password,
                                                 BindingResult bindingResult) throws MethodArgumentNotValidException {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        final Customer createdCustomer = ecommerceService.createCustomer(customer);
        return EntityModel.of(createdCustomer,
                linkTo(methodOn(this.getClass()).findCustomer(createdCustomer.getId(),user,password)).withSelfRel(),
                linkTo(methodOn(this.getClass()).findAllCustomer(user,password)).withRel("all-customers"));

    }

    //Actualiza un customer
    @PutMapping("/updateCustomer")
    public EntityModel<?>updateCustomer(@RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password,
                                               @Valid @RequestBody Customer customer,
                                               BindingResult bindingResult) throws MethodArgumentNotValidException {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        final Customer updatedCustomer = ecommerceService.updateCustomer(customer);
        return EntityModel.of(updatedCustomer,
                linkTo(methodOn(this.getClass()).findCustomer(updatedCustomer.getId(),user,password)).withSelfRel(),
                linkTo(methodOn(this.getClass()).findAllCustomer(user,password)).withRel("all-customers"));
    }

    //Elimina un customer
    @DeleteMapping("/deleteCustomer/{id}")
    public EntityModel<?>deleteCustomer(@PathVariable String id,
                                                 @RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password) {
        if(StringUtils.containsWhitespace(id)) {
            log.error("Algunos de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Debe ingresar el id"));
        }
        final boolean existCustomer = ecommerceService.existsCustomerById(id);
        if(existCustomer){
            ecommerceService.deleteCustomer(id);
            return EntityModel.of(new ErrorResponse("Customer eliminado exitosamente"));
        }
        return EntityModel.of(new ErrorResponse("No se pudo eliminar el customer con id:"+id));
    }


    // Endpoint que busca todos los productos
    @GetMapping("/findAllProduct")
    public CollectionModel<EntityModel<Product>> findAllProduct(@RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            throw new EcommerceNotFoundException(new ErrorResponse("Algunos de los parámetros no se ingresaron"), HttpStatus.BAD_REQUEST);
        }

        // Verificar la validez del usuario
        boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            throw new EcommerceNotFoundException(new ErrorResponse("No está autorizado para ejecutar esta petición"), HttpStatus.UNAUTHORIZED);
        }

        final List<Product> products = ecommerceService.findAllProduct();
        final List<EntityModel<Product>> productModels = products.stream()
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(EcommerceController.class).findProduct(r.getNombre(),user,password)).withSelfRel(),
                        linkTo(methodOn(EcommerceController.class).findAllProduct(user, password)).withRel("products")))
                .collect(Collectors.toList());
        return CollectionModel.of(productModels, linkTo(methodOn(EcommerceController.class).findAllProduct(user,password)).withSelfRel());
    }

    // Endpoint que busca un producto por su id
    @GetMapping("/findProduct/{id}")
    public EntityModel<?> findProduct(@PathVariable String id,
                                              @RequestHeader(value = "user",required = false) String user,
                                              @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }
        // Verificar la validez del usuario
        final boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            return EntityModel.of(new ErrorResponse("No está autorizado para ejecutar esta petición"));
        }
        final Optional<Product> optionalProduct = ecommerceService.findProduct(Long.parseLong(id));
        if (optionalProduct.isPresent()) {
            Product foundProduct = optionalProduct.get();
            log.info("Producto encontrado: {}", optionalProduct.get());
            return EntityModel.of(EntityModel.of(foundProduct,
                    linkTo(methodOn(this.getClass()).findProduct(id,user, password)).withSelfRel()));
        } else {
            log.warn("No se encontró información para el usuario: {}", user);
            return EntityModel.of(new ErrorResponse("No se encontró información para el usuario: " + user));
        }

    }

    //Crea un nuevo producto
    @PostMapping("/createProduct")
    public EntityModel<?>createProduct(@Valid @RequestBody Product product,
                                                 @RequestHeader(value = "user",required = false) String user,
                                                 @RequestHeader(value = "password",required = false) String password,
                                                 BindingResult bindingResult) throws MethodArgumentNotValidException {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        final Product createdProduct = ecommerceService.createProduct(product);
        return EntityModel.of(createdProduct,
                linkTo(methodOn(this.getClass()).findProduct(createdProduct.getNombre(),user,password)).withSelfRel(),
                linkTo(methodOn(this.getClass()).findProduct(String.valueOf(createdProduct),user,password)).withRel("all-products"));
    }

    //Actualiza un producto
    @PutMapping("/updateProduct")
    public EntityModel<?>updateProduct(@RequestHeader(value = "user",required = false) String user,
                                       @RequestHeader(value = "password",required = false) String password,
                                       @Valid @RequestBody Product product,
                                       BindingResult bindingResult) throws MethodArgumentNotValidException {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        final Product updatedProduct = ecommerceService.updateProduct(product);
        return EntityModel.of(updatedProduct,
                linkTo(methodOn(this.getClass()).findProduct(updatedProduct.getNombre(),user,password)).withSelfRel(),
                linkTo(methodOn(this.getClass()).findProduct(String.valueOf(updatedProduct),user,password)).withRel("all-products"));
    }

    //Elimina un customer
    @DeleteMapping("/deleteProduct/{id}")
    public EntityModel<?>deleteProduct(@PathVariable String id,
                                               @RequestHeader(value = "user",required = false) String user,
                                               @RequestHeader(value = "password",required = false) String password) {

        if(StringUtils.containsWhitespace(id)) {
            log.error("Algunos de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Debe ingresar el id"));
        }

        final boolean existProduct = ecommerceService.existsProductById(Long.parseLong(id));
        if(existProduct){
            ecommerceService.deleteProduct(Long.parseLong(id));
            return EntityModel.of(new ErrorResponse("Producto eliminado exitosamente"));
        }
        return EntityModel.of(new ErrorResponse("No se pudo eliminar el producto con id:"+id));
    }


    // Endpoint que busca todos las facturas
    @GetMapping("/findAllInvoice")
    public CollectionModel<EntityModel<Factura>>findAllInvoice(@RequestHeader(value = "user",required = false) String user,
                                                           @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            throw new EcommerceNotFoundException(new ErrorResponse("Algunos de los parámetros no se ingresaron"), HttpStatus.BAD_REQUEST);
        }

        // Verificar la validez del usuario
        boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            throw new EcommerceNotFoundException(new ErrorResponse("No está autorizado para ejecutar esta petición"), HttpStatus.UNAUTHORIZED);
        }

        final List<Factura> invoices = ecommerceService.findAllInvoice();
        final List<EntityModel<Factura>> invoiceModels = invoices.stream()
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(EcommerceController.class).findBill(String.valueOf(r.getId()),user,password)).withSelfRel(),
                        linkTo(methodOn(EcommerceController.class).findAllInvoice(user, password)).withRel("invoices")))
                .collect(Collectors.toList());
        return CollectionModel.of(invoiceModels, linkTo(methodOn(EcommerceController.class).findAllInvoice(user,password)).withSelfRel());

    }

    // Endpoint que busca un producto por su id
    @GetMapping("/findInvoice/{id}")
    public EntityModel<?>findBill(@PathVariable String id,
                                  @RequestHeader(value = "user",required = false) String user,
                                  @RequestHeader(value = "password",required = false) String password) {

        // Verificar si se proporcionaron los parámetros requeridos
        if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
            log.error("Alguno de los parámetros no se ingresaron");
            return EntityModel.of(new ErrorResponse("Alguno de los parámetros no se ingresaron"));
        }

        // Verificar la validez del usuario
        final boolean userValid = ecommerceService.findUser(user, password);
        if (!userValid) {
            log.error("Usuario no autorizado: {}", user);
            return EntityModel.of(new ErrorResponse("No está autorizado para ejecutar esta petición"));
        }
        final Optional<Factura> optionalFactura = ecommerceService.findInvoice(Long.parseLong(id));
        if (optionalFactura.isPresent()) {
            Factura foundFactura = optionalFactura.get();
            log.info("Factura encontrado: {}", foundFactura.getId());
            return EntityModel.of(EntityModel.of(foundFactura,
                    linkTo(methodOn(this.getClass()).findUser(user, password)).withSelfRel()));
        } else {
            log.warn("No se encontró información para la factura: {}", user);
            return EntityModel.of(new ErrorResponse("No se encontró información para la factura: " + user));
        }
    }

}
