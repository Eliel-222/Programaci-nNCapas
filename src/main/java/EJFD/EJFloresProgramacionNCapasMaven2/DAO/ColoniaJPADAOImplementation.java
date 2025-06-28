package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPADAO{

    @Autowired 
    private EntityManager entityManager;
    
    @Override
    public Result ColoniaGetAllJPA(int IdMunicipio) {

        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            
            TypedQuery <Colonia> coloniaQ = entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idmunicipio ORDER BY IdColonia ASC", Colonia.class);
            coloniaQ.setParameter("idmunicipio", IdMunicipio);
            List<Colonia> colonias = coloniaQ.getResultList();

            for(Colonia coloniaJPA : colonias){
                EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia colonia = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia();
                
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombre(coloniaJPA.getNombre());
                colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                
                result.objects.add(colonia);
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
