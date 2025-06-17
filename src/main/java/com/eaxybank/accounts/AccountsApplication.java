package com.eaxybank.accounts;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImp")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts Microservices REST API Documentation",
                description = "EazyBank Accounts microservices REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name="Karthik Addanki",
                        email = "accounts@gmail.com",
                        url="https://localhost:8080"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url ="https://www.eazybytes.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Eazybank Accounts Microservices RESTAPI Documentation",
                url="https://www.eazybytes.com/swager-ui.html"
        )
)
public class AccountsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }
}
