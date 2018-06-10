package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class Modelo {

    private final Connection conexion;
    private final Statement prep;
    private ResultSet consulta;

    public Modelo(String ruta) throws SQLException {

        conexion = DriverManager.getConnection("jdbc:sqlite:" + ruta);
        prep = conexion.createStatement();
    }

    public void Query(String query) {

        try {
            consulta = prep.executeQuery(query);

        } catch (SQLException ex) {
        }
    }

    public void QueryUpdate(int modo, String nombreTabla, String condicion) {

        try {
            switch (modo) {
                case 1:
                    prep.executeUpdate("DELETE FROM " + nombreTabla + " " + condicion);
                case 2:
                    prep.executeUpdate("INSERT INTO " + nombreTabla + " " + condicion);
                case 3:
                    prep.executeUpdate("UPDATE " + nombreTabla + " " + condicion);
            }

        } catch (SQLException ex) {
        }
    }

    public DefaultTableModel QueryTable(String nombreTabla, String condicion) {

        Query("SELECT * FROM " + nombreTabla + " " + condicion + ";");

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("CP");
        model.addColumn("Numero");

        try {

            while (consulta.next()) {
                Vector row = new Vector();
                row.add(consulta.getInt(1));
                row.add(consulta.getString(2));
                row.add(consulta.getInt(3));
                row.add(consulta.getInt(4));
                model.addRow(row);
            }
        } catch (SQLException ex) {
        }

        return model;
    }

}
