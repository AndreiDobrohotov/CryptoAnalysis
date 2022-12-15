import Operations.OperationType;

import java.io.*;
import java.util.Arrays;

public class MainFileOperation {

    //������ ����, ������� ����� � ���������� � ������ ����
    public static void fileOperation(OperationType operation, String pathForRead, String pathForWrite){
        try(FileReader reader = new FileReader(pathForRead);
            FileWriter writer = new FileWriter(pathForWrite)) {

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
                //�������� �����, ������� �������� ��������� �� ����� ������ � ���������� ��� � ����� ����
                writer.write(operation.operate(readBuff));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}