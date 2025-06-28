package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Pais;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Rol;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result RolGetAllJPA() {

        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            TypedQuery <Rol> rolQ = entityManager.createQuery("FROM Rol ORDER BY IdRol ASC", Rol.class);
            List<Rol> roles = rolQ.getResultList();

            for(Rol rolJPA : roles){
                EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol rol = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol();
                
                rol.setIdRol(rolJPA.getIdRol());
                rol.setRol(rolJPA.getRol());
                
                result.objects.add(rol);
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
