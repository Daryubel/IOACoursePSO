import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public interface inversionUtils {


    default void plot2DProfile(double[] x, double[] y, String title) {
        XYChart chart = QuickChart.getChart(title, "X", "Y", "y(x)", x, y);
        new SwingWrapper(chart).displayChart();
    }

    // parameter 1: radius of orbit;
    // parameter 2: depth of orbit center;
    default double[] forwarding(double[] parameterVector) {
        double CONSTANT_G = 6.67259e-1, PI = Math.PI;
        double[] deltaG = new double[50], meshX = new double[50];
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0; }
        for (int i = 0; i < meshX.length; i++) {
            deltaG[i] = (CONSTANT_G*parameterVector[1]*4*PI*Math.pow(parameterVector[0], 3)/3)
                    /Math.pow((Math.pow(meshX[i],2)+Math.pow(parameterVector[1], 2)), 1.5); }
        return deltaG;
    }

    // replace double[] modelParameter with double[] modelModel if not using a known model;
    // modelModel[] in this case should be representing deltaG gathered along the profile;
    default double getMisfit(double[] estimatedParameter, double[] modelParameter) {
        double misfit = 10000;
        double[] modelModel = forwarding(modelParameter);
        double[] estimatedModel = forwarding(estimatedParameter);

        for (int i = 0; i < 50; i++) {
            misfit = misfit + Math.sqrt(Math.pow(modelModel[i] - estimatedModel[i],2)); }
            return misfit;
    }


}
