package connection;

import data_base.QueryerToDB;
import model.*;
import serialization.Serrializator;
import validation.Validator;

import java.util.ArrayList;

public class OrderPerformer {
    private static QueryerToDB queryer = new QueryerToDB();

    public static String doAuthorization(String data){
        String answer;
        UserModel user = (UserModel) Serrializator.JSONToObject(data, UserModel.class);
        ArrayList<UserModel> users = queryer.getAllUsers("", "`login` = ?", user.getLogin(), "false");
        return (answer = userSeaech(users, user)) != "" ? answer : "";

    }

    private static String userSeaech(ArrayList<UserModel> users, UserModel user){
        for (int i = 0; i < users.size(); i++) {
            UserModel iUser = users.get(i);
            if(user.getLogin().equals(iUser.getLogin()) && user.getPassword().equals(iUser.getPassword())){
                return Serrializator.ObjectToJSON(iUser);
            }
        }
        return "";
    }


    public static String getAll(String orderByAndWhere, Class<? extends Model> currentClass){
        String params[] = new String[]{"", ""};
        if(!(orderByAndWhere.equals("")))
            params = orderByAndWhere.split("@@@");

        if(params.length <= 2)
            params = new String[]{params[0], params[1], "", ""};

        return getAnswerFromBD(params,currentClass);
    }

    private static String getAnswerFromBD(String params[], Class<? extends Model> currentClass){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return Serrializator.ObjectsToJSON(queryer.getAllCategorys(params[0], params[1], params[2], params[3]));
            case "model.ProductModel":      return Serrializator.ObjectsToJSON(queryer.getAllProducts(params[0], params[1], params[2], params[3]));
            case "model.PurchaseModel":     return Serrializator.ObjectsToJSON(queryer.getAllPurchases(params[0], params[1] , params[2], params[3]));
            case "model.SupplierModel":     return Serrializator.ObjectsToJSON(queryer.getAllSuppliers(params[0], params[1] , params[2], params[3]));
            case "model.UserModel":         return Serrializator.ObjectsToJSON(queryer.getAllUsers(params[0], params[1], params[2], params[3]));
            case "model.WarehouseModel":    return Serrializator.ObjectsToJSON(queryer.getAllWarehouses(params[0], params[1], params[2], params[3]));
            case "model.SupplierProductModel":      return Serrializator.ObjectsToJSON(queryer.getSupplierProducts(params[1], params[2], params[3]));
            case "model.UserCategoryModel":         return Serrializator.ObjectsToJSON(queryer.getUserCategorys(params[1], params[2], params[3]));
        }
    }


    public static String delete(String data){
        String params[] = data.split("@@@");
        if(!Validator.isInt(params[1])) return "";
        boolean isOk = queryer.delete(params[0], Integer.valueOf(params[1]));
        return isOk ? "@ok@" : queryer.lastError;
    }

    public static String insert(String data, String table, Class<? extends Model> modelType){
        boolean isOk = queryer.insert(Serrializator.JSONToObject(data, modelType), table);
        return isOk ? "@ok@" : queryer.lastError;
    }

    public static String update(String data, String table, Class<? extends Model> modelType){
        String params[] = data.split("@@@");
        System.out.println(data);
        if(!Validator.isInt(params[1])) return "";
        boolean isOk = queryer.update(Serrializator.JSONToObject(params[0], modelType), table, Integer.valueOf(params[1]));// ? "ok" : "";
        return isOk ? "@ok@" : queryer.lastError;
    }

}
