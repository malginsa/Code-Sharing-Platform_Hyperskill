package platform;

import java.time.LocalDateTime;

public class CodeRepository {

    public static CodeRepository currentCode;

    static {
        resetCurrentCodeToSample();
    }

    public static void resetCurrentCodeToSample() {
        currentCode = new CodeRepository();
        currentCode.setCode("Wonder Code!");
        currentCode.setDate(LocalDateTime.parse("2020-11-11T11:11:11"));
    }

    private String code;
    private LocalDateTime date;

    public static CodeRepository getCurrentCode() {
        return currentCode;
    }

    public static void updateCurrentCode(CodeSnippet codeSnippet) {
        currentCode.setCode(codeSnippet.getCode());
        currentCode.setDate(LocalDateTime.now().withNano(0));
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

}
