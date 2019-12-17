import java.util.ArrayList;
public class SumHex {
    public static void main(String[] args) {
        int summ = 0;
        for(String e: args) {
            String num = "";
            int base = 10;
            for(int i = 0; i < e.length(); i++) {
                char c = e.charAt(i);
                if(Character.isDigit(c)){
                    num += c;
                }else if (c=='x' || c=='X') {
                    num = "";
                    base = 16;
                }else if(Character.isLetter(c)) {
                    num += c;
                }else if (c=='-') {
                    num += '-';
                }else if (!num.equals("")) {
                    summ += (int) Long.parseLong(num, base);
                    num = "";
                    base = 10;
                }
            }
            if(!num.equals("")) {
                summ += (int) Long.parseLong(num, base);
            }
        }
        System.out.print(summ);
    }
}