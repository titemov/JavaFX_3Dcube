public class Vector {
    double x=0;
    double y=0;
    double z=0;
    double w=1;
    public Vector(double x,double y,double z,double w){
        this.x=x;
        this.y=y;
        this.z=z;
        this.w=w;
    }

    public Vector multiplyByScalar(double s){
        this.x *=s;
        this.y *=s;
        this.z *=s;
        return this;
    }

    public Vector add(Vector v1, Vector v2){
        return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z,1);
    }

    public double getLen(){
        return Math.pow(this.x*this.x + this.y*this.y + this.z*this.z,0.5);
    }

    public Vector normalize(){
        double len = this.getLen();
        this.x/=len;
        this.y/=len;
        this.z/=len;

        return this;
    }
}
