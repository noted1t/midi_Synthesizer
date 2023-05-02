package org.example;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class SynthesizerLogic {
    //Считывает введенные пользователем ноты и считывает их
    // Для этого используется javax.sound.midi
    //1) приветствие
    //2) Считываем ноты
    //2) Преобразуем их в понятные компьютеру ноты
    //3) Передаем их классу. который отвечает за обработку и вывод
    //4) проигрываем ноты
    //5) повторяем пункты 2-4
    //ДЗ на выбор: сячитывать ноты из текстовика, воспроизведение миди файлов, мой варик: ГУИ
    private static final byte C = 60; // do
    private static final byte D = 62; // re
    private static final byte E = 64; // mi
    private static final byte F = 65; // fa
    private static final byte G = 67; // sol
    private static final byte A = 69; // la
    private static final byte H = 70; // si-bemol(??)

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException, IOException {
      // List<String> strings = Files.readAllLines(new File("input.txt").toPath());



        System.out.println("Please enter notes or file name(example:input.txt) : ");
        Scanner scanner = new Scanner(System.in);
        String choose = scanner.nextLine();
        String consoleCommand;
        if (isNotes(choose)) {
            if(!choose.equals("")) {
                consoleCommand = choose;
            }
            else {
                consoleCommand = scanner.nextLine();
            }
            int noteId = midiCommandParser(consoleCommand.toUpperCase().trim());
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            Receiver receiver = synthesizer.getReceiver();


            //   programChangeOrgan(ShortMessage.PROGRAM_CHANGE, 20, -1, receiver);


            while (!consoleCommand.equals("exit")) {
                String notes[] = consoleCommand.split(" ");
                for (String note : notes) {
                    noteId = midiCommandParser(note.toUpperCase().trim());
                    playNote(noteId, receiver);
                }
                consoleCommand = scanner.nextLine();
            }


            //  Receiver receiver = synthesizer.getReceiver();

            //   playNote(noteId, receiver);

            synthesizer.close();
            scanner.close();
        }
        else {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            Receiver receiver = synthesizer.getReceiver();

            String fileName = choose;
            List<String> strings = Files.readAllLines(new File(fileName).toPath());
            try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
                lines.forEach(midiCommand->{
                    String notes[] = midiCommand.split(" ");
                    for (String note : notes) {
                        int noteId1 = midiCommandParser(note.toUpperCase().trim());
                        try {
                            playNote(noteId1, receiver);
                        } catch (InvalidMidiDataException | InterruptedException e) {
                            System.out.println("""
                                    Неккоректные данные\s
                                    Попробуйте изменить свой файл или его содержимое
                                    форматстрок должен быть таким ->нота нота нота
                                     доступные ноты:ACHDEFG. регистр не имеет значения""");

                        }
                    }

                });

//                for (String midiCommand : (Iterable<String>) lines::iterator) {
//                    String notes[] = midiCommand.split(" ");
//                    for (String note : notes) {
//                        int noteId1 = midiCommandParser(note.toUpperCase().trim());
//                        playNote(noteId1, receiver);
//                    }
//                }
            } catch (IOException e) {
                System.out.println("В вашем файле находится недопустимое содержание");
            }
            synthesizer.close();
            scanner.close();

        }
    }

   // private static void programChangeOrgan(int programChange, int data1, int data2, Receiver receiver) throws InvalidMidiDataException {
   //     ShortMessage programChange = new ShortMessage();
  //      programChange.setMessage(programChange, data1, data2);
  //      receiver.send(programChange, -1);
 //   }
    private static boolean isNotes (String choose) {
        if (choose.equalsIgnoreCase("notes")) {
            System.out.println("Введите ваши ноты: ");
            return true;
        }
        // тут бы все доработать, ибо из-за этого сыча первая входная строка не воспроизвоидтся и необходимо заново ее вводить
        // Можно захардкодить, чтобы не было лишних проверок и тд
        // example: notes || fileName.txt остальное - ошибка или уведомление
        // проверка на пустые строки тоже должна быть
        switch (choose.charAt(0)) {
            case 'a', 'c', 'h', 'd', 'e', 'f', 'g', 'A', 'C', 'H', 'D', 'E', 'F', 'G' -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private static void playNote(int noteId, Receiver receiver) throws InvalidMidiDataException, InterruptedException {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.NOTE_ON, noteId, 100);

        receiver.send(msg, -1);
        Thread.sleep(1000L);
        msg.setMessage(ShortMessage.NOTE_OFF, noteId, 100);
    }

    public static int midiCommandParser(String command) {

        switch (command) {
            case "A" -> {
                return A;
            }
            case "H" -> {
                return H;
            }
            case "C" -> {
                return C;
            }
            case "D" -> {
                return D;
            }
            case "E" -> {
                return E;
            }
            case "F" -> {
                return F;
            }
            case "G" -> {
                return G;
            }
            default -> {
                System.out.println("you entered smth wrong -> " + command);
                return C;
            }
        }

    }
}