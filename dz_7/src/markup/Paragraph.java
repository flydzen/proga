package markup;

import java.util.List;

class Paragraph extends Forik implements InItem{
    Paragraph(List<? extends notParagraph> els){
        super(els, "", "", "");
    }

    public void toMarkdown(StringBuilder s){
        super.toMarkdown(s);
    }

    public void toHtml(StringBuilder s) {
        super.toHtml(s);
    }
}
