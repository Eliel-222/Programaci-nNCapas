package EJFD.EJFloresProgramacionNCapasMaven2.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Pais")
public class Pais {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPais")
    private int IdPais;
    
    @Column(name = "Nombre")
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
