package Operations;

import java.util.ArrayList;

//������� ��������
public class Encryptor implements OperationType {

    public Encryptor(int key) {
        this.key = key;
    }

    private final int key;


    //�������� ������ �������� �� �������� �����
    @Override
    public char[] operate (char[] buff){
        ArrayList<Character> chars = Alphabet.getAllChars();
        //������� �������������� ������ ���� �� �������
        char[] newBuff = new char[buff.length];
        for(int i=0; i<buff.length; i++){
            //�������� ������ ������� �������� �� ������ ��������� �����
            int charNum = chars.indexOf(buff[i]);
            //���� ������� �����������, ���������� ��� ����
            if(charNum==-1) {
                newBuff[i]=buff[i];
            }
            else{
                //�� ������� ������� ��������� �� �����
                int step = charNum + key;
                //���� ������� �� ������� ������, �������� � ������� �����
                newBuff[i] = chars.get(step < 0 ? step + chars.size() :
                        step >= chars.size() ? step - chars.size() : step);
            }
        }
        //���������� ��������� ������
        return newBuff;
    }



}

