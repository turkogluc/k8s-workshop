package com.turkogluc.userservice.api;

import com.turkogluc.userservice.database.UserDataService;
import com.turkogluc.userservice.database.entity.UserEntity;
import com.turkogluc.userservice.object.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class Controller {

    private final UserDataService userDataService;

    @GetMapping("/greeting")
    public ResponseEntity <String> greeting() throws UnknownHostException {
        return ResponseEntity.ok("Hello, k8s! Host: " + InetAddress.getLocalHost().getHostName());
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserEntity> findById(@PathVariable Long id) {
        final Optional <UserEntity> userEntityOpt = userDataService.findById(id);
        return userEntityOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List <UserEntity> findAll() {
        return userDataService.findAll();
    }

    @PostMapping
    public ResponseEntity <UserEntity> insertUser(@RequestBody @Valid UserRequest userRequest) {
        final UserEntity userEntity = userDataService.save(userRequest, Optional.empty());
        return ResponseEntity.ok(userEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity <UserEntity> updateUser(@RequestBody @Valid UserRequest userRequest, @PathVariable Long id) {
        final UserEntity userEntity = userDataService.save(userRequest, Optional.of(id));
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteUser(@PathVariable Long id) {
        userDataService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/increment-post-count/{id}")
    public ResponseEntity <UserEntity> incrementPostCount(@PathVariable Long id) {
        return ResponseEntity.ok(userDataService.incrementPostCount(id));
    }

    @PostMapping("/decrement-post-count/{id}")
    public ResponseEntity <UserEntity> decrementPostCount(@PathVariable Long id) {
        return ResponseEntity.ok(userDataService.decrementPostCount(id));
    }

}
