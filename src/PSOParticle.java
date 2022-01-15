public class PSOParticle implements arrayUtils, inversionUtils{

    public double[] modelParameter = {40,20,44,20,30};
    int vecDim = 5;
    double c1=0.5, c2=0.5, r1=1.5, r2=1.5;
    double leastMisfit = 10000;
    double[] floor = {0,0,0,0,0}, ceil = {100,100,100,100,100}, vCeil = {100,100,100,100,100};
    double[] particleVector = new double[vecDim];
    double[] particleVelocity = new double[vecDim];
    double[] personalBestVector;
    double[] inertiaTerm;
    double[] socialTerm;
    double[] personalTerm;


    public void setParticleVector(double[] givenVec) {
        particleVector = givenVec;
        personalBestVector = particleVector;
    }

    public void setParticleVelocity(double[] givenVelocity) {
        particleVelocity = givenVelocity;
    }

    public void setParticleInterval(double[] givenFloor, double[] givenCeil, double[] givenVelocityCeil) {
        floor = givenFloor;
        ceil = givenCeil;
        vCeil = givenVelocityCeil;
    }

    public void initializeParticle() {
        double[] tempVec = new double[vecDim];
        double[] tempVelocity = new double[vecDim];

        // randomize the initial vector and velocity until
        // they are within the set interval;
        do {
            for (int i = 0; i < vecDim; i++) {
                tempVec[i] = Math.random() * 40;
                tempVelocity[i] = Math.random() * 40;
            }
        } while (!isWithinInterval(tempVec, floor, ceil) || !isWithinInterval(tempVelocity, vCeil));
        setParticleVector(tempVec);
        setParticleVelocity(tempVelocity);
    }

    public double getParticleMisfit() {
        return getMisfit(particleVector, modelParameter);
    }

    public void upgradeParticleVelocity(PSOParticle globalBest) {
        inertiaTerm = particleVelocity;
        socialTerm = arrayMtply(c1,r1,arraySub(globalBest.particleVector,particleVector));
        personalTerm = arrayMtply(c2,r2,arraySub(personalBestVector,particleVector));
        setParticleVelocity(arraySum(inertiaTerm, socialTerm, personalTerm));
    }

    public void upgradeParticleVector() {
        setParticleVector(arraySum(particleVector,particleVelocity));

        // check if the newly upgraded vector is having
        // smaller misfit than previous ones;
        double localMisfit = getMisfit(particleVector, modelParameter);
        if (localMisfit < leastMisfit) {
            leastMisfit = localMisfit;
            personalBestVector = particleVector;
        }
    }

}
