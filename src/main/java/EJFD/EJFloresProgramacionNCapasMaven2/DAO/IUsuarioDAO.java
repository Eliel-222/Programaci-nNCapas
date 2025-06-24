package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import java.util.List;

public interface IUsuarioDAO { //Esta interfaz es la encargada de tener todas aquellas acciones que se realizarán con la base de datos
    Result GetAll();
    
    Result Add(UsuarioDireccion usuarioDireccion);
    
    Result GetAllById(int IdUsuario);
    
    Result UsuarioUptadteSP(UsuarioDireccion usuarioDireccion);
    
    Result BusquedaDinamicaSP(Usuario usuario);
    
    Result InsercionMasiva(List<UsuarioDireccion> usuariosDireccion);//Esta es la lista de usuarios
    
}
