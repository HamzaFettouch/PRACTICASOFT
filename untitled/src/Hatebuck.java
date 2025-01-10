// Hatebuck.java
import java.util.*;

public class Hatebuck {
    private static List<Usuari> usuaris = new ArrayList<>();
    private static List<Moderador> moderadors = new ArrayList<>();

    public static void main(String[] args) {
        // Crear usuaris per a les proves
        Usuari usuari1 = new Usuari("1", "Hamza", "Hamza123");
        Usuari usuari2 = new Usuari("2", "Marceli", "Marceli123");
        Usuari usuari3 = new Usuari("3", "Alice", "Alice123");
        Usuari usuari4 = new Usuari("4", "Bob", "Bob123");
        Usuari usuari5 = new Usuari("5", "Clara", "Clara123");
        Usuari usuari6 = new Usuari("6", "David", "David123");
        // Crear moderadors
        Moderador moderador1 = new Moderador("7", "Eva", "Eva123");
        Moderador moderador2 = new Moderador("8", "Felix", "Felix123");

        // Afegir usuaris i moderadors
        Collections.addAll(usuaris, usuari1, usuari2, usuari3, usuari4, usuari5, usuari6);
        Collections.addAll(moderadors, moderador1, moderador2);

        // Relacions inicials
        usuari1.afegirUsuariAcceptat("2");
        usuari1.establirRelacio("2", "Amic");
        usuari1.afegirUsuariAcceptat("3");
        usuari1.establirRelacio("3", "Conegut");
        usuari2.afegirUsuariAcceptat("1");
        usuari2.establirRelacio("1", "Conegut");
        usuari3.afegirUsuariAcceptat("1");
        usuari3.establirRelacio("1", "Amic");
        usuari4.afegirUsuariAcceptat("3");
        usuari4.establirRelacio("3", "Conegut");
        usuari5.afegirUsuariAcceptat("4");
        usuari5.establirRelacio("4", "Amic");
        usuari6.afegirUsuariAcceptat("5");
        usuari6.establirRelacio("5", "Saludat");
        usuari1.afegirUsuariAcceptat("4");
        usuari1.establirRelacio("4", "Amic");
        usuari1.afegirUsuariAcceptat("5");
        usuari1.establirRelacio("5", "Conegut");
        usuari1.afegirUsuariAcceptat("6");
        usuari1.establirRelacio("6", "Amic");
        usuari2.afegirUsuariAcceptat("3");
        usuari2.establirRelacio("3", "Conegut");
        usuari2.afegirUsuariAcceptat("4");
        usuari2.establirRelacio("4", "Amic");
        usuari2.afegirUsuariAcceptat("5");
        usuari2.establirRelacio("5", "Conegut");

        // Afegir textos de prova
        usuari1.afegirText(new Text("1", "Hamza", "Text inicial de Hamza", false));
        usuari1.afegirText(new Text("2", "Hamza", "Segon text de Hamza", false));
        usuari2.afegirText(new Text("3", "Marceli", "Text inicial de Marceli", false));
        usuari3.afegirText(new Text("4", "Alice", "Text inicial de Alice", false));
        usuari4.afegirText(new Text("5", "Bob", "Text inicial de Bob", false));
        usuari5.afegirText(new Text("6", "Clara", "Text inicial de Clara", false));
        usuari6.afegirText(new Text("7", "David", "Text inicial de David", false));

        Scanner scanner = new Scanner(System.in);
        Usuari usuariAutenticat = null;

        // Autenticació
        while (usuariAutenticat == null) {
            System.out.print("Introdueix el teu nom d'usuari: ");
            String nom = scanner.nextLine();
            System.out.print("Introdueix la contrasenya: ");
            String contrasenya = scanner.nextLine();

            usuariAutenticat = autenticarUsuari(nom, contrasenya);

            if (usuariAutenticat == null) {
                System.out.println("Usuari o contrasenya incorrectes.");
                System.out.print("Vols tornar a intentar? (S/N): ");
                String resposta = scanner.nextLine().toUpperCase();
                if (resposta.equals("N")) {
                    System.out.println("Finalitzant el programa...");
                    return;
                }
            }
        }

        mostrarMenu(usuariAutenticat, scanner);
    }

