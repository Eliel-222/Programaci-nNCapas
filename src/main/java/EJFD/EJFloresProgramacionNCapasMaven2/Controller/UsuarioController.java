package EJFD.EJFloresProgramacionNCapasMaven2.Controller;

import EJFD.EJFloresProgramacionNCapasMaven2.DAO.ColoniaDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.DAO.DireccionDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.DAO.EstadoDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.DAO.MunicipioDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.DAO.PaisDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.DAO.RolDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.DAO.UsuarioDAOImplementation;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Colonia;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Direccion;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Result;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.ResultValidarDatos;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Rol;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.Usuario;
import EJFD.EJFloresProgramacionNCapasMaven2.ML.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Long.parseLong;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;
    
//**********************************************************************************************************************************    
//**********************************************************************************************************************************    
    
    @GetMapping //Carga la vista orincioal del HTML
    public String Index (Model model){
        Result result = usuarioDAOImplementation.GetAll();
        
        if(result.correct){
            model.addAttribute("usuariosDireccion", result.objects);
            model.addAttribute("roles", rolDAOImplementation.GetAllRol().objects);
            model.addAttribute("usuario", new Usuario());
        }
        return "UsuarioIndex";
    }
    
    @PostMapping("/busqueda") //Busca usuarios por ciertos parametros y carga los datos en la tabla
    public String Busqueda(@ModelAttribute Usuario usuario, Model model){
        
        Result result = usuarioDAOImplementation.BusquedaDinamicaSP(usuario);

        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuariosDireccion", result.objects);
        model.addAttribute("roles", rolDAOImplementation.GetAllRol().objects);
        
        return "UsuarioIndex";
    }
    
//**********************************************************************************************************************************    
//**********************************************************************************************************************************    
    
    @GetMapping("Formulario/{idUsuario}")  // Carga información
    public String Formu (@PathVariable("idUsuario") int idUsuario ,Model model){
        
        if(idUsuario < 1){ //Carga solo informacion de pais, rol en el formulario dentro de los selects
            Result result = paisDAOImplementation.GetAllPais();
            model.addAttribute("paises", result.objects);
            model.addAttribute("usuarioDireccion" , new UsuarioDireccion());
            model.addAttribute("roles", rolDAOImplementation.GetAllRol().objects);
            return "Formulario";
            
        }else{//Muestra la información del usuario seleccionado en otro formulario
            Result result = usuarioDAOImplementation.GetAllById(idUsuario);
            model.addAttribute("usuarioDireccion", result.object);
            return "UsuarioDatails";
        }
    }
    
    @PostMapping("Formulario")
    public String Formu(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult bindinResult, Model model,
                        @RequestParam("fotoPerfil") MultipartFile fotoPerfil){
                
            if(bindinResult.hasErrors()){
            model.addAttribute("usuarioDireccion",usuarioDireccion);
            return "Formulario"; //Si existe un error, se retorna al formulario antes de guardar la información.
            }

            try{
                if(!fotoPerfil.isEmpty()){
                    byte[] fotobase64 = fotoPerfil.getBytes();
                    String fotoconvertida = Base64.getEncoder().encodeToString(fotobase64);
                    usuarioDireccion.getUsuario().setFotografia(fotoconvertida);
                }
            }catch(Exception ex){
                System.out.println("Error: "+ex);
                return "Formulario";
            }
            
            Result result = new Result();
            
            if(usuarioDireccion.Usuario.getIdUsuario() == 0 && usuarioDireccion.Direccion.getIdDireccion() == 0){
                result = usuarioDAOImplementation.Add(usuarioDireccion); //Agregar usuario y direccion
                
            }else if(usuarioDireccion.Usuario.getIdUsuario() > 0 && usuarioDireccion.Direccion.getIdDireccion() == -1){
                
                result = usuarioDAOImplementation.UsuarioUptadteSP(usuarioDireccion); //Editar solamente usuario
                
            }else if(usuarioDireccion.Usuario.getIdUsuario() > 0 && usuarioDireccion.Direccion.getIdDireccion() > 0){
                
            }
            
        return "redirect:/usuario";
    }
    
