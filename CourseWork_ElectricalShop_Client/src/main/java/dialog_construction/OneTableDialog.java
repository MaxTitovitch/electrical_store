package dialog_construction;

import connection.DataPreparer;
import dialogs.CDialog;
import listeners.*;
import model.*;
import table_models.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class OneTableDialog {
    private CDialog frameDialog;
    private CDialog currentDialog;
    private String title;
    private Class<? extends Model> currentClass;
    private ArrayList<Model> models = new ArrayList<>();

    public OneTableDialog(CDialog frameDialog, Class<? extends Model> currentClass, String title){
        this.frameDialog = frameDialog;
        this.currentClass = currentClass;
        this.title = "Таблица: " + title;
    }

    public void create(){
        int height = currentClass.getName().equals("model.WarehouseModel") ? 710 : 760;
        height = currentClass.getName().equals("model.SupplierProductModel") ||
                currentClass.getName().equals("model.UserCategoryModel") ? 560 : height;

            currentDialog = new CDialog(title, 800, height);
        addWindowListener();
        prepareDialog();
        setEventsForButtons();
        currentDialog.setVisible(true);
    }

    private void addWindowListener(){
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {
                frameDialog.setVisible(false);
            }
            public void windowClosing(WindowEvent event) {
                frameDialog.setVisible(true);
            }
        };
        currentDialog.addWindowListener(windowListener);
        currentDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void prepareDialog(){
        if((models = OneTableDialog.getData(currentClass)) == null) {
            JOptionPane.showMessageDialog(currentDialog, "Ошибка получения данных!", "Ошибка!",JOptionPane.PLAIN_MESSAGE);
            currentDialog.dispose();
            return;
        }
        currentDialog.addTable(750, 200, 25, 20, OneTableDialog.getModel(models, currentClass));

        currentDialog.addButton(300, 30,250, 240, "Добавить");
        currentDialog.addLabel(300, 30,250, 290, "Номер покупаемого товара:");
        currentDialog.addTextField(300, 30,250, 320);
        currentDialog.addButton(300, 30,250, 370, "Редактировать");
        currentDialog.addButton(300, 30,250, 420, "Удалить");
        currentDialog.addButton(300, 30,250, 470, "Отчёт");
        if(!currentClass.getName().equals("model.SupplierProductModel") && !currentClass.getName().equals("model.UserCategoryModel")) {
            currentDialog.addButton(300, 30, 250, 520, "Поиск");
            currentDialog.addButton(300, 30, 250, 570, "Сортировка");
            currentDialog.addButton(300, 30, 250, 620, "Сброс");
        }
        if(!currentClass.getName().equals("model.WarehouseModel") && !currentClass.getName().equals("model.SupplierProductModel")
                && !currentClass.getName().equals("model.UserCategoryModel"))
            currentDialog.addButton(300, 30, 250, 670, "Статистика");
    }

    private static ArrayList<Model> getData(Class<? extends Model> currentClass){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return DataPreparer.getCategorys("", "");
            case "model.ProductModel":      return DataPreparer.getProducts("", "");
            case "model.PurchaseModel":     return DataPreparer.getPurchases("", "");
            case "model.SupplierModel":     return DataPreparer.getSuppliers("", "");
            case "model.UserModel":         return DataPreparer.getUsers("", "");
            case "model.WarehouseModel":    return DataPreparer.getWarehouses("", "");
            case "model.SupplierProductModel":     return DataPreparer.getSuppliersProducts("", "");
            case "model.UserCategoryModel":         return DataPreparer.getUserCategorys("", "");
        }
    }

    private static AbstractTableModel getModel(ArrayList<Model> model, Class<? extends Model> currentClass){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return new CategoryTableModel(model, true);
            case "model.ProductModel":      return new ProductTableModel(model, true);
            case "model.PurchaseModel":     return new PurchaseTableModel(model, true);
            case "model.SupplierModel":     return new SupplierTableModel(model, true);
            case "model.UserModel":         return new UserTableModel(model, true);
            case "model.WarehouseModel":    return new WarehouseTableModel(model, true);
            case "model.SupplierProductModel":      return new SupplierProductTableModel(model, true);
            case "model.UserCategoryModel":         return new UserCategoryTableModel(model, true);
        }
    }

    protected void setEventsForButtons(){
        setEventAddRed();
        setEventDel();
        setEventSave();
        if(!currentClass.getName().equals("model.SupplierProductModel") && !currentClass.getName().equals("model.UserCategoryModel")) {
            setEventSearch();
            setEventSort();
            setEventReset();
        }
        if(!currentClass.getName().equals("model.WarehouseModel") && !currentClass.getName().equals("model.SupplierProductModel")
            && !currentClass.getName().equals("model.UserCategoryModel"))
            setEventGetInfo();
    }

    private void setEventAddRed(){
        ActionListener actionListener = new BAddRedListener(currentDialog, currentClass, models, this);
        currentDialog.buttons.get(0).addActionListener(actionListener);
        currentDialog.buttons.get(1).addActionListener(actionListener);
    }

    private void setEventDel(){
        ActionListener actionListener = new BDelListener(currentDialog, currentClass, models, this);
        currentDialog.buttons.get(2).addActionListener(actionListener);
    }


    private void setEventSave(){
        BSaveListener actionListener = new BSaveListener(models, currentClass, frameDialog);
        currentDialog.buttons.get(3).addActionListener(actionListener);
    }

    private void setEventSearch(){
        ActionListener actionListener = new BSearchListener(currentDialog, currentClass);
        currentDialog.buttons.get(4).addActionListener(actionListener);
    }

    private void setEventSort(){
        BSortListener actionListener = new BSortListener(currentDialog, currentClass);
        currentDialog.buttons.get(5).addActionListener(actionListener);
    }

    private void setEventReset(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        };
        currentDialog.buttons.get(6).addActionListener(actionListener);
    }

    public ArrayList<Model> updateData(){
        if((models = OneTableDialog.getData(currentClass)) == null) {
            JOptionPane.showMessageDialog(currentDialog, "Ошибка получения данных!", "Ошибка!",JOptionPane.PLAIN_MESSAGE);
            currentDialog.dispose();
        }
        currentDialog.tables.get(0).setModel(getModel(models, currentClass));
        return models;
    }


    private void setEventGetInfo(){
        BGetInfoListener actionListener = new BGetInfoListener(currentClass, this);
        currentDialog.buttons.get(7).addActionListener(actionListener);
    }

}
