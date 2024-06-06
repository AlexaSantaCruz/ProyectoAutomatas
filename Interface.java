import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame {

    JButton[] buttons = new JButton[3]; /*
                                         * 0 get .txt
                                         * 1 start
                                         * 2 information
                                         */
    JTextArea[] TextAreas = new JTextArea[2]; /*
                                               * 0 putTxt text
                                               * 1 to see results
                                               */

    public Interface() {
        setTitle("Proyecto Autómatas :)");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1400, 800);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Crear el título y añadirlo al frame
        JLabel titulo = new JLabel("Lexical Analyzer", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.PLAIN, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(titulo, gbc);

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
        this.add(scrollBarText, gbc);

        // Añadir el JTextArea[1] al frame
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollBarRes, gbc);

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
        this.add(buttonPanel, gbc);

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
    }

    public static void main(String[] args) {
        Interface interfaz = new Interface();
        interfaz.setVisible(true);
    }

    void searchTxt() {
        if (!TextAreas[0].getText().isEmpty() && JOptionPane.showConfirmDialog(
                this,
                "The text area is not empty, do you want to override the content?") != 0) {
            return;
        }
        JFileChooser fileChooser = new JFileChooser(".");
        int result = fileChooser.showOpenDialog(this);
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

    void showCredit() {
        JDialog dialog = new JDialog(this, "Information", true);
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