//**********************************************************************************************************************************    
//**********************************************************************************************************************************    
    
    @GetMapping("cargamasiva")
    public String CargaMasiva(){
        
        return "CargaMasiva";
    }
    
    @PostMapping("cargamasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) throws Exception{
         
        if (archivo != null && !archivo.isEmpty()) {

            String fileExtention = archivo.getOriginalFilename().split("\\.")[1];
            
            //Construcción del archivo 
            String root = System.getProperty("user.dir"); 
            String path = "src/main/resources/files";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
            archivo.transferTo(new File(absolutePath));

            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

            if (fileExtention.equals("txt")) {
                usuariosDireccion = LecturaArchivoTXT(archivo);
                
            } else { //"xlsx"
                 usuariosDireccion = LecturaArchivoExcel(new File(absolutePath));
            }
            
            List<ResultValidarDatos> listaErrores = validarDatos(usuariosDireccion);
            
            try{
                if (listaErrores.isEmpty()) {
                    session.setAttribute("path", absolutePath);
                    model.addAttribute("listaErrores", listaErrores);
                    model.addAttribute("archivoCorrecto", true);
                } else {
                    model.addAttribute("listaErrores", listaErrores);
                    model.addAttribute("archivoCorrecto", false);
                }
            }catch(Exception ex){
                System.out.println("Error: "+ex);
            }
        }
        return "CargaMasiva";
    }
    
    
    public List<UsuarioDireccion> LecturaArchivoTXT(MultipartFile archivo) {

        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {

            bufferedReader.readLine();
            String linea = "";

            while ((linea = bufferedReader.readLine()) != null) {                

                String[] datos = linea.split("\\|");
                
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombreUsuario(datos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(datos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(datos[2]);
                String fecha = datos[3];
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaconvertida = formato.parse(fecha);
                usuarioDireccion.Usuario.setFechaNacimiento(fechaconvertida);
                usuarioDireccion.Usuario.setNumeroTelefono(parseLong(datos[4]));
                usuarioDireccion.Usuario.setEmail(datos[5]);
                usuarioDireccion.Usuario.setPassword(datos[6]);
                usuarioDireccion.Usuario.setSexo(String.valueOf(datos[7]).charAt(0));
                usuarioDireccion.Usuario.setCelular(datos[8]);
                usuarioDireccion.Usuario.setCURP(datos[9]);
                usuarioDireccion.Usuario.setUserName(datos[10]);
                usuarioDireccion.Usuario.Rol = new Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(Integer.parseInt(datos[11]));
                usuarioDireccion.Usuario.setFotografia(datos[12]);
                usuarioDireccion.Usuario.setEstado(Integer.parseInt(datos[13]));
                usuarioDireccion.Direccion.setIdDireccion(Integer.parseInt(datos[14]));
                usuarioDireccion.Direccion.setCalle(datos[15]);
                usuarioDireccion.Direccion.setNumeroInterior(datos[16]);
                usuarioDireccion.Direccion.setNumeroExterior(datos[17]);
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(datos[18]));
                
                usuariosDireccion.add(usuarioDireccion);

            }
        } catch (Exception ex) {
            usuariosDireccion = null;
        }
        return usuariosDireccion;
    }
    
    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo){
        
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();
        
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(archivo);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int condicional = 0;
            for(Row row : sheet){
                if(condicional == 0 ){
                    condicional++;
                }else{
                    System.out.println(row.getCell(3));
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombreUsuario(row.getCell(0) != null ? row.getCell(0).toString() : "");
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1) != null ? row.getCell(1).toString() : "");
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2) != null ? row.getCell(2).toString() : "");
                     String fecha = row.getCell(3).toString();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaconvertida = formato.parse(fecha);
                    usuarioDireccion.Usuario.setFechaNacimiento(fechaconvertida);
                    usuarioDireccion.Usuario.setNumeroTelefono((long) row.getCell(4).getNumericCellValue());
                    usuarioDireccion.Usuario.setEmail(row.getCell(5) != null ? row.getCell(5).toString() : "");
                    usuarioDireccion.Usuario.setPassword(row.getCell(6) != null ? row.getCell(6).toString() : "");
                    usuarioDireccion.Usuario.setSexo(String.valueOf(row.getCell(7)).charAt(0));
                    usuarioDireccion.Usuario.setCelular(row.getCell(8) != null ? row.getCell(8).toString() : "");
                    usuarioDireccion.Usuario.setCURP(row.getCell(9) != null ? row.getCell(9).toString() : "");
                    usuarioDireccion.Usuario.setUserName(row.getCell(10) != null ? row.getCell(10).toString() : "");
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol((int)row.getCell(11).getNumericCellValue());
                    usuarioDireccion.Usuario.setFotografia(row.getCell(12).toString());
                    usuarioDireccion.Usuario.setEstado((int)row.getCell(13).getNumericCellValue());
                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(14).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(15).toString());
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(16).toString());
                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(17).getNumericCellValue());

                    usuariosDireccion.add(usuarioDireccion);
                }
            }
            
        }catch(Exception ex){
            System.out.println("Error al capturar los datos: "+ex);
            usuariosDireccion.clear();
        }
        
        return usuariosDireccion;
    }
    
    public List<ResultValidarDatos> validarDatos(List<UsuarioDireccion> usuarios){
        List<ResultValidarDatos> listaErrores = new ArrayList<>();
        
        int fila = 1;
        
        if(usuarios == null){
            listaErrores.add(new ResultValidarDatos(0, "Lista Inexistente", "La lista no se cargo correctamente"));
        }else if(usuarios.isEmpty()){
            listaErrores.add(new ResultValidarDatos(0, "Lista vacia", "La lista no tiene datos"));
        }else{
            
            for(UsuarioDireccion usuarioDireccion : usuarios){
                if(usuarioDireccion.Usuario.getNombreUsuario() == null || usuarioDireccion.Usuario.getNombreUsuario().equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getNombreUsuario(), "Error: El campo 'Nombre' no puede estar vacio"));
                } else if(!usuarioDireccion.Usuario.getNombreUsuario().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ]+( [A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getNombreUsuario(), "Error: El campo 'Nombre' no es valido por un caracter especial, espacio o número"));
                }
                
                if(usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "Error: El campo 'Apellido Paterno' no puede estar vacio"));
                } else if(!usuarioDireccion.Usuario.getApellidoPaterno().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ]+( [A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "Error: El campo 'Apellido Paterno' no es valido por un caracter especial, espacio o número"));
                }
                
                if(usuarioDireccion.Usuario.getApellidoMaterno() != null || !usuarioDireccion.Usuario.getApellidoMaterno().isEmpty()){
                    if(!usuarioDireccion.Usuario.getApellidoMaterno().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ]+( [A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$")){
                        listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getApellidoMaterno(), "Error: El campo 'Apellido Paterno' no puede estar vacio"));
                    }
                }
                
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = formato.format(usuarioDireccion.Usuario.getFechaNacimiento());
                if(usuarioDireccion.Usuario.getFechaNacimiento() == null || usuarioDireccion.Usuario.getFechaNacimiento().equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, fecha , "Error: El campo 'Fecha de Nacimiento' no puede estar vacio o no cumple con el formato: yyyy-MM-dd"));
                }
                
                if(Long.toString(usuarioDireccion.Usuario.getNumeroTelefono()) == null || Long.toString(usuarioDireccion.Usuario.getNumeroTelefono()).equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, Long.toString(usuarioDireccion.Usuario.getNumeroTelefono()), "Error: El campo 'Numero de telefono' no debe se estar vacio"));
                } else if(!Long.toString(usuarioDireccion.Usuario.getNumeroTelefono()).matches("^\\d{10}$")){
                    listaErrores.add(new ResultValidarDatos(fila, Long.toString(usuarioDireccion.Usuario.getNumeroTelefono()), "Error: El campo 'Numero de telefono' solo admite 10 números"));
                }
                
                if(usuarioDireccion.Usuario.getEmail() == null || usuarioDireccion.Usuario.getEmail().equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getEmail(), "Error: El campo 'Correo Electronico' no puede estar vacio"));
                }else if(!usuarioDireccion.Usuario.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z]+\\.[a-zA-Z]{1,4}$")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getEmail(), "Error: El campo 'Correo Electronico' no es valido."));
                }
                
                if(usuarioDireccion.Usuario.getPassword() == null || usuarioDireccion.Usuario.getPassword().equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getPassword(), "Error: El campo 'Contraseña' no puede estar vacio"));
                }else if(!usuarioDireccion.Usuario.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getPassword(), "Error: El campo 'Contraseña' no cumple con las especificaciones. Debe de contener al menos una letra mayuscula, una minuscula, un número y un caracter especial con una longitud mínima de 8 digitos."));
                }
                
                if(Character.toString(usuarioDireccion.Usuario.getSexo()) == null || Character.toString(usuarioDireccion.Usuario.getSexo()).equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, Character.toString(usuarioDireccion.Usuario.getSexo()), "Error: El campo 'Sexo' no puede estar vacio"));
                }else if(!Character.toString(usuarioDireccion.Usuario.getSexo()).matches("^[MH]$")){
                    listaErrores.add(new ResultValidarDatos(fila, Character.toString(usuarioDireccion.Usuario.getSexo()), "Error: El campo 'Sexo' solo puede ser H o M."));
                }
                
                if(usuarioDireccion.Usuario.getCelular() != null && !usuarioDireccion.Usuario.getCelular().isEmpty()){
                    if(!usuarioDireccion.Usuario.getCelular().matches("^\\d{10}$")){
                        listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getCelular(), "Error: El campo 'Número de Celular' solo puedo tener 10 números."));
                    }
                }
                
                if(usuarioDireccion.Usuario.getCURP() != null && !usuarioDireccion.Usuario.getCURP().isEmpty()){
                    if(!usuarioDireccion.Usuario.getCURP().matches("^[A-Z]{4}\\d{6}[A-Z]{6}\\d{2}$")){
                        listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getCURP(), "Error: El campo 'CURP' no es valido"));
                    }   
                }
                
                if(usuarioDireccion.Usuario.getUserName() == null || usuarioDireccion.Usuario.getUserName().equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getUserName(), "Error: El campo 'Nombre de Usuario' no puede estar vacio"));
                }else if(!usuarioDireccion.Usuario.getUserName().matches("^[a-zA-Z0-9._ -]{2,20}$")){
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getUserName(), "Error: El campo 'Nombre de Usuario' no cumple con las especificaciones"));                    
                }
                
                if(String.valueOf(usuarioDireccion.Usuario.Rol.getIdRol()) == null || String.valueOf(usuarioDireccion.Usuario.Rol.getIdRol()).equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, Integer.toString(usuarioDireccion.Usuario.Rol.getIdRol()), "Error: El campo 'Id Rol' no debe se estar vacio"));
                } else if(!String.valueOf(usuarioDireccion.Usuario.Rol.getIdRol()).matches("^\\d{1,4}$")){
                    listaErrores.add(new ResultValidarDatos(fila,String.valueOf(usuarioDireccion.Usuario.Rol.getIdRol()), "Error: El campo 'Id Rol' solo admite números"));
                }
                
                if(String.valueOf(usuarioDireccion.Usuario.getEstado()) == null || String.valueOf(usuarioDireccion.Usuario.getEstado()).equals("")){
                    listaErrores.add(new ResultValidarDatos(fila, Integer.toString(usuarioDireccion.Usuario.getEstado()), "Error: El campo 'Estado' no debe se estar vacio"));
                } else if(!String.valueOf(usuarioDireccion.Usuario.getEstado()).matches("^[01]$")){
                    listaErrores.add(new ResultValidarDatos(fila,String.valueOf(usuarioDireccion.Usuario.getEstado()), "Error: El campo 'Estado' solo admite un 0 o un 1"));
                }
            }
        }
        
        return listaErrores;
    } 
    
    @GetMapping("/cargamasiva/procesar")
    public String procesarCargaMasiva(HttpSession session){
        
        String ruta = session.getAttribute("path").toString();
        String tipoarchivo = session.getAttribute("path").toString().split("\\.")[1];
        
        if(ruta != null && tipoarchivo != null){
            File archivo = new File(ruta);
            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();
            
            if(tipoarchivo == "txt"){
                usuariosDireccion = LecturaArchivoTXT((MultipartFile) archivo); //Se debe de corregis ese casteo
            }
            if(tipoarchivo.equals("xlsx")){
                usuariosDireccion = LecturaArchivoExcel(archivo);
            }
            
            usuarioDAOImplementation.InsercionMasiva(usuariosDireccion);
        }
        
        session.removeAttribute("path");
        
        return "CargaMasiva";
    }
    
