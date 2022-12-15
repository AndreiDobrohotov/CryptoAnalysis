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

    //������� ������
    private static final ImagePanel PANEL = new ImagePanel();

    //��� ��������� �������
    private static final JButton openFileButton = new JButton();
    private static final JButton encryptionButton = new JButton();
    private static final JButton decryptionButton = new JButton();
    private static final JButton bruteForceButton = new JButton();
    private static final JButton analysisButton = new JButton();
    private static final JButton statisticButton = new JButton();
    private static final JButton plusKeyButton = new JButton();
    private static final JButton minusKeyButton = new JButton();

    //��� ��������� �������
    private static final JLabel openFileStatus = new JLabel("���� �� ������.");
    private static final JLabel fileName1 = new JLabel();
    private static final JLabel encryptedFileName = new JLabel();
    private static final JLabel fileName2 = new JLabel();
    private static final JLabel decryptedFileName = new JLabel();
    private static final JLabel fileName3 = new JLabel();
    private static final JLabel bruteForcedFileName = new JLabel();
    private static final JLabel fileName4 = new JLabel();
    private static final JLabel statisticFileName = new JLabel();
    private static final JLabel key = new JLabel("13");

    //������ ������� �� ������ � �������
    private static int getKey(){return Integer.parseInt(key.getText());}
    private static String getFileName(){return file==null?"":file.getName();}
    private static String getFileNameNoTxt(){return file==null?"":getFileName().substring(0,getFileName().lastIndexOf('.'));}
    private static String getEncryptedFileName(){return file==null?"":getFileNameNoTxt()+"E"+getKey();}
    private static String getDecryptedFileName(){return file==null?"":getFileNameNoTxt()+"D"+getKey();}
    private static String getFileAbsolutePath(){return file==null?"":file.getAbsolutePath();}
    private static String getFilePath() {return file==null?"":file.getParentFile().getAbsolutePath();}

    //������� �����������
    public UserInterface() {
        super("���������� / ����������� / ������������");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width/2 - x/2, dimension.height/2 - y/2,x,y);
        fileChooserRussifier();

        //��������� ������ � ������������ ��������� ���������� ���������
        add(PANEL);
        PANEL.setLayout(null);

        //�������������� � ��������� ��� �������� �� ������
        labelsInitialization();
        buttonsInitialization();

        //�������� ��� ��������, ���� �� ����� ������ ����
        showElements(false);

    }

    //�������������� ��� ������ (�������� ������ ��������� �����)
    private static void buttonsInitialization(){
        //��������� ������ "������� ����" �� ������
        PANEL.add(openFileButton);
        openFileButton.setBounds(265,155,150,25);
        openFileButton.setIcon(new ImageIcon("images/selectButton.png"));
        //��������� �������
        openFileButton.addActionListener(e -> {
            //������� � ��������� ���� ��� ������ �����
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(getFileAbsolutePath()));
            int ret = fileChooser.showDialog(null, "������� ����");
            if (ret == JFileChooser.APPROVE_OPTION) {
                //��������� ��� ��������� ���� � ������� *.txt
                fileIsCorrect(fileChooser.getSelectedFile());
            }
        });


        //��������� ������ "�����������" �� ������
        PANEL.add(encryptionButton);
        encryptionButton.setBounds(250,310,110,25);
        encryptionButton.setIcon(new ImageIcon("images/encryptButton.png"));
        //��������� �������
        encryptionButton.addActionListener(e -> {
            showElements(false);
            //��������� ���������� �� ����
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getEncryptedFileName()+".txt";
                //������� ������ ��������� �� ��������� �������� �����
                OperationType encryptor = new Encryptor(getKey());
                //��������� ����� ���������������� ������ ��� ����������
                MainFileOperation.fileOperation(encryptor,pathForRead,pathForWrite);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("���� ������� ����������.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("�������� ��������. ���� �� ����������.");
            }
            file = null;
        });

        //��������� ������ "������������" �� ������
        PANEL.add(decryptionButton);
        decryptionButton.setBounds(250,460,110,25);
        decryptionButton.setIcon(new ImageIcon("images/decryptButton.png"));
        //��������� �������
        decryptionButton.addActionListener(e -> {
            showElements(false);
            //��������� ���������� �� ����
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getDecryptedFileName()+".txt";
                //������� ������ ����������� �� ��������� �������� �����
                OperationType decryptor = new Decryptor(getKey());
                //��������� ����� ���������������� ������ ��� ������������
                MainFileOperation.fileOperation(decryptor,pathForRead,pathForWrite);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("���� ������� �����������.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("�������� ��������. ���� �� ����������.");
            }
            file = null;
        });

        //��������� ������ "Brute Force" �� ������
        PANEL.add(bruteForceButton);
        bruteForceButton.setBounds(250,612,110,25);
        bruteForceButton.setIcon(new ImageIcon("images/bruteForceButton.png"));
        //��������� �������
        bruteForceButton.addActionListener(e -> {
            showElements(false);
            //��������� ���������� �� ����
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getFileNameNoTxt()+"BF.txt";
                //������� ������ BruteForce
                BruteForce bruteForce = new BruteForce();
                //��������� ����� ���������������� ������ ��� ������ �����
                MainFileOperation.fileOperation(bruteForce,pathForRead,pathForWrite);
                //����� ���� ��� ����� ���������, �������� �������� ����
                int key = bruteForce.getKey();
                if(key!=0){
                    openFileStatus.setForeground(greenColor);
                    openFileStatus.setText("�������� ���� ["+key+"]. ���� �����������.");
                }
                //���� ���� �� ������, �������� � �������
                else{
                    openFileStatus.setForeground(redColor);
                    openFileStatus.setText("�� ������� ��������� ����.");
                }

            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("�������� ��������. ���� �� ����������.");
            }
            file = null;
        });

        //��������� ������ ������� ����� �� ������
        PANEL.add(analysisButton);
        analysisButton.setBounds(108,787,110,25);
        analysisButton.setIcon(new ImageIcon("images/analysisButton.png"));
        //��������� �������
        analysisButton.addActionListener(e -> {
            showElements(false);
            //��������� ���������� �� ����
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                //������ ������ ������� �����
                StatisticAnalyzer.makeSourceFileAnalysis(pathForRead);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("���� ������� ���������������.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("�������� ��������. ���� �� ����������.");
            }
            file = null;
        });

        //��������� ������ ������������� ���������� �� ������
        PANEL.add(statisticButton);
        statisticButton.setBounds(302,756,110,25);
        statisticButton.setIcon(new ImageIcon("images/statisticButton.png"));
        //��������� �������
        statisticButton.addActionListener(e -> {
            showElements(false);
            //��������� ���������� �� ����
            if(Files.exists(Path.of(getFileAbsolutePath()))){
                String pathForRead = getFileAbsolutePath();
                String pathForWrite = getFilePath()+"/"+getFileNameNoTxt()+"SA.txt";
                //������ ������ ������� �����
                StatisticAnalyzer.makeTargetFileAnalysis(pathForRead);
                //������� ���� �� ������ ���� �������� �� ������� ������
                StatisticAnalyzer.createNewEncryptionMap();
                StatisticAnalyzer analyzer = new StatisticAnalyzer();
                //��������� ����� ���������������� ������ ��� ������ �������� �� ������ ����
                MainFileOperation.fileOperation(analyzer,pathForRead,pathForWrite);
                openFileStatus.setForeground(greenColor);
                openFileStatus.setText("���� ������� ���������.");
            }
            else{
                openFileStatus.setForeground(redColor);
                openFileStatus.setText("�������� ��������. ���� �� ����������.");
            }
            file = null;
        });


        //��������� ������ "+", ��� ���������� �����, �� ������
        PANEL.add(plusKeyButton);
        plusKeyButton.setBounds(450,349,25,25);
        plusKeyButton.setIcon(new ImageIcon("images/plusButton.png"));
        //��������� �������
        plusKeyButton.addActionListener(e -> {
            //������������� ���������� �������� �����
            if(getKey()<70) {
                //����������� �������� ����� �� 1
                key.setText(String.valueOf(getKey()+1));
                refreshFileNames();
            }
        });

        //��������� ������ "-", ��� ���������� �����, �� ������
        PANEL.add(minusKeyButton);
        minusKeyButton.setBounds(450,376,25,25);
        minusKeyButton.setIcon(new ImageIcon("images/minusButton.png"));
        //��������� �������
        minusKeyButton.addActionListener(e -> {
            //������������ ���������� �������� �����
            if(getKey()>1) {
                //��������� �������� ����� �� 1
                key.setText(String.valueOf(getKey()-1));
                refreshFileNames();
            }
        });

    }

    //�������������� ��� ��������� ����
    private static void labelsInitialization(){
        //������ ���������� �����
        PANEL.add(openFileStatus);
        openFileStatus.setBounds(200,120,285,25);
        openFileStatus.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ��������� ����� ����������
        PANEL.add(fileName1);
        fileName1.setBounds(110,320,130,25);
        fileName1.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ��������� ����� ������������
        PANEL.add(fileName2);
        fileName2.setBounds(370,470,130,25);
        fileName2.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ����� ���������� ����������
        PANEL.add(encryptedFileName);
        encryptedFileName.setBounds(370,320,130,25);
        encryptedFileName.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ����� ���������� ������������
        PANEL.add(decryptedFileName);
        decryptedFileName.setBounds(110,470,130,25);
        decryptedFileName.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� �����
        PANEL.add(key);
        key.setBounds(396,359,43,31);
        key.setFont(font);
        key.setForeground(purpleColor);
        key.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ��������� ����� Brute Force
        PANEL.add(fileName3);
        fileName3.setBounds(110,621,130,25);
        fileName3.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ����� ���������� Brute Force
        PANEL.add(bruteForcedFileName);
        bruteForcedFileName.setBounds(370,621,130,25);
        bruteForcedFileName.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ��������� ����� ��������������� �������
        PANEL.add(fileName4);
        fileName4.setBounds(215,780,100,25);
        fileName4.setHorizontalAlignment(SwingConstants.CENTER);

        //�������� ����� �������� ��������������� �������
        PANEL.add(statisticFileName);
        statisticFileName.setBounds(400,780,100,25);
        statisticFileName.setHorizontalAlignment(SwingConstants.CENTER);
    }

    //��������� ������������ ��� ���������� ������ �����
    private static void fileChooserRussifier(){
        UIManager.put("FileChooser.acceptAllFileFilterText", "��� �����");
        UIManager.put("FileChooser.cancelButtonToolTipText", "������� ������");
        UIManager.put("FileChooser.directoryDescriptionText", "�����");
        UIManager.put("FileChooser.directoryOpenButtonText", "�������");
        UIManager.put("FileChooser.directoryOpenButtonToolTipText", "������� ��������� ����������");
        UIManager.put("FileChooser.fileDescriptionText", "��� �����");
        UIManager.put("FileChooser.helpButtonText", "������");
        UIManager.put("FileChooser.helpButtonToolTipText", "�������� �������");
        UIManager.put("FileChooser.newFolderErrorText", "������ ��� �������� ��������");
        UIManager.put("FileChooser.openButtonText", "�������");
        UIManager.put("FileChooser.openButtonToolTipText", "������� ��������� ����");
        UIManager.put("FileChooser.openDialogTitleText", "�������");
        UIManager.put("FileChooser.saveButtonText", "���������");
        UIManager.put("FileChooser.saveButtonToolTipText", "��������� ��������� ����");
        UIManager.put("FileChooser.saveDialogTitleText", "���������");
        UIManager.put("FileChooser.updateButtonText", "��������");
        UIManager.put("FileChooser.updateButtonToolTipText", "�������� ������ ����������");
        UIManager.put("FileChooser.detailsViewActionLabelText", "�������");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "�������");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "�������");
        UIManager.put("FileChooser.fileAttrHeaderText", "��������");
        UIManager.put("FileChooser.fileDateHeaderText", "�������");
        UIManager.put("FileChooser.fileNameHeaderText", "���");
        UIManager.put("FileChooser.fileNameLabelText", "��� �����:");
        UIManager.put("FileChooser.fileSizeHeaderText", "������");
        UIManager.put("FileChooser.fileTypeHeaderText", "���");
        UIManager.put("FileChooser.filesOfTypeLabelText", "��� ������:");
        UIManager.put("FileChooser.homeFolderAccessibleName", "�����");
        UIManager.put("FileChooser.homeFolderToolTipText", "�����");
        UIManager.put("FileChooser.listViewActionLabelText", "������");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "������");
        UIManager.put("FileChooser.listViewButtonToolTipText", "������");
        UIManager.put("FileChooser.lookInLabelText", "��������:");
        UIManager.put("FileChooser.newFolderAccessibleName", "����� �����");
        UIManager.put("FileChooser.newFolderActionLabelText", "����� �����");
        UIManager.put("FileChooser.newFolderToolTipText", "������� ����� �����");
        UIManager.put("FileChooser.refreshActionLabelText", "��������");
        UIManager.put("FileChooser.saveInLabelText", "��������� �:");
        UIManager.put("FileChooser.upFolderAccessibleName", "�����");
        UIManager.put("FileChooser.upFolderToolTipText", "����� �� ���� �������");
        UIManager.put("FileChooser.viewMenuLabelText", "��������");
        UIManager.put("FileChooser.cancelButtonText", "������");
        UIManager.put("FileChooser.folderNameLabelText", "���� ����������");
    }

    //�������� ������ ��� ������� ������
    public static class ImagePanel extends JPanel{
        private static final ImageIcon backGround = new ImageIcon("images/background.png");

        //������ ��� �� ������
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backGround.getImage(), 0, 0, null);
        }
    }

    //��������� ��� ��������� ���� *.txt �������
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
            openFileStatus.setText("�������� ������ �����.");
            openFileStatus.setForeground(redColor);
            showElements(false);
        }
    }

    //��������� �������� ������, ����� ��� �������������� �������� �����
    private static void refreshFileNames(){
        if(file!=null){
            encryptedFileName.setText(getEncryptedFileName());
            decryptedFileName.setText(getDecryptedFileName());
        }
    }

    //�������� ��� ���������� ��������� ������ � �����
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

    //��������� ���������� ����� �������� ������� ������
    public static void main(String[] args) {
        new UserInterface();
    }

}
