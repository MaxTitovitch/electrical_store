package data_base;

import model.*;

import java.util.ArrayList;

public class QueryerToDB {
    private ConnectorToDB connector =  ConnectorToDB.getInstance();
    public String lastError = "";

    public ArrayList<CategoryModel> getAllCategorys(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<CategoryModel>  categorys = new ArrayList<>();
            models = connector.select("categorys", where, orderBy, param, isInt);

            for (int i = 0; i < models.size(); i++)
                categorys.add((CategoryModel) models.get(i));
            return categorys;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }

    public ArrayList<ProductModel> getAllProducts(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<ProductModel>  products = new ArrayList<>();
            models = connector.select("products", where, orderBy, param, isInt);
            for (int i = 0; i < models.size(); i++)
                products.add((ProductModel) models.get(i));
            return products;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }



    public ArrayList<SupplierModel> getAllSuppliers(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<SupplierModel>  suppliers = new ArrayList<>();
            models = connector.select("suppliers", where, orderBy, param, isInt);
            for (int i = 0; i < models.size(); i++)
                suppliers.add((SupplierModel) models.get(i));
            return suppliers;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }

    public ArrayList<PurchaseModel> getAllPurchases(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<PurchaseModel>  purchases = new ArrayList<>();
            models = connector.select("purchases", where, orderBy, param, isInt);
            for (int i = 0; i < models.size(); i++)
                purchases.add((PurchaseModel) models.get(i));
            return purchases;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }


    public ArrayList<UserModel> getAllUsers(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<UserModel>  users = new ArrayList<>();
            models = connector.select("users", where, orderBy, param, isInt);

            for (int i = 0; i < models.size(); i++)
                users.add((UserModel) models.get(i));
            return users;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }


    public ArrayList<WarehouseModel> getAllWarehouses(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<WarehouseModel>  warehouses = new ArrayList<>();
            models = connector.select("warehouses", where, orderBy, param, isInt);
            for (int i = 0; i < models.size(); i++)
                warehouses.add((WarehouseModel) models.get(i));
            return warehouses;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }

    public ArrayList<SupplierProductModel> getSupplierProducts(String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<SupplierProductModel>  supplierProducts = new ArrayList<>();
            String query = "select supplie_products.id, supplie_products.supplier_id, suppliers.name, supplie_products.product_id, products.name " +
                    "from (supplie_products join suppliers on supplie_products.supplier_id = suppliers.id) join products " +
                    "on supplie_products.product_id = products.id ";
            if(!("".equals(where.trim()))) query += " where supplie_products." + where;
            query += " group by supplie_products.id";
            models = connector.selectByQuery(query, "supplie_products", param, isInt);
            for (int i = 0; i < models.size(); i++)
                supplierProducts.add((SupplierProductModel) models.get(i));
            return supplierProducts;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }

    public ArrayList<UserCategoryModel> getUserCategorys(String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<UserCategoryModel>  userCategorys = new ArrayList<>();
            String query = "select user_categories.id, user_categories.user_id, users.login, user_categories.category_id, categorys.name " +
                    "from (user_categories join users on user_categories.user_id = users.id) join categorys " +
                    "on user_categories.category_id = categorys.id ";
            if(!("".equals(where.trim()))) query += " where user_categories." + where;
            query += " group by user_categories.id";

            models = connector.selectByQuery(query, "user_categories", param, isInt);
            for (int i = 0; i < models.size(); i++)
                userCategorys.add((UserCategoryModel) models.get(i));
            return userCategorys;
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }


    public boolean delete(String table, int id) {
        lastError = "";
        try {
            ConnectorToDB connector =  ConnectorToDB.getInstance();
            if (connector.delete(table,id)) {
                return true;
            } else {
                throw new Exception(connector.lastError);
            }
        } catch (Exception e){
            lastError = "Ошибка запроса: \"" + e.getClass() + "\"" + connector.lastError;
            return false;
        }
    }

    public boolean insert(Model model, String table) {
        lastError = "";
        try {
            FieldsTransformation transformator = new FieldsTransformation();
            String values = prepareQueryComponents(model);
            String columns = prepareQueryColumns(model);
            String[] valueByQuery = transformator.newData(model);

            if (connector.insert(table, columns, values, valueByQuery, isStrCurrent(model))) {
                return true;
            } else {
                lastError = connector.lastError;
                return false;
            }
        } catch (Exception  e){
            if(e.getMessage().length() > 150) lastError = "Ошибка запроса: \"" + e.getClass() + "\"";
            else lastError = "Ошибка запроса: \"" + e.getMessage() + "\"";
            return false;
        }
    }

