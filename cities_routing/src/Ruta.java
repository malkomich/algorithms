/**
 * Clase ruta representa las aristas del grafo.
 */
public class Ruta {

    private Ciudad ciudadOrigen;
    private int velocidad;
    private Ciudad ciudadDestino;
    private boolean autovia;
    private double distancia;
    private double tiempo;
    private double tOptimo;

    public Ruta(Ciudad ciudadOrigen, int velocidad, Ciudad ciudadDestino, boolean autovia) {

        this.ciudadOrigen = ciudadOrigen;
        this.velocidad = velocidad;
        this.ciudadDestino = ciudadDestino;
        this.autovia = autovia;
        this.distancia = this.distancia();
        this.tiempo = this.tiempo();
        this.tOptimo = this.tiempo();
    }
    
    public Ruta(){
        double infinito = Double.POSITIVE_INFINITY;
        this.distancia = infinito;
        this.tiempo = infinito;
        this.tOptimo = infinito;
    }

    private double distancia() {

        return 2 * (Constantes.RADIOTIERRA) * Math.asin(Math.sqrt(Math.pow(Math.sin((this.ciudadOrigen.getLatitud() - this.ciudadDestino.getLatitud()) / 2), 2) + Math.cos(this.ciudadOrigen.getLatitud()) * Math.cos(this.ciudadDestino.getLatitud()) * Math.pow(Math.sin((this.ciudadOrigen.getLongitud() - this.ciudadDestino.getLongitud()) / 2), 2)));

    }

    private double tiempo() {

        double metrosSegundo = (double)(this.velocidad * 1000)/3600;
        double distanciaMetros = this.distancia * 1000;
        return distanciaMetros / metrosSegundo;

    }

    public Ciudad getCiudadOrigen() {
        return this.ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Ciudad getCiudadDestino() {
        return this.ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public boolean isAutovia() {
        return this.autovia;
    }

    public void setAutovia(boolean autovia) {
        this.autovia = autovia;
    }
    
    public double getDistancia(){
        return this.distancia;
    }
    
    public void setDistancia(double distancia){
        this.distancia = distancia;
    }
    
    public double getTiempo(){
        return this.tiempo;
    }
    
    public void setTiempo(double tiempo){
        this.tiempo=tiempo;
    }
    
    public double getTOptimo(){
        return this.tOptimo;
    }
    
    public void setTOptimo(double tiempo){
        this.tOptimo=tiempo;
    }

}
