package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAllColonias(int IdMunicipio) {
        
        Result result = new Result();
        
        try{
            
            result.correct = jdbcTemplate.execute("{CALL AllColoniaSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{                
            
                callableStatement.setInt(1, IdMunicipio);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    
                    result.objects.add(colonia);
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
