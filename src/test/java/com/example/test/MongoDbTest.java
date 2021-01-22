package com.example.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:4.4.3"));

    @BeforeAll
    static void initAll(){
        container.start();

    }
    @Test
     void containerStartsAndPublicPortIsAvailable() {


        assertThatPortIsAvailable(container);
    }

    @Test
    void saveEmployee(){
        Employee employee = new Employee();
        employee.setName("Sunil");
        Employee e1 = employeeRepository.save(employee).block();
        assert e1 != null;
        Assertions.assertEquals("Sunil", e1.getName());

    }

    private void assertThatPortIsAvailable(MongoDBContainer container) {
        try {
            new Socket(container.getContainerIpAddress(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available!");
        }
    }
}





