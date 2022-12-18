package Operations;

import java.util.ArrayList;

//класс содержащий весь алфавит
public class Alphabet {
    private static final String allSymbols = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЪЭЮЯабвгдеёжзийклмнопрстуфхчшщьъэюя .,:!?-\"";
    private static final String upperSymbols = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЪЭЮЯ";
    private static final ArrayList<Character> allChars = new ArrayList<>();
    private static final ArrayList<Character> allUpperChars = new ArrayList<>();
    private static final ArrayList<Character> allSigns = new ArrayList<>();

    // блок инициализации всех листов из стринговых переменных
    static {
        for(Character character : allSymbols.toCharArray()){
            allChars.add(character);
        }
        for(Character character : upperSymbols.toCharArray()){
            allUpperChars.add(character);
        }
        for(Character character : allSymbols.substring(allSymbols.indexOf('.'),allSymbols.lastIndexOf('?')).toCharArray()){
            allSigns.add(character);
        }
    }

    public static ArrayList<Character> getAllChars() {
        return allChars;
    }

    public static ArrayList<Character> getAllUpperChars() {
        return allUpperChars;
    }

    public static ArrayList<Character> getAllSigns() {
        return allSigns;
    }
}
