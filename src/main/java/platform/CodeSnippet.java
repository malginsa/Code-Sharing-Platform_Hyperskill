package platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.AUTO;

@Entity
@JsonSerialize(using = CodeSerializer.class)
public class CodeSnippet {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;

    private String code;

    private LocalDateTime date;

    public CodeSnippet() {
    }

    public CodeSnippet(String code, LocalDateTime date) {
        this.id = id;
        this.code = code;
        this.date = date;
    }

    public CodeSnippet(String code) {
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

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date=" + date +
                '}';
    }
}
