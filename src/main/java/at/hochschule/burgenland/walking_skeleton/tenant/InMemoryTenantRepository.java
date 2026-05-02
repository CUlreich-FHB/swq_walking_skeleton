package at.hochschule.burgenland.walking_skeleton.tenant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTenantRepository {
    private final List<Tenant> tenants = new ArrayList<>();

    public synchronized Tenant save(Tenant tenant) {
        tenants.add(tenant);
        return tenant;
    }

    public synchronized List<Tenant> findAll() {
        return List.copyOf(tenants);
    }
}