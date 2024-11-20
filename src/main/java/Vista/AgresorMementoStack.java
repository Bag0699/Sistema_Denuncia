
package Vista;

import Modelo.Agresor;
 import java.util.Stack;
public class AgresorMementoStack {



    private final Stack<AgresorMemento> stackMementos = new Stack<>();

     public void guardarEstado(Agresor agresor) {
        System.out.println("Guardando estado en stack: " + agresor);
        stackMementos.push(agresor.saveToMemento());
    }
    
    public AgresorMemento obtenerUltimoEstado() {
        if (!stackMementos.isEmpty()) {
            AgresorMemento memento = stackMementos.pop();
            return memento;
        }
        return null;
    }
    
    public boolean tieneEstadosAnteriores() {
        return !stackMementos.isEmpty();
    }
}
   

