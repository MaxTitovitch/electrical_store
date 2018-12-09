package dialog_construction;

import connection.DataPreparer;
import dialogs.CDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

abstract class AbstractFrameDialogCreator {
    protected CDialog frameDialog;
    private CDialog authorizationjDialog;


    public CDialog getFrameDialog() {
        frameDialog.setVisible(true);
        return frameDialog;
    }

    public void createDialog(CDialog authorizationjDialog){
        frameDialog = new CDialog("Магазин электротоваров. Курсовой проект.", 800, 610);
        setEventsForCloseDialog();
        prepareFrameDialog();
        setEventsForButtons();
        this.authorizationjDialog = authorizationjDialog;
    }

    public void setEventsForCloseDialog() {
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {}

            public void windowClosing(WindowEvent event) {
                String answer = DataPreparer.exit("witchout");
                if(!answer.equals("")) {
                    JOptionPane.showMessageDialog(frameDialog, answer, "Упс!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        frameDialog.addWindowListener(windowListener);
    }

    abstract void prepareFrameDialog();
    abstract void setEventsForButtons();

    protected ActionListener setEventExit(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameDialog.dispose();
                authorizationjDialog.setVisible(true);
            }
        };
        return actionListener;
    }

}
