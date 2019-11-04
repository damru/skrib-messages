package me.skrib.messages.client.skrib.users;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.context.annotation.Bean;

public class FeignFormEncoder {

    @Bean
    public Encoder encoder() {
        return new FormEncoder();
    }

}
