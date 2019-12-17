import java.util.ArrayList;
public class SumLong {
    public static void main(String[] args){
        long summ = 0;
        for(String e: args) {
            String num = "";
            for(int i = 0; i < e.length(); i++){
                char c = e.charAt(i);
                if(Character.isDigit(c)){
                    num += c;

                }else if(c=='-'){
                    num += '-';
                }else if (!num.equals("")) {
                    summ += Long.parseLong(num);
                    num = "";
                }
            }
            if(!num.equals("")) {
                summ += Long.parseLong(num);
            }
        }
        System.out.print(summ);
    }
}