
package Vista;


public class AgresorMemento {
  
    private final String idAgresor;
    private final String nombre;
    private final String apellido;
    private final String telefono;
    private final String direccion;

    public AgresorMemento(String idAgresor, String nombre, String apellido, String telefono, String direccion) {
        this.idAgresor = idAgresor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getIdAgresor() {
        return idAgresor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }
}

