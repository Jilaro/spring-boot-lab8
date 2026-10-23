package ru.urabov.lab8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

// Для теста используется отдельная база в памяти, чтобы не трогать testdb
@SpringBootTest
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class SpringBootLab8ApplicationTests {

    @Test
    void contextLoads() {
    }
}
