package Operations;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StatisticAnalyzer implements OperationType {


    @Override
    public char[] operate(char[] buff) {
        char[] newBuff = new char[buff.length];
        for(int i=0; i<buff.length; i++){
            //если элемент содержится в мапе, делаем замену через ключ на значение
            if(encryptionMap.containsKey(buff[i])){
                newBuff[i]=encryptionMap.get(buff[i]);
            }
            //если не содержится, записываем как есть
            else{
                newBuff[i] = buff[i];
            }
        }
        //возвращаем измененый массив
        return newBuff;

    }


    private static ArrayList<CharCounter> sourceFileAnalysis;
    private static ArrayList<CharCounter> targetFileAnalysis;
    private static HashMap<Character,Character> encryptionMap;
    private static boolean isSourceFileAnalysed = false;

    //проверка, получен ли анализ первого файла, чтобы иметь возможность начать работу со вторым
    public static boolean isSourceFileAnalysed() {
        return isSourceFileAnalysed;
    }

    //создает ArrayList элементов CharCounter на основе алфавита
    private static ArrayList<CharCounter> createEmptyStatisticList(){
        ArrayList<CharCounter> list = new ArrayList<>();
        for(Character character : Alphabet.getAllChars()){
            list.add(new CharCounter(character));
        }
        return list;
    }

    //Запускает анализ первого файла и запимывает в переменную
    public static void makeSourceFileAnalysis(String path) {
        sourceFileAnalysis = createStatisticListFromFile(path);
        isSourceFileAnalysed = true;
    }

    //Запускает анализ второго файла и записывает в переменную
    public static void makeTargetFileAnalysis(String path) {
        targetFileAnalysis = createStatisticListFromFile(path);
    }

    //Создает мапу на основе двух отсортированных листов
    public static void createNewEncryptionMap(){
        encryptionMap = new HashMap<>();
        //сортируем листы по количеству вхождения символов в тексты
        sourceFileAnalysis.sort((o1, o2) -> o2.getCountValue() - o1.getCountValue());
        targetFileAnalysis.sort((o1, o2) -> o2.getCountValue() - o1.getCountValue());
        for(int i = 0; i<sourceFileAnalysis.size(); i++){
            //Закоменченая строка дает информацию о заменах в консоль
            //System.out.println(sourceFileAnalysis.get(i)+ " = " + targetFileAnalysis.get(i));
            //записывает в мапу пары значений по их популярности в тексте
            encryptionMap.put(targetFileAnalysis.get(i).charValue,sourceFileAnalysis.get(i).charValue);
        }
    }

    //читаем файл и получаем из него информацию о количестве вхождений букв алфавита
    private static ArrayList<CharCounter> createStatisticListFromFile(String pathForRead){
        ArrayList<CharCounter> statistic = createEmptyStatisticList();
        try(FileReader reader = new FileReader(pathForRead)) {
            //указываем размер буффера для чтения и записи
            int buffSize = 2048;
            char[] readBuff = new char[buffSize];
            int c;
            //читаем из файла в буффер (переменная 'с' хранит количество считанных символов)
            while((c = reader.read(readBuff))>0){
                if(c < buffSize)  {
                    //если размер буффера больше количества считанных символов, создаем новый буффер поменьше
                    readBuff = Arrays.copyOf(readBuff, c);
                }
                //вызываем метод, который занимается подсчетом
                getStatisticFromBuffer(statistic, readBuff);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statistic;
    }

    //метод подсчета символов алфавита в буфере
    private static void getStatisticFromBuffer(ArrayList<CharCounter> list, char[] readBuff) {
        for(int i=0; i<readBuff.length; i++){
            //если символ содержится в алфавите
            int charNum = Alphabet.getAllChars().indexOf(readBuff[i]);
            if(charNum!=-1) {
                //значение СharCounter увеличивается на 1
                list.get(charNum).incValue();
            }
        }
    }

    //класс пара - символ и его количественное вхождение в текст
    public static class CharCounter {
        private final char charValue;
        private int countValue = 0;

        public CharCounter(char charValue){
            this.charValue = charValue;
        }

        public int getCountValue() {
            return countValue;
        }

        //увеличивает внутренний счетчик на 1
        public void incValue(){
            countValue++;
        }

        //метод для отладки в консоли
        @Override
        public String toString() {
            return charValue + "[" + countValue + "]";
        }
    }
}
