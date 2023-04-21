package com.turkogluc.postservice.object;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull
    Long authorId;

    @NotBlank
    String text;
}
