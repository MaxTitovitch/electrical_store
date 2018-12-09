package model;

public class PurchaseModel implements Model{
    public final static String[] COLUMNS_BY_RUS = {"ИД", "Дата", "Адрес", "Почта", "ИД Продукта", "Колличество"};
    public final static String[] COLUMNS = {"date", "address", "mail", "product_id", "count"};
    public final static boolean[] IS_STR = {true, true, true, false, false};

    private int id;
    private String date;
    private String address;
    private String mail;
    private int product_id;
    private int count;

    public String[] getConstColumnsRus(){return COLUMNS_BY_RUS;}
    public String[] getConstColumns(){return COLUMNS;}
    public boolean[] getConstIsSTR(){return IS_STR;}

    public PurchaseModel(int id, String date, String address, String mail, int product_id, int count){
        this.id = id;
        this.date = date;
        this.address = address;
        this.mail = mail;
        this.product_id = product_id;
        this.count = count;

    }

    public PurchaseModel(){}

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getMail() {
        return mail;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getCount() {
        return count;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
