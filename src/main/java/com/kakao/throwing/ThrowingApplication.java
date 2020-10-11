package com.kakao.throwing;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEncryptableProperties //설정값 복호화에 사용
public class ThrowingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThrowingApplication.class, args);
    }

}
