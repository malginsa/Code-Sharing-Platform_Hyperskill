package platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonSerialize(using = CodeSerializer.class)
public class Code {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int id;
    private String code;
    private LocalDateTime date;

    public Code() {
    }

    public Code(int id, String code, LocalDateTime date) {
        this.id = id;
        this.code = code;
        this.date = date;
    }

    public Code(int id, String code) {
        this.id = id;
        this.code = code;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        return getDate().format(FORMATTER);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
