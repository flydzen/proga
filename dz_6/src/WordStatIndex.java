import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class WordStatIndex {
    public static void main(String[] args){
        LinkedHashMap<String, IntList> dict = new LinkedHashMap<>();
        int cou = 0;
        try (FileInputStream f = new FileInputStream(args[0])){

            Scan scan = new Scan(f);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                Scan lineSc = new Scan(line);
                while (lineSc.hasNext()) {
                    String word = lineSc.next().toLowerCase();
                    cou++;
                    IntList value = dict.getOrDefault(word, new IntList());
                    value.add(cou);
                    dict.put(word, value);
                }
                lineSc.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try (BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            List<String> mass = new ArrayList<>(dict.keySet());

            for (String key : mass) {
                fout.write(key);
                fout.write(" ");
                fout.write(Integer.toString(dict.get(key).size()));
                fout.write(" ");
                fout.write(dict.get(key).toString());
                fout.write("\n");
            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
}