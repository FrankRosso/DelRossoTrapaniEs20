package server20;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author ANDREATRAPANI
 */
public class Tessera {
    
    private int id;
    private boolean valida;
    private LocalDateTime aperturaRecente;
    
    public Tessera(int id) {
        this.id = id;
        valida = true;
        aperturaRecente = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValida() {
        Duration diff = Duration.between(getAperturaRecente(), LocalDateTime.now());
        
        if (valida) {
            return true;
        } else return diff.toHours() >= 72;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }

    public LocalDateTime getAperturaRecente() {
        return aperturaRecente;
    }

    public void setAperturaRecente(LocalDateTime aperturaRecente) {
        this.aperturaRecente = aperturaRecente;
    }
    
}
