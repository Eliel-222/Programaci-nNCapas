package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAllMunicipios(int IdEstado) {
        
        Result result = new Result();
        
        try{
            
            result.correct = jdbcTemplate.execute("{CALL AllMunicipiosSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.setInt(1,IdEstado);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    
                    Municipio municipio = new Municipio();
                    
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(municipio);
                }
                
                return true;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }    
}
