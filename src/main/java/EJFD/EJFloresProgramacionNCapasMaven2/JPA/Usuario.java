package EJFD.EJFloresProgramacionNCapasMaven2.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USUARIO")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    
    //@Size(min=3, max=15, message = "El nombre debe de conener entre 3 y 15 letras")
    //@NotEmpty (message = "Ingresa dato: @")
    @Column(name = "nombreusuario")
    private String NombreUsuario;
    
    //@Size(min=3, max=15, message = "El apellido paterno debe de conener entre 3 y 15 letras")
    //@NotEmpty (message = "Ingresa dato: @")
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    
    //@Size(min=3, max=15, message = "El apellido materno debe de conener entre 3 y 15 letras")
    //@NotEmpty (message = "Ingresa dato: @")
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimiento")
    private Date FechaNacimiento;
    
    //@Digits(integer = 10, fraction = 0, message = "Debe tener exactamente 10 dígitos")
    @Column(name = "numerotelefono")
    private long NumeroTelefono;
    
    //@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Correo invalido")
    @Column(name = "email")
    private String Email;
    
    //@Pattern(regexp = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$/", message = "Debe de tener Mayusculas, minusculas, numeros y un caracter especial")
    @Column(name = "password")
    private String Password;
    
    //@NotNull(message = "Selecciona una opción")
    @Column(name = "sexo")
    private char Sexo;
    
    //@Pattern(regexp = "/^\\d{10}$/", message = "Numero invalido")
    @Column(name = "celular")
    private String Celular;
    
    @Column(name = "curp")
    private String CURP;
    
    //@NotEmpty(message = "Escribe un nombre de usuario")
    @Column(name = "username")
    private String UserName;
    
    @Lob
    @Column(name = "fotografia")
    private String Fotografia;
    
    @Column(name = "estado")
    private int Estado;
    
    @JoinColumn(name = "idrol")
    @ManyToOne
    public Rol Rol;
    
    public Usuario(){
    
    }
    
    public Usuario(int IdUsuario, String NombreUsuario, String ApellidoPaterno, String ApellidoMaterno, Date FechaNacimiento, long NumeroTelefono,
                    String Email, String Password, char Sexo, String Celular, String CURP, String UserName, String Fotografia, int Estado){
        
        this.IdUsuario = IdUsuario;
        this.NombreUsuario = NombreUsuario;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.FechaNacimiento = FechaNacimiento;
        this.NumeroTelefono = NumeroTelefono;
        this.Email = Email;
        this.Password = Password;
        this.Sexo = Sexo;
        this.Celular = Celular;
        this.CURP = CURP;
        this.UserName = UserName;
        this.Fotografia = Fotografia;
        this.Estado = Estado;
    }

    
    public int getIdUsuario(){
        return IdUsuario;
    }
    
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public String getNombreUsuario(){
        return NombreUsuario;
    }
    
    public void setNombreUsuario(String NombreUsuario){
        this.NombreUsuario = NombreUsuario;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public Date getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public void setFechaNacimiento(Date FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }
    
    public long getNumeroTelefono(){
        return NumeroTelefono;
    }
    
    public void setNumeroTelefono(long NumeroTelefono){
        this.NumeroTelefono = NumeroTelefono;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    } 
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public char getSexo (){
        return Sexo;
    }
    
    public void setSexo(char Sexo){
        this.Sexo = Sexo;
    }
    
    public String getCelular (){
        return Celular;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }

    public String getFotografia() {
        return Fotografia;
    }

    public void setFotografia(String Fotografia) {
        this.Fotografia = Fotografia;
    }
    
    public int getEstado(){
        return Estado;
    }
    
    public void setEstado(int Estado){
        this.Estado = Estado;
    }
    
    public Rol getRol(){
        return Rol;
    }
    
    public void setRol(Rol Rol){
        this.Rol = Rol;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

}
