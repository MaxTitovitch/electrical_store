package dialog_construction;

import connection.DataPreparer;
import model.UserModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAdminFrameDialogCreator extends AdminFrameDialogCreator {
    @Override
    public void prepareFrameDialog() {
        super.prepareFrameDialog();
        frameDialog.buttons.get(7).setLocation(150, 430);
        frameDialog.addButton(500, 30,150, 380, "Пользователи");
        frameDialog.addButton(500, 30,150, 480, "Закрыть СЕРВАК и Выйти");
        frameDialog.setSize(800, 570);
    }

    @Override
    public void setEventsForButtons() {
        super.setEventsForButtons();
        setEventUser();
        setEventExitWitchServer();
    }

    protected void setEventUser(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, UserModel.class, "\"Пользователи\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(8).addActionListener(actionListener);
    }

    protected void setEventExitWitchServer(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String exitingServerMessage = DataPreparer.exit("witch");
                if(!"".equals(exitingServerMessage))
                    JOptionPane.showMessageDialog(frameDialog, exitingServerMessage, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
                frameDialog.dispose();
            }
        };
        frameDialog.buttons.get(9).addActionListener(actionListener);
    }
}
