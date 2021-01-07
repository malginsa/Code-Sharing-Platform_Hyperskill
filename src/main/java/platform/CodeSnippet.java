package platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@JsonSerialize(using = CodeSnippetSerializer.class)
public class CodeSnippet {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID uuid;

    private String code;

    private LocalDateTime date;

    private boolean isRestrictedByTime;

    private LocalDateTime expirationTime;

    private boolean isRestrictedByViews;

    private int views;

    public CodeSnippet() {
    }

    public CodeSnippet(CodeDTO codeDTO) {
        setCode(codeDTO.getCode());
        LocalDateTime now = LocalDateTime.now();
        setDate(now);

        int numberOfViews = Integer.max(codeDTO.getViews(),0);
        setRestrictedByViews(numberOfViews > 0);
        setViews(numberOfViews);

        long secondsToExpiration = Long.max(codeDTO.getTime(), 0);
        setRestrictedByTime(secondsToExpiration > 0);
        setExpirationTime(getDate().plusSeconds(secondsToExpiration));
    }

    public boolean isAvailable() {
        return isAvailableByViews() && isAvailableByTime();
    }

    private boolean isAvailableByViews() {
        return !isRestrictedByViews() || getViews() > 0;
    }

    private boolean isAvailableByTime() {
        return !isRestrictedByTime() || getExpirationTime().isAfter(LocalDateTime.now());
    }

    public void decreaseViews() {
        setViews(getViews() - 1);
    }

    public long getRemainingSeconds() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), getExpirationTime());
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return getDate().format(FORMATTER);
    }

    public boolean isRestrictedByViews() {
        return isRestrictedByViews;
    }

    public void setRestrictedByViews(boolean restrictedByViews) {
        isRestrictedByViews = restrictedByViews;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isRestrictedByTime() {
        return isRestrictedByTime;
    }

    public void setRestrictedByTime(boolean restrictedByTime) {
        isRestrictedByTime = restrictedByTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expiration) {
        this.expirationTime = expiration;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "uuid=" + uuid +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", isRestrictedByViews=" + isRestrictedByViews +
                ", views=" + views +
                ", isRestrictedByTime=" + isRestrictedByTime +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
