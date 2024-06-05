import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Interface {
    JFrame frame = new JFrame("Proyecto Autómatas :)");
    JButton[] buttons = new JButton[3]; /*
                                         * 0 get .txt
                                         * 1 start
                                         * 2 information
                                         */
    JTextArea[] TextAreas = new JTextArea[2]; /*
                                               * 0 putTxt text
                                               * 1 to see results
                                               */
    HashMap<String, Integer> resultMap = new HashMap<>(14, 1);
    ArrayList<String> whitespaceChars = new ArrayList<>();
    ArrayList<String> palabrasReservadas = new ArrayList<>();

    public Interface() {
        resultMap.put("Palabras reservadas", 0);
        resultMap.put("Identificadores", 0);
        resultMap.put("Operadores Relacionales", 0);
        resultMap.put("Operadores Lógicos", 0);
        resultMap.put("Operadores Aritméticos", 0);
        resultMap.put("Asignaciones", 0);
        resultMap.put("Números Enteros", 0);
        resultMap.put("Números Decimales", 0);
        resultMap.put("Cadena de caracteres", 0);
        resultMap.put("Comentarios Multilínea", 0);
        resultMap.put("Comentarios de Linea", 0);
        resultMap.put("Paréntesis", 0);
        resultMap.put("Llaves", 0);
        resultMap.put("Errores", 0);

        whitespaceChars.add("\n");
        whitespaceChars.add("\t");
        whitespaceChars.add(" ");
        whitespaceChars.add("\0");
        whitespaceChars.add("");

        palabrasReservadas.add("if");
        palabrasReservadas.add("else");
        palabrasReservadas.add("switch");
        palabrasReservadas.add("case");
        palabrasReservadas.add("default");
        palabrasReservadas.add("for");
        palabrasReservadas.add("while");
        palabrasReservadas.add("break");
        palabrasReservadas.add("int");
        palabrasReservadas.add("String");
        palabrasReservadas.add("double");
        palabrasReservadas.add("char");
        palabrasReservadas.add("print");
    }

    public static void main(String[] args) {
        Interface interfaz = new Interface();
        interfaz.setFrame();
    }

    void setFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Crear el título y añadirlo al frame
        JLabel titulo = new JLabel("Lexical Analyzer", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.PLAIN, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(titulo, gbc);

        // Inicializar las JTextArea
        TextAreas[0] = new JTextArea();
        TextAreas[1] = new JTextArea();

        // Añadir ScrollPanes a las JTextAreas
        JScrollPane scrollBarText = new JScrollPane(TextAreas[0]);
        JScrollPane scrollBarRes = new JScrollPane(TextAreas[1]);

        // Añadir el JTextArea[0] al frame
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(scrollBarText, gbc);

        // Añadir el JTextArea[1] al frame
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(scrollBarRes, gbc);

        // Inicializar los botones
        buttons[0] = new JButton("Get .txt");
        buttons[1] = new JButton("Start");
        buttons[2] = new JButton("Information");

        // Añadir botones al frame
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(buttons[0]);
        buttonPanel.add(buttons[1]);
        buttonPanel.add(buttons[2]);

        // Añadir el panel de botones al frame
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(buttonPanel, gbc);

        // Añadir acción al botón "Get .txt"
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTxt();
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAutomaton();
            }
        });
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCredit();
            }
        });

        frame.setVisible(true);
    }

    void searchTxt() {
        if (!TextAreas[0].getText().isEmpty() && JOptionPane.showConfirmDialog(
                frame,
                "The text area is not empty, do you want to override the content?") != 0) {
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
                TextAreas[0].read(reader, "File");
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void startAutomaton() {
        var automata = new Automata(TextAreas[0].getText());
        automata.resetAutomata();
        automata.computeAutomata();
        TextAreas[1].setText(automata.formatOutput());
    }

    ArrayList<String> filterWords() {
        ArrayList<String> charArray = new ArrayList<>();
        var content = TextAreas[0].getText();
        var charText = content.split("");
        var word = "";

        for (String chars : charText) {
            if (!whitespaceChars.contains(chars)) {
                word += chars;
            } else if (word.length() > 0) {
                charArray.add(word);
                word = "";
            }
        }

        return charArray;
    }

    int processWord(String word) {
        var state = 0;

        var charArray = word.split("");

        for (String chars : charArray) {
            switch (state) {
                case 0:
                    if (chars == "&")
                        state = 1;
                    if (chars == "|")
                        state = 4;
                    if (chars == "!")
                        state = 3;
                    if (chars == "=")
                        state = 5;
                    if (chars == "/")
                        state = 10;
                    if (chars == "\"")
                        state = 15;
                    if (chars == "-")
                        state = 18;
                    if (!Double.isNaN(isNumber(chars)))
                        state = 19;
                    if (isLetter(chars))
                        state = 21;
                    if (chars == "<" || chars == ">")
                        state = 6;
                    if (chars == "(" || chars == ")")
                        state = 8;
                    if (chars == "{" || chars == "}")
                        state = 9;
                    if (chars == "+" || chars == "*" || chars == "%")
                        state = 17;
                    break;
                case 1:
                    if (chars == "&")
                        state = 23;
                    else
                        state = 2;
                    break;
                case 2:
                    break;
                case 3:
                    if (chars == "=")
                        state = 7;
                    else
                        state = 2;
                    break;
                case 4:
                    if (chars == "|")
                        state = 23;
                    else
                        state = 2;
                    break;
                case 5:
                    if (chars == "=")
                        state = 7;
                    else
                        state = 2;
                    break;
                case 6:
                    if (chars == "=")
                        state = 7;
                    else
                        state = 2;
                    break;
                case 7:
                    state = 2;
                    break;
                case 8:
                    state = 2;
                    break;
                case 9:
                    state = 2;
                    break;
                case 10:
                    if (chars == "/")
                        state = 11;
                    else if (chars == "*")
                        state = 12;
                    else
                        state = 2;
                    break;
                case 11:
                    break;
                case 12:
                    if (chars == "*")
                        state = 13;
                    break;
                case 13:
                    if (chars == "/")
                        state = 14;
                    else
                        state = 12;
                    break;
                case 14:
                    state = 2;
                    break;
                case 15:
                    if (chars == "\"")
                        state = 16;
                    break;
                case 16:
                    state = 2;
                    break;
                case 17:
                    state = 2;
                    break;
                case 18:
                    if (!Double.isNaN(isNumber(chars)))
                        state = 19;
                    break;
                case 19:
                    if (chars == ".")
                        state = 20;
                    break;
                case 20:
                    if (chars == ".")
                        state = 2;
                    break;
                case 21:
                    if (!isLetter(chars) || chars != "_")
                        state = 2;
                    break;
                case 23:
                    state = 2;
                    break;
                default:
                    break;
            }
        }

        // Check if the word is reserved
        if (palabrasReservadas.contains(word))
            state = 22;

        return state;
    }

    Double isNumber(String chars) {
        try {
            return Double.parseDouble(chars);
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    boolean isLetter(String chars) {
        int charCode = (int) chars.charAt(0);

        return ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122));
    }

    void processStates(ArrayList<Integer> states) {
        for (Integer state : states) {
            switch (state) {
                case 2:
                    resultMap.put("Errores", resultMap.get("Errores") + 1);
                    break;
                case 3:
                    resultMap.put("Operadores Lógicos", resultMap.get("Operadores Lógicos") + 1);
                    break;
                case 5:
                    resultMap.put("Asignaciones", resultMap.get("Asignaciones") + 1);
                    break;
                case 6:
                    resultMap.put("Operadores Relacionales", resultMap.get("Operadores Relacionales") + 1);
                    break;
                case 7:
                    resultMap.put("Operadores Relacionales", resultMap.get("Operadores Relacionales") + 1);
                    break;
                case 8:
                    resultMap.put("Paréntesis", resultMap.get("Paréntesis") + 1);
                    break;
                case 9:
                    resultMap.put("Llaves", resultMap.get("Llaves") + 1);
                    break;
                case 10:
                    resultMap.put("Operadores Aritméticos", resultMap.get("Operadores Aritméticos") + 1);
                    break;
                case 11:
                    resultMap.put("Comentarios de Linea", resultMap.get("Comentarios de Linea") + 1);
                    break;
                case 14:
                    resultMap.put("Multilínea", resultMap.get("Multilínea") + 1);
                    break;
                case 16:
                    resultMap.put("Cadena de caracteres", resultMap.get("Cadena de caracteres") + 1);
                    break;
                case 17:
                    resultMap.put("Operadores Aritméticos", resultMap.get("Operadores Aritméticos") + 1);
                    break;
                case 18:
                    resultMap.put("Operadores Aritméticos", resultMap.get("Operadores Aritméticos") + 1);
                    break;
                case 19:
                    resultMap.put("Números Enteros", resultMap.get("Números Enteros") + 1);
                    break;
                case 20:
                    resultMap.put("Números Decimales", resultMap.get("Números Decimales") + 1);
                    break;
                case 21:
                    resultMap.put("Identificadores", resultMap.get("Identificadores") + 1);
                    break;
                case 22:
                    resultMap.put("Palabras reservadas", resultMap.get("Palabras reservadas") + 1);
                    break;
                case 23:
                    resultMap.put("Operadores Lógicos", resultMap.get("Operadores Lógicos") + 1);
                    break;
                default:
                    break;
            }
        }
    }

    void setResultMap() {
        TextAreas[1].setText(new Automata("").formatOutput());
    }

    void showCredit() {
        JDialog dialog = new JDialog(frame, "Information", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 150);
        JLabel label = new JLabel("Equipo: ");
        JLabel label2 = new JLabel("Alexa Rebeca Santa Cruz Hurtado - 21310419");
        JLabel label3 = new JLabel("Moises David Lozano Bobadilla - 18310167");
        dialog.add(label);
        dialog.add(label2);
        dialog.add(label3);
        dialog.setVisible(true);
    }
}
