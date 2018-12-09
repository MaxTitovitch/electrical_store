package model;

public class ProductModel implements Model {
    public final static String[] COLUMNS_BY_RUS = {"ИД", "Название", "Описание", "Цена", "Колличество",
            "Производитель", "Категория", "Склад"};
    public final static String[] COLUMNS = {"name", "description", "price", "count", "manufacturer_name",
            "category_id", "warehouse_id"};
    public final static boolean[] IS_STR = {true, true, false, false, true, false, false};

    private int id;
    private String name;
    private String description;
    private double price;
    private int count;
    private String manufacturer_name;
    private int category_id;
    private int warehouse_id;

    public String[] getConstColumnsRus(){return COLUMNS_BY_RUS;}
    public String[] getConstColumns(){return COLUMNS;}
    public boolean[] getConstIsSTR(){return IS_STR;}

    public ProductModel(int id, String name, String description, double pricce, int count, String manufacturer_name,
                        int category_id, int warehouse_id){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = pricce;
        this.count = count;
        this.manufacturer_name = manufacturer_name;
        this.category_id = category_id;
        this.warehouse_id = warehouse_id;
    }

    public ProductModel(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }
}
