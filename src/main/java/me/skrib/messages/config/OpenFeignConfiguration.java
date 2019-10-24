package me.skrib.messages.config;

import me.skrib.messages.client.skrib.users.SkribUsersApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = SkribUsersApi.class)
public class OpenFeignConfiguration {
}
