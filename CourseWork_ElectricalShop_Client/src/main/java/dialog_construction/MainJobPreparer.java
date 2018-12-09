package dialog_construction;

import connection.DataPreparer;
import dialogs.CDialog;
import model.UserModel;
import validation.Validator;


import javax.swing.*;
import java.awt.event.*;

public class MainJobPreparer {

    private CDialog frameDialog = null;
    private CDialog connectDialog = null;
    private CDialog authorizationjDialog = null;
    private UserModel currentUser = null;

    public void getStarted(){
        connectDialog = new CDialog("Как кеннектнуться к Серваку?", 400, 300);
        prepareConnectionDialog();
        setEventsForConnectionDialog();
        connectDialog.setVisible(true);
    }


    private void prepareConnectionDialog(){
        connectDialog.addLabel(300, 30,50, 20, "Адрес:");
        connectDialog.addTextField(300, 30,50, 50);
        connectDialog.addLabel(300, 30,50, 100, "Порт:");
        connectDialog.addTextField(300, 30,50, 130);
        connectDialog.addButton(300, 30, 50, 180, "Войти");

        connectDialog.textFields.get(0).setText("127.0.0.1");
        connectDialog.textFields.get(1).setText("2525");
    }

    public void setEventsForConnectionDialog() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String host = connectDialog.textFields.get(0).getText();
                String port = connectDialog.textFields.get(1).getText();
                if( !Validator.isHost(host) || !Validator.isPort(port)) {
                    JOptionPane.showMessageDialog(connectDialog, "Некорректные данные", "Упс!", JOptionPane.PLAIN_MESSAGE);
                } else {
                    String answer = DataPreparer.startConnection(host, Integer.valueOf(port));
                    if (!answer.equals("")) {
                        JOptionPane.showMessageDialog(connectDialog, answer, "Упс!", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        connectDialog.dispose();
                        createAuthorizationDialog();
                    }
                }

            }
        };
        connectDialog.buttons.get(0).addActionListener(actionListener);
    }

    private void createAuthorizationDialog(){
        authorizationjDialog = new CDialog("Авторизация", 400, 300);
        prepareAuthorizationDialog();
        setEventsForAuthorizationDialog();
        setEventsForCloseAuthorizationDialog();
        authorizationjDialog.setVisible(true);
    }

    private void prepareAuthorizationDialog(){
        authorizationjDialog.addLabel(300, 30,50, 20, "Логин:");
        authorizationjDialog.addTextField(300, 30,50, 50);
        authorizationjDialog.addLabel(300, 30,50, 100, "Пароль:");
        authorizationjDialog.addTextField(300, 30,50, 130);
        authorizationjDialog.addButton(300, 30, 50, 180, "Войти");
    }

    public void setEventsForAuthorizationDialog() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectLogAndPass()) {
                    if((currentUser = getAuthorisationDataFromServer()) != null){
                        createFrameDialog();
                        authorizationjDialog.textFields.get(0).setText("");
                        authorizationjDialog.textFields.get(1).setText("");
                        authorizationjDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(authorizationjDialog, "Неверный логин и\\или пароль", "Упс!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        };
        authorizationjDialog.buttons.get(0).addActionListener(actionListener);
    }
    private void createFrameDialog() {
        DirectorByCreate directorByCreate = new DirectorByCreate(authorizationjDialog);
        if(currentUser.getRole() == 0) {
            directorByCreate.setDialogCreator(new MainAdminFrameDialogCreator());
        } else if(currentUser.getRole() == 1){
            directorByCreate.setDialogCreator(new AdminFrameDialogCreator());
        } else {
            directorByCreate.setDialogCreator(new UserFrameDialogCreator());
        }
        directorByCreate.createDialog();
        frameDialog = directorByCreate.getDialog();
    }

    public void setEventsForCloseAuthorizationDialog() {
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
                    JOptionPane.showMessageDialog(authorizationjDialog, answer, "Упс!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        authorizationjDialog.addWindowListener(windowListener);
    }

    private boolean isCorrectLogAndPass(){
        boolean isOk = true;
        String login, password, errors = "";
        login = authorizationjDialog.textFields.get(0).getText().trim();
        password = authorizationjDialog.textFields.get(1).getText().trim();
        if("".equals(login) || login.length() < 3){
            errors += "Логин пуст, либо слишком мал!\n";
            isOk = false;
        }
        if("".equals(login) || password.length() < 3){
            errors += "Пароль пуст, либо слишком мал!\n";
            isOk = false;
        }
        if(!Validator.isHaveOnlyGoodChars(login) ||  !Validator.isHaveOnlyGoodChars(password)){
            errors += "Содержатся запрещённые символы\n";
            isOk = false;
        }
        if (!isOk) JOptionPane.showMessageDialog(authorizationjDialog, errors, "Ошибка ввода данных!", JOptionPane.PLAIN_MESSAGE);
        return isOk;
    }

    private UserModel getAuthorisationDataFromServer(){
        UserModel user = new UserModel (
            0,
            authorizationjDialog.textFields.get(0).getText().trim(),
            authorizationjDialog.textFields.get(1).getText().trim(),
            2
        );

        return DataPreparer.checkAuthorization(user);
    }

}
