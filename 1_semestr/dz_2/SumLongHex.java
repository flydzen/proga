import java.util.ArrayList;
public class SumLongHex {
    public static long parse(int l, int r, String s, int base){
        if (base == 16) {
            return Long.parseUnsignedLong(s.substring(l, r+1), base);
        } else {
            return Long.parseLong(s.substring(l, r+1), base);
        }
    }

    public static void main(String[] args) {
        long summ = 0;
        for(String e: args) {
            int base = 10;
            int l = 0;
            int r = -1;
            for(int i = 0; i < e.length(); i++) {
                char c = e.charAt(i);
                if(Character.isWhitespace(c)){
                    if(l<=r) {
                        long parsedN = parse(l, r, e, base);
                        summ += parsedN;
                        base = 10;
                    }
                    l = i+1;
                }else {
                    c = Character.toLowerCase(c);
                    if(c == 'x'){
                        base = 16;
                        l += 2;
                    }
                    r = i;
                }
            }
            if(l<=r) {
                long parsedN = parse(l, r, e, base);
                summ += parsedN;
            }
        }
        System.out.print(summ);
    }
}