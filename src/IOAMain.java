import java.util.ArrayList;
import java.util.Arrays;

public class IOAMain implements arrayUtils, inversionUtils{

    public double[] modelParameter = {15,20,44,17,10};

    public void run() {

        // initialize profile with meshX, sampling 50 points;
        double[] meshX = new double[50];
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0;
        }
        ArrayList<Double> misfit = new ArrayList();
        double[] iterate = null;

        // initialize particle swarm;
        PSOSwarm swarm = new PSOSwarm();
        swarm.setPSOSwarmSize(100);
        swarm.setSwarmInterval(new double[]{0,0,0,0,0}, new double[]{100,100,100,100,100}, new double[]{10,10,10,10,10});
        swarm.initializeSwarm();
        plot2DProfile(meshX, forwardingRect(swarm.globalBest.particleVector), "optimized");

        // initial parameter forwarding output;
//        plot2DProfile(meshX, forwardingOrbit(swarm.globalBest.particleVector), "initial");

        // print swarm composition;
        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(swarm.swarmBody.get(i).particleVector));
        }

        // iteration condition = misfit < 10^-3 or iteration time > 10000
        int iteration = 0;
        while ((swarm.getLeastMisfit() > 1e-3) && (iteration<1e3)) {
            swarm.upgradeSwarm();
            swarm.getGlobalBest();
            iteration = iteration + 1;
            misfit.add(getMisfit(swarm.globalBest.particleVector,modelParameter));
        }
        iterate = new double[iteration];
        for (int i = 0; i < iteration; i++) {
            iterate[i] = i;
        }

        // draw the optimized parameter and the accurate model;
        // here the model is given by {3.0,5.0};
//        plot2DProfile(meshX, forwardingOrbit(swarm.globalBest.particleVector), "optimized");
//        plot2DProfile(meshX, forwardingOrbit(new double[]{3.0,5.0}), "model");
        plot2DProfile(meshX, forwardingRect(swarm.globalBest.particleVector), "optimized");
        plot2DProfile(meshX, forwardingRect(modelParameter), "model");
        plot2DProfile(iterate,misfit,"misfit curve");
        System.out.print("iteration finished in ");
        System.out.print(iteration);
        System.out.println("iteration epochs;");
        System.out.println("estimated model parameter: ");
        System.out.println(Arrays.toString(swarm.globalBest.particleVector));
        System.out.println("model misfit: ");
        System.out.println(misfit);
    }



    public static void main(String[] args) {
        IOAMain psoSolve = new IOAMain();
        psoSolve.run();
    }
}
