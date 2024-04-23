# API de Ecommerce

Esta API proporciona funcionalidades para gestionar usuarios, roles, clientes, productos y facturas en un sistema de Ecommerce.

## Endpoints Disponibles

### Usuarios

- **Buscar Usuario**
    - Método: GET
    - Ruta: `/api/findUser`
    - Descripción: Busca un usuario por nombre de usuario y contraseña.

### Roles

- **Buscar Todos los Roles**
    - Método: GET
    - Ruta: `/api/findAllRoles`
    - Descripción: Busca todos los roles disponibles en el sistema.

- **Buscar Rol por Nombre**
    - Método: GET
    - Ruta: `/api/findRolByName/{name}`
    - Descripción: Busca un rol por su nombre.

- **Crear Rol**
    - Método: POST
    - Ruta: `/api/createRol`
    - Descripción: Crea un nuevo rol en el sistema.

- **Actualizar Rol**
    - Método: PUT
    - Ruta: `/api/updateRol/{id}`
    - Descripción: Actualiza un rol existente en el sistema.

- **Eliminar Rol**
    - Método: DELETE
    - Ruta: `/api/deleteRol/{id}`
    - Descripción: Elimina un rol del sistema.

### Clientes

- **Buscar Todos los Clientes**
    - Método: GET
    - Ruta: `/api/findAllCustomer`
    - Descripción: Busca todos los clientes registrados en el sistema.

- **Buscar Cliente por ID**
    - Método: GET
    - Ruta: `/api/findCustomer/{customerId}`
    - Descripción: Busca un cliente por su ID.

- **Crear Cliente**
    - Método: POST
    - Ruta: `/api/createCustomer`
    - Descripción: Crea un nuevo cliente en el sistema.

- **Actualizar Cliente**
    - Método: PUT
    - Ruta: `/api/updateCustomer`
    - Descripción: Actualiza los datos de un cliente existente en el sistema.

- **Eliminar Cliente**
    - Método: DELETE
    - Ruta: `/api/deleteCustomer/{id}`
    - Descripción: Elimina un cliente del sistema.

### Productos

- **Buscar Todos los Productos**
    - Método: GET
    - Ruta: `/api/findAllProduct`
    - Descripción: Busca todos los productos disponibles en el sistema.

- **Buscar Producto por ID**
    - Método: GET
    - Ruta: `/api/findProduct/{id}`
    - Descripción: Busca un producto por su ID.

- **Crear Producto**
    - Método: POST
    - Ruta: `/api/createProduct`
    - Descripción: Crea un nuevo producto en el sistema.

- **Actualizar Producto**
    - Método: PUT
    - Ruta: `/api/updateProduct`
    - Descripción: Actualiza los datos de un producto existente en el sistema.

- **Eliminar Producto**
    - Método: DELETE
    - Ruta: `/api/deleteProduct/{id}`
    - Descripción: Elimina un producto del sistema.

### Facturas

- **Buscar Todas las Facturas**
    - Método: GET
    - Ruta: `/api/findAllInvoice`
    - Descripción: Busca todas las facturas registradas en el sistema.

- **Buscar Factura por ID**
    - Método: GET
    - Ruta: `/api/findInvoice/{id}`
    - Descripción: Busca una factura por su ID.

- **Eliminar Factura**
    - Método: DELETE
    - Ruta: `/api/deleteInvoice/{id}`
    - Descripción: Elimina una factura del sistema.

## Manejo de Errores

La API maneja los siguientes errores:

- Errores de validación de datos.
- Errores de violación de restricción única en la base de datos.

Todos los errores se devuelven en formato JSON.
