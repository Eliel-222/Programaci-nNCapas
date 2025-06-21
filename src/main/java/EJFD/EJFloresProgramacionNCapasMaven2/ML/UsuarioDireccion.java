package EJFD.EJFloresProgramacionNCapasMaven2.ML;

import jakarta.validation.Valid;
import java.util.List;

public class UsuarioDireccion {

    @Valid
    public Usuario Usuario;
    public Direccion Direccion;
    public List<Direccion> Direcciones;

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    public Direccion getDireccion() {
        return Direccion;
    }

    public void setDireccion(Direccion Direccion) {
        this.Direccion = Direccion;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

}
