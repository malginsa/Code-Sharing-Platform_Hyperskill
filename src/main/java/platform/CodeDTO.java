package platform;

public class CodeDTO {

    private String code;

    private long time;

    private int views;

    public CodeDTO() {
    }

    public CodeDTO(String code, long time, int views) {
        this.code = code;
        this.time = time;
        this.views = views;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
