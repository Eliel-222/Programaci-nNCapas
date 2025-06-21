package EJFD.EJFloresProgramacionNCapasMaven2.ML;

public class ResultValidarDatos {
    private int fila;
    private String texto;
    private String mensaje;
    
    public ResultValidarDatos(){
        
    }
    
    public ResultValidarDatos(int fila, String texto, String mensaje){
        this.fila = fila;
        this.texto = texto;
        this.mensaje = mensaje;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
