package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
    Hey Nathan,

    Documentation I used for this can be found on: https://spring.io/guides/gs/testing-web/

    There are 2 tests I put below and added some comments.

    To run the test:
        - run your app first (DemoApplication)
        - run the test class (ControllerTest)


 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @Autowired
    private Controller controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getListSample() {
        // **** test to make sure the context /list starts
        assertThat(controller).isNotNull();

        // **** test the /list API to make sure it contains "Hello" as part of the response
        assertThat(this.restTemplate.getForObject("http://localhost:8080/list",
                String.class)).contains("Hello");

    }
}