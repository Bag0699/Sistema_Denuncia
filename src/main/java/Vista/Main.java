  package Vista;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Error al configurar Look and Feel: " + ex.getMessage());
        }

        // Iniciar la GUI en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            Proxy_sistema proxySistema = new Proxy_sistema();
            proxySistema.setVisible(true);
        }); 
   }
}
