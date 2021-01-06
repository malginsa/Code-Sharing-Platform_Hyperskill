package platform;

import java.util.UUID;

public class UuidDto {
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isIdValid() {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
