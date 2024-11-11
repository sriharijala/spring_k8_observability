package com.sjala.micro.services.user.reviews.cdc;

import com.sjala.springboot3.jpa.hibernate.UserServiceApplication;
import com.sjala.springboot3.jpa.hibernate.controllers.UserController;
import com.sjala.springboot3.jpa.hibernate.model.Customer;
import com.sjala.springboot3.jpa.hibernate.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;
import java.util.Optional;

@SpringBootTest(classes = UserServiceApplication.class)
public class ConsumerDrivenTestingBase {

    @Autowired
    UserController userController;

    @MockBean
    UserService userService;

    static Map<String, Customer> orders = Map.of(
            "1", new Customer(1L, "testFirstName", "testLastName", "testemail@yahoo.com", null)
    );

    @BeforeEach
    public void setup()
    {
        RestAssuredMockMvc.standaloneSetup(userController);

        Mockito.when(userService.getUserById(1L))
                .thenReturn(Optional.ofNullable(orders.get("1")));
    }
}
