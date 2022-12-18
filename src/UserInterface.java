import Operations.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class UserInterface extends JFrame {
    private static final int x = 600, y = 940;
    private static File file;

    private static final Color purpleColor = new Color(89,14,91);
    private static final Color redColor = new Color(110,0,0);
    private static final Color greenColor = new Color(0,90,0);
    private static final Font font = new Font("Magneto",Font.BOLD,21);

    //главная панель
    private static final ImagePanel PANEL = new ImagePanel();

    //все кнопочные виджеты
    private static final JButton openFileButton = new JButton();
    private static final JButton encryptionButton = new JButton();
    private static final JButton decryptionButton = new JButton();
    private static final JButton bruteForceButton = new JButton();
    private static final JButton analysisButton = new JButton();
    private static final JButton statisticButton = new JButton();
    private static final JButton plusKeyButton = new JButton();
    private static final JButton minusKeyButton = new JButton();

    //все текстовые виджеты
    private static final JLabel openFileStatus = new JLabel("Файл не выбран.");
    private static final JLabel fileName1 = new JLabel();
    private static final JLabel encryptedFileName = new JLabel();
    private static final JLabel fileName2 = new JLabel();
    private static final JLabel decryptedFileName = new JLabel();
    private static final JLabel fileName3 = new JLabel();
    private static final JLabel bruteForcedFileName = new JLabel();
    private static final JLabel fileName4 = new JLabel();
    private static final JLabel statisticFileName = new JLabel();
    private static final JLabel key = new JLabel("13");

    //группа методов по работе с текстом
    private static int getKey(){return Integer.parseInt(key.getText());}
    private static String getFileName(){return file==null?"":file.getName();}
    private static String getFileNameNoTxt(){return file==null?"":getFileName().substring(0,getFileName().lastIndexOf('.'));}
    private static String getEncryptedFileName(){return file==null?"":getFileNameNoTxt()+"E"+getKey();}
    private static String getDecryptedFileName(){return file==null?"":getFileNameNoTxt()+"D"+getKey();}
    private static String getFileAbsolutePath(){return file==null?"":file.getAbsolutePath();}
    private static String getFilePath() {return file==null?"":file.getParentFile().getAbsolutePath();}

    //главный конструктор
    public UserInterface() {
        super("Шифрование / Расшифровка / Криптоанализ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width/2 - x/2, dimension.height/2 - y/2,x,y);
        fileChooserRussifier();

        //добавляем панель и устаналиваем свободное размещение элементов
        add(PANEL);
        PANEL.setLayout(null);

        //инициализируем и добавляем все элементы на панель
        labelsInitialization();
        buttonsInitialization();

        //скрываем все элементы, пока не будет выбран файл
        showElements(false);

    }

    //инициализируем все кнопки (основная логика находится здесь)
    private static void buttonsInitialization(){
        //Добавляем кнопку "выбрать файл" на панель
        PANEL.add(openFileButton);
        openFileButton.setBounds(265,155,150,25);
        openFileButton.setIcon(new ImageIcon("images/selectButton.png"));
        //обработка нажатия
        openFileButton.addActionListener(e -> {
            //Создаем и открываем меню для выбора файла
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(getFileAbsolutePath()));
            int ret = fileChooser.showDialog(null, "Выбрать файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                //Проверяем что выбранный файл в формате *.txt
                fileIsCorrect(fileChooser.getSelectedFile());
            }
        });


        //Добавляем кнопку "зашифровать" на панель
        PANEL.add(encryptionButton);
        encryptionButton.setBounds(250,310,110,25);
        encryptionButton.setIcon(new ImageIcon("images/encryptButton.png"));
        //обработка нажатия
        encryptionButton.addActionListener(e -> {
            showElements(false);
            //проверяем существует ли файл
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getEncryptedFileName()+".txt";
                //создаем объект шифротора со значением текущего ключа
                OperationType encryptor = new Encryptor(getKey());
                //выполняем метод вспомогательного класса для шифрования
                MainFileOperation.fileOperation(encryptor,pathForRead,pathForWrite);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("Файл успешно зашифрован.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("Операция прервана. Файл не существует.");
            }
            file = null;
        });

        //Добавляем кнопку "расшифровать" на панель
        PANEL.add(decryptionButton);
        decryptionButton.setBounds(250,460,110,25);
        decryptionButton.setIcon(new ImageIcon("images/decryptButton.png"));
        //обработка нажатия
        decryptionButton.addActionListener(e -> {
            showElements(false);
            //проверяем существует ли файл
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getDecryptedFileName()+".txt";
                //создаем объект дешифротора со значением текущего ключа
                OperationType decryptor = new Decryptor(getKey());
                //выполняем метод вспомогательного класса для дешифрования
                MainFileOperation.fileOperation(decryptor,pathForRead,pathForWrite);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("Файл успешно расшифрован.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("Операция прервана. Файл не существует.");
            }
            file = null;
        });

        //Добавляем кнопку "Brute Force" на панель
        PANEL.add(bruteForceButton);
        bruteForceButton.setBounds(250,612,110,25);
        bruteForceButton.setIcon(new ImageIcon("images/bruteForceButton.png"));
        //обработка нажатия
        bruteForceButton.addActionListener(e -> {
            showElements(false);
            //проверяем существует ли файл
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getFileNameNoTxt()+"BF.txt";
                //создаем объект BruteForce
                BruteForce bruteForce = new BruteForce();
                //выполняем метод вспомогательного класса для поиска ключа
                MainFileOperation.fileOperation(bruteForce,pathForRead,pathForWrite);
                //после того как метод отработал, получаем найденый ключ
                int key = bruteForce.getKey();
                if(key!=0){
                    openFileStatus.setForeground(greenColor);
                    openFileStatus.setText("Подобран ключ ["+key+"]. Файл расшифрован.");
                }
                //если ключ не найдет, сообщаем о неудаче
                else{
                    openFileStatus.setForeground(redColor);
                    openFileStatus.setText("Не удалось подобрать ключ.");
                }

            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("Операция прервана. Файл не существует.");
            }
            file = null;
        });

        //Добавляем кнопку анализа файла на панель
        PANEL.add(analysisButton);
        analysisButton.setBounds(108,787,110,25);
        analysisButton.setIcon(new ImageIcon("images/analysisButton.png"));
        //обработка нажатия
        analysisButton.addActionListener(e -> {
            showElements(false);
            //проверяем существует ли файл
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                //делаем анализ первого файла
                StatisticAnalyzer.makeSourceFileAnalysis(pathForRead);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("Файл успешно проанализирован.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("Операция прервана. Файл не существует.");
            }
            file = null;
        });

        //Добавляем кнопку статистичекой дешифровки на панель
        PANEL.add(statisticButton);
        statisticButton.setBounds(302,756,110,25);
        statisticButton.setIcon(new ImageIcon("images/statisticButton.png"));
        //обработка нажатия
        statisticButton.addActionListener(e -> {
            showElements(false);
            //проверяем существует ли файл
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getFileNameNoTxt()+"SA.txt";
                //делаем анализ второго файла
                StatisticAnalyzer.makeTargetFileAnalysis(pathForRead);
                //создаем мапу на основе двух анализов со списком замены
                StatisticAnalyzer.createNewEncryptionMap();
                StatisticAnalyzer analyzer = new StatisticAnalyzer();
                //выполняем метод вспомогательного класса для замены символов на основе мапы
                MainFileOperation.fileOperation(analyzer,pathForRead,pathForWrite);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("Файл успешно переведен.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("Операция прервана. Файл не существует.");
            }
            file = null;
        });


        //Добавляем кнопку "+", для увеличения ключа, на панель
        PANEL.add(plusKeyButton);
        plusKeyButton.setBounds(450,349,25,25);
        plusKeyButton.setIcon(new ImageIcon("images/plusButton.png"));
        //обработка нажатия
        plusKeyButton.addActionListener(e -> {
            //устанавливаем предельное значение ключа
            if(getKey()<70) {
                //увеличиваем значение ключа на 1
                key.setText(String.valueOf(getKey()+1));
                refreshFileNames();
            }
        });

        //Добавляем кнопку "-", для уменьшения ключа, на панель
        PANEL.add(minusKeyButton);
        minusKeyButton.setBounds(450,376,25,25);
        minusKeyButton.setIcon(new ImageIcon("images/minusButton.png"));
        //обработка нажатия
        minusKeyButton.addActionListener(e -> {
            //устаналиваем предельное значение ключа
            if(getKey()>1) {
                //уменьшаем значение ключа на 1
                key.setText(String.valueOf(getKey()-1));
                refreshFileNames();
            }
        });

    }

    //инициализируем все текстовые поля
    private static void labelsInitialization(){
        //статус выбранного файла
        PANEL.add(openFileStatus);
        openFileStatus.setBounds(200,120,285,25);
        openFileStatus.setHorizontalAlignment(SwingConstants.CENTER);

        //название исходного файла шифрования
        PANEL.add(fileName1);
        fileName1.setBounds(110,320,130,25);
        fileName1.setHorizontalAlignment(SwingConstants.CENTER);

        //название исходного файла дешифрования
        PANEL.add(fileName2);
        fileName2.setBounds(370,470,130,25);
        fileName2.setHorizontalAlignment(SwingConstants.CENTER);

        //название файла результата шифрования
        PANEL.add(encryptedFileName);
        encryptedFileName.setBounds(370,320,130,25);
        encryptedFileName.setHorizontalAlignment(SwingConstants.CENTER);

        //название файла результата дишифрования
        PANEL.add(decryptedFileName);
        decryptedFileName.setBounds(110,470,130,25);
        decryptedFileName.setHorizontalAlignment(SwingConstants.CENTER);

        //значение ключа
        PANEL.add(key);
        key.setBounds(396,359,43,31);
        key.setFont(font);
        key.setForeground(purpleColor);
        key.setHorizontalAlignment(SwingConstants.CENTER);

        //название исходного файла Brute Force
        PANEL.add(fileName3);
        fileName3.setBounds(110,621,130,25);
        fileName3.setHorizontalAlignment(SwingConstants.CENTER);

        //название файла результата Brute Force
        PANEL.add(bruteForcedFileName);
        bruteForcedFileName.setBounds(370,621,130,25);
        bruteForcedFileName.setHorizontalAlignment(SwingConstants.CENTER);

        //название исходного файла статистического анализа
        PANEL.add(fileName4);
        fileName4.setBounds(215,780,100,25);
        fileName4.setHorizontalAlignment(SwingConstants.CENTER);

        //название файла результа статистического анализа
        PANEL.add(statisticFileName);
        statisticFileName.setBounds(400,780,100,25);
        statisticFileName.setHorizontalAlignment(SwingConstants.CENTER);
    }

    //частичная руссификация для интерфейса выбора файла
    private static void fileChooserRussifier(){
        UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Закрыть диалог");
        UIManager.put("FileChooser.directoryDescriptionText", "Папка");
        UIManager.put("FileChooser.directoryOpenButtonText", "Открыть");
        UIManager.put("FileChooser.directoryOpenButtonToolTipText", "Открыть выбранную директорию");
        UIManager.put("FileChooser.fileDescriptionText", "Все файлы");
        UIManager.put("FileChooser.helpButtonText", "Помощь");
        UIManager.put("FileChooser.helpButtonToolTipText", "Показать справку");
        UIManager.put("FileChooser.newFolderErrorText", "Ошибка при создании каталога");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть выбранный файл");
        UIManager.put("FileChooser.openDialogTitleText", "Открыть");
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить выбранный файл");
        UIManager.put("FileChooser.saveDialogTitleText", "Сохранить");
        UIManager.put("FileChooser.updateButtonText", "Обновить");
        UIManager.put("FileChooser.updateButtonToolTipText", "Обновить список директорий");
        UIManager.put("FileChooser.detailsViewActionLabelText", "Таблица");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Таблица");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблица");
        UIManager.put("FileChooser.fileAttrHeaderText", "Атрибуты");
        UIManager.put("FileChooser.fileDateHeaderText", "Изменен");
        UIManager.put("FileChooser.fileNameHeaderText", "Имя");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла:");
        UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
        UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлов:");
        UIManager.put("FileChooser.homeFolderAccessibleName", "Домой");
        UIManager.put("FileChooser.homeFolderToolTipText", "Домой");
        UIManager.put("FileChooser.listViewActionLabelText", "Список");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "Список");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.lookInLabelText", "Просмотр:");
        UIManager.put("FileChooser.newFolderAccessibleName", "Новая папка");
        UIManager.put("FileChooser.newFolderActionLabelText", "Новая папка");
        UIManager.put("FileChooser.newFolderToolTipText", "Создать новую папку");
        UIManager.put("FileChooser.refreshActionLabelText", "Обновить");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить в:");
        UIManager.put("FileChooser.upFolderAccessibleName", "Вверх");
        UIManager.put("FileChooser.upFolderToolTipText", "Вверх на один уровень");
        UIManager.put("FileChooser.viewMenuLabelText", "Просмотр");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.folderNameLabelText", "Путь директории");
    }

    //создание класса для главной панели
    public static class ImagePanel extends JPanel{
        private static final ImageIcon backGround = new ImageIcon("images/background.png");

        //задаем фон на панели
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backGround.getImage(), 0, 0, null);
        }
    }

    //проверяем что выбранный файл *.txt формата
    private static void fileIsCorrect(File fileForCheck){
        if(fileForCheck.getName().endsWith(".txt")){
            openFileStatus.setForeground(Color.BLACK);
            file = fileForCheck;
            openFileStatus.setText(getFileName());
            fileName1.setText(getFileNameNoTxt());
            fileName2.setText(getFileNameNoTxt());
            fileName3.setText(getFileNameNoTxt());
            fileName4.setText(getFileNameNoTxt());
            bruteForcedFileName.setText(getFileNameNoTxt()+"BF");
            statisticFileName.setText(getFileNameNoTxt()+"SA");
            refreshFileNames();
            showElements(true);
        }
        else{
            file = null;
            openFileStatus.setText("Неверный формат файла.");
            openFileStatus.setForeground(redColor);
            showElements(false);
        }
    }

    //обновляем названия файлов, чтобы они соответсвовали текущему ключу
    private static void refreshFileNames(){
        if(file!=null){
            encryptedFileName.setText(getEncryptedFileName());
            decryptedFileName.setText(getDecryptedFileName());
        }
    }

    //скрываем или показываем некоторые кнопки и текст
    private static void showElements(boolean visible){
        decryptionButton.setVisible(visible);
        encryptionButton.setVisible(visible);
        bruteForceButton.setVisible(visible);
        fileName1.setVisible(visible);
        fileName2.setVisible(visible);
        fileName3.setVisible(visible);
        encryptedFileName.setVisible(visible);
        decryptedFileName.setVisible(visible);
        bruteForcedFileName.setVisible(visible);
        analysisButton.setVisible(visible);

        boolean condition = StatisticAnalyzer.isSourceFileAnalysed();
        statisticFileName.setVisible(visible && condition);
        fileName4.setVisible(visible && condition);
        statisticButton.setVisible(visible && condition);
    }

    //запускаем приложение путем создания объекта фрейма
    public static void main(String[] args) {
        new UserInterface();
    }

}
