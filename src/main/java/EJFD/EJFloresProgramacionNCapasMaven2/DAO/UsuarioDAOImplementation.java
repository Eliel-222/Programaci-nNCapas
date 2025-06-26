package EJFD.EJFloresProgramacionNCapasMaven2.DAO;

import EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Estado;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Municipio;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Pais;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;


@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        
        Result result = new Result();
        
        try{
            
            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioGetAllSP(?)}", (CallableStatementCallback<Integer>) callableStatement ->{
            int idUsuarioPrevio = 0;   
                
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
            
                while(resultSet.next()){
                    
                    idUsuarioPrevio = resultSet.getInt("IdUsuario");
                    
                    if (!result.objects.isEmpty() && idUsuarioPrevio == ((UsuarioDireccion) (result.objects.get(result.objects.size()-1))).Usuario.getIdUsuario()) {  
                        
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        
                        ((UsuarioDireccion) (result.objects.get(result.objects.size()-1))).Direcciones.add(direccion);
                        
                    }else{
                        
                        UsuarioDireccion usuariodireccion = new UsuarioDireccion();
                    
                        usuariodireccion.Usuario = new Usuario();
                        usuariodireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuariodireccion.Usuario.setNombreUsuario(resultSet.getString("NombreUsuario"));
                        usuariodireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuariodireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuariodireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        //usuariodireccion.Usuario.setNumeroTelefono(resultSet.getInt("NumeroTelefono"));
                        usuariodireccion.Usuario.setEmail(resultSet.getString("Email"));
                        usuariodireccion.Usuario.setPassword(resultSet.getString("Password"));
                        usuariodireccion.Usuario.setSexo(resultSet.getString("Sexo").charAt(0));
                        usuariodireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        usuariodireccion.Usuario.setCURP(resultSet.getString("CURP"));
                        usuariodireccion.Usuario.setUserName(resultSet.getString("UserName"));
                        usuariodireccion.Usuario.setFotografia(resultSet.getString("Fotografia"));

                        usuariodireccion.Usuario.Rol = new Rol();
                        usuariodireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuariodireccion.Usuario.Rol.setRol(resultSet.getString("Rol"));
                        
                        usuariodireccion.Direcciones = new ArrayList<>();
                        
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getNString("CodigoPostal"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        usuariodireccion.Direcciones.add(direccion);
                        
                        result.objects.add(usuariodireccion);
                    }
                }
                
                return 1;
                
            });
            
            if(procesoCorrecto > 0){
                result.correct = true;
            }
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        
        Result result = new Result();
        
        try{
            jdbcTemplate.execute("{CALL UsuarioAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement ->{
            
            callableStatement.setString(1, usuarioDireccion.Usuario.getNombreUsuario());
            callableStatement.setString(2, usuarioDireccion.Usuario.getApellidoPaterno());
            callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoMaterno());
            callableStatement.setDate(4, new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
            callableStatement.setLong(5, usuarioDireccion.Usuario.getNumeroTelefono());
            callableStatement.setString(6, usuarioDireccion.Usuario.getEmail());
            callableStatement.setString(7, usuarioDireccion.Usuario.getPassword());
            callableStatement.setString(8, String.valueOf(usuarioDireccion.Usuario.getSexo()));
            callableStatement.setString(9, usuarioDireccion.Usuario.getCelular());
            callableStatement.setString(10, usuarioDireccion.Usuario.getCURP());
            callableStatement.setString(11, usuarioDireccion.Usuario.getUserName());
            callableStatement.setInt(12, usuarioDireccion.Usuario.Rol.getIdRol());
            callableStatement.setString(13, usuarioDireccion.Usuario.getFotografia());
            callableStatement.setInt(14, usuarioDireccion.Usuario.getEstado());
            callableStatement.setString(15, usuarioDireccion.Direccion.getCalle());
            callableStatement.setString(16, usuarioDireccion.Direccion.getNumeroInterior());
            callableStatement.setString(17, usuarioDireccion.Direccion.getNumeroExterior());
            callableStatement.setInt(18, usuarioDireccion.Direccion.Colonia.getIdColonia());
            
            int rowAffected = callableStatement.executeUpdate();
            
            if(rowAffected > 0){
                result.correct = true;
            }else{
                result.correct = false;
            }
                
            return 1;    
        });
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }   
        return result;
    }
    
    @Override
    public Result GetAllById(int IdUsuario){
        
        Result result = new Result();
        
        try{
            
            result.correct = jdbcTemplate.execute("{CALL UsuarioGetById(?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, Types.REF_CURSOR);
                callableStatement .execute();
                
                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                ResultSet resultSetDirecciones = (ResultSet) callableStatement.getObject(3);
                
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                
                if(resultSetUsuario.next()){
                    
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombreUsuario(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setFechaNacimiento(resultSetUsuario.getDate("FechaNacimiento"));
                    usuarioDireccion.Usuario.setNumeroTelefono(resultSetUsuario.getLong("NumeroTelefono"));
                    usuarioDireccion.Usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.Usuario.setPassword(resultSetUsuario.getString("Password"));
                    usuarioDireccion.Usuario.setSexo(resultSetUsuario.getString("Sexo").charAt(0));
                    usuarioDireccion.Usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuarioDireccion.Usuario.setCURP(resultSetUsuario.getString("CURP"));
                    usuarioDireccion.Usuario.setUserName(resultSetUsuario.getString("UserName"));
                    usuarioDireccion.Usuario.setFotografia(resultSetUsuario.getString("Fotografia"));
                    
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol(resultSetUsuario.getInt("IdRol"));
                    usuarioDireccion.Usuario.Rol.setRol(resultSetUsuario.getString("Rol"));
                    
                    
                }else{
                    result.correct = false;
                }
                    
                usuarioDireccion.Direcciones = new ArrayList<>();
                
                while(resultSetDirecciones.next()){
                    
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDirecciones.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDirecciones.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDirecciones.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDirecciones.getString("NumeroExterior"));

                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setNombre(resultSetDirecciones.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultSetDirecciones.getNString("CodigoPostal"));

                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setNombre(resultSetDirecciones.getString("NombreMunicipio"));

                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setNombre(resultSetDirecciones.getString("NombreEstado"));

                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSetDirecciones.getString("NombrePais"));

                    usuarioDireccion.Direcciones.add(direccion);

                    result.object = usuarioDireccion;
                }
                
                return true;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result BusquedaDinamicaSP(Usuario usuario){
        Result result = new Result();
        
        try{
            
            result.correct = jdbcTemplate.execute("{CALL BusquedaDinamicaSP(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
            int idUsuarioPrevio = 0;
            
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.setString(2, usuario.getNombreUsuario());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                
                if(usuario.Rol == null){
                    usuario.Rol.setIdRol(0);
                }
                else{
                    callableStatement.setInt(5, usuario.Rol.getIdRol());
                }
                
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
            
                while(resultSet.next()){
                    
                    idUsuarioPrevio = resultSet.getInt("IdUsuario");
                    
                    if (!result.objects.isEmpty() && idUsuarioPrevio == ((UsuarioDireccion) (result.objects.get(result.objects.size()-1))).Usuario.getIdUsuario()) {  
                        
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        
                        ((UsuarioDireccion) (result.objects.get(result.objects.size()-1))).Direcciones.add(direccion);
                        
                    }else{
                        
                        UsuarioDireccion usuariodireccion = new UsuarioDireccion();
                    
                        usuariodireccion.Usuario = new Usuario();
                        usuariodireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuariodireccion.Usuario.setNombreUsuario(resultSet.getString("NombreUsuario"));
                        usuariodireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuariodireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuariodireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        //usuariodireccion.Usuario.setNumeroTelefono(resultSet.getInt("NumeroTelefono"));
                        usuariodireccion.Usuario.setEmail(resultSet.getString("Email"));
                        usuariodireccion.Usuario.setPassword(resultSet.getString("Password"));
                        usuariodireccion.Usuario.setSexo(resultSet.getString("Sexo").charAt(0));
                        usuariodireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        usuariodireccion.Usuario.setCURP(resultSet.getString("CURP"));
                        usuariodireccion.Usuario.setUserName(resultSet.getString("UserName"));
                        usuariodireccion.Usuario.setFotografia(resultSet.getString("Fotografia"));

                        usuariodireccion.Usuario.Rol = new Rol();
                        usuariodireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuariodireccion.Usuario.Rol.setRol(resultSet.getString("Rol"));
                        
                        usuariodireccion.Direcciones = new ArrayList<>();
                        
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getNString("CodigoPostal"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        usuariodireccion.Direcciones.add(direccion);
                        
                        result.objects.add(usuariodireccion);
                    }
                }
                                
                return true;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result UsuarioUptadteSP(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        
            try{
                jdbcTemplate.execute("{CALL UsuarioUpdateSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                    
                    String password = usuarioDireccion.Usuario.getPassword();

                    if (password == null || password.trim().isEmpty()) {
                        UsuarioDireccion usuarioActualDireccion = (UsuarioDireccion) GetAllById(usuarioDireccion.Usuario.getIdUsuario()).object;
                        Usuario usuarioActual = usuarioActualDireccion.Usuario;
                        password = usuarioActual.getPassword();
                    }
                    
                    callableStatement.setInt(1, usuarioDireccion.Usuario.getIdUsuario());
                    callableStatement.setString(2, usuarioDireccion.Usuario.getNombreUsuario());
                    callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoPaterno());
                    callableStatement.setString(4, usuarioDireccion.Usuario.getApellidoMaterno());
                    callableStatement.setDate(5, new java.sql.Date( usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
                    callableStatement.setLong(6, usuarioDireccion.Usuario.getNumeroTelefono());
                    callableStatement.setString(7, usuarioDireccion.Usuario.getEmail());
                    callableStatement.setString(8, password);
                    callableStatement.setString(9, String.valueOf(usuarioDireccion.Usuario.getSexo()));
                    callableStatement.setString(10, usuarioDireccion.Usuario.getCelular());
                    callableStatement.setString(11, usuarioDireccion.Usuario.getCURP());
                    callableStatement.setString(12, usuarioDireccion.Usuario.getUserName());
                    callableStatement.setInt(13, usuarioDireccion.Usuario.Rol.getIdRol());
                    callableStatement.setString(14, usuarioDireccion.Usuario.getFotografia());
                    callableStatement.setInt(15, usuarioDireccion.Usuario.getEstado());
                    
                    int rowAffected = callableStatement.executeUpdate();
            
                    if(rowAffected > 0){
                        result.correct = true;
                    }else{
                        result.correct = false;
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

    @Override
    public Result InsercionMasiva(List<UsuarioDireccion> usuariosDireccion) {
        
        Result result = new Result();
        
        try{
            for(UsuarioDireccion usuarioDireccion : usuariosDireccion){
                this.Add(usuarioDireccion);
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
