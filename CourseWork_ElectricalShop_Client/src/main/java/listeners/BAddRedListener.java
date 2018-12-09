package listeners;

import connection.DataPreparer;
import dialogs.CDialog;
import dialogs.CDialogNotFrame;
import dialog_construction.OneTableDialog;
import model.*;
import validation.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BAddRedListener extends BAbstractEditListener {
    private CDialogNotFrame dialogAdd;

    public BAddRedListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models, OneTableDialog parental){
        super(frameDialog, currentClass, models, parental);
    }

    public void actionPerformed(ActionEvent e) {
        boolean isRed;
        isRed = (e.getActionCommand() == "Добавить") ? false : true;
        if(isRed && !isIdCorrect()) return;
        prepareAddData(isRed);
        addEventAdd(isRed);
        dialogAdd.setVisible(true);
    }

    protected void prepareAddData(final boolean isRed){
        dialogAdd = new CDialogNotFrame("Добавление", 300, 420, true);
        int i;
        String columns[] = getColumns();

        if(currentClass.getName().equals("model.SupplierProductModel") || currentClass.getName().equals("model.UserCategoryModel")){
            dialogAdd.addLabel(200, 30, 50, 10, columns[2]);
            dialogAdd.addTextField(200, 30, 50, 40);
            dialogAdd.addLabel(200, 30, 50, 90, columns[4]);
            dialogAdd.addTextField(200, 30, 50, 120);
            i = 3;
        } else {
            for (i = 1; i < columns.length; i++) {
                dialogAdd.addLabel(200, 30, 50, 10 + (i - 1) * 80, columns[i]);
                dialogAdd.addTextField(200, 30, 50, 40 + (i - 1) * 80);
            }
        }

        dialogAdd.addButton(200, 30,50, 90+(i-2)*80, "Сохранить");
        dialogAdd.setSize(300, 180+(i-2)*80);
        if (isRed) addDataLast();
    }

    private String[] getColumns(){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return CategoryModel.COLUMNS_BY_RUS;
            case "model.ProductModel":      return ProductModel.COLUMNS_BY_RUS;
            case "model.PurchaseModel":     return PurchaseModel.COLUMNS_BY_RUS;
            case "model.SupplierModel":     return SupplierModel.COLUMNS_BY_RUS;
            case "model.UserModel":         return UserModel.COLUMNS_BY_RUS;
            case "model.WarehouseModel":    return WarehouseModel.COLUMNS_BY_RUS;
            case "model.SupplierProductModel":  return SupplierProductModel.COLUMNS_BY_RUS;
            case "model.UserCategoryModel":     return UserCategoryModel.COLUMNS_BY_RUS;
        }
    }

    protected void addDataLast(){
        String where = " id = ? @@@" + frameDialog.textFields.get(0).getText() + "@@@" + " ";

        ArrayList<Model> result = getData(where);
        if(result == null){
            JOptionPane.showMessageDialog(dialogAdd, "Ошибка поиска!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
            dialogAdd.dispose();
        }
        Model model = result.get(0);
        addOneLastData(model);
    }

    private ArrayList<Model> getData(String where){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return DataPreparer.getCategorys("", where);
            case "model.ProductModel":      return DataPreparer.getProducts("", where);
            case "model.PurchaseModel":     return DataPreparer.getPurchases("", where);
            case "model.SupplierModel":     return DataPreparer.getSuppliers("", where);
            case "model.UserModel":         return DataPreparer.getUsers("", where);
            case "model.WarehouseModel":    return DataPreparer.getWarehouses("", where);
            case "model.SupplierProductModel":      return DataPreparer.getSuppliersProducts("", where);
            case "model.UserCategoryModel":         return DataPreparer.getUserCategorys("", where);
        }
    }

    private void addOneLastData(Model model){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     addOneLastDataCategory((CategoryModel) model); break;
            case "model.ProductModel":      addOneLastDataProduct((ProductModel) model); break;
            case "model.PurchaseModel":     addOneLastDataPurchase((PurchaseModel) model); break;
            case "model.SupplierModel":     addOneLastDataSupplier((SupplierModel) model); break;
            case "model.UserModel":         addOneLastDataUser((UserModel) model); break;
            case "model.WarehouseModel":    addOneLastDataWarehouse((WarehouseModel) model); break;
            case "model.SupplierProductModel":      addOneLastDataSupplierProduct((SupplierProductModel) model); break;
            case "model.UserCategoryModel":         addOneLastDataUserCategory((UserCategoryModel) model); break;
        }
    }

    private void addOneLastDataCategory(CategoryModel model) {
        dialogAdd.textFields.get(0).setText(model.getName());
        dialogAdd.textFields.get(1).setText(String.valueOf(model.getParent_category_id()));
    }

    private void addOneLastDataProduct(ProductModel model) {
        dialogAdd.textFields.get(0).setText(model.getName());
        dialogAdd.textFields.get(1).setText(model.getDescription());
        dialogAdd.textFields.get(2).setText(String.valueOf(model.getPrice()));
        dialogAdd.textFields.get(3).setText(String.valueOf(model.getCount()));
        dialogAdd.textFields.get(4).setText(model.getManufacturer_name());
        dialogAdd.textFields.get(5).setText(String.valueOf(model.getCategory_id()));
        dialogAdd.textFields.get(6).setText(String.valueOf(model.getWarehouse_id()));
    }

    private void addOneLastDataPurchase(PurchaseModel model) {
        dialogAdd.textFields.get(0).setText(model.getDate());
        dialogAdd.textFields.get(1).setText(model.getAddress());
        dialogAdd.textFields.get(2).setText(model.getMail());
        dialogAdd.textFields.get(3).setText(String.valueOf(model.getProduct_id()));
        dialogAdd.textFields.get(4).setText(String.valueOf(model.getCount()));
    }

    private void addOneLastDataSupplier(SupplierModel model) {
        dialogAdd.textFields.get(0).setText(model.getName());
        dialogAdd.textFields.get(1).setText(model.getCountry());
        dialogAdd.textFields.get(2).setText(model.getDate_of_cooperation());
    }

    private void addOneLastDataUser(UserModel model) {
        dialogAdd.textFields.get(0).setText(model.getLogin());
        dialogAdd.textFields.get(1).setText(model.getPassword());
        dialogAdd.textFields.get(2).setText(String.valueOf(model.getRole()));
    }

    private void addOneLastDataWarehouse(WarehouseModel model) {
        dialogAdd.textFields.get(0).setText(model.getName());
        dialogAdd.textFields.get(1).setText(model.getAddress());

    }

    private void addOneLastDataSupplierProduct(SupplierProductModel model) {
        dialogAdd.textFields.get(0).setText(String.valueOf(model.getSupplier_id()));
        dialogAdd.textFields.get(1).setText(String.valueOf(model.getProduct_id()));
    }

    private void addOneLastDataUserCategory(UserCategoryModel model) {
        dialogAdd.textFields.get(0).setText(String.valueOf(model.getuser_id()));
        dialogAdd.textFields.get(1).setText(String.valueOf(model.getcategory_id()));
    }

    protected void addEventAdd(final boolean isRed){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(changeAndCheckOk()){
                    addData(isRed, generateNewModel());
                    parental.updateData();
                    dialogAdd.dispose();
                }
            }
        };
        dialogAdd.buttons.get(0).addActionListener(actionListener);
    }

    private void addData(boolean isRed, Model model){
        String error;
        if(isRed){
            if (!"".equals(error = getQueryUpdate(model))) {
                JOptionPane.showMessageDialog(dialogAdd, "Ошибка редактирования: " + error, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
            }
        } else {
            if (!"".equals(error = getQueryInsert(model))) {
                JOptionPane.showMessageDialog(dialogAdd, "Ошибка добавления: " + error, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }


    private Model generateNewModel(){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return generateNewCategory();
            case "model.ProductModel":      return generateNewProduct();
            case "model.PurchaseModel":     return generateNewPurchase();
            case "model.SupplierModel":     return generateNewSupplier();
            case "model.UserModel":         return generateNewUser();
            case "model.WarehouseModel":    return generateNewWarehouse();
            case "model.SupplierProductModel":      return generateNewSupplierProduct();
            case "model.UserCategoryModel":         return generateNewUserCategory();
        }
    }

    private Model generateNewCategory() {
        CategoryModel model;
        if("".equals(dialogAdd.textFields.get(1).getText()))
            model = new CategoryModel(
                    0,
                    dialogAdd.textFields.get(0).getText()
            );
        else
            model = new CategoryModel(
                    0,
                    dialogAdd.textFields.get(0).getText(),
                    Integer.valueOf(dialogAdd.textFields.get(1).getText())
            );
        return (Model) model;
    }

    private Model generateNewProduct() {
        ProductModel model = new ProductModel(
                0,
                dialogAdd.textFields.get(0).getText(),
                dialogAdd.textFields.get(1).getText(),
                Double.valueOf(dialogAdd.textFields.get(2).getText()),
                Integer.valueOf(dialogAdd.textFields.get(3).getText()),
                dialogAdd.textFields.get(4).getText(),
                Integer.valueOf(dialogAdd.textFields.get(5).getText()),
                Integer.valueOf(dialogAdd.textFields.get(6).getText())
        );
        return (Model) model;
    }

    private Model generateNewPurchase() {
        PurchaseModel model = new PurchaseModel(
                0,
                dialogAdd.textFields.get(0).getText(),
                dialogAdd.textFields.get(1).getText(),
                dialogAdd.textFields.get(2).getText(),
                Integer.valueOf(dialogAdd.textFields.get(3).getText()),
                Integer.valueOf(dialogAdd.textFields.get(4).getText())
        );
        return (Model) model;
    }

    private Model generateNewSupplier() {
        SupplierModel model = new SupplierModel(
                0,
                dialogAdd.textFields.get(0).getText(),
                dialogAdd.textFields.get(1).getText(),
                dialogAdd.textFields.get(2).getText()
        );
        return (Model) model;
    }

    private Model generateNewUser() {
        UserModel model = new UserModel(
                0,
                dialogAdd.textFields.get(0).getText(),
                dialogAdd.textFields.get(1).getText(),
                Integer.valueOf(dialogAdd.textFields.get(2).getText())
        );
        return (Model) model;
    }

    private Model generateNewWarehouse() {
        WarehouseModel model = new WarehouseModel(
                0,
                dialogAdd.textFields.get(0).getText(),
                dialogAdd.textFields.get(1).getText()
        );
        return (Model) model;
    }

    private Model generateNewSupplierProduct() {
        SupplierProductModel model = new SupplierProductModel(
                0,
                Integer.valueOf(dialogAdd.textFields.get(0).getText()),
                "",
                Integer.valueOf(dialogAdd.textFields.get(1).getText()),
                ""
        );
        return (Model) model;
    }

    private Model generateNewUserCategory() {
        UserCategoryModel model = new UserCategoryModel(
                0,
                Integer.valueOf(dialogAdd.textFields.get(0).getText()),
                "",
                Integer.valueOf(dialogAdd.textFields.get(1).getText()),
                ""
        );
        return (Model) model;
    }

    private boolean changeAndCheckOk(){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return checkAddOk(CategoryModel.COLUMNS_BY_RUS, CategoryModel.IS_STR);
            case "model.ProductModel":      return checkAddOk(ProductModel.COLUMNS_BY_RUS, ProductModel.IS_STR);
            case "model.PurchaseModel":     return checkAddOk(PurchaseModel.COLUMNS_BY_RUS, PurchaseModel.IS_STR);
            case "model.SupplierModel":     return checkAddOk(SupplierModel.COLUMNS_BY_RUS, SupplierModel.IS_STR);
            case "model.UserModel":         return checkAddOk(UserModel.COLUMNS_BY_RUS, UserModel.IS_STR);
            case "model.WarehouseModel":    return checkAddOk(WarehouseModel.COLUMNS_BY_RUS, WarehouseModel.IS_STR);
            case "model.SupplierProductModel":      return checkAddOkJoin(SupplierProductModel.COLUMNS_BY_RUS);
            case "model.UserCategoryModel":         return checkAddOkJoin(UserCategoryModel.COLUMNS_BY_RUS);
        }
    }

    private boolean checkAddOkJoin(String columns[]){
        String message = "";

        if (!Validator.isInt(dialogAdd.textFields.get(0).getText())) {
            message += "  " + columns[1] + " не целое число;\r\n";
        }

        if (!Validator.isInt(dialogAdd.textFields.get(1).getText())) {
            message += "  " + columns[3] + " не целое число;\r\n";
        }

        if(!message.equals("")) JOptionPane.showMessageDialog(dialogAdd, "Ошибки:\r\n" + message, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        return message.equals("") ? true : false;
    }

    private boolean checkAddOk(String columns[], boolean isStrColumns[]){
        String message = "";
        for (int i = 0; i < isStrColumns.length; i++) {
            if(!isSpecialColumn(i)) {
                if (isStrColumns[i]) {
                    if ("".equals(dialogAdd.textFields.get(i).getText().trim())) {
                        message += "  " + columns[i + 1] + " пусто;\r\n";
                    }
                } else {
                    if (!Validator.isInt(dialogAdd.textFields.get(i).getText())) {
                        message += "  " + columns[i + 1] + " не целое число;\r\n";
                    }
                }
            }
        }
        message += checkAddOkSpecial();
        if(!message.equals("")) JOptionPane.showMessageDialog(dialogAdd, "Ошибки:\r\n" + message, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        return message.equals("") ? true : false;
    }

    private boolean isSpecialColumn(int i){
        boolean answer = false;
        if(currentClass.getName().equals("model.PurchaseModel"))
            if (i == 0 || i == 2)
                answer = true;
        if(currentClass.getName().equals("model.SupplierModel"))
            if(i == 2)
                answer = true;
        if(currentClass.getName().equals("model.ProductModel"))
            if(i == 2)
                answer = true;
        if(currentClass.getName().equals("model.CategoryModel"))
            if(i == 1)
                answer = true;
        if(currentClass.getName().equals("model.UserModel"))
            if(i == 2)
                answer = true;

        return answer;
    }

    private String checkAddOkSpecial(){
        String message = "";
        if(currentClass.getName().equals("model.PurchaseModel")) {
            if (!Validator.isDate(dialogAdd.textFields.get(0).getText()))
                message += "  Некторректная дата;\r\n";
            if (!Validator.isEMail(dialogAdd.textFields.get(2).getText()))
                message += "  Некторректный email;\r\n";
        }
        if(currentClass.getName().equals("model.SupplierModel"))
            if(!Validator.isDate(dialogAdd.textFields.get(2).getText()))
                message += "  Некторректная дата;\r\n";
        if(currentClass.getName().equals("model.ProductModel"))
            if(!Validator.isDouble(dialogAdd.textFields.get(2).getText()))
                message += "  Цена не число;\r\n";
        if(currentClass.getName().equals("model.CategoryModel"))
            if(!Validator.isInt(dialogAdd.textFields.get(1).getText()) && !(dialogAdd.textFields.get(1).getText().equals("")))
                message += "  НадКатегория не число;\r\n";
        if(currentClass.getName().equals("model.UserModel"))
            if(!Validator.isInt(dialogAdd.textFields.get(2).getText()))
                message += "  Роль - не число;\r\n";
            else if(Integer.valueOf(dialogAdd.textFields.get(2).getText()) < 0 || Integer.valueOf(dialogAdd.textFields.get(2).getText()) > 2)
                message += "  Роль - не роль;\r\n";
        return message;
    }

}
