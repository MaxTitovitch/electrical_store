package dialog_construction;


import model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminFrameDialogCreator extends AbstractFrameDialogCreator {
    @Override
    public void prepareFrameDialog(){
        frameDialog.setSize(800, 470);
        frameDialog.setTitle(frameDialog.getTitle() + " Администратор!");
        frameDialog.addButton(500, 30,150, 30, "Категории");
        frameDialog.addButton(500, 30,150, 80, "Продукты");
        frameDialog.addButton(500, 30,150, 130, "Покупки");
        frameDialog.addButton(500, 30,150, 180, "Поставщики");
        frameDialog.addButton(500, 30,150, 230, "Склады");
        frameDialog.addButton(500, 30,150, 280, "Поставщики Продуктов");
        frameDialog.addButton(500, 30,150, 330, "Категории Пользователей");
        frameDialog.addButton(500, 30,150, 380, "ВЫЙТИ");
    }

    @Override
    public void setEventsForButtons() {
        setEventCategory();
        setEventProduct();
        setEventPurchase();
        setEventSupplier();
        setEventWarehouse();
        setEventSupplierProduct();
        setEventCategoryUser();
        setEventExit();
    }

    protected void setEventCategory(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, CategoryModel.class, "\"Категориии\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(0).addActionListener(actionListener);
    }

    protected void setEventProduct(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, ProductModel.class, "\"Продукты\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(1).addActionListener(actionListener);
    }

    protected void setEventPurchase(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, PurchaseModel.class, "\"Покупки\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(2).addActionListener(actionListener);
    }

    protected void setEventSupplier(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, SupplierModel.class, "\"Поставщики\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(3).addActionListener(actionListener);
    }

    //protected void setEventUser(){}

    protected void setEventWarehouse(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, WarehouseModel.class, "\"Склады\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(4).addActionListener(actionListener);
    }

    protected void setEventSupplierProduct(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, SupplierProductModel.class, "\"Поставщики-Продукты\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(5).addActionListener(actionListener);
    }

    protected void setEventCategoryUser(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneTableDialog categoryDialog = new OneTableDialog(frameDialog, UserCategoryModel.class, "\"Категории-Пользователи\"");
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(6).addActionListener(actionListener);
    }

    protected ActionListener setEventExit(){
        ActionListener actionListener = super.setEventExit();
        frameDialog.buttons.get(7).addActionListener(actionListener);
        return actionListener;
    }
}
