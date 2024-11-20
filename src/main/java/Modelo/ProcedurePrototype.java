package Modelo;

import Control.UsuarioControlador;
import Vista.DenunciaObservable;
import Vista.Observer;
import Vista.Usuarios;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcedurePrototype {

    /*
     * DENUNCIA - CRUD
     * */
    public List<Denuncia> getDncFecha(Date fecha) throws SQLException {
        List<Denuncia> denuncias = new ArrayList<>();

        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
            CallableStatement st = cn.prepareCall("{call PROCE1(?)}");
            st.setDate(1, fecha);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Denuncia denuncia = new Denuncia(
                    rs.getString("id_denc"),
                    rs.getString("USUARIO_id_usuario"),
                    rs.getString("AGRESOR_id_agresor"),
                    rs.getString("descripcion"),
                    rs.getString("estado"),
                    rs.getString("tipo_violencia"),
                    rs.getString("rel_denc"),
                    rs.getString("fecha")
                );
                denuncias.add((Denuncia)denuncia.clonar());                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            cn.close();
        } finally {
            //if(cn != null) cn.close();
            cn.close();
            System.out.println("Se cerro la conexion");
        }
        return denuncias;
    }

    public void crearDenuncia(Denuncia denuncia) throws SQLException{
        Connection cn = ConexionBdSingleton.getInstace().getConnection();

        try {
            CallableStatement st = cn.prepareCall("{call sp_InsertDenuncia(?, ?, ?, ?, ?, ?, ?, ?)}");
            st.setString(1, denuncia.getIdDenuncia());
            st.setDate(2, Date.valueOf(denuncia.getFec()));
            st.setString(3, denuncia.getDesc());
            st.setString(4, denuncia.getEstado());
            st.setString(5, denuncia.getTipo());
            st.setString(6, denuncia.getRelDenc());
            st.setString(7, denuncia.getId_usuario());
            st.setString(8, denuncia.getId_agresor());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            cn.close();
        } finally {
            cn.close();
        }
    }

    public Denuncia leerDenuncia(String idDenuncia) throws SQLException {
        Denuncia denuncia = null;
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
            CallableStatement st = cn.prepareCall("{call sp_GetDenuncia(?)}");
            st.setString(1, idDenuncia);
            ResultSet rs = st.executeQuery();

            if (rs.next()){
                denuncia = new Denuncia(
                        rs.getString("id_denc"),
                        rs.getString("USUARIO_id_usuario"),
                        rs.getString("AGRESOR_id_agresor"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("tipo_violencia"),
                        rs.getString("rel_denc"),
                        rs.getDate("fecha").toString()
                );
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            cn.close();
        }
        return  denuncia;
    }

    public void actualizarDenuncia(Denuncia denuncia) throws SQLException{
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
            CallableStatement st = cn.prepareCall("{call sp_UpdateDenuncia(?, ?, ?, ?, ?, ?, ?, ?)}");
            st.setString(1, denuncia.getIdDenuncia());
            st.setDate(2, Date.valueOf(denuncia.getFec()));
            st.setString(3, denuncia.getDesc());
            st.setString(4, denuncia.getEstado());
            st.setString(5, denuncia.getTipo());
            st.setString(6, denuncia.getRelDenc());
            st.setString(7, denuncia.getId_usuario());
            st.setString(8, denuncia.getId_agresor());
            st.executeUpdate();
           
            DenunciaObservable denunciaObservable = new DenunciaObservable();
            
         UsuarioControlador usuarioControlador = new UsuarioControlador();
            Usuario usuario = usuarioControlador.leerUsuario(denuncia.getId_usuario());

            // Crear una instancia de Usuarios y configurarla
           Usuarios usuarioVista = new Usuarios(usuario.getNombre(), usuario.getApellido());

            // Agregar el usuario como observador y notificar
            denunciaObservable.añadirObserver(usuarioVista);
            denunciaObservable.notificarObservers("su denuncia ha sido actualizada.");
        
        } catch (SQLException e) {
        } finally {
            if(cn != null) cn.close();
        }
    }

    public void eliminarDenuncia(String idDenuncia) throws SQLException{
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        
        try {
            CallableStatement st = cn.prepareCall("{call sp_DeleteDenuncia(?)}");
            st.setString(1,idDenuncia);
            st.executeUpdate();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(cn != null) cn.close();
        }
    }

    /*
    * USUARIO - CRUD
    * */

    public void crearUsuario(Usuario usuario) throws SQLException {
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
            CallableStatement st = cn.prepareCall("{call sp_InsertUsuario(?, ?, ?, ?, ?, ?, ?)}");
            st.setString(1, usuario.getIdUsuario());
            st.setString(2, usuario.getNombre());
            st.setString(3, usuario.getApellido());
            st.setString(4, usuario.getEmail());
            st.setString(5, usuario.getTelefono());
            st.setString(6, usuario.getDireccion());
            st.setString(7, usuario.getTipoUsuario());
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(cn != null) cn.close();
        }
    }

    public Usuario leerUsuario(String idUsuario) throws SQLException {
        Usuario usuario = null;
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
            CallableStatement st = cn.prepareCall("{call sp_GetUsuario(?)}");
            st.setString(1, idUsuario);
            ResultSet rs = st.executeQuery();

            if (rs.next()){
                usuario = new Usuario(
                        rs.getString("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("tipo_usuario")
                );
            }

        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            cn.close();
        }
        return  usuario;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
            CallableStatement st = cn.prepareCall("{call sp_UpdateUsuario(?, ?, ?, ?, ?, ?, ?)}");
            st.setString(1, usuario.getIdUsuario());
            st.setString(2, usuario.getNombre());
            st.setString(3, usuario.getApellido());
            st.setString(4, usuario.getEmail());
            st.setString(5, usuario.getTelefono());
            st.setString(6, usuario.getDireccion());
            st.setString(7, usuario.getTipoUsuario());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(cn != null) cn.close();
        }
    }
    
    public void actualizarUsuarioconId(String idUsuario, Usuario usuario) throws SQLException {
    Connection cn = ConexionBdSingleton.getInstace().getConnection();
    try {
        CallableStatement st = cn.prepareCall("{call sp_UpdateUsuario(?, ?, ?, ?, ?, ?, ?)}");
        st.setString(1, idUsuario);                  // ID del usuario para buscar y actualizar
        st.setString(2, usuario.getNombre());
        st.setString(3, usuario.getApellido());
        st.setString(4, usuario.getEmail());
        st.setString(5, usuario.getTelefono());
        st.setString(6, usuario.getDireccion());
        st.setString(7, usuario.getTipoUsuario());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (cn != null) cn.close();
    }
} 

    public void eliminarUsuario(String idUsuario) throws SQLException {
        Connection cn = ConexionBdSingleton.getInstace().getConnection();
        try {
           CallableStatement st = cn.prepareCall("{call sp_DeleteUsuario(?)}");
           st.setString(1,idUsuario);
           st.executeUpdate();
           cn.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(cn != null) cn.close();
        }
    }
    
    public void crearAgresor(Agresor agresor) throws SQLException {
    Connection cn = ConexionBdSingleton.getInstace().getConnection();
    try {
        CallableStatement st = cn.prepareCall("{call sp_InsertAgresor(?, ?, ?, ?, ?)}");
        st.setString(1, agresor.getIdAgresor());
        st.setString(2, agresor.getNombre());
        st.setString(3, agresor.getApellido());
        st.setString(4, agresor.getTelefono());
        st.setString(5, agresor.getDireccion());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (cn != null) cn.close();
    }
}

