package cl.company.ecommerce.controller;

import cl.company.ecommerce.model.Customer;
import cl.company.ecommerce.model.User;
import cl.company.ecommerce.service.EcommerceService;
import cl.company.ecommerce.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

        // Verificar la validez del usuario
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


}
