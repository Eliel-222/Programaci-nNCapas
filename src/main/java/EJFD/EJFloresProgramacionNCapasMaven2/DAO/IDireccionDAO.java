package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;

public interface IDireccionDAO {
    Result DireccionesAllSP(int IdDireccion);
    
    Result DireccionAddSP(UsuarioDireccion usuarioDireccion);
     
}
