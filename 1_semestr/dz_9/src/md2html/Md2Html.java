package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Md2Html {
    public static class Paragraph {
        private Map<String, Integer> inRange;
        private StringBuilder textSb;
        private Map<String, String> partR, partOne;
        private Map<Integer, String> modif;
        private char[] text;
        public int h = 0;
        private boolean empty;

        public Paragraph() {
            inRange = new HashMap<>();
            partR = new HashMap<>();
            partOne = new HashMap<>();
            textSb = new StringBuilder();
            partR.put("*", "em");
            partR.put("**", "strong");
            partR.put("_", "em");
            partR.put("__", "strong");
            partR.put("-", "s");
            partR.put("--", "s");
            partR.put("`", "code");
            partOne.put("<", "&lt;");
            partOne.put(">", "&gt;");
            partOne.put("&", "&amp;");

            modif = new HashMap<>();
            empty = true;
        }

        public boolean isEmpty(){
            return empty;
        }


        public void Add(String line) {
            empty = false;
            textSb.append(line);
            textSb.append("\n");
        }

        public String convert(){
            preparse();
            return build();
        }

        private void preparse() {
            text = textSb.toString().toCharArray();
            while (text[h] == '#') {
                h += 1;
            }
            if (text[h] != ' ') {
                h = 0;
            }
            if (h > 0) {
                for (int i = 0; i < h + 1; i++) {
                    text[i] = 0;
                }
            }
            for (int i = h; i < text.length; i++) {
                if (partR.get(Character.toString(text[i]))!=null && inRange.get("![")==null) {
                    boolean two = false;
                    if (i + 1 < text.length && text[i + 1] == text[i]) {
                        two = true;
                        i++;
                    }
                    char c = text[i];
                    if (c == '-' && !two){
                        continue;
                    }
                    String sub = two ? String.valueOf(c) + c : String.valueOf(c);
                    int op = inRange.getOrDefault(sub, -1);
                    if (op == -1) {
                        inRange.put(sub, i);
                    } else {
                        String html = partR.get(sub);
                        modif.put(op, "<"+html+">");
                        modif.put(i, "</"+html+">");
                        text[op] = 0;
                        text[i] = 0;
                        if (two){
                            text[op-1] = 0;
                            text[i-1] = 0;
                        }
                        inRange.put(sub, -1);
                    }
                } else if (text[i] == '\\') {
                    text[i] = 0;
                    i++;
                } else if (partOne.get(Character.toString(text[i]))!=null){
                    modif.put(i, partOne.get(String.valueOf(text[i])));
                    text[i] = 0;
                } else if (text[i] == '!' && i+1 < text.length && text[i+1] == '['){
                    inRange.put("![", i);
                    i++;
                } else if (text[i] == ']' && inRange.get("![")!=null){
                    if (i+1 < text.length && text [i+1] == '(') {
                        inRange.put("](", i);
                        i++;
                    }
                } else if (text[i] == ')' && inRange.get("](")!=null){
                    int a = inRange.get("![");
                    int b = inRange.get("](");
                    text[a] = 0;
                    text[a+1] = 0;
                    text[b] = 0;
                    text[b+1] = 0;
                    text[i] = 0;
                    modif.put(a, "<img alt='");
                    modif.put(b, "' src='");
                    modif.put(i, "'>");
                    inRange.put("![", null);
                    inRange.put("](", null);
                }
            }
        }

        private String build() {
            StringBuilder sb = new StringBuilder();
            if (h != 0) {
                sb.append("<h").append(h).append(">");
            } else {
                sb.append("<p>");
            }
            for (int i = 0; i < textSb.length(); i++) {
                if (text[i] != 0) {
                    sb.append(text[i]);
                }else{
                    sb.append(modif.getOrDefault(i, ""));
                }
            }
            sb.deleteCharAt(sb.length()-1);
            if (h != 0) {
                sb.append("</h").append(h).append(">");
            } else {
                sb.append("</p>");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        List<Paragraph> paragraphs = new ArrayList<>();
        try (FileInputStream f = new FileInputStream(args[0])) {
            Scan scan = new Scan(f);
            Paragraph p = new Paragraph();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!line.equals("")) {
                    p.Add(line);
                }else if (!p.isEmpty()){
                    paragraphs.add(p);
                    p = new Paragraph();
                }
            }
            if (!p.isEmpty()){
                paragraphs.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));){
            for(Paragraph p: paragraphs){
                String s = p.convert();
                fout.write(s);
                fout.write("\n");
            }
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
