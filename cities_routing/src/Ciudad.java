/**
 * Clase Ciudad representa los vértices del grafo.
 */
public class Ciudad {

    private String nombre;
    private boolean autovia;
    private double latitud;
    private double longitud;

    public Ciudad(String nombre, boolean autovia, double latitud, double longitud) {

        this.nombre = nombre;
        this.autovia = autovia;
        this.latitud = Math.toRadians(latitud);
        this.longitud = Math.toRadians(longitud);

    }

    public double distancia(Ciudad c) {

        return 2 * (Constantes.RADIOTIERRA) * Math.asin(Math.sqrt(Math.pow(Math.sin((this.latitud - c.latitud) / 2), 2) + Math.cos(this.latitud) * Math.cos(c.latitud) * Math.pow(Math.sin((this.longitud - c.longitud) / 2), 2)));

    }

    public String getNombre() {

        return this.nombre;

    }

	public boolean isAutovia() {
		return autovia;
	}

	public void setAutovia(boolean autovia) {
		this.autovia = autovia;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
