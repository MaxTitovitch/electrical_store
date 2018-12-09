package table_models;

import model.Model;
import model.PurchaseModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PurchaseTableModel extends AbstractTableModel {
    private ArrayList<PurchaseModel> purchases;

    public ArrayList<PurchaseModel> getPurchases() {
        return purchases;
    }

    public PurchaseTableModel(ArrayList<PurchaseModel> purchases) {
        super();
        this.purchases = purchases;
    }

    public PurchaseTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.purchases = Converter.modelToPurchase(models);
    }

    @Override
    public int getRowCount() {
        return purchases.size();
    }

    @Override
    public int getColumnCount() {
        return PurchaseModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return purchases.get(r).getId();
            case 1: return purchases.get(r).getDate();
            case 2: return purchases.get(r).getAddress();
            case 3: return purchases.get(r).getMail();
            case 4: return purchases.get(r).getProduct_id();
            case 5: return purchases.get(r).getCount();
        }
    }

    @Override
    public String getColumnName(int c) {
        return PurchaseModel.COLUMNS_BY_RUS[c];
    }

}
