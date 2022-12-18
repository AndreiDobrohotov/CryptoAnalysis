import Operations.OperationType;

import java.io.*;
import java.util.Arrays;

public class MainFileOperation {

    //читаем файл, шифруем текст и записываем в другой файл
    public static void fileOperation(OperationType operation, String pathForRead, String pathForWrite){
        try(FileReader reader = new FileReader(pathForRead);
            FileWriter writer = new FileWriter(pathForWrite)) {

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
                //вызываем метод, который изменяет считанный из файла буффер и записываем его в новый файл
                writer.write(operation.operate(readBuff));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}