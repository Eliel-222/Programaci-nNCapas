package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Estado;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result EstadoGetAllJPA(int IdPais) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            
            TypedQuery <Estado> estadoQ = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais ORDER BY IdEstado ASC", Estado.class);
            estadoQ.setParameter("idpais", IdPais);
            List<Estado> estados = estadoQ.getResultList();

            for(Estado estadoJPA : estados){
                EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado estado = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado();
                
                estado.setIdEstado(estadoJPA.getIdEstado());
                estado.setNombre(estadoJPA.getNombre());
                
                result.objects.add(estado);
            }
            
            result.correct = true;
            
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
