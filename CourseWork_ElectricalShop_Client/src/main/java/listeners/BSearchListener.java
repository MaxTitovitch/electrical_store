package listeners;


import dialogs.*;
import model.*;
import validation.Validator;

import javax.swing.*;
import java.awt.event.*;


public class BSearchListener extends BAbstractSelectListener {
    private CDialogNotFrame dialogSearch;

    public BSearchListener(CDialog parentalDialog, Class<? extends Model> currentClass){
        super(parentalDialog, currentClass);
    }

    public void actionPerformed(ActionEvent e) {
        prepareSearchData();
        addEventSearch();
        dialogSearch.setVisible(true);
    }

    private void prepareSearchData(){
        dialogSearch = new CDialogNotFrame("Поиск", 300, 430, true);

        dialogSearch.addLabel(200, 30,50, 20, "Поле для поиска:");
        dialogSearch.addComboBox(false,200, 30,50, 50);
        choseDefCols(dialogSearch);
        dialogSearch.addLabel(200, 30,50, 100, "Значение поиска:");
        dialogSearch.addTextField(200, 30,50, 130);
        dialogSearch.addLabel(200, 30,50, 180, "Знак для поиска:");
        dialogSearch.addRadioButtonGroupe(200, 20,50, 210, new String[]{"=", ">", "<", "<>"});

        dialogSearch.addButton(200, 30,50, 340, "Получить");

    }


    private void addEventSearch() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choseWhereStr();
                if(!choseMessageOk()) return;
                getData();
                dialogSearch.dispose();
            }
        };
        dialogSearch.buttons.get(0).addActionListener(actionListener);
    }

    private void choseWhereStr(){
        String apos = "";

        switch (currentClass.getName()) {
            case "model.CategoryModel":
                apos = prepareWhereString(CategoryModel.COLUMNS_BY_RUS, CategoryModel.COLUMNS, CategoryModel.IS_STR); break;
            case "model.ProductModel":
                apos = prepareWhereString(ProductModel.COLUMNS_BY_RUS, ProductModel.COLUMNS, ProductModel.IS_STR); break;
            case "model.PurchaseModel":
                apos = prepareWhereString(PurchaseModel.COLUMNS_BY_RUS, PurchaseModel.COLUMNS, PurchaseModel.IS_STR); break;
            case "model.SupplierModel":
                apos = prepareWhereString(SupplierModel.COLUMNS_BY_RUS, SupplierModel.COLUMNS, SupplierModel.IS_STR); break;
            case "model.UserModel":
                apos = prepareWhereString(UserModel.COLUMNS_BY_RUS, UserModel.COLUMNS, UserModel.IS_STR); break;
            case "model.WarehouseModel":
                apos = prepareWhereString(WarehouseModel.COLUMNS_BY_RUS, WarehouseModel.COLUMNS, WarehouseModel.IS_STR); break;
        }
        where += " " + getSimbolSearch() + " ? @@@" + dialogSearch.textFields.get(0).getText() + "@@@" + apos;
    }

    private String prepareWhereString(String colsRus[], String cols[], boolean isStr[]){
        String selected = dialogSearch.comboBoxes.get(0).getSelectedItem().toString();
        if(selected.equals(colsRus[0])) where = "id";
        for (int i = 1; i < colsRus.length; i++) {
            if(selected.equals(colsRus[i])) {
                where = cols[i-1];
                if(isStr[i-1]) return "false";
            }
        }
        return "true";
    }

    private String getSimbolSearch() {
        for (int i = 0; i < dialogSearch.radioBoxGroups.get(0).length; i++)
            if(dialogSearch.radioBoxGroups.get(0)[i].isSelected()){
                return dialogSearch.radioBoxGroups.get(0)[i].getText();
            }
        return "=";
    }

    private boolean choseMessageOk(){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return messageIntOk(CategoryModel.IS_STR, false);
            case "model.ProductModel":      return messageIntOk(ProductModel.IS_STR, true);
            case "model.PurchaseModel":     return messageIntOk(PurchaseModel.IS_STR, false);
            case "model.SupplierModel":     return messageIntOk(SupplierModel.IS_STR, false);
            case "model.UserModel":         return messageIntOk(UserModel.IS_STR, false);
            case "model.WarehouseModel":    return messageIntOk(WarehouseModel.IS_STR, false);
        }
    }

    private boolean messageIntOk(boolean isStr[], boolean isProduct){
        int selecterIndex = dialogSearch.comboBoxes.get(0).getSelectedIndex();
        boolean isIntColumns = selecterIndex == 0;
        for (int i = 0; i < isStr.length; i++) {
            if(!isStr[i]){
                isIntColumns = isIntColumns || selecterIndex == i+1;
            }
        }

        return checkCulumns(isIntColumns, isProduct, selecterIndex);
    }

    private boolean checkCulumns(boolean isIntColumns, boolean isProduct, int selecterIndex){
        if(isIntColumns) {
            if (!Validator.isInt(dialogSearch.textFields.get(0).getText())) {
                String mes = "Введено не целое число для числового поля!";
                JOptionPane.showMessageDialog(dialogSearch, mes, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        } else if(isProduct && selecterIndex == 3){
            if (!Validator.isDouble(dialogSearch.textFields.get(0).getText())) {
                String mes = "Введено не число для числового поля!";
                JOptionPane.showMessageDialog(dialogSearch, mes, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        }
        return true;
    }

}
