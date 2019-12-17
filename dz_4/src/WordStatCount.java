import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class WordStatCount {
    private static LinkedHashMap<String, Integer> dict;

    public static void main(String[] args) throws IOException {
        BufferedWriter ftemp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[0] + "aaa"), StandardCharsets.UTF_8));
        dict = new LinkedHashMap<>();
        int cou = 0;
        try (BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
            String line;
            while ((line = fin.readLine()) != null) {
                line = line.toLowerCase();
                int l = 0;
                int r = -2;
                for (int i = 0; i < line.length() + 1; i++) {
                    char c = i != line.length() ? line.charAt(i) : '0';
                    ftemp.write(c);
                    if (Character.getType(c) == 20 || c == '\'' || Character.isLetter(c)) {
                        r = i;
                    } else {
                        if (r == i - 1) {
                            String word = line.substring(l, r + 1);
                            int value = dict.getOrDefault(word, 0);
                            if (value == 0) {
                                dict.put(word, 1);
                                cou++;
                            } else {
                                dict.put(word, value + 1);
                            }
                        }
                        l = i + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ftemp.close();

        List<String> mass = new ArrayList<>(dict.keySet());

        Comparator<String> comparator = Comparator.comparingInt(o -> dict.get(o));
        mass.sort(comparator);
        try {
            BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            for (int i = 0; i < cou; i++) {
                String key = mass.get(i);
                fout.write(key);
                fout.write(" ");
                fout.write(Integer.toString(dict.get(key)));
                fout.write("\n");
            }
            fout.close();
            for (int i = 0; i < 1000; i++) {
                cou++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}