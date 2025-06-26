package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Direccion;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Municipio;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class DireccionJPADAOImplementation implements IDireccionJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public Result DireccionEditJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        
        try{
            
            Direccion direccionJPA = entityManager.find(Direccion.class, usuarioDireccion.Direccion.getIdDireccion());
            
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getCalle());
            
            Colonia coloniaJPA = new Colonia();
            coloniaJPA.setIdColonia(0);
            
            Municipio municipioJPA = new Municipio();
            
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }   
}
