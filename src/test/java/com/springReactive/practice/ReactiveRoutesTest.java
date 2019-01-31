package com.springReactive.practice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        PracticeApplication.class})
public class ReactiveRoutesTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetMe() {
        webTestClient.get().uri("/getMe").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
    }

    @Test
    public void testGetMeWrongAcceptHeader() {
        webTestClient.get().uri("/getMe").accept().exchange().expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
    }

    @Test
    public void testGetWrongContentType() {
        webTestClient.post().uri("/postKeys").contentType(MediaType.APPLICATION_ATOM_XML).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }
}