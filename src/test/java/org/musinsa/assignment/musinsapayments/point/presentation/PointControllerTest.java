package org.musinsa.assignment.musinsapayments.point.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.musinsa.assignment.musinsapayments.point.domain.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PointService pointService;

    @BeforeEach
    void setUp() {
        Point point = new Point(1L, 1000L, false);
        pointService.savePoint(point);
    }

    @Test
    void 포인트_적립_전역_테스트() throws Exception {
        mockMvc.perform(post("/api/point/earn")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":1,\"amount\":1000}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pointId").isNumber())
            .andExpect(jsonPath("$.remainPoint").isNumber());
    }

    @Test
    void 포인트_적립_취소_전역_테스트() throws Exception {
        mockMvc.perform(post("/api/point/earn/1/cancel"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pointId").isNumber())
            .andExpect(jsonPath("$.remainPoint").isNumber());
    }

    @Test
    void 포인트_사용_전역_테스트() throws Exception {
        mockMvc.perform(post("/api/point/use")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":1,\"orderId\":1,\"amount\":100}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.remainPoint").isNumber());
    }

    @Test
    void 포인트_사용_취소_전역_테스트() throws Exception {
        pointService.usePoint(1L, 1L, 100L);

        mockMvc.perform(post("/api/point/use/cancel")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":1,\"orderId\":1,\"amount\":100}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.remainPoint").isNumber());
    }
}