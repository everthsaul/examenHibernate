import java.sql.*;

public class App {
    private Connection con; // Suponiendo que la conexión ya está establecida

    // a. Mostrar los 3 vehículos más nuevos (Basado en el modelo o placa descendente)
    public void mostrarVehiculosNuevos() {
        String sql = "SELECT * FROM vehiculo ORDER BY modelo DESC LIMIT 3";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("--- Los 3 Vehículos más Nuevos ---");
            while (rs.next()) {
                System.out.println("Placa: " + rs.getString("placa") + 
                                   " | Modelo: " + rs.getString("modelo") + 
                                   " | Tipo: " + rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // b. Mostrar técnicos ordenados descendentemente por su contacto
    public void mostrarTecnicosOrdenados() {
        String sql = "SELECT * FROM tecnico ORDER BY contacto DESC";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Técnicos (Orden Descendente por Contacto) ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   " | Nombre: " + rs.getString("nombre") + " " + rs.getString("apellido") +
                                   " | Contacto: " + rs.getInt("contacto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // c. Mostrar registros de revisión con información detallada (Joins)
    public void mostrarDetalleRevisiones() {
        String sql = "SELECT r.id, " +
                     "CONCAT(t.nombre, ' ', t.apellido) AS tecnico_nombre, " +
                     "v.placa, v.tipo, " +
                     "CONCAT(o.nombre, ' ', o.apellido) AS operador_nombre, " +
                     "r.fecha_revision " +
                     "FROM revision r " +
                     "JOIN tecnico t ON r.encargado = t.id " +
                     "JOIN vehiculo v ON r.vehiculo = v.placa " +
                     "JOIN operador o ON r.chofer = o.id";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- Reporte Detallado de Revisiones ---");
            System.out.printf("%-5s | %-20s | %-15s | %-20s | %-12s%n", 
                              "ID", "Técnico", "Vehículo (Tipo)", "Operador", "Fecha");
            
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-15s | %-20s | %-12s%n",
                    rs.getInt("id"),
                    rs.getString("tecnico_nombre"),
                    rs.getString("placa") + " (" + rs.getString("tipo") + ")",
                    rs.getString("operador_nombre"),
                    rs.getDate("fecha_revision"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
