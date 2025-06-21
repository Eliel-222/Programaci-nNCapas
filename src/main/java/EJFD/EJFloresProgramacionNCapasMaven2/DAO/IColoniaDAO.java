package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;

public interface IColoniaDAO {
    Result GetAllColonias(int IdMunicipio);
}
