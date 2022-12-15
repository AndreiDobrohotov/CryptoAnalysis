package Operations;

//����� ���������� Brute Force
public class BruteForce implements OperationType {
    private int key = 0;

    public int getKey() {
        return key;
    }

    @Override
    //�������� ����� ����, ������� �������� ���� ��������� ������ ����������
    public char[] operate(char[] buff) {
        char[] newBuff = null;
        //���� ���� �� ��������, �������� ��������� ��� � ������ ������� ���������� ������
        if(key==0){
            for(int i = 1; i<Alphabet.getAllChars().size(); i++){
                //��������� �� ����� i
                newBuff = new Decryptor(i).operate(buff);
                //����������� ���������
                int result = analysis(newBuff);
                //���� ���������� �������� �������� ������ 0 - ��� ��� ����.
                if(result>0) {
                    key = i;
                    break;
                }
            }
        }
        //���� ���� ������, ���������� ���������� ���������� ������������������� �������
        else{
            newBuff = new Decryptor(key).operate(buff);
        }
        return newBuff;
    }

    //��������� ����� �� ��� �������� �������
    private int analysis(char[] chars){
        //���������� �������� � �� �������� �������� (-1 �� ������, +1 �� �����)
        //�� ����� ����������, ���������� ��������� �������� �� ���� ������������� ������ - ������ ��� �������� ������ ���� 0
        int successCounter = 0;
        for(int i=1;i<chars.length-1;i++){
            //���� ������ �������� ������ ���������� - ��������� ��������� ������ �� ������
            if(Alphabet.getAllSigns().contains(chars[i])) {
                successCounter += isSpace(chars[i+1]);
            }
            //�����, ���� ������ �������� ��������� ������ - ��������� ���������� ������ �� ������
            else if(Alphabet.getAllUpperChars().contains(chars[i])){
                successCounter += isSpace(chars[i-1]);
            }
        }
        return successCounter;
    }

    //��������� ������ �� ������, ����� ������ +1, ������� -1
    private int isSpace(char isSpace) {
        return isSpace==' '? 1 : -1;
    }



}
