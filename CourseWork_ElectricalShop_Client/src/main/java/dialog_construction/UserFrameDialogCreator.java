package dialog_construction;

import connection.DataPreparer;
import listeners.*;
import model.*;
import table_models.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UserFrameDialogCreator extends AbstractFrameDialogCreator {
    private ArrayList<Model> allProducts;

    @Override
    public void prepareFrameDialog(){
        if((allProducts = DataPreparer.getProducts("", "")) == null) {
            JOptionPane.showMessageDialog(frameDialog, "Ошибка получения данных!", "Ошибка!",JOptionPane.PLAIN_MESSAGE);
            frameDialog.close();
        }
        frameDialog.setTitle(frameDialog.getTitle() + " Пользователь!");

        frameDialog.addTable(750, 200, 25, 20, new ProductTableModel(allProducts, true));
        frameDialog.addLabel(300, 30,250, 240, "Номер покупаемого товара:");
        frameDialog.addTextField(300, 30,250, 270);
        frameDialog.addButton(300, 30,250, 320, "Купить");
        frameDialog.addButton(300, 30,250, 370, "Поиск");
        frameDialog.addButton(300, 30,250, 420, "Сортировка");
        frameDialog.addButton(300, 30,250, 470, "Сброс");
        frameDialog.addButton(300, 30,250, 520, "ВЫЙТИ");
    }

    @Override
    public void setEventsForButtons(){
        addEventBuy();
        setEventSearch();
        setEventSort();
        setEventReset();
        setEventExit();
    }

    private void addEventBuy(){
        ActionListener actionListener = new BBuyListener(frameDialog, allProducts);
        frameDialog.buttons.get(0).addActionListener(actionListener);
    }


    private void setEventSearch(){
        ActionListener actionListener = new BSearchListener(frameDialog, ProductModel.class);
        frameDialog.buttons.get(1).addActionListener(actionListener);
    }

    private void setEventSort(){
        BSortListener actionListener = new BSortListener(frameDialog, ProductModel.class);
        frameDialog.buttons.get(2).addActionListener(actionListener);
    }

    private void setEventReset(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        };
        frameDialog.buttons.get(3).addActionListener(actionListener);
    }

    private void updateData(){
        frameDialog.tables.get(0).setModel(new ProductTableModel(allProducts, true));
    }

    protected ActionListener setEventExit(){
        ActionListener actionListener = super.setEventExit();
        frameDialog.buttons.get(4).addActionListener(actionListener);
        return actionListener;
    }

}
