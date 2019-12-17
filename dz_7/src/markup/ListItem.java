package markup;

import java.util.List;

public class ListItem extends Forik{
    ListItem(List<? extends InItem> els) {
        super(els);
    }

    public void toMarkdown(StringBuilder s) {

    }

    public void toHtml(StringBuilder s){
        s.append("<li>");
        super.toHtml(s);
        s.append("</li>");
    }
}
