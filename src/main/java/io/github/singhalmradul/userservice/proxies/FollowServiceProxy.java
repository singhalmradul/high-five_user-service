package io.github.singhalmradul.userservice.proxies;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.singhalmradul.userservice.views.IdOnly;

@FeignClient(name = "follow-service")
public interface FollowServiceProxy {

    @GetMapping("/users/{userId}/followers")
    public List<IdOnly> getFollowers(@PathVariable("userId") UUID userId);

    @GetMapping("/users/{userId}/following")
    public List<IdOnly> getFollowing(@PathVariable("userId") UUID userId);
}