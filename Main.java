import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al sistema de conversión de unidades! :D");

        try {
            System.out.print("Que tipo de unidad quieres convertir? (Longitud, Masa, Volumen): ");
            String tipo = scanner.nextLine().trim().toLowerCase();

            String categ;
            String predet;

            if (tipo.equals("longitud")) {
                categ = "longitud";
                predet = "m";
            } else if (tipo.equals("masa")) {
                categ = "masa";
                predet = "kg";
            } else if (tipo.equals("volumen")) {
                categ= "volumen";
                predet = "L";
            } else {
                throw new IllegalArgumentException("Tipo de unidad inválida.");
            }

            System.out.print("Por favor ingresa tu " + categ + " inicial, opcionalmente con su unidad (Separado por un espacio)");
            System.out.println();
            System.out.println("ejem: 100 M, 100 KG, 100 L");
            String input = scanner.nextLine().trim();

            String unidadInicial = "";
            int i = input.length() - 1;
            while (i >= 0 && Character.isLetter(input.charAt(i))) {
                unidadInicial = input.charAt(i) + unidadInicial;
                i--;
            }

            String valorStr = input.substring(0, i + 1).trim();

            if (unidadInicial.isEmpty()) {
                unidadInicial = predet;
            }

            valorStr = valorStr.replace(",", ".");
            double valor = Double.parseDouble(valorStr);

            Medida medida = new Medida(valor, unidadInicial);
            System.out.println("Medida creada: " + medida);

            System.out.print("Ingresa la unidad a la que quieres convertir ");
            System.out.println();
            System.out.println("ejem: M, KG,  L");
            String unidadDestino = scanner.nextLine().trim();

            Medida.Unidad destUnidad = Medida.Unidad.fromString(unidadDestino);

            if (medida.getUnidad().getSimbolo().equalsIgnoreCase(destUnidad.getSimbolo())) {
                throw new ConversionInvalidaException("La unidad no puede convertirse a si misma");
            }

            Medida medidaConvertida = medida.convertir(unidadDestino);
            System.out.println("Resultado de la conversión: " + medidaConvertida);

        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de valor inválido.");
        } catch (ConversionInvalidaException e) {
            System.out.println("Error de conversión: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Unidad inválida. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}