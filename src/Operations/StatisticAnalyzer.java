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
            //���� ������� ���������� � ����, ������ ������ ����� ���� �� ��������
            if(encryptionMap.containsKey(buff[i])){
                newBuff[i]=encryptionMap.get(buff[i]);
            }
            //���� �� ����������, ���������� ��� ����
            else{
                newBuff[i] = buff[i];
            }
        }
        //���������� ��������� ������
        return newBuff;

    }


    private static ArrayList<CharCounter> sourceFileAnalysis;
    private static ArrayList<CharCounter> targetFileAnalysis;
    private static HashMap<Character,Character> encryptionMap;
    private static boolean isSourceFileAnalysed = false;

    //��������, ������� �� ������ ������� �����, ����� ����� ����������� ������ ������ �� ������
    public static boolean isSourceFileAnalysed() {
        return isSourceFileAnalysed;
    }

    //������� ArrayList ��������� CharCounter �� ������ ��������
    private static ArrayList<CharCounter> createEmptyStatisticList(){
        ArrayList<CharCounter> list = new ArrayList<>();
        for(Character character : Alphabet.getAllChars()){
            list.add(new CharCounter(character));
        }
        return list;
    }

    //��������� ������ ������� ����� � ���������� � ����������
    public static void makeSourceFileAnalysis(String path) {
        sourceFileAnalysis = createStatisticListFromFile(path);
        isSourceFileAnalysed = true;
    }

    //��������� ������ ������� ����� � ���������� � ����������
    public static void makeTargetFileAnalysis(String path) {
        targetFileAnalysis = createStatisticListFromFile(path);
    }

    //������� ���� �� ������ ���� ��������������� ������
    public static void createNewEncryptionMap(){
        encryptionMap = new HashMap<>();
        //��������� ����� �� ���������� ��������� �������� � ������
        sourceFileAnalysis.sort((o1, o2) -> o2.getCountValue() - o1.getCountValue());
        targetFileAnalysis.sort((o1, o2) -> o2.getCountValue() - o1.getCountValue());
        for(int i = 0; i<sourceFileAnalysis.size(); i++){
            //������������ ������ ���� ���������� � ������� � �������
            //System.out.println(sourceFileAnalysis.get(i)+ " = " + targetFileAnalysis.get(i));
            //���������� � ���� ���� �������� �� �� ������������ � ������
            encryptionMap.put(targetFileAnalysis.get(i).charValue,sourceFileAnalysis.get(i).charValue);
        }
    }

    //������ ���� � �������� �� ���� ���������� � ���������� ��������� ���� ��������
    private static ArrayList<CharCounter> createStatisticListFromFile(String pathForRead){
        ArrayList<CharCounter> statistic = createEmptyStatisticList();
        try(FileReader reader = new FileReader(pathForRead)) {
            //��������� ������ ������� ��� ������ � ������
            int buffSize = 2048;
            char[] readBuff = new char[buffSize];
            int c;
            //������ �� ����� � ������ (���������� '�' ������ ���������� ��������� ��������)
            while((c = reader.read(readBuff))>0){
                if(c < buffSize)  {
                    //���� ������ ������� ������ ���������� ��������� ��������, ������� ����� ������ ��������
                    readBuff = Arrays.copyOf(readBuff, c);
                }
                //�������� �����, ������� ���������� ���������
                getStatisticFromBuffer(statistic, readBuff);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statistic;
    }

    //����� �������� �������� �������� � ������
    private static void getStatisticFromBuffer(ArrayList<CharCounter> list, char[] readBuff) {
        for(int i=0; i<readBuff.length; i++){
            //���� ������ ���������� � ��������
            int charNum = Alphabet.getAllChars().indexOf(readBuff[i]);
            if(charNum!=-1) {
                //�������� �harCounter ������������� �� 1
                list.get(charNum).incValue();
            }
        }
    }

    //����� ���� - ������ � ��� �������������� ��������� � �����
    public static class CharCounter {
        private final char charValue;
        private int countValue = 0;

        public CharCounter(char charValue){
            this.charValue = charValue;
        }

        public int getCountValue() {
            return countValue;
        }

        //����������� ���������� ������� �� 1
        public void incValue(){
            countValue++;
        }

        //����� ��� ������� � �������
        @Override
        public String toString() {
            return charValue + "[" + countValue + "]";
        }
    }
}
