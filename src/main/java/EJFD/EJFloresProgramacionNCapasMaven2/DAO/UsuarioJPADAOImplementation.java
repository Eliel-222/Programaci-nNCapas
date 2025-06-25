package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Direccion;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import EJFD.EJFloresProgramacionNCapasMaven2.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
            //usuarioJPA.setEstado(usuarioDireccion.Usuario.getEstado());

            Rol rol = new Rol();
            rol.setIdRol(usuarioDireccion.Usuario.Rol.getIdRol());
            usuarioJPA.setRol(rol);

            entityManager.persist(usuarioJPA);
            entityManager.flush(); 

            Direccion direccionJPA = new Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());

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
}
