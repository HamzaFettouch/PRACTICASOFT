public class Paraula extends Element {
    private String contingut;
    private boolean tipus; // 1: Normal, 2: Especial

    public Paraula(String contingut, boolean tipus) {
        this.contingut = contingut;
        this.tipus = tipus;
    }

    @Override
    public String mostrar() {
        if (tipus) {
            return "[" + contingut + "]";
        } else {
            return contingut;
        }
    }
}