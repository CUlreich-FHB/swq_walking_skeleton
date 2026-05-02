package at.hochschule.burgenland.walking_skeleton.tenant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TenantServiceTests {
    private final InMemoryTenantRepository tenantRepository = new InMemoryTenantRepository();
    private final TenantService tenantService = new TenantService(tenantRepository);

    @Test
    void createsTenantWithValidName() {
        Tenant tenant = tenantService.createTenant("Tenant123");

        assertNotNull(tenant.id());
        assertEquals("Tenant123", tenant.name());
        assertEquals(tenant, tenantRepository.findAll().getFirst());
    }

    @Test
    void allowsDuplicateTenantNames() {
        Tenant firstTenant = tenantService.createTenant("Tenant123");
        Tenant secondTenant = tenantService.createTenant("Tenant123");

        assertEquals("Tenant123", firstTenant.name());
        assertEquals("Tenant123", secondTenant.name());
        assertEquals(2, tenantRepository.findAll().size());
    }

    @Test
    void rejectsNullTenantName() {
        InvalidTenantException exception =
                assertThrows(InvalidTenantException.class, () -> tenantService.createTenant(null));

        assertEquals("Tenant name is mandatory.", exception.getMessage());
    }

    @Test
    void rejectsBlankTenantName() {
        InvalidTenantException exception =
                assertThrows(InvalidTenantException.class, () -> tenantService.createTenant(" "));

        assertEquals("Tenant name is mandatory.", exception.getMessage());
    }

    @Test
    void rejectsTooShortTenantName() {
        InvalidTenantException exception =
                assertThrows(InvalidTenantException.class, () -> tenantService.createTenant("AB"));

        assertEquals("Tenant name must contain at least 3 characters.", exception.getMessage());
    }

    @Test
    void rejectsTooLongTenantName() {
        InvalidTenantException exception =
                assertThrows(
                        InvalidTenantException.class,
                        () -> tenantService.createTenant("ABCDEFGHIJKLMNOPQRSTU"));

        assertEquals("Tenant name must contain at most 20 characters.", exception.getMessage());
    }

    @Test
    void rejectsTenantNameWithSpecialCharacters() {
        InvalidTenantException exception =
                assertThrows(InvalidTenantException.class, () -> tenantService.createTenant("Tenant-1"));

        assertEquals("Tenant name must contain only letters and numbers.", exception.getMessage());
    }

    @Test
    void rejectsTenantNameWithUmlauts() {
        InvalidTenantException exception =
                assertThrows(InvalidTenantException.class, () -> tenantService.createTenant("Müller"));

        assertEquals("Tenant name must contain only letters and numbers.", exception.getMessage());
    }

    @Test
    void rejectsTenantNameWithSpaces() {
        InvalidTenantException exception =
                assertThrows(InvalidTenantException.class, () -> tenantService.createTenant("Tenant 1"));

        assertEquals("Tenant name must contain only letters and numbers.", exception.getMessage());
    }
}
