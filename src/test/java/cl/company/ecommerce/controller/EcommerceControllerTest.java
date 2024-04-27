package cl.company.ecommerce.controller;

import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.Rol;
import cl.company.ecommerce.service.EcommerceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EcommerceController.class)
@AutoConfigureMockMvc
class EcommerceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EcommerceService ecommerceServiceMock;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAllCustomerTest() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer().setId("carriagada"));
        customers.add(new Customer().setId("cmundaca"));

        when(ecommerceServiceMock.findAllCustomer()).thenReturn(customers);

        String user = "H514228";
        String password = "admin";

        when(ecommerceServiceMock.findUser(user, password)).thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = get("/api/findAllCustomer")
                .header("user", user)
                .header("password", password);

        // Realizar la solicitud y validar la respuesta
        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customerList.length()").value(2))
                .andExpect(jsonPath("$._embedded.customerList[0].id").value("carriagada"))
                .andExpect(jsonPath("$._embedded.customerList[1].id").value("cmundaca"));

        verify(ecommerceServiceMock).findAllCustomer();
        verify(ecommerceServiceMock).findUser(user, password);
    }


    @Test
    void findCustomerByIdTest() throws Exception {
        String user = "H514228";
        String password = "admin";
        String customerId = "carriagada";
        Customer customer = new Customer().setId("carriagada").setNombre("Arriagada");

        when(ecommerceServiceMock.findUser(user, password)).thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = get("/api/findCustomer/{customerId}", customerId)
                .header("user", user)
                .header("password", password);

        when(ecommerceServiceMock.findCustomer("carriagada")).thenReturn(Optional.of(customer));

        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("carriagada"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/findCustomer/" + customerId));

        verify(ecommerceServiceMock).findCustomer(customerId);
    }

    @Test
    void saveCustomer() throws Exception {
        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");

        Customer customer = new Customer();
        customer.setId("mperez");
        customer.setNombre("Nombre Cliente 1");
        customer.setApellido("Apellido Cliente 1");
        customer.setEdad(30);
        customer.setTelefono("123456789");
        customer.setDireccion("Dirección Cliente 1");
        customer.setRoles(rol1);

        String user = "H514228";
        String password = "admin";
        when(ecommerceServiceMock.findUser(user, password)).thenReturn(true);

        when(ecommerceServiceMock.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/createCustomer")
                        .header("user", user)
                        .header("password", password)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value("mperez"))
                .andExpect(jsonPath("$.nombre").value("Nombre Cliente 1"))
                .andExpect(jsonPath("$.apellido").value("Apellido Cliente 1"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.telefono").value("123456789"))
                .andExpect(jsonPath("$.direccion").value("Dirección Cliente 1"));

        verify(ecommerceServiceMock).createCustomer(any(Customer.class));
    }


    @Test
    void updateCustomerTest() throws Exception {

        Rol rol1 = new Rol();
        rol1.setId(1L);
        rol1.setNombre("Rol 1");
        rol1.setDescripcion("Descripción del Rol 1");

        Customer customer = new Customer();
        customer.setId("mperez");
        customer.setNombre("Nombre Cliente 1");
        customer.setApellido("Apellido Cliente 1");
        customer.setEdad(30);
        customer.setTelefono("123456789");
        customer.setDireccion("Dirección Cliente 1");
        customer.setRoles(rol1);

        String user = "H514228";
        String password = "admin";
        when(ecommerceServiceMock.findUser(user, password)).thenReturn(true);

        when(ecommerceServiceMock.updateCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(put("/api/updateCustomer")
                        .header("user", user)
                        .header("password", password)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());

        verify(ecommerceServiceMock).updateCustomer(any(Customer.class));
    }


}
