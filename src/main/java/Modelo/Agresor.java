
package Modelo;

import Vista.AgresorMemento;

public class Agresor implements ClonableProcedure{
   
    private String idAgresor;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;

    public Agresor(String idAgresor, String nombre, String apellido, String telefono, String direccion) {
        this.idAgresor = idAgresor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    @Override
      public String toString() {
        return "Agresor{" +
                "idAgresor='" + idAgresor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public String getIdAgresor() {
        return idAgresor;
    }

    public Agresor setIdAgresor(String idAgresor) {
        this.idAgresor = idAgresor;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Agresor setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public Agresor setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getTelefono() {
        return telefono;
    }

    public Agresor setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public String getDireccion() {
        return direccion;
    }

    public Agresor setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }
     @Override
    public ClonableProcedure clonar() {
        return new Agresor(idAgresor,nombre,apellido,telefono,direccion);
    }
    
    public AgresorMemento saveToMemento() {
        return new AgresorMemento(idAgresor, nombre, apellido, telefono, direccion);
    }

    public void restoreFromMemento(AgresorMemento memento) {
        this.idAgresor = memento.getIdAgresor();
        this.nombre = memento.getNombre();
        this.apellido = memento.getApellido();
        this.telefono = memento.getTelefono();
        this.direccion = memento.getDireccion();
    }
}

