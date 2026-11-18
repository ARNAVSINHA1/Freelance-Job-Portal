package com.freelance.portal.dto;

import com.freelance.portal.models.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserRoleUpdateRequest(@NotNull UserRole role) {
}
