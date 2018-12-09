package data_base;

import model.*;
import validation.Validator;

import java.sql.*;
import java.util.ArrayList;

public class ConnectorToDB {
    private static ConnectorToDB ourInstance = new ConnectorToDB();
    private String userName;
    private String password;
    private String connectionURL;
    public String lastError = "";

    public static ConnectorToDB getInstance() {
        return ourInstance;
    }

    private ConnectorToDB() {
        lastError = "";
        try {
            userName = "root";
            password = "root";
            connectionURL = "jdbc:mysql://localhost:3306/electrical_store_database?useLegacyDatetimeCode=false&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            lastError = "Ошибка соединения с БД: \"" + e.getClass() + "\"";
        }
    }

    private boolean queryNotReturn(String query, String[] arr, boolean[] isString){
        try(
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                PreparedStatement statement = connection.prepareStatement(query)
        ){
            for (int i = 0; i < arr.length; i++) {
                if(!isString[i]){
                    setSpecial(statement, i+1,arr[i]);
                } else {
                    statement.setString(i+1, arr[i]);
                }
            }
            statement.executeUpdate();
            return true;
        } catch (Exception e){
            lastError = "Ошибка соединения с БД: \"" + e.getClass() + "\"";
            return false;
        }
    }

    private void setSpecial(PreparedStatement statement, int id, String data) throws SQLException {
        if(data.equals("NULL"))
            statement.setNull(id, java.sql.Types.NULL);
        else if(!Validator.isInt(data)) {
            if (Validator.isDouble(data))
                statement.setDouble(id, Double.valueOf(data));
        } else statement.setInt(id, Integer.valueOf(data));
    }

    public boolean insert (String table, String columns, String values, String[] arrayData, boolean[] isStr){
        lastError = "";
        String query = "insert into `" + table + "`(" + columns + ") values (" + values + ")";
        return queryNotReturn(query, arrayData, isStr);
    }

    public boolean update (String table, int id, String values, String[] arrayData, boolean[] isStr){
        lastError = "";
        String query = "update `" + table + "` set " + values + " where id = ?"; // + id;

        return queryNotReturn(query, getAllStr(arrayData, id), getAllBool(isStr, false));

    }

    private String[] getAllStr(String[] arrayData, int id){
        String[] array = new String[arrayData.length+1];
        for (int i = 0; i < arrayData.length; i++) {
            array[i] = arrayData[i];
        }
        array[array.length-1] = String.valueOf(id);
        return array;
    }

    private boolean[] getAllBool(boolean[] arrayData, boolean id){
        boolean[] array = new boolean[arrayData.length+1];
        for (int i = 0; i < arrayData.length; i++) {
            array[i] = arrayData[i];
        }
        array[array.length-1] = id;
        return array;
    }

    public boolean delete (String table, int id){
        lastError = "";
        String query = "delete from `" + table + "` where id = ?"; //+ id;
        return queryNotReturn(query, new String[]{String.valueOf(id)}, new boolean[]{false});
    }

    private void setSelectParam(PreparedStatement statement, String param, String isInt) throws SQLException{
        if(isInt.equals("true")){
            if(!Validator.isInt(param)) statement.setDouble( 1, Double.valueOf(param));
            else statement.setInt( 1, Integer.valueOf(param));
        } else {
            statement.setString(1, param);
        }
    }

    public ArrayList<Model> select(String table , String where, String orderBy, String param, String isInt) {
        lastError = "";
        if(!("".equals(orderBy.trim()))) orderBy = "order by " + orderBy;
        if(!("".equals(where.trim()))) where = "where " + where;
        String query = "select * from `" + table + "` " + where + " " + orderBy;
        //System.out.println(query);
        try(
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                PreparedStatement statement = connection.prepareStatement(query);
        ){
            if(!("".equals(where.trim()))) setSelectParam(statement, param, isInt);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Model> categorys = new ArrayList<>();
            while (resultSet.next()) categorys.add(resToModel(resultSet, table));
            return categorys;
        } catch (Exception e){
            lastError = "Ошибка соединения с БД: \"" + e.getClass() + "\"";
            return null;
        }
    }

    public ArrayList<Model> selectByQuery(String query, String table, String param, String isInt) {
        lastError = "";
        try(
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                PreparedStatement statement = connection.prepareStatement(query)
        ){
            if(!("".equals(param.trim()))) setSelectParam(statement,  param,  isInt);

            ArrayList<Model> currentModels = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                currentModels.add(resToModel(resultSet, table));
            }
            return currentModels;
        } catch (Exception e){
            lastError = "Ошибка соединения с БД: \"" + e.getClass() + "\"";
            return null;
        }
    }

    private Model resToModel(ResultSet resultSet, String table){
        try {
            switch (table) {
                case "categorys":               return resToCategoryModel(resultSet);
                case "products":                return resToProductModel(resultSet);
                case "purchases":               return resToPurchaseModel(resultSet);
                case "suppliers":               return resToSupplierModel(resultSet);
                case "users":                   return resToUserModel(resultSet);
                default: case "warehouses":     return resToWarehouseModel(resultSet);
                case "supplie_products":        return resToSupplierProductModel(resultSet);
                case "user_categories":         return restToUserCategory(resultSet);
            }
        } catch (Exception e){
            lastError = "Ошибка соединения с БД: \"" + e.getClass() + "\"";
            return null;
        }
    }

    private CategoryModel resToCategoryModel(ResultSet resultSet) throws Exception  {
        CategoryModel category = new CategoryModel(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getInt(3)
        );
        return category;
    }

    private ProductModel resToProductModel(ResultSet resultSet) throws Exception  {
        ProductModel product = new ProductModel(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getDouble(4),
            resultSet.getInt(5),
            resultSet.getString(6),
            resultSet.getInt(7),
            resultSet.getInt(8)
        );
        return product;
    }

    private PurchaseModel resToPurchaseModel(ResultSet resultSet) throws Exception  {
        PurchaseModel purchase = new PurchaseModel(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getString(4),
            resultSet.getInt(5),
            resultSet.getInt(6)
        );
        return purchase;
    }

    private SupplierModel resToSupplierModel(ResultSet resultSet) throws Exception  {
        SupplierModel supplier = new SupplierModel(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getString(4)
        );
        return supplier;
    }

    private UserModel resToUserModel(ResultSet resultSet) throws Exception  {
        UserModel user = new UserModel(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4)
        );
        return user;
    }

    private WarehouseModel resToWarehouseModel(ResultSet resultSet) throws Exception  {
        WarehouseModel warehouse = new WarehouseModel(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3)
        );
        return warehouse;
    }

    private SupplierProductModel resToSupplierProductModel(ResultSet resultSet) throws Exception  {
        SupplierProductModel supplierProduct = new SupplierProductModel(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getString(5)
        );
        return supplierProduct;
    }

    private UserCategoryModel restToUserCategory(ResultSet resultSet) throws Exception  {
        UserCategoryModel userCategory = new UserCategoryModel(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getString(5)
        );
        return userCategory;
    }

}
