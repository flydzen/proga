package markup;

import java.util.List;

public class OrderedList extends Forik implements AbstractList {
    OrderedList(List<ListItem> els) {
        super(els);
    }

    @Override
    public void toHtml(StringBuilder s){
        s.append("<ol>");
        super.toHtml(s);
        s.append("</ol>");
    }
}
