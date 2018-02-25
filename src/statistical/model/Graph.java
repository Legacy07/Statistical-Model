package statistical.model;

import java.awt.Color;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JPanel {

    public static Main_GUI main;
    public static double stripsValue = 1000;
    public static double mean = Double.parseDouble(Main_GUI.getMeanText());
    public static double standardD = Double.parseDouble(Main_GUI.getStandardDText());

    public Graph() {
//        JPanel graphPanel = Main_GUI.graphPanel();
    }

    public static XYDataset DataSet() {
        //Values on X-axis to draw the line minumum to maximum 
        double xMinumum = mean - (4.1 * standardD);
        double xMaximum = mean + (4.1 * standardD);
        //the graph will change depending on the mean and standard Deviation.
        XYSeriesCollection dataset = new XYSeriesCollection();
        Function2D function = new NormalDistributionFunction2D(mean, standardD);
        // drawing the line across the axis and giving a curve.
        XYSeries xYSeries = DatasetUtilities.sampleFunction2DToSeries(function, xMinumum, xMaximum, 120, "");
        dataset.addSeries(xYSeries);

        return dataset;
    }

    // Creating a line chart using the data from dataset.
    public static JFreeChart Graph(XYDataset dataset) {

        //layout of the chart 
        JFreeChart chart = ChartFactory.createXYLineChart("", "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        //x axis gaps in between, lower margin is right, upper margin is left handside of zero baseline
        xAxis.setLowerMargin(0.1);
        xAxis.setUpperMargin(0.1);

        //Shading in graph
        XYDifferenceRenderer renderer = new XYDifferenceRenderer();
        Color blankColour = new Color(0, 0, 0, 0);
        //hides the outline of the area
        renderer.setSeriesPaint(1, blankColour);
        //hide the overlapped area thats not necessary
        renderer.setNegativePaint(blankColour);
        //the clour of the shade
        renderer.setPositivePaint(Color.blue);
        plot.setRenderer(renderer);

        //add the dataset series from Main gui class so it paints 
        ((XYSeriesCollection) dataset).addSeries(Main_GUI.belowG);
        ((XYSeriesCollection) dataset).addSeries(Main_GUI.aboveG);
        ((XYSeriesCollection) dataset).addSeries(Main_GUI.betweenG);
        ((XYSeriesCollection) dataset).addSeries(Main_GUI.outsideG);
        //repainting the graph 
        chart.getXYPlot().setDataset(chart.getXYPlot().getDataset());
        return chart;
    }

}
