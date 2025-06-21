package EJFD.EJFloresProgramacionNCapasMaven2.ML;

public class Rol {
    
    private int IdRol;
    private String Rol;
    
    public Rol(){
    
    }
    
    public Rol(int IdRol, String Rol){
        this.IdRol = IdRol;
        this.Rol = Rol;
    }
    
    public int getIdRol(){
        return IdRol;
    }
    
    public void setIdRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public String getRol(){
        return Rol;
    }
    
    public void setRol(String Rol){
        this.Rol = Rol;
    }
    
}
