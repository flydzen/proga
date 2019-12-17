package markup;

import java.util.List;

public class Strong extends Forik implements inLine{

    public Strong(List<? extends notParagraph> els) {
        super(els, "__", "<strong>", "</strong>");
    }

    @Override
    public void toMarkdown(StringBuilder s){
        super.toMarkdown(s);
    }

    @Override
    public void toHtml(StringBuilder s) {
        super.toHtml(s);
    }
}
