package table_models;

import model.CategoryModel;
import model.Model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CategoryTableModel extends AbstractTableModel {
    private ArrayList<CategoryModel> categorys;

    public ArrayList<CategoryModel> getCategorys() {
        return categorys;
    }

    public CategoryTableModel(ArrayList<CategoryModel> categorys) {
        super();
        this.categorys = categorys;
    }

    public CategoryTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.categorys = Converter.modelToCategory(models);
    }

    @Override
    public int getRowCount() {
        return categorys.size();
    }

    @Override
    public int getColumnCount() {
        return CategoryModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return categorys.get(r).getId();
            case 1: return categorys.get(r).getName();
            case 2: return categorys.get(r).getParent_category_id();
        }
    }

    @Override
    public String getColumnName(int c) {
        return CategoryModel.COLUMNS_BY_RUS[c];
    }

}
