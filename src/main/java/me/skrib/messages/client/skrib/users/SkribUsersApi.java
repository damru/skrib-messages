package me.skrib.messages.client.skrib.users;

import io.damru.openfeign.oauth2.JwtForward;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
        name = "skrib-users",
        url = "${app.client.users.url}",
        configuration = {
                JwtForward.class
        }
)
public interface SkribUsersApi {

    @RequestMapping(
            path = "/me",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<User> me();

    @RequestMapping(
            path = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<User> getByCriteria(Map<String, ?> params);

}
