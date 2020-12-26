package platform;

public class HtmlWrapper {

    private String pieceOfCode;

    public HtmlWrapper(String pieceOfCode) {
        this.pieceOfCode = pieceOfCode;
    }

    public String getAsHtml() {
        return "<html>" +
                    "<head><title>Code</title></head>" +
                    "<body><pre>" + pieceOfCode + "</pre></body>" +
                "</html>";
    }
}
