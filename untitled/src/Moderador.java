public class Moderador extends Usuari{
    private String id;
    private String nom;
    private String contrasenya;


    public Moderador(String id, String nom, String contrasenya) {
        super(id, nom, contrasenya);
        this.id = id;
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    public void canviarContrasenya(String novaContrasenya) {
        this.contrasenya = novaContrasenya;
    }


    public String getNom() {
        return nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public boolean autenticar(String contrasenya) {
        return this.contrasenya.equals(contrasenya);
    }
}
