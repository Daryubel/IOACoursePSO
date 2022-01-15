import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public interface inversionUtils {


    default void plot2DProfile(double[] x, double[] y, String title) {
        XYChart chart = QuickChart.getChart(title, "X", "Y", "V_{zz}", x, y);
        new SwingWrapper(chart).displayChart(); }
    default void plot2DProfile(double[] x, ArrayList<Double> yy, String title) {
        double[] y = new double[yy.size()];
        for (int i = 0; i < yy.size(); i++) {
            y[i] = yy.get(i); }
        XYChart chart = QuickChart.getChart(title, "X", "Y", "V_{zz}", x, y);
        new SwingWrapper(chart).displayChart();
    }

    // parameter 1: radius of orbit;
    // parameter 2: depth of orbit center;
    default double[] forwardingOrbit(double[] parameterVector) {
        double CONSTANT_G = 6.67259e-1, PI = Math.PI;
        double[] deltaG = new double[50], meshX = new double[50];
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0; }
        for (int i = 0; i < meshX.length; i++) {
            deltaG[i] = (CONSTANT_G*parameterVector[1]*4*PI*Math.pow(parameterVector[0], 3)/3)
                    /Math.pow((Math.pow(meshX[i],2)+Math.pow(parameterVector[1], 2)), 1.5); }
        return deltaG;
    }

    // parameter 1: depth of the upper surface of the 1st rectangular;
    // parameter 2: depth of the upper surface of the 2nd rectangular;
    // parameter 3: depth of the upper surface of the 3rd rectangular;
    // parameter 4: depth of the upper surface of the 4th rectangular;
    // parameter 5: depth of the upper surface of the 5th rectangular;
    default double[] forwardingRect(double[] parameterVector) {
        double CONSTANT_G = 6.67259e-1, PI = Math.PI;
        double[][] deltaG = new double[5][50];
        double[] meshX = new double[50], deltaGGG = new double[50];
        int recX = -4;
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0; }
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < meshX.length; i++) {
                deltaG[j][i] = 2*CONSTANT_G*Math.atan((2*1*parameterVector[j]) / (Math.pow(meshX[i]+recX, 2)+Math.pow(parameterVector[j],2)-1)); }
            recX += 2; }
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < meshX.length; i++){
                deltaGGG[i] = deltaG[j][i] + deltaG[j+1][i];
            }
        }
        return deltaGGG;
    }

    // replace double[] modelParameter with double[] modelModel if not using a known model;
    // modelModel[] in this case should be representing deltaG gathered along the profile;
    default double getMisfit(double[] estimatedParameter, double[] modelParameter) {
        double misfit = 10000;
        double[] modelModel = forwardingOrbit(modelParameter);
        double[] estimatedModel = forwardingOrbit(estimatedParameter);

        for (int i = 0; i < 50; i++) {
            misfit = misfit + Math.sqrt(Math.pow(modelModel[i] - estimatedModel[i],2)); }
            return misfit;
    }


}