public Agresor leerAgresor(String idAgresor) throws SQLException {
    Agresor agresor = null;
    Connection cn = ConexionBdSingleton.getInstace().getConnection();
    try {
        CallableStatement st = cn.prepareCall("{call sp_GetAgresor(?)}");
        st.setString(1, idAgresor);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            agresor = new Agresor(
                rs.getString("id_agresor"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("direccion")
            );
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        cn.close();
    }
    return agresor;
}

public void actualizarAgresor(Agresor agresor) throws SQLException {
    Connection cn = ConexionBdSingleton.getInstace().getConnection();
    try {
        CallableStatement st = cn.prepareCall("{call sp_UpdateAgresor(?, ?, ?, ?, ?)}");
        st.setString(1, agresor.getIdAgresor());
        st.setString(2, agresor.getNombre());
        st.setString(3, agresor.getApellido());
        st.setString(4, agresor.getTelefono());
        st.setString(5, agresor.getDireccion());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (cn != null) cn.close();
    }
}

public void actualizarAgresorConId(String idAgresor, Agresor agresor) throws SQLException {
    Connection cn = ConexionBdSingleton.getInstace().getConnection();
    try {
        CallableStatement st = cn.prepareCall("{call sp_UpdateAgresor(?, ?, ?, ?, ?)}");
        st.setString(1, idAgresor);                  // ID del agresor para buscar y actualizar
        st.setString(2, agresor.getNombre());
        st.setString(3, agresor.getApellido());
        st.setString(4, agresor.getTelefono());
        st.setString(5, agresor.getDireccion());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (cn != null) cn.close();
    }
}

public void eliminarAgresor(String idAgresor) throws SQLException {
    Connection cn = ConexionBdSingleton.getInstace().getConnection();
    try {
        CallableStatement st = cn.prepareCall("{call sp_DeleteAgresor(?)}");
        st.setString(1, idAgresor);
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (cn != null) cn.close();
    }
}

    
}