    private String prepareQueryComponents(Model model){
        if(model instanceof CategoryModel)  return "?,?";
        if(model instanceof ProductModel)   return "?,?,?,?,?,?,?";
        if(model instanceof PurchaseModel)  return "?,?,?,?,?";
        if(model instanceof SupplierModel)  return "?,?,?";
        if(model instanceof UserModel)      return "?,?,?";
        if(model instanceof WarehouseModel) return "?,?";
        if(model instanceof SupplierProductModel)   return "?,?";
        if(model instanceof UserCategoryModel)      return "?,?";
        return null;
    }

    private String prepareQueryColumns(Model model){
        if(model instanceof CategoryModel)  return prepareColumns(CategoryModel.COLUMNS);
        if(model instanceof ProductModel)   return prepareColumns(ProductModel.COLUMNS);
        if(model instanceof PurchaseModel)  return prepareColumns(PurchaseModel.COLUMNS);
        if(model instanceof SupplierModel)  return prepareColumns(SupplierModel.COLUMNS);
        if(model instanceof UserModel)      return prepareColumns(UserModel.COLUMNS);
        if(model instanceof WarehouseModel) return prepareColumns(WarehouseModel.COLUMNS);
        if(model instanceof SupplierProductModel)   return prepareColumns(SupplierProductModel.COLUMNS_ABSOLUTE);
        if(model instanceof UserCategoryModel)      return prepareColumns(UserCategoryModel.COLUMNS_ABSOLUTE);
        return null;
    }

    private boolean[] isStrCurrent(Model model){
        if(model instanceof CategoryModel)  return CategoryModel.IS_STR;
        if(model instanceof ProductModel)   return ProductModel.IS_STR;
        if(model instanceof PurchaseModel)  return PurchaseModel.IS_STR;
        if(model instanceof SupplierModel)  return SupplierModel.IS_STR;
        if(model instanceof UserModel)      return UserModel.IS_STR;
        if(model instanceof WarehouseModel) return WarehouseModel.IS_STR;
        if(model instanceof SupplierProductModel)   return SupplierProductModel.IS_STR_ABSOLUTE;
        if(model instanceof UserCategoryModel)      return UserCategoryModel.IS_STR_ABSOLUTE;
        return null;
    }

    private String prepareColumns(String columns[]){
        String str = "";
        for (int i = 0; i < columns.length; i++) {
            str += columns[i];
            if(i != columns.length - 1) str +=",";
        }
        return str;
    }


    public boolean update(Model model, String table, int id) {
        lastError = "";
        try {
            FieldsTransformation transformator = new FieldsTransformation();
            String[] valueByQuery = transformator.newData(model);

            String values = prepareQueryValue(model);
            if (connector.update(table, id, values, valueByQuery, isStrCurrent(model))) {
                return true;
            } else {
                lastError = connector.lastError;
                return false;
            }
        } catch (Exception e){
            if(e.getMessage().length() > 150) lastError = "Ошибка запроса: \"" + e.getClass() + "\"";
            else lastError = "Ошибка запроса: \"" + e.getMessage() + "\"";
            return false;
        }
    }

    private String prepareQueryValue(Model model){

        if(model instanceof CategoryModel){
            return prepareCategoryValue();
        }
        if(model instanceof ProductModel){
            return prepareProductValue();
        }
        if(model instanceof PurchaseModel){
            return preparePurchaseValue();
        }
        if(model instanceof SupplierModel){
            return prepareSupplierValue();
        }
        if(model instanceof UserModel){
            return prepareUserValue();
        }
        if(model instanceof WarehouseModel){
            return prepareWarehouseValue();
        }
        if(model instanceof SupplierProductModel){
            return prepareSupplierProductValue();
        }
        if(model instanceof UserCategoryModel){
            return prepareUserCategoryValue();
        }
        return "";
    }

    private String prepareCategoryValue(){
        String str;
        str = "name = ? , parent_category_id = ?";
        return str;
    }

    private String prepareProductValue(){
        String str;
        str = "name = ?, description = ?, price = ?, count = ? ,manufacturer_name = ? " +
                ",category_id = ?, warehouse_id = ?";
        return str;
    }

    private String preparePurchaseValue(){
        String str;
        str = "date = ?, address = ?, mail = ?,product_id = ?, count = ?";
        return str;
    }

    private String prepareSupplierValue( ){
        String str;
        str = "name = ?, country = ?, date_of_cooperation = ?";
        return str;
    }

    private String prepareUserValue(){
        String str;
        str = "login = ?, password = ?, role = ?";
        return str;
    }

    private String prepareWarehouseValue(){
        String str;
        str = "name = ?, address = ?";
        return str;
    }

    private String prepareSupplierProductValue(){
        String str;
        str = "supplier_id = ?, product_id = ?";
        return str;
    }

    private String prepareUserCategoryValue(){
        String str;
        str = "user_id = ?, category_id = ?";
        return str;
    }
}
