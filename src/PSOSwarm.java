import java.util.ArrayList;

public class PSOSwarm implements arrayUtils,inversionUtils{

    public double[] modelParameter = {15,20,44,17,10};
    ArrayList<PSOParticle> swarmBody = new ArrayList<>(100);
    int LocSize;
    PSOParticle globalBest;

    public void setPSOSwarmSize(int givenSize) {
        LocSize = givenSize;
    }

    public void setSwarmInterval(double[] floor, double[] ceil, double[] vCeil) {
        for (PSOParticle psoParticle : swarmBody) {
            psoParticle.setParticleInterval(floor, ceil, vCeil);
        }
    }

    // initialize particles;
    // set the initial global best particle as the first one;
    public void initializeSwarm() {
        for (int i = 0; i < LocSize; i++) {
            PSOParticle tempParticle = new PSOParticle();
            tempParticle.initializeParticle();
            swarmBody.add(tempParticle); }
        globalBest = swarmBody.get(0);
    }

    public int getSize() {
        return LocSize;
    }

    public void getGlobalBest() {
        double leastMisfit = 10000, tempMisfit;
        int index = 0;
        for (int i = 0; i < swarmBody.size(); i++) {
            tempMisfit = swarmBody.get(i).getParticleMisfit();
            if (tempMisfit < leastMisfit) {
                leastMisfit = tempMisfit;
                index = i;} }
        globalBest = swarmBody.get(index);
    }

    public double getLeastMisfit() {
        return getMisfit(globalBest.particleVector, modelParameter);
    }

    public void upgradeSwarm() {
        for (PSOParticle psoParticle : swarmBody) {
            psoParticle.upgradeParticleVelocity(globalBest);
            psoParticle.upgradeParticleVector();
        }
    }

}
