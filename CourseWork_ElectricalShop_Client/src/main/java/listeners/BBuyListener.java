package listeners;

import connection.DataPreparer;
import dialogs.CDialog;
import dialogs.CDialogNotFrame;
import model.Model;
import model.ProductModel;
import model.PurchaseModel;
import validation.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BBuyListener implements ActionListener {
    private ArrayList<Model> allProducts;
    private CDialogNotFrame dialogBuy;
    private CDialog frameDialog;

    public BBuyListener(CDialog frameDialog, ArrayList<Model> allProducts){
        super();
        this.frameDialog = frameDialog;
        this.allProducts = allProducts;
    }

    public void actionPerformed(ActionEvent e) {
        ProductModel selectedProduct ;
        if((selectedProduct = messageOk()) == null) return;

        prepareBuyData(selectedProduct);
        setEventBuy(selectedProduct.getCount(), selectedProduct.getId());
        dialogBuy.setVisible(true);
    }

    private ProductModel messageOk(){
        if(Validator.isInt(frameDialog.textFields.get(0).getText())){
            int myId = Integer.valueOf(frameDialog.textFields.get(0).getText());

            for (int i = 0; i < allProducts.size(); i++){
                if(allProducts.get(i).getId() == myId){
                    return (ProductModel)allProducts.get(i);
                }
            }
        }

        JOptionPane.showMessageDialog(dialogBuy, "Несуществующий индекс!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        return null;
    }

    private void prepareBuyData(ProductModel selectedProduct){
        dialogBuy = new CDialogNotFrame("Покупка", 300, 430, true);

        dialogBuy.addLabel(100, 30,50, 20, "Ваш товарТовар:");
        dialogBuy.addTextField(200, 30,50, 50);
        addProduct(selectedProduct);
        dialogBuy.addLabel(100, 30,50, 100, "Ваш email:");
        dialogBuy.addTextField(200, 30,50, 130);
        dialogBuy.addLabel(100, 30,50, 180, "Ваш адрес для доставки:");
        dialogBuy.addTextField(200, 30,50, 210);
        dialogBuy.addLabel(100, 30,50, 260, "Количество:");
        dialogBuy.addTextField(200, 30,50, 290);

        dialogBuy.addButton(200, 30,50, 340, "Купить");

    }

    private void addProduct(ProductModel product){
        String stringProduct = product.getManufacturer_name() + " \"" + product.getName() + "\" Цена: " + product.getPrice();
        dialogBuy.textFields.get(0).setText(stringProduct);
        dialogBuy.textFields.get(0).setEnabled(false);

    }

    private void setEventBuy(final int count,final int id){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(chectAddOk(count)){
                    String messageToUser;
                    if(addNewPurchase(count, id)) {
                        frameDialog.textFields.get(0).setText("");
                        messageToUser = "Товар куплен, мы скоро свяжемся с вами!";
                    } else {
                        messageToUser = "Ошибка добавления покупки в Базу Данных!";
                    }
                    JOptionPane.showMessageDialog(dialogBuy, messageToUser, "Операция завершена!", JOptionPane.PLAIN_MESSAGE);
                    dialogBuy.dispose();
                }
            }
        };
        dialogBuy.buttons.get(0).addActionListener(actionListener);
    }

    private boolean addNewPurchase(int count, int id){
        PurchaseModel purchase = new PurchaseModel(0,"", dialogBuy.textFields.get(2).getText().trim(),
                dialogBuy.textFields.get(1).getText().trim(), id, count);

        if("@ok@".equals(DataPreparer.insert(purchase, "purchases"))) return true;
        else return false;
    }

    private boolean chectAddOk(int count){
        String message = "";

        if(!Validator.isEMail(dialogBuy.textFields.get(1).getText().trim()))
            message += "  E-mail не корректен;\r\n";
        if("".equals(dialogBuy.textFields.get(2).getText().trim()))
            message += "  Адрес пуст пусто;\r\n";
        if(!Validator.isInt(dialogBuy.textFields.get(3).getText())){
            message += "  Колличество - не число!;\r\n";
        } else if(Integer.valueOf(dialogBuy.textFields.get(3).getText()) <= 0 || Integer.valueOf(dialogBuy.textFields.get(3).getText()) > count) {
            message += "  Колличество - в недопустимых пределах!;\r\n";
        }

        if(!message.equals("")) JOptionPane.showMessageDialog(dialogBuy, "Ошибки: \r\n" + message, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        return message.equals("") ? true : false;
    }


}
