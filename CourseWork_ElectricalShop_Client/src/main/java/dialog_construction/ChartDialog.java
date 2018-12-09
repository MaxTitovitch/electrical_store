package dialog_construction;

import model.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.util.ArrayList;

public class ChartDialog {
    private Class<? extends Model> currentClass;
    private String chartName;

    public ChartDialog(Class<? extends Model> currentClass){
        this.currentClass = currentClass;
        this.chartName = getChartName();
    }

    private String getChartName(){
        switch (currentClass.getName()) {
            default:
            case "model.CategoryModel":     return "Процент главных категорий";
            case "model.ProductModel":      return "Общая СТОИМОСТЬ всех Товаров";
            case "model.PurchaseModel":     return "Процент покупок по годам";
            case "model.SupplierModel":     return "Статистика стран производителей";
            case "model.UserModel":         return "Статистика ролей пользователей";
        }
    }

    public PieDataset createDataset(ArrayList<Model> models) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (int i = 0; i < models.size(); i++) {
            switch (currentClass.getName()) {
                default:
                case "model.CategoryModel":     addOneCategory(dataset, models.get(i)); break;
                case "model.ProductModel":      addOneProduct(dataset, models.get(i), calculateTotalCost(models)); break;
                case "model.PurchaseModel":     addOnePurchase(dataset, models.get(i)); break;
                case "model.SupplierModel":     addOneSupplier(dataset, models.get(i)); break;
                case "model.UserModel":         addOneUser(dataset, models.get(i)); break;
            }
        }
        return dataset;
    }

    private double calculateTotalCost (ArrayList<Model> models){
        double total_cost = 0;
        for (int i = 0; i < models.size(); i++) {
            total_cost += ((ProductModel)models.get(i)).getCount()*((ProductModel)models.get(i)).getPrice();
        }
        return total_cost;
    }

    private void addOneCategory(DefaultPieDataset dataset, Model model){
        CategoryModel category = (CategoryModel) model;
        try {
            if(category.getParent_category_id() == 0)
                dataset.setValue("Секция", dataset.getValue("Секция").intValue() + 1);
            else
                dataset.setValue("Подкотегория", dataset.getValue("Подкотегория").intValue() + 1);
        } catch (Exception e){
            if(category.getParent_category_id() == 0)
                dataset.setValue("Секция", 1);
            else
                dataset.setValue("Подкотегория", 1);
        }
    }

    private void addOneProduct(DefaultPieDataset dataset, Model model, double total_cost){
        ProductModel product = (ProductModel) model;
        double cost = product.getPrice()*product.getCount();
        String poursent =  String.format("%.2f", (cost/total_cost*100));
        String title = "\"" + product.getManufacturer_name() + " " + product.getName() + "\" - " + cost + "(" + poursent + "%)";
        dataset.setValue(title, product.getCount()*product.getPrice());
    }

    private void addOnePurchase(DefaultPieDataset dataset, Model model){
        char[] yearChar = new char[4];
        ((PurchaseModel) model).getDate().getChars(0, 4, yearChar, 0);
        String year = String.valueOf(yearChar);
        try {
            dataset.getValue(year);
            dataset.setValue(year, dataset.getValue(year).intValue() + 1);
        } catch (Exception e){
            dataset.setValue(year, 1);
        }
    }

    private void addOneSupplier(DefaultPieDataset dataset, Model model){
        SupplierModel supplier = (SupplierModel) model;
        try {
            dataset.getValue(supplier.getCountry());
            dataset.setValue(supplier.getCountry(), dataset.getValue(supplier.getCountry()).intValue() + 1);
        } catch (Exception e){
            dataset.setValue(supplier.getCountry(), 1);
        }
    }

    private void addOneUser(DefaultPieDataset dataset, Model model){
        UserModel user = (UserModel) model;
        String role = user.getRole() == 2 ? "Пользователь" : (user.getRole() == 1 ? "Админ": "Главный Админ");
        try {
            dataset.getValue(role);
            dataset.setValue(role, dataset.getValue(role).intValue() + 1);
        } catch (Exception e){
            dataset.setValue(role, 1);
        }
    }

    public JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(chartName, dataset,false,true, false);

        chart.setBackgroundPaint(new GradientPaint(
                new Point(0, 0),
                new Color(20, 20, 20),
                new Point(400, 200),
                Color.DARK_GRAY)
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.10);
        plot.setOutlineVisible(false);

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(3.0f));

        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }

}