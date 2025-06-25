package EJFD.EJFloresProgramacionNCapasMaven2.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Rol")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRol")
    private int IdRol;
    
    @Column(name = "Rol")
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
