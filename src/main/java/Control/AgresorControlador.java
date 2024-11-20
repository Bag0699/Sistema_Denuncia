
package Control;

import Modelo.ProcedurePrototype;
import Modelo.Agresor;
import Vista.AgresorMemento;
import Vista.AgresorMementoStack;

import java.sql.SQLException;


    public class AgresorControlador implements ControladorBase{
    private final ProcedurePrototype procedurePrototype;
    private final AgresorMementoStack stack;


    public AgresorControlador() {
        this.procedurePrototype = new ProcedurePrototype();
         this.stack = new AgresorMementoStack();
    }

    public void crearAgresor(Agresor agresor) throws SQLException {
        procedurePrototype.crearAgresor(agresor);
    }

    public Agresor leerAgresor(String idAgresor) throws SQLException {
        return procedurePrototype.leerAgresor(idAgresor);
    }

    public void actualizarAgresor(Agresor agresor) throws SQLException {
        Agresor estadoActual = procedurePrototype.leerAgresor(agresor.getIdAgresor());
        if (estadoActual != null) {
            stack.guardarEstado(estadoActual);
        }
        
        procedurePrototype.actualizarAgresor(agresor);
        System.out.println("Nuevo estado guardado: " + agresor);}

    public void eliminarAgresor(String idAgresor) throws SQLException {
        procedurePrototype.eliminarAgresor(idAgresor);
    }

    public void actualizarAgresorConId(String idAgresor, Agresor agresor) throws SQLException {
        stack.guardarEstado(agresor);
        procedurePrototype.actualizarAgresorConId(idAgresor, agresor);
    }
     public void deshacerActualizacion(Agresor agresor) throws SQLException {
         AgresorMemento memento = stack.obtenerUltimoEstado();
        if (memento != null) {
           
            agresor.restoreFromMemento(memento);
            procedurePrototype.actualizarAgresor(agresor);
        } 
     }
    @Override
    public void ejecutarControlador() {
        System.out.println("¡Ejecutando el controlador de Agresor!");
    }

    }


