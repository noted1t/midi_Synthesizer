package org.example;

import javax.swing.*;
import java.awt.event.*;

public class SyntheizerGUI extends JDialog {
    private JPanel contentPane;
    private JButton playButton;
    private JButton exitButton;
    private JTextField notesCDETextField;
    private JRadioButton notesRadioButton;
    private JRadioButton fileNameRadioButton;
    private JTextField textField1;
    private JButton buttonOK;
    private JButton buttonCancel;

    public SyntheizerGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Расписать кнопки и потырить код из мейн-класса(синтезатора без гуи)
        // Добавить возможность ввода(или выбора) времени пауз, времени воспроизведения --> проверка формата файла
        // --> example(A id время воспроизведения,A id время воспроизведения,...)
        // Кнопка циклирования воспроизведения(??)
        // Добавить выбор инструмента. Могу предположить, что ноты надо будет умножить или увеличить на n для смены
        // (какой-то инструмент)
        // C = 60; do
        //D = 62;  re
        //E = 64;  mi
        //F = 65;  fa
        //G = 67;  sol
        //A = 69;  la
        //H = 70;  si-bemol(??)
        //UPD: пытаемся в ООП


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }




    public static void main(String[] args) {
        SyntheizerGUI dialog = new SyntheizerGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
