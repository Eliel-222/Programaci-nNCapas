package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Estado;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Municipio;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result MunicipioGetAllJPA(int IdEstado) {
        
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            
            TypedQuery <Municipio> municipioQ = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado ORDER BY IdMunicipio ASC", Municipio.class);
            municipioQ.setParameter("idestado", IdEstado);
            List<Municipio> municipios = municipioQ.getResultList();

            for(Municipio municipioJPA : municipios){
                EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio municipio = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio();
                
                municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
                municipio.setNombre(municipioJPA.getNombre());
                
                result.objects.add(municipio);
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
