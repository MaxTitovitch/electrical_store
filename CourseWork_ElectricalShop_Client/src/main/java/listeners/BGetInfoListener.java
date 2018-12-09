package listeners;

import dialog_construction.OneTableDialog;
import dialogs.CDialogNotFrame;
import dialog_construction.ChartDialog;
import model.Model;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BGetInfoListener implements ActionListener {
    private OneTableDialog parental;
    private Class<? extends Model> currentClass;
    private CDialogNotFrame infoDialog;

    public BGetInfoListener(Class<? extends Model> currentClass, OneTableDialog parental){
        this.currentClass = currentClass;
        this.parental = parental;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        prepareData();
        infoDialog.setVisible(true);
    }

    private void prepareData(){

        infoDialog = new CDialogNotFrame("Аналитическая информация", 800, 600, true);

        ChartDialog dataset = new ChartDialog(currentClass);

        JFreeChart chart = dataset.createChart(dataset.createDataset(parental.updateData()));
        chart.setPadding(new RectangleInsets(10, 10, 10, 10));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(100, 100));

        panel.setVisible(true);
        infoDialog.setContentPane(panel);
    }


}
