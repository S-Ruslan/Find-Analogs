
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileWork {
    private String fileName = "dataBase.txt";
    Map<String, String[]> analogs = new HashMap<>();

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int count = 0;

    public int getCount() {
        return count;
    }
    public void LoadingNewFile(String fileName) throws IOException {
        count = 0;
        fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + fileName;
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName),"CP1251");
        Scanner fileScanner = new Scanner(isr);
        String s;
        String[] parts = new String[]{"","",""};
        String[] init;
        String[] information;
        while (fileScanner.hasNextLine()){
            boolean addingArt = false;
            boolean addingComm = false;
            information = new String[2];
            s = fileScanner.nextLine().trim();
            init = s.split("\t");
            if(init.length>0){
                for (int i = 0; i < init.length; i++){
                    parts[i] = init[i];
                }
            }
            information[0] = parts[1].trim();
            information[1] = parts[2].trim();
            parts[0] = parts[0].trim();
            if(!information[0].equals("") || !information[1].equals("")){
                if(!analogs.containsKey(parts[0])){
                    analogs.put(parts[0].trim(),information);
                    count++;
                    System.out.println(parts[0]);
                }
                else {
                    if (!analogs.get(parts[0])[0].contains(information[0])){
                        information[0] = analogs.get(parts[0])[0] + "!" + information[0];
                        addingArt = true;
                    }
                    if (!analogs.get(parts[0])[1].contains(information[1])){
                        information[1] = analogs.get(parts[0])[1] + "!" + information[1];
                        addingComm = true;
                    }
                    //предущий участок кода аналогичен загрузке из базы
                    //проверка на добавления артикула/коммента.
                    //если есть оба - перезаписывается в чистом виде
                    //если артикул обновлен - коммент подтягивается из базы
                    //если коммент обновлен - артикул подтягивается из базы
                    if (addingArt && addingComm){
                        analogs.put(parts[0],information);
                        count++;
                    } else if (addingArt){
                        information[1] = analogs.get(parts[0])[1];
                        analogs.put(parts[0],information);
                        count++;
                    } else if (addingComm){
                        information[0] = analogs.get(parts[0])[0];
                        analogs.put(parts[0],information);
                        count++;
                    }
                }
            }
        }
        fileScanner.close();
        isr.close();
    }

    //базовый конструктор для заполнения списка MAP из фаила базы данных
    public FileWork() throws IOException {
        fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + fileName;
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName),"CP1251");
        Scanner fileScanner = new Scanner(isr);
        String s;
        String[] parts = new String[]{"","",""};
        String[] init;
        String[] information;
        while (fileScanner.hasNextLine()){
            boolean adding = false;
            for (int i = 0; i < parts.length; i++){
                parts[i] = "";
            }
            information = new String[2];
            //обрезка строки от лишних пробелов
            s = fileScanner.nextLine().trim();
            //деление строки на части по знаку табуляции
            init = s.split("\t");
            //заполнение массива частей
            if(init.length>0){
                for (int i = 0; i < init.length; i++){
                    parts[i] = init[i];
                }
            }
            //обрезка первых 2 частей (аналог и коммент)
                information[0] = parts[1].trim();
                information[1] = parts[2].trim();
                if(!information[0].equals("") || !information[1].equals("")){
                    //1 элемент в фаиле - артикул, далее аналог и коммент.
                    //если в базе нет артикула, добаляется в базу новый артикул с ключом.
                    //данная процедура излишне в идеальной базе, но позволяет перестраховаться
                    //если в базе есть повторы
                    if(!analogs.containsKey(parts[0])){
                        analogs.put(parts[0].trim(),information);
                    }
                    //проверка насчет совпадений, упоминалось выше
                    //inf0 - артикул, inf1 - коммент
                    else {
                        //суммирование совпадающих аналогов и комментов
                        //защита от повторов, т.к. позиции могут быть частично совпадающими
                        if (!analogs.get(parts[0])[0].contains(information[0])){
                            information[0] = analogs.get(parts[0])[0] + "!" + information[0];
                            adding = true;
                        }
                        if (!analogs.get(parts[0])[1].contains(information[1])){
                            information[1] = analogs.get(parts[0])[1] + "!" + information[1];
                            adding = true;
                        }
                        //если найдены отличия и сформированы новые записи, то происходит добавление
                        //если 2 строки были идентичны - ничего не делать
                        if (adding){
                            analogs.put(parts[0],information);
                            System.out.println(parts[0]);
                        }
                    }
                }
            }
        fileScanner.close();
        isr.close();
    }


    public void FileWorkConvert(Map<String, String[]> analogs, String text) throws IOException {
        //работа с фаилом input и создание фаила результата out
        fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + text;
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName),"CP1251");
        Scanner fileScanner = new Scanner(isr);
        String s;
        fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + "out.txt";
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileName),"CP1251");
        BufferedWriter writer = new BufferedWriter(osw);
        boolean contain;
        while (fileScanner.hasNextLine()){
            contain = false;
            s = fileScanner.nextLine().trim();
            //Проверка строки (считаем за ключ) - со всей базой данных.
            //далее форматированный вывод
            for (String key : analogs.keySet()) {
                if (s.toUpperCase().contains(key.toUpperCase())){
                    String format = analogs.get(key)[0] + "\t" + analogs.get(key)[1];
                    writer.write(key +"\t" + format + "\n");
                    contain = true;
                    break;
                }
            }
            if (!contain){
                writer.write(s + "\t" + "NOT FOUNDED\n");
            }

            }
        fileScanner.close();
        isr.close();
        writer.close();
        osw.close();

    }
    public String[] getAnalog(String input){
        if (!analogs.containsKey(input)){
            return new String[]{"Аналога в списке нет", ""};
        }
        else return analogs.get(input);
    }
}
