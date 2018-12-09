package model;

public interface Model {
    public void setId(int id);
    public int getId();
    public String[] getConstColumnsRus();
    public String[] getConstColumns();
    public boolean[] getConstIsSTR();
}
