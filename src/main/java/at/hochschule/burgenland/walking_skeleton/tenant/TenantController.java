package at.hochschule.burgenland.walking_skeleton.tenant;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@RequestBody CreateTenantRequest request) {
        String name = request == null ? null : request.name();
        TenantResponse response = TenantResponse.from(tenantService.createTenant(name));

        return ResponseEntity.created(URI.create("/tenants/" + response.id())).body(response);
    }

    @ExceptionHandler(InvalidTenantException.class)
    public ResponseEntity<ApiError> handleInvalidTenant(InvalidTenantException exception) {
        return ResponseEntity.badRequest().body(new ApiError(exception.getMessage()));
    }
}