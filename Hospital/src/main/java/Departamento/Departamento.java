package Departamento;
/**
 *
 * @author Pauleth
 */
public class Departamento {
    private  String Departamento_id;
    private String nombreDepartamento;
    private String locazionDepartamento;

    public Departamento(String Departamento_id, String nombreDepartamento, String locazionDepartamento) {
        this.Departamento_id = Departamento_id;
        this.nombreDepartamento = nombreDepartamento;
        this.locazionDepartamento = locazionDepartamento;
    }

    public String getDepartamento_id() {
        return Departamento_id;
    }

    public void setDepartamento_id(String Departamento_id) {
        this.Departamento_id = Departamento_id;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getLocazionDepartamento() {
        return locazionDepartamento;
    }

    public void setLocazionDepartamento(String locazionDepartamento) {
        this.locazionDepartamento = locazionDepartamento;
    }
 
  }
 

           
