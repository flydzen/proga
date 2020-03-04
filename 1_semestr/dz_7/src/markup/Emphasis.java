package markup;

import java.util.ArrayList;
import java.util.List;

public class Emphasis extends Forik implements inLine {
    Emphasis(List<? extends notParagraph> els) {
        super(els, "<em>", "</em>");
    }
    @Override
    public void toMarkdown(StringBuilder s){
        super.toMarkdown(s);
    }

    @Override
    public void toHtml(StringBuilder s){
        super.toHtml(s);
    }
}
