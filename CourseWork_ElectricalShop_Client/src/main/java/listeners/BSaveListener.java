package listeners;

import dialogs.CDialog;
import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BSaveListener implements ActionListener {
    private ArrayList<Model> models;
    private Class<? extends Model> currentClass;
    private CDialog dialog;

    public BSaveListener(ArrayList<Model> models, Class<? extends Model> currentClass, CDialog dialog){
        this.models = models;
        this.currentClass = currentClass;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new MyTXTFilter());
        fileChooser.setDialogTitle("Сохранить отчёт: '" + currentClass.getSimpleName() + "'");
        if (fileChooser.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            saveDataInFile(models, getCorrectFileName(fileToSave.getAbsolutePath()));
        }
    }

    private String getCorrectFileName(String name){
        return name.substring(name.length()-4).equals(".txt") ? name : name + ".txt";
    }

    class MyTXTFilter extends FileFilter implements java.io.FileFilter {
        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            }
            if (pathname.getName().toLowerCase().endsWith(".txt")) {
                return true;
            }
            return false;
        }

        public String getDescription() {
            return "Текстовые файлы (*.txt)";
        }
    }

    private void saveDataInFile(ArrayList<Model> models, String file){
        try (
                FileWriter fout = new FileWriter(file, false);
        ){
            switch (currentClass.getName()) {
                default:
                case "model.CategoryModel":      saveCategory(models, fout); break;
                case "model.ProductModel":       saveProduct(models, fout); break;
                case "model.PurchaseModel":      savePurchase(models, fout); break;
                case "model.SupplierModel":      saveSupplier(models, fout); break;
                case "model.UserModel":          saveUser(models, fout); break;
                case "model.WarehouseModel":     saveWarehouse(models, fout); break;
                case "model.SupplierProductModel":      saveSupplierProduct(models, fout); break;
                case "model.UserCategoryModel":         saveCategoryUser(models, fout); break;
            }
            JOptionPane.showMessageDialog(dialog, "Отчёт создан!", "Всё ок!",JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(dialog, "Ошибка создания отчёта!", "Ошибка!",JOptionPane.PLAIN_MESSAGE);
        }
    }

    private String getSpaces(String line, int length){
        int size = line.length();
        if(size > length){
            char[] simbols = new char[length];
            line.getChars(0, length-2, simbols, 0);
            simbols[length-2] = '.';
            simbols[length-1] = '.';
            line = String.valueOf(simbols);

        } else {
            for (int i = 0; i < length - size; i++) {
                line += " ";
            }
        }
        return line;
    }

    private String getLine(int length){
        String answer = "";
        for (int i = 0; i < length+2; i++) {
            answer += "=";
        }
        return answer + "\r\n";
    }

    private void saveCategory(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("Название", 35) + "|"
                 + getSpaces("К.ИД", 5) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<КАТЕГОРИИ>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            CategoryModel category = (CategoryModel)model.get(i);
            fout.write("|" + category.getId() + "\t|"
                    + getSpaces(category.getName(), 35) + "|"
                    + getSpaces(String.valueOf(category.getParent_category_id()), 5) + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void saveProduct(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("Название", 25) + "|"
                + getSpaces("Описание", 35) + "|"  + getSpaces("Цена", 15) + "|"
                + getSpaces("Кл-во", 10) + "|" + getSpaces("Производитель", 25) + "|"
                + getSpaces("К.ИД", 5) + "|" +  getSpaces("С.ИД", 5) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<ПРОДУКТЫ>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            ProductModel product = (ProductModel)model.get(i);
            fout.write("|" + product.getId() + "\t|" + getSpaces(product.getName(), 25) + "|"
                    + getSpaces(product.getDescription(), 35) + "|"  + getSpaces(String.valueOf(product.getPrice()), 15) + "|"
                    + getSpaces(String.valueOf(product.getCount()), 10) + "|" + getSpaces(product.getManufacturer_name(), 25) + "|"
                    + getSpaces(String.valueOf(product.getCategory_id()), 5) + "|"
                    + getSpaces(String.valueOf(product.getWarehouse_id()),5) + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void savePurchase(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("Дата", 15) + "|"
                + getSpaces("Адрес", 25)  + "|" + getSpaces("Почта", 25) + "|"
                + getSpaces("П.ИД", 5) + "|" + getSpaces("Кл-во", 10) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<ПОКУПКИ>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            PurchaseModel purchase = (PurchaseModel)model.get(i);
            fout.write("|" + purchase.getId() + "\t|" + getSpaces(purchase.getDate(), 15) + "|"
                    + getSpaces(purchase.getAddress(), 25)  + "|" + getSpaces(purchase.getMail(), 25) + "|"
                    + getSpaces(String.valueOf(purchase.getProduct_id()), 5) + "|"
                    + getSpaces(String.valueOf(purchase.getCount()), 10) + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void saveSupplier(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("Название", 25) + "|"
                + getSpaces("Страна",25) + "|" + getSpaces("Сотрудничество с", 25) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<ПОСТАВЩИКИ>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            SupplierModel supplier = (SupplierModel)model.get(i);
            fout.write("|" + supplier.getId() + "\t|" + getSpaces(supplier.getName(), 25) + "|"
                    + getSpaces(supplier.getCountry(), 25) + "|" + getSpaces(supplier.getDate_of_cooperation(), 25)
                    + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void saveUser(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("Логин", 25) + "|"
                + getSpaces("Паспорт", 25) + "|" + "Роль|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<ПОСТАВЩИКИ>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            UserModel user = (UserModel)model.get(i);
            fout.write("|" + user.getId() + "\t|" + getSpaces(user.getLogin(), 25) + "|"
                    + getSpaces(user.getPassword(), 25) + "|" + user.getRole() + "   |\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void saveWarehouse(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("Название", 25) + "|"
                + getSpaces("Адрес", 25) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<СКЛАДЫ>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            WarehouseModel warehouse = (WarehouseModel)model.get(i);
            fout.write("|" + warehouse.getId() + "\t|" + getSpaces(warehouse.getName(), 25) + "|"
                    + getSpaces(warehouse.getAddress(), 25) + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void saveSupplierProduct(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("ИД Поставщика", 15) + "|"
                + getSpaces("Поставщик", 25) + "|"+ getSpaces("ИД Продукт", 15) + "|"
                + getSpaces("Продукт", 25) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<Производ-Продукт>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            SupplierProductModel supplierProduct = (SupplierProductModel)model.get(i);
            fout.write("|" + supplierProduct.getId() + "\t|" + getSpaces(String.valueOf(supplierProduct.getSupplier_id()), 15)
                    + "|" + getSpaces(supplierProduct.getSupplier_name(), 25) + "|" + getSpaces(String.valueOf(supplierProduct.getProduct_id()), 15)
                    + "|" + getSpaces(supplierProduct.getProduct_name(), 25) + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }

    private void saveCategoryUser(ArrayList<Model> model, FileWriter fout) throws IOException {
        String columnHead = "|" + "ИД\t|" + getSpaces("ИД Категор", 15) + "|"
                + getSpaces("Категоря", 25) + "|"+ getSpaces("ИД Пользоват", 15) + "|"
                + getSpaces("Пользователь", 25) + "|\r\n";
        fout.write(getLine(columnHead.length()) + getSpaces("| Таблица: <<<Категор-Пользователь>>>", columnHead.length()) + " |\r\n");
        fout.write(getLine(columnHead.length()) + columnHead + getLine(columnHead.length()));
        for (int i = 0; i < model.size(); i++) {
            UserCategoryModel userCategory = (UserCategoryModel)model.get(i);
            fout.write("|" + userCategory.getId() + "\t|" + getSpaces(String.valueOf(userCategory.getcategory_id()), 15)
                    + "|" + getSpaces(userCategory.getcategory_name(), 25) + "|" + getSpaces(String.valueOf(userCategory.getuser_id()), 15)
                    + "|" + getSpaces(userCategory.getuser_name(), 25) + "|\r\n");
        }
        fout.write(getLine(columnHead.length()));
    }
}
