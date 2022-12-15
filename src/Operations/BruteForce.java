package Operations;

//класс реализации Brute Force
public class BruteForce implements OperationType {
    private int key = 0;

    public int getKey() {
        return key;
    }

    @Override
    //пытаемся найти ключ, методом перебора всех доступных ключей шифрования
    public char[] operate(char[] buff) {
        char[] newBuff = null;
        //если ключ не известен, пытаемся вычислить его в рамках первого пришедшего буфера
        if(key==0){
            for(int i = 1; i<Alphabet.getAllChars().size(); i++){
                //дешифруем по ключу i
                newBuff = new Decryptor(i).operate(buff);
                //анализируем результат
                int result = analysis(newBuff);
                //если количество успешных проверок больше 0 - это наш ключ.
                if(result>0) {
                    key = i;
                    break;
                }
            }
        }
        //если ключ найден, делигируем дальнейшую дешифровку специализированному объекту
        else{
            newBuff = new Decryptor(key).operate(buff);
        }
        return newBuff;
    }

    //проверяем буфер на два основных правила
    private int analysis(char[] chars){
        //количество успешных и не успешных проверок (-1 за провал, +1 за успех)
        //по моему наблюдению, количество неудачных проверок на всех зашифрованных файлах - уводит это значение сильно ниже 0
        int successCounter = 0;
        for(int i=1;i<chars.length-1;i++){
            //если символ является знаком препинания - проверяем следующий символ на пробел
            if(Alphabet.getAllSigns().contains(chars[i])) {
                successCounter += isSpace(chars[i+1]);
            }
            //иначе, если символ является заглавной буквой - проверяем предыдущий символ на пробел
            else if(Alphabet.getAllUpperChars().contains(chars[i])){
                successCounter += isSpace(chars[i-1]);
            }
        }
        return successCounter;
    }

    //проверяет символ на пробел, успех вернет +1, неудача -1
    private int isSpace(char isSpace) {
        return isSpace==' '? 1 : -1;
    }



}
