package com.turkogluc.postservice.api;


import com.turkogluc.postservice.database.entity.PostEntity;
import com.turkogluc.postservice.database.service.PostService;
import com.turkogluc.postservice.object.PostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class Controller {

    private final PostService postService;

    @GetMapping("/greeting")
    public ResponseEntity <String> greeting() {
        return ResponseEntity.ok("Hello, k8s!");
    }

    @GetMapping("/{id}")
    public ResponseEntity <PostEntity> findById(@PathVariable Long id) {
        return ResponseEntity.of(postService.findById(id));
    }

    @PostMapping
    public ResponseEntity <PostEntity> save(@RequestBody @Valid PostRequest postRequest) {
        final PostEntity entity = postService.save(postRequest, Optional.empty());
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity <PostEntity> update(@RequestBody @Valid PostRequest postRequest, @PathVariable Long id) {
        final PostEntity entity = postService.save(postRequest, Optional.of(id));
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
