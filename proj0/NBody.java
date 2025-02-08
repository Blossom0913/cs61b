import java.util.ArrayList;


public class NBody {

    /**
     * read the file and return the second line double, which means the radius of universe
     * @param filename
     * @return the radius of universe
     */
    public static double readRadius(String filename){
        In in = new In(filename);

        int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

        return secondItemInFile;

    }

    /**
     * read the file and return a list of all planets
     * @param filename
     * @return a list of all planets in the file
     */
    public static Planet[] readPlanets(String filename) {
        
        In in = new In(filename);

        int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

        Planet[] allPlanets = new Planet[firstItemInFile];

        for(int i = 0;i < firstItemInFile;++i){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            Planet planet = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            allPlanets[i] = planet;
        }
        return allPlanets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] allPlanets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();

        Double currentTime = 0.0;

        StdDraw.setScale(-radius, radius);


        while(currentTime < T){
            double[] xForces = new double[allPlanets.length];
            double[] yForces = new double[allPlanets.length];

            for(int i = 0;i < allPlanets.length;++i){
                xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
                yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
            }

            for(int i = 0;i < allPlanets.length;++i){
                allPlanets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0,0,"images/starfield.jpg",radius,radius);

            for(Planet planet:allPlanets){
                planet.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);

            currentTime += dt;
        }

        
        StdOut.printf("%d\n", allPlanets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < allPlanets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
                        allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);   
        }
    }
}
