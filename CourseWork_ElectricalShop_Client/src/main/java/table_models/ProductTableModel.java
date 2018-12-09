package table_models;

import model.Model;
import model.ProductModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductTableModel extends AbstractTableModel {
    private ArrayList<ProductModel> products;

    public ArrayList<ProductModel> getProducts(){
        return products;
    }

    public ProductTableModel(ArrayList<ProductModel> products) {
        super();
        this.products = products;
    }

    public ProductTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.products = Converter.modelToProduct(models);
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return ProductModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return products.get(r).getId();
            case 1: return products.get(r).getName();
            case 2: return products.get(r).getDescription();
            case 3: return products.get(r).getPrice();
            case 4: return products.get(r).getCount();
            case 5: return products.get(r).getManufacturer_name();
            case 6: return products.get(r).getCategory_id();
            case 7: return products.get(r).getWarehouse_id();
        }
    }

    @Override
    public String getColumnName(int c) {
        return ProductModel.COLUMNS_BY_RUS[c];
    }
}
