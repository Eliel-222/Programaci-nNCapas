package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Pais;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result PaisGetAllJPA() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            TypedQuery <Pais> paisQ = entityManager.createQuery("FROM Pais ORDER BY IdPais ASC", Pais.class);
            List<Pais> paises = paisQ.getResultList();

            for(Pais paisJPA : paises){
                EJFD.EJFloresProgramacionNCapasMaven2.ML.Pais pais = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Pais();
                
                pais.setIdPais(paisJPA.getIdPais());
                pais.setNombre(paisJPA.getNombre());
                
                result.objects.add(pais);
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
