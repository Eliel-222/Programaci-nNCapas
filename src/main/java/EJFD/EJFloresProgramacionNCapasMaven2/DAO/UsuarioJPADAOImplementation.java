package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Direccion;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO{

    @Autowired
    private EntityManager entityManager;
        
    @Transactional
    @Override
    public Result UsuarioADDJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setNombreUsuario(usuarioDireccion.Usuario.getNombreUsuario());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
            usuarioJPA.setNumeroTelefono(usuarioDireccion.Usuario.getNumeroTelefono());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setFotografia(usuarioDireccion.Usuario.getFotografia());
            usuarioJPA.setEstado(usuarioDireccion.Usuario.getEstado());

            Rol rol = new Rol();
            rol.setIdRol(usuarioDireccion.Usuario.Rol.getIdRol());
            usuarioJPA.setRol(rol);

            entityManager.persist(usuarioJPA);
            //entityManager.flush(); 
            
            Direccion direccionJPA = new Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());

            Colonia colonia = new Colonia();
            colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.setColonia(colonia);

            direccionJPA.setUsuario(usuarioJPA); 

            entityManager.persist(direccionJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }   

    @Override
    public Result UsuarioGetAllJPA() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            
            TypedQuery <Usuario> usuarioQuery = entityManager.createQuery("FROM Usuario WHERE Estado = 1 ORDER BY IdUsuario ASC", Usuario.class);
            List<Usuario> usuarios = usuarioQuery.getResultList();
            
            for(Usuario usuarioJPA : usuarios){
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                
                usuarioDireccion.Usuario = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Usuario();
                usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioDireccion.Usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
                usuarioDireccion.Usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuarioDireccion.Usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuarioDireccion.Usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
                usuarioDireccion.Usuario.setNumeroTelefono(usuarioJPA.getNumeroTelefono());
                usuarioDireccion.Usuario.setEmail(usuarioJPA.getEmail());
                usuarioDireccion.Usuario.setPassword(usuarioJPA.getPassword());
                usuarioDireccion.Usuario.setSexo(usuarioJPA.getSexo());
                usuarioDireccion.Usuario.setCelular(usuarioJPA.getCelular());
                usuarioDireccion.Usuario.setCURP(usuarioJPA.getCURP());
                usuarioDireccion.Usuario.setUserName(usuarioJPA.getUserName());
                usuarioDireccion.Usuario.setFotografia(usuarioJPA.getFotografia());
                usuarioDireccion.Usuario.setEstado(usuarioJPA.getEstado());
                
                usuarioDireccion.Usuario.Rol = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(usuarioJPA.Rol.getIdRol());
                usuarioDireccion.Usuario.Rol.setRol(usuarioJPA.Rol.getRol());
                
                TypedQuery <Direccion> direccionQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                direccionQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<Direccion> direcciones = direccionQuery.getResultList();
                
                if(direcciones.size() != 0){
                    
                    usuarioDireccion.Direcciones = new ArrayList<>();
                    
                    for(Direccion direccionJPA : direcciones){
                        EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion direccion = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion();
                        
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                        
                        direccion.Colonia = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia();
                        direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
                        direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());
                        
                        direccion.Colonia.Municipio = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio();
                        direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());
                        
                        direccion.Colonia.Municipio.Estado = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado();
                        direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());
                        
                        direccion.Colonia.Municipio.Estado.Pais = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());
                        
                        usuarioDireccion.Direcciones.add(direccion);
                    }
                }
                result.objects.add(usuarioDireccion);
            }
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result UsuarioGetByIdJPA(int IdUsuario) {
        Result result = new Result();
        result.object = new Object();
        
        try{
            
            Usuario usuarioJPA = entityManager.find(Usuario.class, IdUsuario);
            
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        
            usuarioDireccion.Usuario = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Usuario();
            usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
            usuarioDireccion.Usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
            usuarioDireccion.Usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
            usuarioDireccion.Usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
            usuarioDireccion.Usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
            usuarioDireccion.Usuario.setNumeroTelefono(usuarioJPA.getNumeroTelefono());
            usuarioDireccion.Usuario.setEmail(usuarioJPA.getEmail());
            usuarioDireccion.Usuario.setPassword(usuarioJPA.getPassword());
            usuarioDireccion.Usuario.setSexo(usuarioJPA.getSexo());
            usuarioDireccion.Usuario.setCelular(usuarioJPA.getCelular());
            usuarioDireccion.Usuario.setCURP(usuarioJPA.getCURP());
            usuarioDireccion.Usuario.setUserName(usuarioJPA.getUserName());
            usuarioDireccion.Usuario.setFotografia(usuarioJPA.getFotografia());
            usuarioDireccion.Usuario.setEstado(usuarioJPA.getEstado());

            usuarioDireccion.Usuario.Rol = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol();
            usuarioDireccion.Usuario.Rol.setIdRol(usuarioJPA.Rol.getIdRol());
            usuarioDireccion.Usuario.Rol.setRol(usuarioJPA.Rol.getRol());

            TypedQuery <Direccion> direccionQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
            direccionQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
            List<Direccion> direcciones = direccionQuery.getResultList();

            if(direcciones.size() != 0){

                usuarioDireccion.Direcciones = new ArrayList<>();

                for(Direccion direccionJPA : direcciones){
                    EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion direccion = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion();
                    
                    direccion.setIdDireccion(direccionJPA.getIdDireccion());
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                    direccion.Colonia = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia();
                    direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
                    direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                    direccion.Colonia.Municipio = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio();
                    direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());

                    direccion.Colonia.Municipio.Estado = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado();
                    direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());

                    direccion.Colonia.Municipio.Estado.Pais = new EJFD.EJFloresProgramacionNCapasMaven2.ML.Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());

                    usuarioDireccion.Direcciones.add(direccion);
                }
            }
            result.object = usuarioDireccion;
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
    public Result UsuarioUpdateJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioDireccion.Usuario.getIdUsuario());
            
            if (usuarioJPA == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado.";
                return result;
            }

            String password = usuarioDireccion.Usuario.getPassword();
            if (password == null || password.trim().isEmpty()) {
                password = usuarioJPA.getPassword();
            }

            usuarioJPA.setNombreUsuario(usuarioDireccion.Usuario.getNombreUsuario());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
            usuarioJPA.setNumeroTelefono(usuarioDireccion.Usuario.getNumeroTelefono());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.setPassword(password);
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setFotografia(usuarioDireccion.Usuario.getFotografia());
            usuarioJPA.setEstado(usuarioDireccion.Usuario.getEstado());

            Rol rol = new Rol();
            rol.setIdRol(usuarioDireccion.Usuario.Rol.getIdRol());
            usuarioJPA.setRol(rol);

            entityManager.merge(usuarioJPA); 

            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UsuarioBusquedaDinamicaJPA(Usuario usuario) {
        Result result = new Result();
        
        
        
        return result;
    }

    @Override
    @Transactional
    public Result InsercionMasivaJPA(List<UsuarioDireccion> usuariosDireccion) {
        
        Result result = new Result();
        
        try{
            
            for(UsuarioDireccion usuarioDireccion : usuariosDireccion){
                this.UsuarioADDJPA(usuarioDireccion);
            }
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
