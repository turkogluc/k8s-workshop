package com.turkogluc.userservice.api;

import com.turkogluc.userservice.database.UserDataService;
import com.turkogluc.userservice.database.entity.UserEntity;
import com.turkogluc.userservice.object.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UserDataService userDataService;

    @GetMapping("/greeting")
    public ResponseEntity <String> greeting() {
        return ResponseEntity.ok("Hello, k8s!");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity <UserEntity> findById(@PathVariable Long id) {
        final Optional <UserEntity> userEntityOpt = userDataService.findById(id);
        return userEntityOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity <UserEntity> insertUser(@RequestBody @Valid UserRequest userRequest) {
        final UserEntity userEntity = userDataService.save(userRequest, Optional.empty());
        return ResponseEntity.ok(userEntity);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity <UserEntity> updateUser(@RequestBody @Valid UserRequest userRequest, @PathVariable Long id) {
        final UserEntity userEntity = userDataService.save(userRequest, Optional.of(id));
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity <?> deleteUser(@PathVariable Long id) {
        userDataService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/increment-post-count/{id}")
    public ResponseEntity <UserEntity> incrementPostCount(@PathVariable Long id) {
        return ResponseEntity.ok(userDataService.incrementPostCount(id));
    }

    @PostMapping("/users/decrement-post-count/{id}")
    public ResponseEntity <UserEntity> decrementPostCount(@PathVariable Long id) {
        return ResponseEntity.ok(userDataService.decrementPostCount(id));
    }


}
