package at.hochschule.burgenland.walking_skeleton.tenant;

public class InvalidTenantException extends RuntimeException {
    public InvalidTenantException(String message) {
        super(message);
    }
}