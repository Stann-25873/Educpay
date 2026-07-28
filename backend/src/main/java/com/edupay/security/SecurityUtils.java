package com.edupay.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Central security helpers.
 * Never trust client-supplied tenantId; use TenantContextHolder populated from the JWT server-side.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Optional<String> getCurrentUsername() {
        Authentication auth = getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return Optional.ofNullable(userDetails.getUsername());
        }

        return principal == null ? Optional.empty() : Optional.of(principal.toString());
    }

    public static String getRequiredTenantId() {
        return TenantContextHolder.getRequiredTenantId();
    }

    public static Optional<String> getTenantId() {
        return TenantContextHolder.getTenantId();
    }

    public static void clearTenantContext() {
        TenantContextHolder.clear();
    }

   
}


