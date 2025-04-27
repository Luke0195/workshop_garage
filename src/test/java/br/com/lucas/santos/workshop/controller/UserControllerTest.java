package br.com.lucas.santos.workshop.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class UserControllerTest {



}