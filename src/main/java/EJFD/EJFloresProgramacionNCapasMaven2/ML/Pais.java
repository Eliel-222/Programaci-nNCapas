package EJFD.EJFloresProgramacionNCapasMaven2.ML;

public class Pais {
    private int IdPais;
    private String Nombre;
    
    public Pais(){
    
    }
    
    public Pais(int IdPais, String Nombre){
        this.IdPais = IdPais;
        this.Nombre = Nombre;
    }
    
    public int getIdPais(){
        return IdPais;
    }
    
    public void setIdPais(int IdPais){
        this.IdPais = IdPais;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
}
