import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Panel2 extends JFrame {

    //Задание кнопок
    //кнопка поиска по артикулу
    private JButton find = new JButton("find");
    //кнопка добавления в базу
    private JButton addButton = new JButton("add");
    //кнопка обработки списка
    private JButton listButton = new JButton("list");
    //private Font font = new Font("SanSerif", Font.BOLD, 14);
    //текстовое поле для ввода артикула и подпись
    private JTextField input = new JTextField("",6);
    private JLabel inputName = new JLabel("Введите артикул");
    //вывод артикула и подпись
    private JTextArea outAnalog = new JTextArea(4,1);
    private JLabel analogName = new JLabel("Аналог");
    //вывод коммента к артикула и подпись
    private JTextArea outComment = new JTextArea(10,1);
    private JLabel commentName = new JLabel("Комментарий");
    //ввод имени фаила для добавления в базу
    private JTextField addFile = new JTextField("",6);
    private JLabel addFileName = new JLabel("Добавить фаил в базу");
    //вывод результата обработки фаила
    private JTextField addResult = new JTextField("",6);
    //ввод имени фаила для обработки списка
    private JTextField listFile = new JTextField("",6);
    private JLabel listFileName = new JLabel("Обработка списка");
    //вывод результата обработки фаила
    private JTextField listResult = new JTextField("",6);



    public Panel2(){
        //базовые параметры окна
        super("Find analogs");
        setLayout(null);
        setFocusable(true);
        this.setSize(760,450);
        this.setLocationRelativeTo(null); //Чтобы окошко было в центре экрана
        this.setResizable(true); //возможность изменять размер окна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //что делать при нажатии на крестик
        this.setVisible(true);

        Container container = this.getContentPane();
        //вывод 3 блоков ввода артикула
        inputName.setBounds(20,20,120,40);
        container.add(inputName);

        input.setBounds(20,60,200,20);
        container.add(input);

        find.setBounds(20,85,80,20);
        container.add(find);

        //вывод заголовка и текстового поля вывода аналога(многострочное поле)
        analogName.setBounds(230,20,120,40);
        container.add(analogName);

        outAnalog.setBounds(230,60,200,70);
        outAnalog.setLineWrap(true);
        outAnalog.setWrapStyleWord(true);
        container.add(outAnalog);

        //вывод заголовка и текстового поля вывода комментария(многострочное поле)
        commentName.setBounds(440,20,120,40);
        container.add(commentName);

        outComment.setBounds(440,60,300,100);
        outComment.setLineWrap(true);
        outComment.setWrapStyleWord(true);
        container.add(outComment);

        //ввод имени фаила и обработка результата (заголовок, поле ввода, поле вывода результата, кнопка ввода)
        addFileName.setBounds(20,200,200,20);
        container.add(addFileName);

        addFile.setBounds(20,240,200,20);
        container.add(addFile);

        addResult.setBounds(230,240,200,20);
        container.add(addResult);

        addButton.setBounds(20,265,80,20);
        container.add(addButton);

        //ввод имени фаила и обработка результата (заголовок, поле ввода, поле вывода результата, кнопка ввода)
        listFileName.setBounds(20,285,200,20);
        container.add(listFileName);

        listFile.setBounds(20,305,200,20);
        container.add(listFile);

        listResult.setBounds(230,305,200,20);
        container.add(listResult);

        listButton.setBounds(20,330,80,20);
        container.add(listButton);



        //создание класса для использования методов обработки фаилов
        FileWork f = null;
        try {
            f = new FileWork();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //конструктор по умолчанию подтягивает фаил dataBase и заполняет список MAP
        FileWork finalF = f;

        //actionlistener для кнопки поиска аналога
        ActionListener l = (ActionEvent e) -> {
            String a1;
            String a2;
            String text = input.getText();
            //подтягиваение информации из списка MAP по введенному ключу
            a1 = finalF.getAnalog(text.toUpperCase())[0];
            a2 = finalF.getAnalog(text.toUpperCase())[1];
            outAnalog.setText(a1);
            outComment.setText(a2);
            //временный функционал по конвертации фаила, перенос в другую кнопку
            /*try {
                finalF.FileWorkConvert(finalF.analogs);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }*/
        };
            find.addActionListener(l);

        //actionlistener для кнопки добавления фаила
        FileWork finalF1 = f;
        ActionListener k = (ActionEvent e) -> {
            try {
                finalF1.LoadingNewFile(addFile.getText());
                addResult.setText("Добавлено " + finalF1.getCount() + " артикулов");
                FileWorkWriteNew f2 = new FileWorkWriteNew(finalF1.analogs);
            } catch (IOException ioException) {
                addResult.setText("Что-то не так");
            }
        };
            addButton.addActionListener(k);

        //actionlistener для кнопки обработки списка
        FileWork finalF2 = f;
        ActionListener d = (ActionEvent e) -> {
            try {
                String text = listFile.getText();
                finalF2.FileWorkConvert(finalF2.analogs,text);
                listResult.setText("Фаил обработан");
            } catch (IOException ioException) {
                listResult.setText("Что-то не так");
            }
        };
        listButton.addActionListener(d);


    }


}
