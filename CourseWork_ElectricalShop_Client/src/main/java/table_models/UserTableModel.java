package table_models;

import model.Model;
import model.UserModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UserTableModel extends AbstractTableModel {
    private ArrayList<UserModel> users;

    public ArrayList<UserModel> getUsers() {
        return users;
    }

    public UserTableModel(ArrayList<UserModel> users) {
        super();
        this.users = users;
    }

    public UserTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.users = Converter.modelToUser(models);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return UserModel.COLUMNS_BY_RUS.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return users.get(r).getId();
            case 1: return users.get(r).getLogin();
            case 2: return users.get(r).getPassword();
            case 3: return users.get(r).getRole();
        }
    }

    @Override
    public String getColumnName(int c) {
        return UserModel.COLUMNS_BY_RUS[c];
    }

}
