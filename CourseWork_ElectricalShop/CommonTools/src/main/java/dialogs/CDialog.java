package dialogs;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CDialog extends JFrame {
    private JPanel panel;
    public ArrayList<JButton> buttons = new ArrayList<>();
    public ArrayList<JTextField> textFields = new ArrayList<>();
    public ArrayList<JLabel> labels = new ArrayList<>();
    public ArrayList<JRadioButton[]> radioBoxGroups = new ArrayList<>();
    public ArrayList<JTable> tables = new ArrayList<>();

    public CDialog(String title, int width, int height){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        panel = new JPanel();
        panel.setLayout(null);
        setLocationRelativeTo(null);
    }


    public void addButton(int w, int h, int x, int y, String title){
        JButton button = DialogComponentСreator.addButton(panel, w, h, x, y, title);
        setContentPane(panel);
        buttons.add(button);
    }


    public void addTextField(int w, int h, int x, int y){
        JTextField textField = DialogComponentСreator.addTextField(panel, w, h, x, y);
        setContentPane(panel);
        textFields.add(textField);
    }

    public void addLabel(int w, int h, int x, int y, String title){
        JLabel label = DialogComponentСreator.addLabel(panel, w, h, x, y, title);
        setContentPane(panel);
        labels.add(label);
    }

    public void addRadioButtonGroupe(int w, int h, int x, int y, String[] titles){
        JRadioButton[] radioButtons = DialogComponentСreator.addRadioButtonGroupe(panel, w, h, x, y, titles);
        setContentPane(panel);
        radioBoxGroups.add(radioButtons);
    }

    public void addTable(int w, int h, int x, int y, AbstractTableModel model){
        JTable table = DialogComponentСreator.addTable(panel, w, h, x, y, model);          //new UserTableModel(models)
        setContentPane(panel);
        tables.add(table);
    }

    public void close(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}




