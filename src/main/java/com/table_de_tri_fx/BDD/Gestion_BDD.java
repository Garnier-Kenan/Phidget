package com.table_de_tri_fx.BDD;

import java.sql.*;

public class Gestion_BDD {
    private static final String URL = "jdbc:mysql://localhost:3306/table_de_tri";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "";
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
    public Boolean dechet(int id,String poid0, String poid1, String poid2){
        Boolean flag = false;
        connecter();
        try {
            String query = "INSERT INTO dechet (id_user, alimentaire,emballage, pain) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1,String.valueOf(id));
            preparedStatement.setString(2,poid0);
            preparedStatement.setString(3,poid1);
            preparedStatement.setString(4,poid2);

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
    private void decconecter(){
        try {
            connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
