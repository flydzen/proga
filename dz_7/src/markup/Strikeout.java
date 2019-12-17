package markup;

import java.util.List;

public class Strikeout extends Forik implements inLine {
    Strikeout(List<? extends notParagraph> els) {
        super(els);
    }
    @Override
    public void toMarkdown(StringBuilder s){
        s.append("~");
        super.toMarkdown(s);
        s.append("~");
    }
    @Override
    public void toHtml(StringBuilder s){
        s.append("<s>");
        super.toHtml(s);
        s.append("</s>");
    }
}
