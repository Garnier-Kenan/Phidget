package com.table_de_tri_fx.BDD;

import java.sql.*;

public class Gestion_BDD {
    private static final String URL = "jdbc:mysql://10.0.0.117:3306/table_de_tri";
    private static final String UTILISATEUR = "rat";
    private static final String MOT_DE_PASSE = "NukeTown@07";
    private static Connection connexion;
    private void connecter(){
        try {
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String verifCarte(String tagRFID){
        String flag = "2";
        connecter();
        try {
            String query = "SELECT `user`.*, `badge`.`tag`FROM `user` JOIN `badge` ON `user`.`id_badge` = `badge`.`id_badge`WHERE `badge`.`tag` LIKE ?;";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1,tagRFID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                flag = ("1/" + resultSet.getString(5) + "/" + resultSet.getString(6) + "/" + resultSet.getString(1));
            }else {
                flag = "2";
            }
            decconecter();
        } catch (SQLException e) {
            decconecter();
            throw new RuntimeException(e);
        }
        return flag;
    }
    public Boolean dechet(int id,int id_table,Double poid0, Double poid1, Double poid2){
        Boolean flag = false;
        connecter();
        try {
            String query = "INSERT INTO dechet (id_user,id_table, pain,alimentaire,emballage) VALUES (?,?, ?, ?, ?);";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,id_table);
            preparedStatement.setDouble(3,poid0);
            preparedStatement.setDouble(4,poid1);
            preparedStatement.setDouble(5,poid2);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                flag = true;
            } else {
                flag = false;
            }
            decconecter();
        } catch (SQLException e) {
            decconecter();
            throw new RuntimeException(e);
        }
        return flag;
    }
    public double semaine (){
        double semaine = 0.000;
        connecter();
        try {
            String query = "SELECT FORMAT(SUM(pain + alimentaire + emballage), 3) AS total_semaine FROM dechet WHERE YEARWEEK(horodatage) = YEARWEEK(NOW());";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                semaine = resultSet.getDouble(1);
            }else {
                semaine = 0.000;
            }
            decconecter();
        } catch (SQLException e) {
            decconecter();
            throw new RuntimeException(e);
        }
        return semaine;
    }
    private void decconecter(){
        try {
            connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
