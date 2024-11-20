
package Vista;

import javax.swing.JOptionPane;

public class Usuarios implements Observer {
    private String nombre;
    private String apellido;

    public Usuarios(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public void actualizar(String mensaje) {
        JOptionPane.showMessageDialog(null, nombre + " " + apellido + ", " + mensaje);
    }
}