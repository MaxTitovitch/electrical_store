package table_models;

import model.Model;
import model.SupplierModel;
import model.SupplierProductModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SupplierProductTableModel extends AbstractTableModel {
    private ArrayList<SupplierProductModel> supplierProducts;

    public ArrayList<SupplierProductModel> getSupplierProducts() {
        return supplierProducts;
    }

    public SupplierProductTableModel(ArrayList<SupplierProductModel> supplierProducts) {
        super();
        this.supplierProducts = supplierProducts;
    }

    public SupplierProductTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.supplierProducts = Converter.modelToSupplierProduct(models);
    }

    @Override
    public int getRowCount() {
        return supplierProducts.size();
    }

    @Override
    public int getColumnCount() {
        return SupplierProductModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default: case 0: return supplierProducts.get(r).getId();
            case 1: return supplierProducts.get(r).getSupplier_id();
            case 2: return supplierProducts.get(r).getSupplier_name();
            case 3: return supplierProducts.get(r).getProduct_id();
            case 4: return supplierProducts.get(r).getProduct_name();
        }
    }

    @Override
    public String getColumnName(int c) {
        return SupplierProductModel.COLUMNS_BY_RUS[c];
    }

}
