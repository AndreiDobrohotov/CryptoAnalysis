package Operations;

//��������, ������ � �������� ������
public class Decryptor extends Encryptor{
    public Decryptor(int key) {
        super(-key);
    }
}