//**********************************************************************************************************************************    
//**********************************************************************************************************************************    
    
    @GetMapping("/EstadosByPais/{idPais}")
    @ResponseBody 
    public Result AllEstados(@PathVariable("idPais") int idPais){
        return estadoDAOImplementation.GetAllEstados(idPais);
    }
    
    @GetMapping("/MunicipiosByEstado/{idEstado}")
    @ResponseBody
    public Result AllMunicipios(@PathVariable("idEstado") int idEstado){
        return municipioDAOImplementation.GetAllMunicipios(idEstado);
    }
    
    @GetMapping("/ColoniasByMunicipio/{idMunicipio}")
    @ResponseBody
    public Result AllColonias(@PathVariable("idMunicipio") int idMunicipio){
        return coloniaDAOImplementation.GetAllColonias(idMunicipio);
    }
    
    
//***************************************************************************************************************************    
//***************************************************************************************************************************    
    
    @GetMapping("/formeditable")
    public String EditarDireccion(@RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion, Model model){
        
        
        if(IdDireccion == -1){ // ---->>> EDITAR USUARIO
            
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.GetAllById(IdUsuario).object;
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("usuarioDireccion", usuarioDireccion); //Se debe de colocar el IdUsuario para recuperar la información y mostrarla en el formulario
            model.addAttribute("roles", rolDAOImplementation.GetAllRol().objects);
        
        }else if(IdDireccion == 0){ // ----->> AGREGAR DIRECCION
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(0);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            
        }else{  // ---->> EDITAR DIRECCION 
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion = (Direccion) direccionDAOImplementation.DireccionesAllSP(IdDireccion).object;
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("direccion", usuarioDireccion.Direccion);
            
            //Recuperar datos de las tablas de pais, estado, municipio y colonia.
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            model.addAttribute("estados", estadoDAOImplementation.GetAllEstados(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
            model.addAttribute("municipios", municipioDAOImplementation.GetAllMunicipios(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado()).objects);
            model.addAttribute("colonia", coloniaDAOImplementation.GetAllColonias(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);
        }
        return "Formulario";
    }

}
