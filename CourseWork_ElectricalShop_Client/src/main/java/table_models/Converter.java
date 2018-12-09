package table_models;

import model.*;

import java.util.ArrayList;

public class Converter {
    public static ArrayList<CategoryModel> modelToCategory(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<CategoryModel> categorys = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            categorys.add((CategoryModel) models.get(i));
        }

        return categorys;
    }

    public static ArrayList<ProductModel> modelToProduct(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<ProductModel> products = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            products.add((ProductModel) models.get(i));
        }

        return products;
    }

    public static ArrayList<PurchaseModel> modelToPurchase(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<PurchaseModel> purchases = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            purchases.add((PurchaseModel) models.get(i));
        }

        return purchases;
    }

    public static ArrayList<SupplierModel> modelToSupplier(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<SupplierModel> purchases = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            purchases.add((SupplierModel) models.get(i));
        }

        return purchases;
    }

    public static ArrayList<UserModel> modelToUser(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<UserModel> users = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            users.add((UserModel) models.get(i));
        }

        return users;
    }

    public static ArrayList<WarehouseModel> modelToWarehouse(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<WarehouseModel> warehouses = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            warehouses.add((WarehouseModel) models.get(i));
        }

        return warehouses;
    }

    public static ArrayList<SupplierProductModel> modelToSupplierProduct(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<SupplierProductModel> warehouses = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            warehouses.add((SupplierProductModel) models.get(i));
        }

        return warehouses;
    }

    public static ArrayList<UserCategoryModel> modelToUserCategory(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<UserCategoryModel> warehouses = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            warehouses.add((UserCategoryModel) models.get(i));
        }

        return warehouses;
    }
}
