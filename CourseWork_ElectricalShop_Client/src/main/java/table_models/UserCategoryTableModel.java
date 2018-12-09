package table_models;

import model.Model;
import model.SupplierProductModel;
import model.UserCategoryModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UserCategoryTableModel extends AbstractTableModel {
    private ArrayList<UserCategoryModel> userCategorys;

    public ArrayList<UserCategoryModel> getUserCategorys() {
        return userCategorys;
    }

    public UserCategoryTableModel(ArrayList<UserCategoryModel> userCategorys) {
        super();
        this.userCategorys = userCategorys;
    }

    public UserCategoryTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.userCategorys = Converter.modelToUserCategory(models);
    }

    @Override
    public int getRowCount() {
        return userCategorys.size();
    }

    @Override
    public int getColumnCount() {
        return UserCategoryModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default: case 0: return userCategorys.get(r).getId();
            case 1: return userCategorys.get(r).getuser_id();
            case 2: return userCategorys.get(r).getuser_name();
            case 3: return userCategorys.get(r).getcategory_id();
            case 4: return userCategorys.get(r).getcategory_name();
        }
    }

    @Override
    public String getColumnName(int c) {
        return UserCategoryModel.COLUMNS_BY_RUS[c];
    }

}
