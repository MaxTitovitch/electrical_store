package data_base;

import model.*;
import validation.Validator;

public class FieldsTransformation {


    public String[] newData(Model model){
        switch (model.getClass().getName()){
            case "model.CategoryModel":               return newDataCategory(model);
            case "model.ProductModel":                return newDataProduct(model);
            case "model.PurchaseModel":               return newDataPurchase(model);
            case "model.SupplierModel":               return newDataSupplier(model);
            case "model.UserModel":                   return newDataUser(model);
            default: case "model.WarehouseModel":     return newDataWarehouse(model);
            case "model.SupplierProductModel":        return newDataSupplierProduct(model);
            case "model.UserCategoryModel":           return newDataUserCategory(model);
        }
    }

    private String[] newDataCategory(Model model){
        CategoryModel category = (CategoryModel)model;
        String categor;
        categor = category.getParent_category_id() != 0 ? String.valueOf(category.getParent_category_id()) : "NULL";
        String answer[] = new String[]{
                category.getName(),
                categor
        };
        return answer;
    }

    private String[] newDataProduct(Model model){
        ProductModel product = (ProductModel)model;
        String answer[] = new String[]{
                product.getName(),
                product.getDescription(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getCount()),
                product.getManufacturer_name(),
                String.valueOf(product.getCategory_id()),
                String.valueOf(product.getWarehouse_id())
        };
        return answer;
    }

    private String[] newDataPurchase(Model model){
        PurchaseModel purchase = (PurchaseModel)model;
        String answer[] = new String[]{
                purchase.getDate(),
                purchase.getAddress(),
                purchase.getMail(),
                String.valueOf(purchase.getProduct_id()),
                String.valueOf(purchase.getCount())
        };
        return answer;
    }

    private String[] newDataSupplier(Model model){
        SupplierModel supplier = (SupplierModel)model;
        String answer[] = new String[]{
                supplier.getName(),
                supplier.getCountry(),
                supplier.getDate_of_cooperation()
        };
        return answer;
    }

    private String[] newDataUser(Model model){
        UserModel user = (UserModel)model;
        String answer[] = new String[]{
                user.getLogin(),
                user.getPassword(),
                String.valueOf( user.getRole())
        };
        return answer;
    }

    private String[] newDataWarehouse(Model model){
        WarehouseModel warehouse = (WarehouseModel)model;
        String answer[] = new String[]{
                warehouse.getName(),
                warehouse.getAddress()
        };
        return answer;
    }

    private String[] newDataSupplierProduct(Model model){
        SupplierProductModel supplierProduct = (SupplierProductModel)model;
        String answer[] = new String[]{
                String.valueOf(supplierProduct.getSupplier_id()),
                String.valueOf(supplierProduct.getProduct_id())
        };
        return answer;
    }

    private String[] newDataUserCategory(Model model){
        UserCategoryModel userCategory = (UserCategoryModel)model;
        String answer[] = new String[]{
                String.valueOf(userCategory.getuser_id()),
                String.valueOf(userCategory.getcategory_id())
        };
        return answer;
    }

}
