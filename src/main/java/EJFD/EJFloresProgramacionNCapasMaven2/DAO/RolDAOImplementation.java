package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class RolDAOImplementation implements IRolDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAllRol(){
        
        Result result = new Result();
        
        try{
            
            jdbcTemplate.execute("{CALL RolGetAll(?)}", (CallableStatementCallback<Integer>) callableStatement ->{
            
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Rol rol = new Rol();
                    
                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setRol(resultSet.getString("Rol"));
                    
                    result.objects.add(rol);
                }
                return 1;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
}
