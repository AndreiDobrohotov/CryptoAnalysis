package Operations;

//шифратор, только с обратным ключем
public class Decryptor extends Encryptor{
    public Decryptor(int key) {
        super(-key);
    }
}
