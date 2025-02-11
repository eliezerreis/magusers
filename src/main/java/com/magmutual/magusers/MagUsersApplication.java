package com.magmutual.magusers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Users Microservice REST API Documentation",
        description = "This API give the CRUD features to manage users",
        version = "v1",
        contact = @Contact(
            name = "Eliezer Oliveira",
            email = "eliezerreis@gmail.com"
        ),
        license = @License(
            name = "Apache 2.0"
        )
    )
)
public class MagUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagUsersApplication.class, args);
    }

}
