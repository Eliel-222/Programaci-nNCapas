package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import java.util.List;

public interface IUsuarioJPADAO {
    
    Result UsuarioADDJPA(UsuarioDireccion usuarioDireccion);
    
    Result UsuarioGetAllJPA();
    
    Result UsuarioGetByIdJPA(int IdUsuario);
    
    Result UsuarioBusquedaDinamicaJPA(Usuario usuario);
    
    Result UsuarioUpdateJPA(UsuarioDireccion usuarioDireccion);
    
    Result InsercionMasivaJPA(List<UsuarioDireccion> usuariosDireccion);
}
