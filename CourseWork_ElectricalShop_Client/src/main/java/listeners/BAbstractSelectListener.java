package listeners;

import connection.DataPreparer;
import dialogs.*;
import model.*;
import table_models.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

abstract class BAbstractSelectListener implements ActionListener {
    protected CDialog parentalDialog;
    protected String orderBy = "";
    protected String where = "";
    protected Class<? extends Model> currentClass;

    public BAbstractSelectListener(CDialog parentalDialog, Class<? extends Model> currentClass){
        super();
        this.parentalDialog = parentalDialog;
        this.currentClass = currentClass;
    }


    protected void choseDefCols(CDialogNotFrame dialog){
        switch (currentClass.getName()) {
            case "model.CategoryModel":     addDefCols(dialog, CategoryModel.COLUMNS_BY_RUS); break;
            case "model.ProductModel":      addDefCols(dialog, ProductModel.COLUMNS_BY_RUS); break;
            case "model.PurchaseModel":     addDefCols(dialog, PurchaseModel.COLUMNS_BY_RUS); break;
            case "model.SupplierModel":     addDefCols(dialog, SupplierModel.COLUMNS_BY_RUS); break;
            case "model.UserModel":         addDefCols(dialog, UserModel.COLUMNS_BY_RUS); break;
            case "model.WarehouseModel":    addDefCols(dialog, WarehouseModel.COLUMNS_BY_RUS); break;
        }
    }

    private void addDefCols(CDialogNotFrame dialog, String items[]){
        for (int i = 0; i < items.length; i++) {
            dialog.comboBoxes.get(0).addItem(items[i]);
        }
    }

    protected void getData(){
        switch (currentClass.getName()) {
            case "model.CategoryModel":     getDataCategory(); break;
            case "model.ProductModel":      getDataProduct(); break;
            case "model.PurchaseModel":     getDataPurchase(); break;
            case "model.SupplierModel":     getDataSupplier(); break;
            case "model.UserModel":         getDataUser(); break;
            case "model.WarehouseModel":    getDataWarehouse(); break;
        }
    }

    private void getDataCategory(){
        ArrayList<Model> result;
        if((result = DataPreparer.getCategorys(orderBy, where)) == null){
            JOptionPane.showMessageDialog(parentalDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            parentalDialog.tables.get(0).setModel(new CategoryTableModel(result, true));
        }
    }

    private void getDataProduct(){
        ArrayList<Model> result;
        if((result = DataPreparer.getProducts(orderBy, where)) == null){
            JOptionPane.showMessageDialog(parentalDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            parentalDialog.tables.get(0).setModel(new ProductTableModel(result, true));
        }
    }


    private void getDataPurchase(){
        ArrayList<Model> result;
        if((result = DataPreparer.getPurchases(orderBy, where)) == null){
            JOptionPane.showMessageDialog(parentalDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            parentalDialog.tables.get(0).setModel(new PurchaseTableModel(result, true));
        }
    }


    private void getDataSupplier(){
        ArrayList<Model> result;
        if((result = DataPreparer.getSuppliers(orderBy, where)) == null){
            JOptionPane.showMessageDialog(parentalDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            parentalDialog.tables.get(0).setModel(new SupplierTableModel(result, true));
        }
    }

    private void getDataUser(){
        ArrayList<Model> result;
        if((result = DataPreparer.getUsers(orderBy, where)) == null){
            JOptionPane.showMessageDialog(parentalDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            parentalDialog.tables.get(0).setModel(new UserTableModel(result, true));
        }
    }

    private void getDataWarehouse(){
        ArrayList<Model> result;
        if((result = DataPreparer.getWarehouses(orderBy, where)) == null){
            JOptionPane.showMessageDialog(parentalDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            parentalDialog.tables.get(0).setModel(new WarehouseTableModel(result, true));
        }
    }

}
