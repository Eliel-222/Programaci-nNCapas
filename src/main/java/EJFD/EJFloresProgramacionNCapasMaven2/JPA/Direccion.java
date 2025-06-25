package EJFD.EJFloresProgramacionNCapasMaven2.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "Direccion")
public class Direccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDireccion")
    private int IdDireccion;
    
    @Column(name = "Calle")
    private String Calle;
    
    @Column(name = "NumeroInterior")
    private String NumeroInterior;
    
    @Column(name = "NumeroExterior")
    private String NumeroExterior;
    
    @JoinColumn(name = "")
    @ManyToOne
    public Colonia Colonia;
    
    @JoinColumn(name = "IdUsuario")
    @ManyToOne
    public Usuario Usuario;
    
    public Direccion(){
        
    }
    
    public Direccion(int IdDireccion, String Calle, String NumeroInterior, String NumeroExterior){
        this.IdDireccion = IdDireccion;
        this.Calle = Calle;
        this.NumeroInterior = NumeroInterior;
        this.NumeroExterior = NumeroExterior;
    }
    
    public int getIdDireccion(){
        return IdDireccion;
    }
    
    public void setIdDireccion(int IdDireccion){
        this.IdDireccion = IdDireccion;
    }
    
    public String getCalle(){
        return Calle;
    }
    
    public void setCalle(String Calle){
        this.Calle = Calle;
    }
    
    public String getNumeroInterior(){
        return NumeroInterior;
    }
    
    public void setNumeroInterior(String NumeroInterior){
        this.NumeroInterior = NumeroInterior;
    }
    
    public String getNumeroExterior(){
        return NumeroExterior;
    }
    
    public void setNumeroExterior(String NumeroExterior){
        this.NumeroExterior = NumeroExterior;
    }
    
    public Colonia getColonia(){
        return Colonia;
    }
    
    public void setColonia(Colonia Colonia){
        this.Colonia = Colonia;
    }
    
    public Usuario getUsuario(){
        return Usuario;
    }
    
    public void setUsuario(Usuario Usuario){
        this.Usuario = Usuario;
    }

}
