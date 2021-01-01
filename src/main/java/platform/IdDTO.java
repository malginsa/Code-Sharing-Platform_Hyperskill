package platform;

public class IdDTO {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIdValid() {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
