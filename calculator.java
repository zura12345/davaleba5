import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class calculator {

    ArrayList<String> NewArr;
    String AnswearHere;
    String[] operators = {"+", "-", "^", "*", "/"};
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");

    public calculator {
        Scanner input = new Scanner(System.in);
        System.out.print("sheiyvanet texti ");
        String input_answear = input.nextLine();
        String result = calculate(input_answear);
        System.out.println("rusult: " + result);
        write_in_file(result);
    }

    public String parseStringHere(String s) {

        NewArr = new ArrayList<String>();
        AnswearHere = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            if (Character.isDigit(s.charAt(i))) {
                AnswearHere = s.charAt(i) + AnswearHere;
                if (i == 0) {
                    if(!AnswearHere.equals("")){
                        NewArr.add(0,AnswearHere);
                        AnswearHere="";
                    }
                }
            } else {
                if (s.charAt(i) == '.') AnswearHere = s.charAt(i) + AnswearHere;
                else if (s.charAt(i) == '-' && (i == 0 || (!Character.isDigit(s.charAt(i - 1))))) {
                    AnswearHere = s.charAt(i) + AnswearHere;
                    if(!AnswearHere.equals("")){
                        NewArr.add(0,AnswearHere);
                        AnswearHere="";
                    }
                } else {
                    if(!AnswearHere.equals("")){
                        NewArr.add(0,AnswearHere);
                        AnswearHere="";
                    }
                    AnswearHere += s.charAt(i);
                    if(!AnswearHere.equals("")){
                        NewArr.add(0,AnswearHere);
                        AnswearHere="";
                    }
                }
            }
        }
        for(int i =0; i < 5; i++) {
            NewArr = calculate_AnswearHere(NewArr, operators[i]);
        }
        return NewArr.get(0);
    }
    public String calculate(String ToAnswear) {
        while (ToAnswear.contains(Character.toString('(')) || ToAnswear.contains(Character.toString(')'))) {
            for (int o = 0; o < ToAnswear.length(); o++) {
                if (ToAnswear.charAt(o) == ')') {
                    for (int i = o; i >= 0; i--) {
                        if (ToAnswear.charAt(i) == '(') {
                            String in = ToAnswear.substring(i + 1, o);
                            in = parseStringHere(in);
                            ToAnswear = ToAnswear.substring(0, i) + in + ToAnswear.substring(o + 1);
                            i = o = 0;
                        }
                    }
                }
            }
        }
        ToAnswear = parseStringHere(ToAnswear);
        return ToAnswear;
    }
    public ArrayList<String>calculate_AnswearHere(ArrayList<String> arrayList, String op1){
        double result = 0;
        double ResultOne;
        double ResultTwo;
        for(int c = 0; c<arrayList.size();c++){
            if(arrayList.get(c).equals(op1)){
                ResultOne = Double.parseDouble(arrayList.get(c-1));
                ResultTwo = Double.parseDouble(arrayList.get(c+1));
                switch (arrayList.get(c)) {
                    case "^":
                        result = Math.pow(ResultOne,(Integer.parseInt(arrayList.get(c+1))));
                        break;
                    case "*":
                        result = ResultOne * ResultTwo;
                        break;
                    case "/":
                        result = ResultOne / ResultTwo;
                        break;
                    case "+":
                        result = ResultOne + ResultTwo;
                        break;
                    case "-":
                        result = ResultOne - ResultTwo;
                        break;
                }
                try{
                    arrayList.set(c, String.valueOf(result));
                    arrayList.remove(c + 1);
                    arrayList.remove(c - 1);
                }catch (Exception ignored){}
            }else continue;

            c = 0;
        }
        return arrayList;
    }

    public void write_in_file(String str){
        try{
            LocalDateTime now = LocalDateTime.now();
            FileWriter file = new FileWriter("file.txt",true);
            PrintWriter pw = new PrintWriter(file);
            pw.print(str);
            pw.println(dtf.format(now));
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}