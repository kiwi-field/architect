package com.kiwi.field.architect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArchitectApplicationTests {

    @Test
    void contextLoads() {
        boolean a = true;
        boolean b =false;
        System.out.println(a && b);
    }

}
