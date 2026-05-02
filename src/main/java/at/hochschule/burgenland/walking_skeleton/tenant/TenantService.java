package at.hochschule.burgenland.walking_skeleton.tenant;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 20;
    private static final String ALPHANUMERIC_PATTERN = "[A-Za-z0-9]+";

    private final InMemoryTenantRepository tenantRepository;

    public Tenant createTenant(String name) {
        validateName(name);

        return tenantRepository.save(new Tenant(UUID.randomUUID(), name));
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidTenantException("Tenant name is mandatory.");
        }

        if (name.length() < MIN_NAME_LENGTH) {
            throw new InvalidTenantException("Tenant name must contain at least 3 characters.");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new InvalidTenantException("Tenant name must contain at most 20 characters.");
        }

        if (!name.matches(ALPHANUMERIC_PATTERN)) {
            throw new InvalidTenantException("Tenant name must contain only letters and numbers.");
        }
    }
}