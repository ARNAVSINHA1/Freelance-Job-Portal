package com.freelance.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobRequest(
        @NotBlank @Size(max = 150) String title,
        @NotBlank @Size(max = 5000) String description,
        @NotBlank @Size(max = 150) String employer
) {
}
