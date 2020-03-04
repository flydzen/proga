import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordStatSortedLastIndex {
    public static void main(String[] args) throws IOException {
        Map<String, IntList> dict = new TreeMap<>();
        FileInputStream f = new FileInputStream(args[0]);
        Scan scan = new Scan(f);
        while (scan.hasNextLine()) {
            int cou = 0;
            Set<String> was = new HashSet<>();
            String line = scan.nextLine();
            Scan lineSc = new Scan(line);
            while (lineSc.hasNext()) {
                String word = lineSc.next().toLowerCase();
                cou++;
                IntList value = dict.getOrDefault(word, new IntList());
                if (value.size() == 0) {
                    value.add(0);
                }
                if (was.add(word)) {
                    // если элемента нет в строке
                    value.add(cou);
                } else {
                    value.set(cou);
                }
                value.increment(0);
                dict.put(word, value);
            }
            lineSc.close();
        }
        scan.close();
        f.close();
        List<String> mass = new ArrayList<>(dict.keySet());
        BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
        for (String key : mass) {
            fout.write(key);
            fout.write(" ");
            fout.write(dict.get(key).toString());
            fout.write("\n");
        }
        fout.close();
    }
}