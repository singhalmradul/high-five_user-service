package io.github.singhalmradul.userservice.proxies;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "follow-service")
public interface FollowServiceProxy {

    @GetMapping("/users/{userId}/follow/{followId}")
    public boolean isUserFollowing(
        @PathVariable("userId") UUID userId,
        @PathVariable("followId")UUID followId
    );
}
