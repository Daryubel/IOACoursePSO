import java.util.Arrays;

public class IOAMain implements arrayUtils, inversionUtils{

    public double[] modelParameter = {40,20,44,20,30};

    public void run() {

        // initialize profile with meshX, sampling 50 points;
        double[] meshX = new double[50];
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0;
        }

        // initialize particle swarm;
        PSOSwarm swarm = new PSOSwarm();
        swarm.setPSOSwarmSize(100);
        swarm.setSwarmInterval(new double[]{0,0,0,0,0}, new double[]{100,100,100,100,100}, new double[]{1,1,1,1,1});
        swarm.initializeSwarm();
        plot2DProfile(meshX, forwardingRect(swarm.globalBest.particleVector), "initial");

        // initial parameter forwarding output;
//        plot2DProfile(meshX, forwardingOrbit(swarm.globalBest.particleVector), "initial");

        // print swarm composition;
        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(swarm.swarmBody.get(i).particleVector));
        }

        // iteration condition = misfit < 10^-3 or iteration time > 10000
        int iteration = 0;
        while ((swarm.getLeastMisfit() > 1e-3) && (iteration<1e2)) {
            swarm.upgradeSwarm();
            swarm.getGlobalBest();
            iteration = iteration + 1;
        }

        // draw the optimized parameter and the accurate model;
        // here the model is given by {3.0,5.0};
//        plot2DProfile(meshX, forwardingOrbit(swarm.globalBest.particleVector), "optimized");
//        plot2DProfile(meshX, forwardingOrbit(new double[]{3.0,5.0}), "model");
        plot2DProfile(meshX, forwardingRect(swarm.globalBest.particleVector), "optimized");
        plot2DProfile(meshX, forwardingRect(modelParameter), "model");
    }



    public static void main(String[] args) {
        IOAMain psoSolve = new IOAMain();
        psoSolve.run();
    }
}
