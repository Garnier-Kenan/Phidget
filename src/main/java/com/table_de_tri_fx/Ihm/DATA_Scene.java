package com.table_de_tri_fx.Ihm;

import com.table_de_tri_fx.BDD.Gestion_BDD;
import com.table_de_tri_fx.Ihm.Controllers.Controller_Connexion;
import com.table_de_tri_fx.Ihm.Controllers.Controller_Principale;
import com.table_de_tri_fx.Ihm.Controllers.Controller_PopUP;
import com.table_de_tri_fx.Phidget.Gestion;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DATA_Scene {
    public static Controller_Connexion controller_Connexion;
    public  static Controller_Principale controller_Principale;
    public static Controller_PopUP controller_popUP;
    public static Gestion gestion;
    public static Stage primaryStage;
    public static Scene scene_Connexion, scene_Principale,scene_PopUP;
    public static Boolean position;
    public static Gestion_BDD gestion_bdd;
}
