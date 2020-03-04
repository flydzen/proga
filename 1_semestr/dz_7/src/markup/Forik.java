package markup;

import java.util.List;

public class Forik implements Markable {
    private List<? extends Markable> items;
    String text;
    private String l = "";
    private String r = "";
    private String m = "";
    Forik(List<? extends Markable> els, String m, String l, String r) {
        items = List.copyOf(els);
        this.l = l;
        this.r = r;
        this.m = m;
    }
    Forik(String text){
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder s){
        s.append(m);
        for(Markable p : items){
            p.toMarkdown(s);
        }
        s.append(m);
    }

    @Override
    public void toHtml(StringBuilder s) {
        s.append(l);
        for(Markable p : items){
            p.toHtml(s);
        }
        s.append(r);
    }
}
