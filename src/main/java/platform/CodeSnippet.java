package platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@JsonSerialize(using = CodeSerializer.class)
public class CodeSnippet {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID uuid;

    private String code;

    private LocalDateTime date;

    public CodeSnippet() {
    }

    public CodeSnippet(String code, LocalDateTime date) {
        this.code = code;
        this.date = date;
    }

    public CodeSnippet(String code) {
        this.code = code;
        this.date = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "uuid=" + uuid +
                ", code='" + code + '\'' +
                ", date=" + date +
//                ", views=" + views +
//                ", expiration=" + expiration +
                '}';
    }
}
