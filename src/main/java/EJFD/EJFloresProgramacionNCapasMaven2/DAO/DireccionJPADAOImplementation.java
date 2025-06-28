package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Direccion;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Estado;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Municipio;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Pais;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
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
            
            direccionJPA.Colonia = new Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            
            direccionJPA.Colonia.Municipio = new Municipio();
            direccionJPA.Colonia.Municipio.setIdMunicipio(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio());
            
            direccionJPA.Colonia.Municipio.Estado = new Estado();
            direccionJPA.Colonia.Municipio.Estado.setIdEstado(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado());
            
            direccionJPA.Colonia.Municipio.Estado.Pais = new Pais();
            direccionJPA.Colonia.Municipio.Estado.Pais.setIdPais(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais());
            
            entityManager.merge(direccionJPA);
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }   

    @Override
    public Result DireccionByIdJPA(int IdDireccion) {
        Result result = new Result();
        result.object = new Object();
        
        try{
            
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
                        
            EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion direccion = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion();
            
            direccion.setIdDireccion(direccionJPA.getIdDireccion());
            direccion.setCalle(direccionJPA.getCalle());
            direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
            direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
            
            direccion.Colonia = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia();
            direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
            direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());
            
            direccion.Colonia.Municipio = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio();
            direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
            
            direccion.Colonia.Municipio.Estado = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado();
            direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
            
            direccion.Colonia.Municipio.Estado.Pais = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Pais();
            direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
            
            result.object = direccion;
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    @Transactional
    public Result DireccionAddJPA(UsuarioDireccion usuarioDireccion) {
        
        Result result = new Result();
        
        try{
            Direccion direccionJPA = new Direccion();
            
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            
            direccionJPA.Usuario = new Usuario();
            direccionJPA.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());
            
            direccionJPA.Colonia = new Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            
            direccionJPA.Colonia.Municipio = new Municipio();
            direccionJPA.Colonia.Municipio.setIdMunicipio(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio());
            
            direccionJPA.Colonia.Municipio.Estado = new Estado();
            direccionJPA.Colonia.Municipio.Estado.setIdEstado(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado());
            
            direccionJPA.Colonia.Municipio.Estado.Pais = new Pais();
            direccionJPA.Colonia.Municipio.Estado.Pais.setIdPais(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais());
            
            entityManager.persist(direccionJPA);
            result.correct = true;
                
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public Result DireccionDeleteJPA(int IdDireccion) {
        Result result = new Result();
        
        try{
            
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            
            entityManager.remove(direccionJPA);
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
