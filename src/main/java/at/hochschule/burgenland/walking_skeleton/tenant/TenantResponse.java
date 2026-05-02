package at.hochschule.burgenland.walking_skeleton.tenant;

import java.util.UUID;

public record TenantResponse(UUID id, String name) {
    public static TenantResponse from(Tenant tenant) {
        return new TenantResponse(tenant.id(), tenant.name());
    }
}