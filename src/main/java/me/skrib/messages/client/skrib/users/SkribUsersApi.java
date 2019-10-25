package me.skrib.messages.client.skrib.users;

import io.damru.openfeign.oauth2.JwtForward;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "skrib-users",
        url = "${app.client.users.url}",
        configuration = JwtForward.class
)
public interface SkribUsersApi {

    @RequestMapping(
            path = "/me",
            method = RequestMethod.GET
    )
    ResponseEntity<User> me();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    ResponseEntity<User> getById(@PathVariable(value = "id") Long id);

}
