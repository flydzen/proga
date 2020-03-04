package markup;

public interface Markable {
    public void toMarkdown(StringBuilder s);
    public void toHtml(StringBuilder s);
}
