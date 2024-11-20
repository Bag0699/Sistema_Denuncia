
package Vista;


public interface Subject {
    void añadirObserver(Observer observer);
    void eliminarObserver(Observer observer);
    void notificarObservers(String mensaje);
}
