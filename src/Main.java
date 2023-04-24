import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class Main {


    public static void ventasNYC(List<Ventas> ventasList) {
        ventasList.stream()
                .filter(v -> v.getCity().equals("NYC"))
                .forEach(v -> {
                    System.out.print("orderNumber: " + v.getOrderNumber() + ", ");
                    System.out.print("quantityOrdered: " + v.getQuantityOrdered() + ", ");
                    System.out.print("priceEach: " + v.getPriceEach() + ", ");
                    System.out.print("orderLineNumber: " + v.getOrderLineNumber() + ", ");
                    System.out.print("sales: " + v.getSales() + ", ");
                    System.out.print("orderDate: " + v.getOrderDate() + ", ");
                    System.out.print("status: " + v.getStatus() + ", ");
                    System.out.print("qtr_id: " + v.getQtrId() + ", ");
                    System.out.print("month_id: " + v.getMonthId() + ", ");
                    System.out.print("year_id: " + v.getYearId() + ", ");
                    System.out.print("productLine: " + v.getProductLine() + ", ");
                    System.out.print("msrp: " + v.getMsrp() + ", ");
                    System.out.print("productCode: " + v.getProductCode() + ", ");
                    System.out.print("customerName: " + v.getCustomerName() + ", ");
                    System.out.print("phone: " + v.getPhone() + ", ");
                    System.out.print("adressLine1: " + v.getAddressLine1() + ", ");
                    System.out.print("adressLine2: " + v.getAddressLine2() + ", ");
                    System.out.print("city: " + v.getCity() + ", ");
                    System.out.print("state: " + v.getState() + ", ");
                    System.out.print("postalCode: " + v.getPostalCode() + ", ");
                    System.out.print("country: " + v.getCountry() + ", ");
                    System.out.print("territory: " + v.getTerritory() + ", ");
                    System.out.print("contactLastName: " + v.getContactLastName() + ", ");
                    System.out.print("contactFirstName: " + v.getContactFirstName() + ", ");
                    System.out.println("dealSize: " + v.getDealSize());
                });
    }




    public static double ventasNewYork(List<Ventas> ventasList, String city) {
        return ventasList.stream()
                .filter(v -> {
                    return v.getCity().equals(city);
                })
                .mapToDouble(v -> v.getSales())
                .sum();
    }

    public static int carrosClasicosNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(v-> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Classic Cars");
                })
                .mapToInt(v-> v.getQuantityOrdered())
                .sum();

    }

    public static double ventasCarrosClasicosNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(v-> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Classic Cars");
                })
                .mapToDouble(v-> v.getSales())
                .sum();

    }


    public static int motocicletasNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(v -> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Motorcycles");
                })
                .mapToInt(v -> v.getQuantityOrdered())
                .sum();
    }

    public static double ventasMotocicletasNYC(List<Ventas> ventasList, String city){
        return ventasList.stream()
                .filter(v -> {
                    return v.getCity().equals(city) && v.getProductLine().equals("Motorcycles");
                })
                .mapToDouble(v -> v.getSales())
                .sum();
    }



    public static String clienteMasAutosComproEnNY(List<Ventas> ventasList) {
        Map<String, Integer> cantidadAutosPorCliente = new HashMap<>();
        ventasList.stream()
                .filter(v -> v.getCity().equals("NYC") && (v.getProductLine().equals("Classic Cars") || v.getProductLine().equals("Vintage Cars")))
                .forEach(v -> {
                    String cliente = v.getCustomerName();
                    Integer cantidadAutos = v.getQuantityOrdered();
                    cantidadAutosPorCliente.put(cliente, cantidadAutosPorCliente.getOrDefault(cliente, 0) + cantidadAutos);
                });
        return cantidadAutosPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public static String clienteMasCompro(List<Ventas> ventasList) {
        Map<String, Integer> cantidadPorCliente = new HashMap<>();
        ventasList.forEach(v -> {
            String cliente = v.getCustomerName();
            Integer cantidad = v.getQuantityOrdered();
            cantidadPorCliente.put(cliente, cantidadPorCliente.getOrDefault(cliente, 0) + cantidad);
        });
        return cantidadPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay cliente que compró más");
    }

    public static String clienteMenosCompro(List<Ventas> ventasList) {
        Map<String, Integer> cantidadPorCliente = new HashMap<>();
        ventasList.forEach(v -> {
            String cliente = v.getCustomerName();
            Integer cantidad = v.getQuantityOrdered();
            cantidadPorCliente.put(cliente, cantidadPorCliente.getOrDefault(cliente, 0) + cantidad);
        });
        return cantidadPorCliente.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay un cliente que compró menos");
    }




    public static List<Ventas> readVentasFromFile(String fileName) throws IOException {
        List<Ventas> ventasList = new ArrayList<>();        //Creando una colección de reducción mutable

        Pattern pattern = Pattern.compile("^(\\d+),\"(\\d+)\",\"(\\d+\\.\\d+)\",(\\d+),(\\d+\\.\\d+),\"([\\d/]+)\",\"(\\w+)\",(\\d+),(\\d+),(\\d+),\"(.+)\",(\\d+),\"(\\w+)\",\"(.+)\",\"(.+)\",\"(.+)\",\"(.+)\",\"(.+)\",(\\w+),(\\w+),(\\d+),\"(.+)\",\"(.+)\",\"(.+)\",(\\d+\\.\\d+)\",(\\w+)\"(.+)\",\"(.+)\",\"(\\w+)\"$");


        //Las proximas lineas abren el archivo .csv y guarda los datos usando los setters de cada atributo
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            ventasList = br.lines()
                    .skip(1) // skip header
                    .map(line -> {
                        Ventas ventas = new Ventas();
                        String[] fields = pattern.matcher(line).replaceAll("$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$21,$22,$23,$24,$25").split(",");
                        ventas.setOrderNumber(Integer.parseInt(fields[0]));
                        ventas.setQuantityOrdered(Integer.parseInt(fields[1]));
                        ventas.setPriceEach(Double.parseDouble(fields[2]));
                        ventas.setOrderLineNumber(Integer.parseInt(fields[3]));
                        ventas.setSales(Double.parseDouble(fields[4]));
                        ventas.setOrderDate(fields[5]);
                        ventas.setStatus(fields[6]);
                        ventas.setQtrId(Integer.parseInt(fields[7]));
                        ventas.setMonthId(Integer.parseInt(fields[8]));
                        ventas.setYearId(Integer.parseInt(fields[9]));
                        ventas.setProductLine(fields[10]);
                        ventas.setMsrp(Integer.parseInt(fields[11]));
                        ventas.setProductCode(fields[12]);
                        ventas.setCustomerName(fields[13]);
                        ventas.setPhone(fields[14]);
                        ventas.setAddressLine1(fields[15]);
                        ventas.setAddressLine2(fields[16]);
                        ventas.setCity(fields[17]);
                        ventas.setState(fields[18]);
                        ventas.setPostalCode(fields[19]);
                        ventas.setCountry(fields[20]);
                        ventas.setTerritory(fields[21]);
                        ventas.setContactLastName(fields[22]);
                        ventas.setContactFirstName(fields[23]);
                        ventas.setDealSize(fields[24]);
                        return ventas;
                    })
                    .toList();
        }

        return ventasList;
    }


public static void main(String[] args) throws IOException {
    List<Ventas> ventasList = Main.readVentasFromFile("C:\\Users\\Simon\\Documents\\S2\\Lenguajes\\Java\\Practica2\\src\\sales_data.csv");      //Esta linea busca el archivo .csv

    Main.ventasNYC(ventasList);
    System.out.println("El total de ventas de New York es de: " + Main.ventasNewYork(ventasList, "NYC"));
    System.out.println("New York vendió " + Main.carrosClasicosNYC(ventasList, "NYC") + " autos clásicos");
    System.out.println("El total de ventas de autos clásicos en New York es de : " + Main.ventasCarrosClasicosNYC(ventasList, "NYC"));
    System.out.println("New York vendió " + Main.motocicletasNYC(ventasList, "NYC") + " motocicletas");
    System.out.println("El total de ventas de motocicletas en New York es de : " + Main.ventasMotocicletasNYC(ventasList, "NYC"));
    System.out.println("El cliente que más autos compró en New York es: " + Main.clienteMasAutosComproEnNY(ventasList));
    System.out.println("El cliente que más compró es: " + Main.clienteMasCompro(ventasList));
    System.out.println("El cliente que menos compró es: " + Main.clienteMenosCompro(ventasList));

    }
}
