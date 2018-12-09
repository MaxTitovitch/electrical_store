package connection;


import com.google.gson.reflect.TypeToken;
import model.*;
import serialization.Serrializator;

import java.util.ArrayList;


public class DataPreparer {
    private static ClientSocketConnector connector;

    public static String startConnection(String host, int port){
        connector = new ClientSocketConnector(host, port);
        connector.run();
        return connector.isHaveError ? connector.errorMessage : "";
    }

    public static UserModel checkAuthorization(UserModel userModel) {
        String userJSON = Serrializator.ObjectToJSON(userModel), command = "authorization", answer;
        answer = connector.writeCommand(command, userJSON);
        return  !("".equals(answer)) ? (UserModel)Serrializator.JSONToObject(answer, UserModel.class) : null;
    }

    public static ArrayList<Model> getCategorys(String orderBy, String where){
        String answer = getModelsFromServer("getCategorys", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<CategoryModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getProducts(String orderBy, String where){
        String answer = getModelsFromServer("getProducts", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<ProductModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getPurchases(String orderBy, String where){
        String answer = getModelsFromServer("getPurchases", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<PurchaseModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getSuppliers(String orderBy, String where){
        String answer = getModelsFromServer("getSuppliers", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<SupplierModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getUsers(String orderBy, String where){
        String answer = getModelsFromServer("getUsers", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<UserModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getWarehouses(String orderBy, String where){
        String answer = getModelsFromServer("getWarehouses", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<WarehouseModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getSuppliersProducts(String orderBy, String where){
        String answer = getModelsFromServer("getSuppliersProducts", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<SupplierProductModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getUserCategorys(String orderBy, String where){
        String answer = getModelsFromServer("getUserCategorys", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<UserCategoryModel>>(){}.getType()) : null;
    }

    private static String getModelsFromServer(String command, String orderBy, String where) {
        String data = "";
        if(!orderBy.trim().equals("") || !where.trim().equals("")) {
            data = orderBy + " @@@ " + where;
        }
        return connector.writeCommand(command, data);
    }


    public static String insert(Model model, String table){
        String command = "insert_", data;
        data = Serrializator.ObjectToJSON(model);
        return connector.writeCommand(command + table, data);
    }

    public static String update(Model model, String table, int id){
        String command = "update_", data;
        data = Serrializator.ObjectToJSON(model) + "@@@" + id;
        return connector.writeCommand(command + table, data);
    }

    public static String delete(int id, String table){
        String command = "delete", data;
        data = table + "@@@" + id;
        return connector.writeCommand(command, data);
    }

    public static String exit(String message){
        return connector.closeSocket(message);
    }


}
