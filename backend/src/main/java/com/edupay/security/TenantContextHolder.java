package com.edupay.security;

import java.util.Optional;

/**
 * Tenant context stored per request thread.
 * Multi-tenant anti-IDOR: backend code must always use tenantId from this holder
 * populated from the authenticated JWT (server-side).
 */
public final class TenantContextHolder {

    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();

    private TenantContextHolder() {
    }

    public static void setTenantId(String tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static Optional<String> getTenantId() {
        return Optional.ofNullable(TENANT_ID.get());
    }

    public static String getRequiredTenantId() {
        return getTenantId().orElseThrow(() -> new IllegalStateException("Missing tenant_id in TenantContextHolder"));
    }

    public static void clear() {
        TENANT_ID.remove();
    }
}


