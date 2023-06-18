package com.turkogluc.userservice.database;

import com.turkogluc.userservice.database.entity.UserEntity;
import com.turkogluc.userservice.database.repository.UserRepository;
import com.turkogluc.userservice.object.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository userRepository;

    public Optional <UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public List <UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public UserEntity save(UserRequest userRequest, Optional <Long> idOpt) {
        UserEntity userEntity = new UserEntity();
        if (idOpt.isPresent()) {
            userEntity = userRepository.findById(idOpt.get())
                    .orElseThrow(() -> new NoSuchElementException("No user found for id: " + idOpt.get()));
        }
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setAmountOfPosts(0L);
        return userRepository.save(userEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        final UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found for id: " + id));
        userRepository.delete(userEntity);
    }

    @Transactional
    public UserEntity incrementPostCount(Long userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found for id: " + userId));

        userEntity.setAmountOfPosts(userEntity.getAmountOfPosts() + 1);
        return userRepository.save(userEntity);
    }

    @Transactional
    public UserEntity decrementPostCount(Long userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found for id: " + userId));

        userEntity.setAmountOfPosts(userEntity.getAmountOfPosts() - 1);
        return userRepository.save(userEntity);
    }
}
