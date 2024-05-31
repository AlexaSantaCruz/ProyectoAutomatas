import java.awt.*;
import javax.swing.*;

public class Interface{
    JFrame frame = new JFrame("Proyecto Automatas :)");
    JButton[] buttons=new JButton[2]; /*0 get .txt 
                                        1 start
                                        2 information*/
    JTextArea[] TextAreas = new JTextArea[1]; /*0 putTxt text
                                                1 to see results*/
    public static void main(String[] args) {
        Interface interfaz=new Interface();
        interfaz.setFrame();

    }

    void setFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,800);
        frame.setVisible(true);

        JLabel titulo=new JLabel();
        titulo.setText("Automatas");
    }

    void searchTxt(){
        /* TODO */
    }
    
    void startAutomaton(){
        /* TODO */
    }

    void showCredit(){
        /* TODO */
    }
}