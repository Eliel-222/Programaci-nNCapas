package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;

public interface IDireccionJPADAO {
    Result DireccionEditJPA(UsuarioDireccion usuarioDireccion);
    
    Result DireccionByIdJPA(int IdDireccion);
    
    Result DireccionAddJPA(UsuarioDireccion usuarioDireccion);
    
    Result DireccionDeleteJPA(int IdDireccion);
}
