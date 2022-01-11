import java.util.Arrays;

public class IOAMain implements arrayUtils, inversionUtils{


    public void run() {
        double[] meshX = new double[50];
        for (int i = 0; i < meshX.length; i++) {
            meshX[i] = i - 50.0/2.0;
        }

        PSOSwarm swarm = new PSOSwarm();
        swarm.setPSOSwarmSize(100);
        swarm.setSwarmInterval(new double[]{2,3}, new double[]{4,6}, new double[]{2,2});
        swarm.initializeSwarm();
        plot2DProfile(meshX, forwarding(swarm.globalBest.particleVector), "initial");
        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(swarm.swarmBody.get(i).particleVector));
        }

        int iteration = 0;
        while ((swarm.getLeastMisfit() > 1e-3) && (iteration<1e4)) {
            swarm.upgradeSwarm();
            swarm.getGlobalBest();
            iteration = iteration + 1;
        }

        plot2DProfile(meshX, forwarding(swarm.globalBest.particleVector), "optimized");
        plot2DProfile(meshX, forwarding(new double[]{3.0,5.0}), "model");
    }






    public static void main(String[] args) {
//        System.out.println("shit");
        IOAMain psoSolve = new IOAMain();
        psoSolve.run();
    }
}
