package org.musinsa.assignment.musinsapayments.point.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 포인트_적립_전역_테스트() throws Exception {
        mockMvc.perform(post("/api/point/earn")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":1,\"amount\":1000}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pointId").isNumber())
            .andExpect(jsonPath("$.remainPoint").isNumber());
    }



}