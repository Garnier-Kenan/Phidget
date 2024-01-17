package com.table_de_tri_fx.BDD;

import java.sql.*;

public class BDD {
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
        String flag = "";
        connecter();
        try {
            String query = "\"SELECT `badge`.`id_badge`, `user`.* FROM `badge`, `user`WHERE `badge`.`id_badge` = ?;\"";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1,tagRFID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                flag = ("1" + resultSet.toString());
            }else {
                flag = "2";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
    private void decconecter(){

    }

}
