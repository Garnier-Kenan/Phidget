package com.table_de_tri_fx.BDD;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.table_de_tri_fx.Ihm.DATA_Scene;

import java.sql.*;

public class Gestion_BDD {
    private static final String URL = "jdbc:mysql://tabletri.iris07.local/table_de_tri";
    private static final String UTILISATEUR = "rat";
    private static final String MOT_DE_PASSE = "NukeTown@07";
    private static Connection connexion;
    public void pingBDD(){
        connecter();
    }
    private void connecter() {
        try {
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            DATA_Scene.controller_popUP.popup_bdd = false;
            DATA_Scene.controller_popUP.popup_close();
        } catch (SQLException e) {
            if (!DATA_Scene.controller_popUP.popup_bdd) {
                DATA_Scene.controller_popUP.popup_bdd = true;
                DATA_Scene.controller_popUP.popUP();
            }
        }
    }
    public String verifCarte(String tagRFID) {
        String flag = "2";
        connecter();
        try {
            String query = "SELECT `user`.*, `badge`.`tag`FROM `user` JOIN `badge` ON `user`.`id_badge` = `badge`.`id_badge`WHERE `badge`.`tag` LIKE ?;";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, tagRFID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flag = ("1/" + resultSet.getString(5) + "/" + resultSet.getString(6) + "/" + resultSet.getString(1));
            } else {
                flag = "2";
            }
            decconecter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public Boolean dechet(int id, int id_table, Double poid0, Double poid1, Double poid2) {
        Boolean flag = false;
        connecter();
        try {
            String query = "INSERT INTO dechet (id_user,id_table, pain,alimentaire,emballage) VALUES (?,?, ?, ?, ?);";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id_table);
            preparedStatement.setDouble(3, poid0);
            preparedStatement.setDouble(4, poid1);
            preparedStatement.setDouble(5, poid2);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                flag = true;
            } else {
                flag = false;
            }
            decconecter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public Double semaine() {
        Double semaine = 0.000;
        connecter();
        if (connexion == null) {
            if (!DATA_Scene.controller_popUP.popup_bdd) {
                DATA_Scene.controller_popUP.popup_bdd = true;
                DATA_Scene.controller_popUP.popUP();
            }
        }else {
            try {
                String query = "SELECT REPLACE(FORMAT(SUM(pain + alimentaire + emballage), 3,'fr_FR'), ',', '.') AS total_semaine FROM dechet WHERE YEARWEEK(horodatage) = YEARWEEK(NOW());";
                PreparedStatement preparedStatement = connexion.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    semaine = resultSet.getDouble("total_semaine");
                } else {
                    semaine = 0.000;
                }
                decconecter();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return semaine;
    }

    private void decconecter() {
        try {
            connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
