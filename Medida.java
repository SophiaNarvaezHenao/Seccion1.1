public class Medida {
    private double valor;
    private Categoria categoria;
    private Unidad unidad;
    private enum Categoria {
        LONGITUD, MASA, VOLUMEN
    }
    public enum Unidad {
        M(Categoria.LONGITUD, 1.0, "m"),
        KM(Categoria.LONGITUD, 1000.0, "km"),
        CM(Categoria.LONGITUD, 0.01, "cm"),
        MM(Categoria.LONGITUD, 0.001, "mm"),
        IN(Categoria.LONGITUD, 0.0254, "in"),
        FT(Categoria.LONGITUD, 0.3048, "ft"),

        KG(Categoria.MASA, 1.0, "kg"),
        T(Categoria.MASA, 1000.0, "t"),
        G(Categoria.MASA, 0.001, "g"),
        MG(Categoria.MASA, 0.000001, "mg"),

        L(Categoria.VOLUMEN, 1.0, "L"),
        M3(Categoria.VOLUMEN, 1000.0, "m3"),
        ML(Categoria.VOLUMEN, 0.001, "ml"),
        KM3(Categoria.VOLUMEN, 1e12, "km3"),
        CM3(Categoria.VOLUMEN, 0.001, "cm3");

        private final Categoria categoria;
        private final double factorConver;
        private final String simbolo;

        Unidad(Categoria categoria, double factorConver, String simbolo) {
            this.categoria = categoria;
            this.factorConver = factorConver;
            this.simbolo = simbolo;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public double getFactorConver() {
            return factorConver;
        }

        public String getSimbolo() {
            return simbolo;
        }

        public static Unidad fromString(String s) throws IllegalArgumentException {
            for (Unidad u : values()) {
                if (u.simbolo.equalsIgnoreCase(s)) {
                    return u;
                }
            }
            throw new IllegalArgumentException("Unidad desconocida: " + s);
        }
    }

    public Medida(double valor, String unidadStr) throws IllegalArgumentException {
        this.unidad = Unidad.fromString(unidadStr);
        this.categoria = this.unidad.getCategoria();
        this.valor = valor;
    }

    public Medida(double valor) {
        this.unidad = Unidad.M;
        this.categoria = Categoria.LONGITUD;
        this.valor = valor;
    }

    private Medida(double valor, Unidad unidad) {
        this.valor = valor;
        this.unidad = unidad;
        this.categoria = unidad.getCategoria();
    }

    public Medida convertir(String nuevaUnidadStr) throws ConversionInvalidaException, IllegalArgumentException {
        Unidad nuevaUnidad = Unidad.fromString(nuevaUnidadStr);
        return convertir(nuevaUnidad);
    }

    public Medida convertir(Unidad nuevaUnidad) throws ConversionInvalidaException {
        if (this.categoria != nuevaUnidad.getCategoria()) {
            throw new ConversionInvalidaException("No se puede convertir entre categorías diferentes: " + this.categoria + " a " + nuevaUnidad.getCategoria());
        }
        // Convertir a unidad base
        double valorEnBase = this.valor * this.unidad.getFactorConver();
        // Convertir de unidad base a nueva unidad
        double nuevoValor = valorEnBase / nuevaUnidad.getFactorConver();
        return new Medida(nuevoValor, nuevaUnidad);
    }

    public double getValor() {
        return valor;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return valor + " " + unidad.getSimbolo();
    }
}