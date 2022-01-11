public interface arrayUtils {


    // add each element in several arrays and return the added array;
    default double[] arraySum(double[] a, double[] b) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i]; }
        return c; }
    // reloaded with different parameter count;
    default double[] arraySum(double[] a, double[] b, double[] c) {
        double[] d = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            d[i] = a[i] + b[i] + c[i]; }
        return d;
    }


    default double[] arraySub(double[] subtracted, double[] subtracter) {
        double[] c = new double[subtracted.length];
        for (int i = 0; i < subtracted.length; i++) {
            c[i] = subtracted[i] - subtracter[i]; }
        return c;
    }


    // coefficient A times matrix/vector B
    default double[] arrayMtply(double a, double[] b) {
        double[] c = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = a * b[i]; }
        return c; }
    // reloaded with different parameter count;
    default double[] arrayMtply(double a, double b, double[] arrayA) {
        double[] c = new double[arrayA.length];
        for (int i = 0; i < arrayA.length; i++) {
            c[i] = a * arrayA[i] * b; }
        return c;
    }


    // decide if all the element of the vector is within the given interval;
    default boolean isWithinInterval(double[] a, double[] floor, double[] ceil) {
        boolean isInInterval = false;
        for (int i = 0; i < a.length; i++) {
            isInInterval = (a[i] > floor[i]) && (a[i] < ceil[i]); }
        return isInInterval; }
    // reloaded with different parameter count;
    default boolean isWithinInterval(double[] a, double[] ceil) {
        boolean isInInterval = false;
        for (int i = 0; i < a.length; i++) {
            isInInterval = a[i] < ceil[i]; }
        return isInInterval;
    }
}
