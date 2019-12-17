package markup;

import java.util.List;

public class UnorderedList extends Forik implements AbstractList {
    UnorderedList(List<ListItem> els) {
        super(els, "", "<ul>", "</ul>");
    }

    @Override
    public void toHtml(StringBuilder s){
        super.toHtml(s);
    }


}
