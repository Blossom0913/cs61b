/**
 * @author chenxutian
 * class that describe a planet
 */
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
                this.xxPos = xP;
                this.yyPos = yP;
                this.xxVel = xV;
                this.yyVel = yV;
                this.mass = m;
                this.imgFileName = img;
              }

    public Planet(Planet b) {

        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    /**
     * calculate the distance between two planets
     * @param planet
     * @return distance
     */
    public double calcDistance(Planet planet) {
      double dis_x = Math.abs(this.xxPos - planet.xxPos);
      double dis_y = Math.abs(this.yyPos - planet.yyPos);
      return Math.sqrt(dis_x * dis_x + dis_y * dis_y);
    }

    /**
     * calculate the Force of two planets.
     * @param planet
     * @return Force Exerted by input planet
     */
    public double calcForceExertedBy(Planet planet) {

      double dis = calcDistance(planet);
      final double G = 6.67*1e-11;
      double force = (G * this.mass * planet.mass)/(dis * dis);
      return force;
    }

    /**
     * calculate the Force by X axis exerted by input planet.
     * @param planet
     * @return force in X direction
     */
    public double calcForceExertedByX(Planet planet) {
      double force = calcForceExertedBy(planet);
      double dis_x = planet.xxPos - xxPos;
      double dis = calcDistance(planet);

      return (force * dis_x) / dis;
    }

    /**
     * calculate the Force by Y axis exerted by input planet.
     * @param planet
     * @return force in Y direction
     */
    public double calcForceExertedByY(Planet planet) {
      double force = calcForceExertedBy(planet);
      double dis_y = planet.yyPos - yyPos;
      double dis = calcDistance(planet);

      return (force * dis_y) / dis;
    }

    /**
     * calculate the net force by all planets in X direction
     * @param allPlanets
     * @return net force combined all planets in X direction
     */
    public double calcNetForceExertedByX(Planet[] allPlanets) {
      double netForce = 0;
      for(Planet planet:allPlanets){
        if(this.equals(planet))
          continue;
        netForce += calcForceExertedByX(planet);
      }
      return netForce;
    }

    /**
     * calculate the net force by all planets in Y direction
     * @param allPlanets
     * @return net force combined all planets in Y direction
     */
    public double calcNetForceExertedByY(Planet[] allPlanets) {
      double netForce = 0;
      for(Planet planet:allPlanets){
        if(this.equals(planet))
          continue;
        netForce += calcForceExertedByY(planet);
        
      }
      return netForce;
    }

    /**
     * update the velocity and position of planet by given dt and force
     * @param dt time tiny
     * @param fX force in X direction
     * @param fY force in Y direction
     */
    public void update(double dt, double fX, double fY) {
      double aX = fX / this.mass;
      double aY = fY / this.mass;

      this.xxVel += dt * aX;
      this.yyVel += dt * aY;

      this.xxPos += dt * xxVel;
      this.yyPos += dt * yyVel;
    }

    public void draw() {
      StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
    
}
