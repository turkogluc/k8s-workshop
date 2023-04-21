package com.turkogluc.postservice.database.service;

import com.turkogluc.postservice.database.entity.PostEntity;
import com.turkogluc.postservice.database.repository.PostRepository;
import com.turkogluc.postservice.object.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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
        return postRepository.save(postEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        final PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No post found for id: " + id));
        postRepository.delete(postEntity);
    }
}
