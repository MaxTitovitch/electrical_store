package table_models;

import model.Model;
import model.SupplierModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SupplierTableModel extends AbstractTableModel {
    private ArrayList<SupplierModel> suppliers;

    public ArrayList<SupplierModel> getSuppliers() {
        return suppliers;
    }

    public SupplierTableModel(ArrayList<SupplierModel> suppliers) {
        super();
        this.suppliers = suppliers;
    }

    public SupplierTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.suppliers = Converter.modelToSupplier(models);
    }

    @Override
    public int getRowCount() {
        return suppliers.size();
    }

    @Override
    public int getColumnCount() {
        return SupplierModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return suppliers.get(r).getId();
            case 1: return suppliers.get(r).getName();
            case 2: return suppliers.get(r).getCountry();
            case 3: return suppliers.get(r).getDate_of_cooperation();
        }
    }

    @Override
    public String getColumnName(int c) {
        return SupplierModel.COLUMNS_BY_RUS[c];
    }

}
