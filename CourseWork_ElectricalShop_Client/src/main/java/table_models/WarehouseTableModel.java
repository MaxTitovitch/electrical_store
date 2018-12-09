package table_models;

import model.Model;
import model.WarehouseModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class WarehouseTableModel extends AbstractTableModel {
    private ArrayList<WarehouseModel> warehouses;

    public ArrayList<WarehouseModel> getWarehouses() {
        return warehouses;
    }

    public WarehouseTableModel(ArrayList<WarehouseModel> warehouses) {
        super();
        this.warehouses = warehouses;
    }

    public WarehouseTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.warehouses = Converter.modelToWarehouse(models);
    }

    @Override
    public int getRowCount() {
        return warehouses.size();
    }

    @Override
    public int getColumnCount() {
        return WarehouseModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return warehouses.get(r).getId();
            case 1: return warehouses.get(r).getName();
            case 2: return warehouses.get(r).getAddress();
        }
    }

    @Override
    public String getColumnName(int c) {
        return WarehouseModel.COLUMNS_BY_RUS[c];
    }

}
