package main;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Controlador {

    private static Modelo conexion;

    /**
     * Llama a "Modelo" para conectar con la base.
     */
    public static void conectarBD() {

        try {
            conexion = new Modelo("C:/Users/Joshua/Documents/NetBeansProjects/BD/bbdd.db");

        } catch (SQLException ex) {
        }
    }

    /**
     * Llama a 'QueryTable' con el nombre de la tabla y introduce el modelo de
     * tabla que recibe en el JTable
     *
     * @param tabla
     * @param nombreTabla
     */
    public static void cargarTabla(JTable tabla, String nombreTabla) {

        tabla.setModel(conexion.QueryTable(nombreTabla, ""));
    }

    /**
     * Llama a 'QueryTable' con el nombre de la tabla y introduce el modelo de
     * tabla que recibe en el JTable Sobrecarga que envia una condicion (Where)
     *
     * @param tabla
     * @param nombreTabla
     * @param condicion
     */
    public static void cargarTabla(JTable tabla, String nombreTabla, String condicion) {

        tabla.setModel(conexion.QueryTable(nombreTabla, condicion));
    }

    /**
     * Manda un Query con el Nombre de la tabla y la condición de borrado.
     * Muestra un mensaje del resultado.
     *
     * @param tabla
     */
    public static void borrarRow(JTable tabla) {

        if (tabla.getSelectedRow() != -1) {

            conexion.QueryUpdate(1, "RESTAURANTES", "WHERE CODIGO = " + tabla.getValueAt(tabla.getSelectedRow(), 0) + ";");
            cargarTabla(tabla, "RESTAURANTES");
            JOptionPane.showMessageDialog(null, "El restaurante ha sido borrado correctamente");
        }
    }

    /**
     * Manda un Query con el Nombre de la tabla y la condición de añadido.
     * Muestra un mensaje del resultado.
     *
     * @param tabla
     */
    public static void añadirRow(JTable tabla) {

        conexion.QueryUpdate(2, "RESTAURANTES", "VALUES((SELECT MAX(CODIGO) + 1 FROM RESTAURANTES), '"
                + Vista.tfNombre.getText() + "', "
                + Integer.valueOf(Vista.tfVida.getText()) + ", "
                + Integer.valueOf(Vista.tfNumero.getText()) + ");");
        cargarTabla(tabla, "RESTAURANTES");
        JOptionPane.showMessageDialog(null, "El restaurante ha sido añadido correctamente");
    }

    /**
     * Manda un Query con el Nombre de la tabla y la condición de modificación.
     * Muestra un mensaje del resultado.
     *
     * @param tabla
     */
    public static void actualizarRow(JTable tabla) {

        if (tabla.getSelectedRow() != -1) {

            conexion.QueryUpdate(3, "RESTAURANTES", "SET Nombre = '"
                    + Vista.tfNombre.getText() + "', CP = "
                    + Vista.tfVida.getText() + ", Numero = "
                    + Vista.tfNumero.getText() + " WHERE CODIGO = "
                    + tabla.getValueAt(tabla.getSelectedRow(), 0) + ";");
            cargarTabla(tabla, "RESTAURANTES");
            JOptionPane.showMessageDialog(null, "El restaurante ha sido modificado correctamente");
        }
    }
}
