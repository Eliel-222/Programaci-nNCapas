package EJFD.EJFloresProgramacionNCapasMaven2.ML;

public class Municipio {
    
    private int IdMunicipio;
    private String Nombre;
    public Estado Estado;
    
    public Municipio(){
        
    }
    
    public Municipio(int IdMunicipio, String Nombre){
        this.IdMunicipio = IdMunicipio;
        this.Nombre = Nombre;
    }
    
    public int getIdMunicipio(){
        return IdMunicipio;
    }
    
    public void setIdMunicipio(int IdMunicipio){
        this.IdMunicipio = IdMunicipio;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public Estado getEstado(){
        return Estado;
    }
    
    public void setEstado(Estado Estado){
        this.Estado = Estado;
    }
}
