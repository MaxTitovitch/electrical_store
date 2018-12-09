package listeners;

import connection.DataPreparer;
import dialogs.CDialog;
import dialog_construction.OneTableDialog;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class BDelListener extends BAbstractEditListener {
    public BDelListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models, OneTableDialog parental){
        super(frameDialog, currentClass, models, parental);
    }

    public void actionPerformed(ActionEvent e) {
        String error;
        if(!isIdCorrect()) return;
        int id = Integer.valueOf(frameDialog.textFields.get(0).getText());

        if(!"".equals(error = getQueryDelete(id))) {
            JOptionPane.showMessageDialog(frameDialog, "Ошибка удаления!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        }
        parental.updateData();
    }

}
