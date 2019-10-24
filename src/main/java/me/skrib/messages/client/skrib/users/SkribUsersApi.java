package me.skrib.messages.client.skrib.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "skribUsers",
        url = "http://localhost:8082/users"
)
public interface SkribUsersApi {

    @RequestMapping(
            method = RequestMethod.GET
    )
    ResponseEntity<User> getByOktaId(@RequestParam(value = "oktaId") String oktaId);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    ResponseEntity<User> getById(@PathVariable(value = "id") Long id);

}
