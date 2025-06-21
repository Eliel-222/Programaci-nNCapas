package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAllEstados(int IdPais) {
        
        Result result = new Result();
        
        try{
            
            result.correct = jdbcTemplate.execute("{CALL AllEstadosSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{                
                
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                    
                }
                return true;
            });
            
        }catch(Exception ex){
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }    
}
