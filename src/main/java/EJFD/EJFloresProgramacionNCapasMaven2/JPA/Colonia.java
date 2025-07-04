package EJFD.EJFloresProgramacionNCapasMaven2.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "COLONIA")
public class Colonia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    private int IdColonia;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @Column(name = "codigopostal")
    private String CodigoPostal;
    
    @JoinColumn(name = "idmunicipio")
    @ManyToOne
    public Municipio Municipio;
    
    public Colonia(){
        
    }
    
    public Colonia(int IdColonia, String Nombre, String CodigoPostal){
        this.IdColonia = IdColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
    }
    
    public int getIdColonia(){
        return IdColonia;
    }
    
    public void setIdColonia(int IdColonia){
        this.IdColonia = IdColonia;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
}
