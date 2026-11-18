package com.freelance.portal.security;

import com.freelance.portal.models.User;
import com.freelance.portal.models.UserRole;
import com.freelance.portal.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.NonNull;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityAuthorizationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    void databaseUserCanLoginAndAccessOwnProfile() throws Exception {
        User account = new User();
        account.setName("Session Test User");
        account.setEmail("session-test@example.com");
        account.setPassword(passwordEncoder.encode("StrongPass123"));
        account.setRole(UserRole.USER);
        account = userRepository.saveAndFlush(account);

        mockMvc.perform(post("/api/auth/login")
                        .with(csrfRequest())
                        .contentType("application/x-www-form-urlencoded")
                        .param("email", account.getEmail())
                        .param("password", "wrong-password"))
                .andExpect(status().isUnauthorized());

        var loginResult = mockMvc.perform(post("/api/auth/login")
                        .with(csrfRequest())
                        .contentType("application/x-www-form-urlencoded")
                        .param("email", account.getEmail())
                        .param("password", "StrongPass123"))
                .andExpect(status().isNoContent())
                .andReturn();

        MockHttpSession session = Objects.requireNonNull(
                (MockHttpSession) loginResult.getRequest().getSession(false),
                "Successful login must establish a session");
        mockMvc.perform(get("/api/users/{id}", account.getId()).session(session))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/logout")
                        .session(session)
                        .with(csrfRequest()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{id}", account.getId()).session(session))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void roleChangesExpireExistingSessions() throws Exception {
        User account = new User();
        account.setName("Role Change Test User");
        account.setEmail("role-change-test@example.com");
        account.setPassword(passwordEncoder.encode("StrongPass123"));
        account.setRole(UserRole.USER);
        account = userRepository.saveAndFlush(account);

        var loginResult = mockMvc.perform(post("/api/auth/login")
                        .with(csrfRequest())
                        .contentType("application/x-www-form-urlencoded")
                        .param("email", account.getEmail())
                        .param("password", "StrongPass123"))
                .andExpect(status().isNoContent())
                .andReturn();
        MockHttpSession session = Objects.requireNonNull(
                (MockHttpSession) loginResult.getRequest().getSession(false),
                "Successful login must establish a session");

        mockMvc.perform(patch("/api/users/{id}/role", account.getId())
                        .with(authenticatedAs("admin", "ADMIN"))
                        .with(csrfRequest())
                        .contentType("application/json")
                        .content("{\"role\":\"EMPLOYER\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/{id}", account.getId()).session(session))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void anonymousCanBrowseJobsButCannotListUsers() throws Exception {
        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void employerCannotCreateJobUntilOwnershipIsModeled() throws Exception {
        mockMvc.perform(post("/api/jobs")
                        .with(authenticatedAs("employer", "EMPLOYER"))
                        .with(csrfRequest())
                        .contentType("application/json")
                        .content("""
                                {
                                  "title": "Backend Developer",
                                  "description": "Build APIs",
                                  "employer": "Example"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    void userCannotChangeRoles() throws Exception {
        mockMvc.perform(patch("/api/users/123/role")
                        .with(authenticatedAs("ordinary-user", "USER"))
                        .with(csrfRequest())
                        .contentType("application/json")
                        .content("{\"role\":\"ADMIN\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void userCanOnlyAccessTheirOwnProfile() throws Exception {
        User user = new User();
        user.setId(42L);
        user.setEmail("user@example.com");
        user.setPassword("encoded");
        user.setRole(com.freelance.portal.models.UserRole.USER);
        PortalUserPrincipal principal = new PortalUserPrincipal(user);
        var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                principal, principal.getPassword(), principal.getAuthorities());

        mockMvc.perform(get("/api/users/42").with(authenticationRequest(auth)))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/users/43").with(authenticationRequest(auth)))
                .andExpect(status().isForbidden());
    }

    @SuppressWarnings("null")
    private static @NonNull RequestPostProcessor csrfRequest() {
        return org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf();
    }

    @SuppressWarnings("null")
    private static @NonNull RequestPostProcessor authenticatedAs(String username, String... roles) {
        return org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                .user(username)
                .roles(roles);
    }

    @SuppressWarnings("null")
    private static @NonNull RequestPostProcessor authenticationRequest(Authentication authentication) {
        return org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                .authentication(authentication);
    }
}
