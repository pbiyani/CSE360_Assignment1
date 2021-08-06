import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class plotGraph extends JFrame
{
    private static final long serialVersionUID = 6294689542092367723L;

    JTable table;

    public plotGraph(String title, JTable attendanceTable, List<String> dates)
    {
        super(title);
        this.table = attendanceTable;

        XYDataset dataset = createDataset(dates);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "",
                "% of students", "Count", dataset);
        XYPlot plot = (XYPlot)chart.getPlot();
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(List<String> dates)
    {
        HashMap<Double, Double> map = new HashMap<>();

        XYSeriesCollection dataset = new XYSeriesCollection();

        for(int k=0; k<dates.size(); k++)
        {
            XYSeries series = new XYSeries(dates.get(k));

            for(int i=0; i<table.getRowCount(); i++)
            {
                double currVal = (Double.parseDouble(""+table.getModel().getValueAt(i,5+k+1)));
                currVal = (currVal*100)/75;
                map.put(currVal, map.getOrDefault(currVal,0.0)+1);
            }

            for(int i=0; i<table.getRowCount(); i++)
            {
                double currVal = (Double.parseDouble(""+table.getModel().getValueAt(i,5+k+1)));
                currVal = (currVal*100)/75;

                if(map.containsKey(currVal))
                {
                    series.add(currVal, map.get(currVal));
                    map.remove(currVal);
                }

            }

            dataset.addSeries(series);
        }
        return dataset;
    }
}