    private static void mostrarMenu(Usuari usuari, Scanner scanner) {
        int opcio;
        do {
            System.out.println("\nMenú principal:");
            System.out.println("1. Enviar missatge privat");
            System.out.println("2. Modificar text");
            System.out.println("3. Canviar relació");
            System.out.println("0. Sortir");
            System.out.print("Escull una opció: ");
            opcio = scanner.nextInt();
            scanner.nextLine(); // Consumir línia

            switch (opcio) {
                case 1:
                    System.out.println("Usuaris amb els que pots enviar missatges:");
                    boolean existeix = false;
                    for (Usuari u : usuaris) {
                        if (usuari.potEnviarMissatge(u)) {
                            existeix = true;
                            System.out.println(u.getNom());
                        }
                    }

                    if (!existeix) {
                        System.out.println("No tens cap usuari amb qui enviar missatges.");
                        break;
                    }
                    else{
                        System.out.print("Introdueix l'usuari receptor: ");

                        String receptorNom = scanner.nextLine();
                        Usuari receptor = trobarUsuariNom(receptorNom);
                        if (receptor != null) {
                            System.out.println("Entrant el missatge paraula a paraula...");
                            List<Element> missatge = llegirTextPerElements(scanner);
                            if (usuari.potEnviarMissatge(receptor)) {
                                String missatgeString = construirMissatge(missatge);
                                receptor.rebreMissatge(new MissatgePrivat(usuari, receptor, missatgeString));
                                System.out.println("Missatge enviat a " + receptorNom + ": " + missatgeString);
                                receptor.enviarMissatgePrivat(usuari, missatgeString);
                                receptor.mostraMissatgesRebuts();
                            }
                            else {
                                System.out.println("El receptor no accepta missatges del teu usuari.");
                            }
                        }

                    }
                    break;
                case 2:
                    if (usuari instanceof Moderador) {
                        for (Usuari u : usuaris) { //mostrem els noms dels usuaris que tenim al sistema
                            System.out.println(u.getNom());
                        }
                        System.out.print("Introdueix el nom de l'usuari del text a modificar: ");
                        String nomUsuari = scanner.nextLine();
                        Usuari usuariSeleccionat = trobarUsuariNom(nomUsuari);
                        if (usuariSeleccionat != null) {
                            modificarTextos(usuariSeleccionat, scanner);
                        }

                    }
                    else {
                        modificarTextos(usuari, scanner);
                    }
                    break;

                case 3:
                    System.out.println("Llistat de persones amb les que tens relació:");
                    Map<String, Relacio> relacionsMap = usuari.getRelacionsMap();
                    if (relacionsMap.isEmpty()) {
                        System.out.println("No tens cap relació per modificar.");
                        break;
                    }
                    for (Map.Entry<String, Relacio> entry : relacionsMap.entrySet()) {
                        Usuari relUsuari = trobarUsuari(entry.getKey());
                        if (relUsuari != null) {
                            System.out.println("Usuari: " + relUsuari.getNom() + " - Relació: " + entry.getValue().getTipusRelacio());
                        }
                    }
                    System.out.print("Introdueix el nom de la persona amb qui vols modificar la relació: ");
                    String nomRelacio = scanner.nextLine();
                    Usuari usuariRelacio = trobarUsuariNom(nomRelacio);
                    if (usuariRelacio == null || !relacionsMap.containsKey(usuariRelacio.getId())) {
                        System.out.println("Selecció no vàlida o relació no existent.");
                        break;
                    }
                    System.out.println("Selecciona el tipus de relació:");
                    System.out.println("1. Amic");
                    System.out.println("2. Conegut");
                    System.out.println("3. Saludat");
                    System.out.print("Opció: ");
                    int tipusRelacioOpcio = scanner.nextInt();
                    scanner.nextLine();
                    String tipusRelacio;
                    switch (tipusRelacioOpcio) {
                        case 1:
                            tipusRelacio = "Amic";
                            break;
                        case 2:
                            tipusRelacio = "Conegut";
                            break;
                        case 3:
                            tipusRelacio = "Saludat";
                            break;
                        default:
                            System.out.println("Opció no vàlida. S'establirà com a Saludat per defecte.");
                            tipusRelacio = "Saludat";
                            break;
                    }
                    usuari.establirRelacio(usuariRelacio.getId(), tipusRelacio);
                    System.out.println("Relació establerta amb " + nomRelacio + " com a " + tipusRelacio);
                    System.out.println("Estat actual de les relacions:");
                    for (Map.Entry<String, Relacio> entry : relacionsMap.entrySet()) {
                        Usuari relUsuari = trobarUsuari(entry.getKey());
                        if (relUsuari != null) {
                            System.out.println("Usuari: " + relUsuari.getNom() + " - Relació: " + entry.getValue().getTipusRelacio());
                        }
                    }
                    break;
                case 0:
                    System.out.println("Sortint del programa...");
                    break;
                default:
                    System.out.println("Opció no vàlida.");
                    break;
            }
        } while (opcio != 0);
    }

