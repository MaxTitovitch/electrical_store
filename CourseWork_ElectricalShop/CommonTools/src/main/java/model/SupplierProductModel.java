package model;

public class SupplierProductModel implements Model {
    public final static String[] COLUMNS_BY_RUS = {"ИД", "ИД Поставщик", "Поставщик", "ИД Продукта", "Продукт"};
    public final static String[] COLUMNS = {"supplier_id", "supplier_name", "product_id", "product_name" };
    public final static boolean[] IS_STR = {false, true, false, true};
    public final static String[] COLUMNS_ABSOLUTE = {"supplier_id", "product_id" };
    public final static boolean[] IS_STR_ABSOLUTE = {false, false};

    private int id;
    private int supplier_id;
    private String supplier_name;
    private int product_id;
    private String product_name;

    public String[] getConstColumnsRus(){return COLUMNS_BY_RUS;}
    public String[] getConstColumns(){return COLUMNS;}
    public boolean[] getConstIsSTR(){return IS_STR;}

    public SupplierProductModel(int id, int supplier_id, String supplier_name, int product_id, String product_name){
        this.id = id;
        this.supplier_id = supplier_id;
        this.product_name = product_name;
        this.product_id = product_id;
        this.supplier_name = supplier_name;
    }

    public SupplierProductModel(){}

    public int getId(){
        return id;
    }


    public int getSupplier_id() {
        return supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
