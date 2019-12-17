package markup;

import java.util.List;

public class Text extends Forik implements notParagraph {
    Text(String text) {
        super(text);
    }
    @Override
    public void toMarkdown(StringBuilder s){
        s.append(super.text);
    }
    @Override
    public void toHtml(StringBuilder s) {
        toMarkdown(s);
    }
}
