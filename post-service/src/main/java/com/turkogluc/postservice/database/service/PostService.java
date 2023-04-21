package com.turkogluc.postservice.database.service;

import com.turkogluc.postservice.database.entity.PostEntity;
import com.turkogluc.postservice.database.repository.PostRepository;
import com.turkogluc.postservice.object.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    @Value("${service.user.url}")
    private String userServiceUrl;

    public Optional <PostEntity> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public PostEntity save(PostRequest postRequest, Optional <Long> idOpt) {
        PostEntity postEntity = new PostEntity();
        if (idOpt.isPresent()) {
            postEntity = findById(idOpt.get())
                    .orElseThrow(() -> new NoSuchElementException("No post found for id: " + idOpt.get()));
        }

        postEntity.setPostedAt(LocalDate.now());
        postEntity.setAuthorId(postRequest.getAuthorId());
        postEntity.setText(postRequest.getText());
        final PostEntity postEntitySaved = postRepository.save(postEntity);
        incrementUserPostCount(postEntitySaved.getAuthorId());
        return postEntitySaved;
    }

    @Transactional
    public void deleteById(Long id) {
        final PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No post found for id: " + id));
        postRepository.delete(postEntity);
        decrementUserPostCount(id);
    }

    private void incrementUserPostCount(Long authorId) {
        String url = userServiceUrl + "/users/increment-post-count/" + authorId;
        restTemplate.postForObject(url, HttpEntity.EMPTY, String.class);
    }

    private void decrementUserPostCount(Long authorId) {
        String url = userServiceUrl + "/users/decrement-post-count/" + authorId;
        restTemplate.postForObject(url, HttpEntity.EMPTY, String.class);
    }
}