    private static Usuari trobarUsuariNom(String nom) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNom().equals(nom)) {
                return usuari;
            }
        }
        System.out.println("Usuari no trobat.");
        return null;
    }


    private static Usuari autenticarUsuari(String nom, String contrasenya) {
        for (Moderador moderador : moderadors) {
            if (moderador.autenticar(contrasenya) && moderador.getNom().equals(nom)) {
                return moderador;
            }
        }

        for (Usuari usuari : usuaris) {
            if (usuari.autenticar(contrasenya) && usuari.getNom().equals(nom)) {
                return usuari;
            }
        }
        return null;
    }

    private static void modificarTextos(Usuari usuari, Scanner scanner) {
        System.out.println("Textos disponibles per modificar:");
        List<Text> textos = usuari.getTextos();
        for (Text text : textos) {
            System.out.println("ID: " + text.getId() + " - Contingut: " + text.getContingut());
        }

        System.out.print("Introdueix l'ID del text a modificar: ");
        String idText = scanner.nextLine();

        for (Text text : textos) {
            if (text.getId().equals(idText)) {
                List<Element> nouContingut = llegirTextPerElements(scanner);
                text.setContingut(construirMissatge(nouContingut));
                System.out.println("Text modificat correctament: " + text.getContingut());
                for (Text text1 : textos) {
                    System.out.println("ID: " + text1.getId() + " - Contingut: " + text1.getContingut());
                }
                return;
            }
        }

        System.out.println("No s'ha trobat cap text amb aquest ID.");
    }

    private static Usuari trobarUsuari(String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getId().equals(nomUsuari)) {
                return usuari;
            }
        }
        System.out.println("Usuari no trobat.");
        return null;
    }

    private static List<Element> llegirTextPerElements(Scanner scanner) {
        List<Element> elements = new ArrayList<>();
        String opcio;
        do {
            System.out.println("Vols entrar una paraula (P) o un símbol (S)? (E per acabar): ");
            opcio = scanner.nextLine().toUpperCase();
            if (opcio.equals("P")) {
                System.out.print("Introdueix la paraula: ");
                String paraula = scanner.nextLine();
                System.out.println("És una paraula especial? (S/N): ");
                boolean especial = scanner.nextLine().equalsIgnoreCase("S");
                elements.add(new Paraula(paraula, especial));
            } else if (opcio.equals("S")) {
                System.out.print("Introdueix el símbol: ");
                String simbol = scanner.nextLine();
                elements.add(new SignePuntuacio(simbol));
            }
        } while (!opcio.equals("E"));
        return elements;
    }

    private static String construirMissatge(List<Element> nouContingut) {
        StringBuilder missatge = new StringBuilder();
        for (Element element : nouContingut) {
            missatge.append(element.mostrar()).append(" ");
        }
        return missatge.toString().trim();
    }
}
