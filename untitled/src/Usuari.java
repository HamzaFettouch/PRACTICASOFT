import java.util.*;


public class Usuari {
    private String id;
    private String nom;
    private String contrasenya;
    private Map<String, Relacio> relacions = new HashMap<>();
    private List<Text> textos = new ArrayList<>();
    private Set<String> usuarisAcceptats = new HashSet<>();
    private Set<String> usuarisBloquejats = new HashSet<>();
    private List<MissatgePrivat> missatgesRebuts = new ArrayList<>();



    public Usuari(String id, String nom, String contrasenya) {
        this.id = id;
        this.nom = nom;
        this.contrasenya = contrasenya;
        this.missatgesRebuts = new ArrayList<>();
    }

    public boolean autenticar(String contrasenya) {
        return this.contrasenya.equals(contrasenya);
    }

    public String getNom() {
        return nom;
    }

    public void afegirUsuariAcceptat(String usuariId) {
        usuarisAcceptats.add(usuariId);
    }

    public boolean potEnviarMissatge(Usuari receptor) {
        return receptor.usuarisAcceptats.contains(this.id);
    }

    public void enviarMissatgePrivat(Usuari receptor, String contingut) {
        if (potEnviarMissatge(receptor)) {
            MissatgePrivat missatge = new MissatgePrivat(this, receptor, contingut);
            receptor.rebreMissatge(missatge);
        } else {
            System.out.println("No tens permís per enviar aquest missatge.");
        }
    }

    void rebreMissatge(MissatgePrivat missatge) {
        missatgesRebuts.add(missatge);
    }

    public void afegirText(Text text) {
        textos.add(text);
    }

    public boolean potModificarText(String idText) {
        for (Text text : textos) {
            if (text.getId().equals(idText)) {
                return true;
            }
        }
        return false;
    }

    public void modificarText(String idText, String nouContingut) {
        for (Text text : textos) {
            if (text.getId().equals(idText)) {
                text.setContingut(nouContingut);
                break;
            }
        }
    }

    public void establirRelacio(String idUsuari, String tipusRelacio) {
        relacions.put(idUsuari, new Relacio(tipusRelacio));
    }

    public List<Text> getTextos() {
        return textos;
    }

    public Map<String,Relacio> getRelacionsMap() {
        return relacions;
    }

    public String getId() {
        return id;
    }


    public void mostraMissatgesRebuts() {
        System.out.println("Missatges rebuts:");
        for (MissatgePrivat missatge : missatgesRebuts) {
            System.out.println(missatge.getEmissor().getNom() + ": " + missatge.getContingut());
        }
    }
}
