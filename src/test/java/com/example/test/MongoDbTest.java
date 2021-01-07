package com.example.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Socket;

@Testcontainers
@SpringBootTest
public class MongoDbTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Container
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:4.0"));

    //    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", container::ur);
//      //  registry.add("spring.datasource.password", container::getPassword);
//      //  registry.add("spring.datasource.username", container::getUsername);
//    }
    @Test
    public void containerStartsAndPublicPortIsAvailable() {
        // try (MongoDbContainer containe
        // r = new MongoDbContainer(DockerImageName.parse("mongo:4.0"))) {
        container.start();
        Employee employee = new Employee();
        employee.setName("Sunil");
        Employee e1 = employeeRepository.save(employee).block();
        Assertions.assertEquals("Sunil", e1.getName());
        //assertThatPortIsAvailable(container);
    }

    private void assertThatPortIsAvailable(MongoDBContainer container) {
        try {
            new Socket(container.getContainerIpAddress(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available!");
        }
    }
}





