package santech;

import javafx.scene.canvas.GraphicsContext;
import santech.model.Attribute;
import santech.model.Truba;

import java.util.ArrayList;

public class Config {
    public static int k = 10;
    public static int su_dlina = 2000;
    public static int su_shirina = 2000;
    public static double x = 0;
    public static double y = 0;
    public static double ugol = 0;
    public static double vert,hor;
    public static double xpod = 0,xsliv = 0,ypod = 0,ysliv = 0;
    public static double x0truba,y0truba,x1truba,y1truba;

    public static ArrayList<Truba> trubas;
    public static ArrayList<Attribute> attrs_vanna;
    public static ArrayList<Attribute> attrs_tualet ;
    public static ArrayList<Attribute> attrs_umivalnic;
    public static ArrayList<Attribute> attrs_mashinki;
    public static ArrayList<Attribute> attrs_all;

    public static ArrayList<Truba> truba_all;
    public static ArrayList<Truba> truba_all_panel;

    public static Attribute attr_curr;
    public static Truba truba_curr;
    public static ArrayList<Truba> trubas_nal;
    public static GraphicsContext gc;

}
