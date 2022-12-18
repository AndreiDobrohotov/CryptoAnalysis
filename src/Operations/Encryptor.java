package Operations;

import java.util.ArrayList;

//базовый шифратор
public class Encryptor implements OperationType {

    public Encryptor(int key) {
        this.key = key;
    }

    private final int key;


    //изменяем массив символов на значение ключа
    @Override
    public char[] operate (char[] buff){
        ArrayList<Character> chars = Alphabet.getAllChars();
        //создаем результирующий массив того же размера
        char[] newBuff = new char[buff.length];
        for(int i=0; i<buff.length; i++){
            //получаем индекс каждого элемента из списка элементов шифра
            int charNum = chars.indexOf(buff[i]);
            //если элемент неизвеистен, записываем как есть
            if(charNum==-1) {
                newBuff[i]=buff[i];
            }
            else{
                //на сколько позиций смещаемся по шифру
                int step = charNum + key;
                //если выходим за границу списка, начинаем с другого конца
                newBuff[i] = chars.get(step < 0 ? step + chars.size() :
                        step >= chars.size() ? step - chars.size() : step);
            }
        }
        //возвращаем измененый массив
        return newBuff;
    }



}

