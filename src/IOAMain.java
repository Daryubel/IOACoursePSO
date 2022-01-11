import java.util.Arrays;

public class IOAMain implements arrayUtils, inversionUtils{


    public void run() {

        // initialize profile with meshX, sampling 50 points;
        double[] meshX = new double[50];
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0;
        }

        // initialize particle swarm;
        PSOSwarm swarm = new PSOSwarm();
        swarm.setPSOSwarmSize(100);
        swarm.setSwarmInterval(new double[]{2,3}, new double[]{4,6}, new double[]{2,2});
        swarm.initializeSwarm();

        // initial parameter forwarding output;
        plot2DProfile(meshX, forwarding(swarm.globalBest.particleVector), "initial");

        // print swarm composition;
        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(swarm.swarmBody.get(i).particleVector));
        }

        // iteration condition = misfit < 10^-3 or iteration time > 10000
        int iteration = 0;
        while ((swarm.getLeastMisfit() > 1e-3) && (iteration<1e4)) {
            swarm.upgradeSwarm();
            swarm.getGlobalBest();
            iteration = iteration + 1;
        }

        // draw the optimized parameter and the accurate model;
        // here the model is given by {3.0,5.0};
        plot2DProfile(meshX, forwarding(swarm.globalBest.particleVector), "optimized");
        plot2DProfile(meshX, forwarding(new double[]{3.0,5.0}), "model");
    }



    public static void main(String[] args) {
        IOAMain psoSolve = new IOAMain();
        psoSolve.run();
    }
}
