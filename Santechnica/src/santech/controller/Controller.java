package santech.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import santech.Config;
import santech.model.Attribute;
import santech.model.Truba;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    ArrayList<String> trubas_str = new ArrayList<>();
    ArrayList<String> attrs_vanna_str = new ArrayList<>();
    ArrayList<String> attrs_tualet_str = new ArrayList<>();
    ArrayList<String> attrs_umivalnic_str = new ArrayList<>();
    ArrayList<String> attrs_mashinki_str = new ArrayList<>();

    public ComboBox<String> cb1;
    public ComboBox<String> cb2;
    public ComboBox<String> cb3;
    public ComboBox<String> cb4;
    public ComboBox<String> cb5;
    public Label l1;
    public Button prov;
    public Button tuallcheck;
    public Button vannacheck;
    public Button umivalcheck;
    public Button mashinkicheck;
    public Button trubicheck;
    public VBox glav;
    public StackPane panel;
    public ListView lv;
    public ComboBox cb_nal;
    public TextField tf_nal;
    public ListView lv_nal;
    public Button bt_nal;

    boolean zaxvat;
    boolean podacha = false;
    boolean sliv = false;

    public void init()
    {   Config.trubas = new ArrayList<>();
        Config.attrs_vanna = new ArrayList<>();
        Config.attrs_tualet = new ArrayList<>();
        Config.attrs_umivalnic = new ArrayList<>();
        Config.attrs_mashinki = new ArrayList<>();
        Config.attrs_all = new ArrayList<>();
        Config.truba_all = new ArrayList<>();
        Config.truba_all_panel = new ArrayList<>();
        Config.trubas_nal = new ArrayList<>();
        initTrubas();
        initAttributes("vanna",Config.attrs_vanna,attrs_vanna_str);
        initAttributes("unitaz",Config.attrs_tualet,attrs_tualet_str);
        initAttributes("umivalnic",Config.attrs_umivalnic,attrs_umivalnic_str);
        initAttributes("mashinki",Config.attrs_mashinki,attrs_mashinki_str);
    }

    void initTrubas()
    {
        ArrayList<String> name = getData("truba","name");
        ArrayList<String> diametr = getData("truba","diametr");
        ArrayList<String> dlina = getData("truba","dlina");
        ArrayList<String> color = getData("truba","color");
        for (int i=0;i<name.size();i++)
        {
            Truba tr = new Truba();
            tr.setName(name.get(i));
            tr.setDiametr(Double.parseDouble(diametr.get(i)));
            tr.setDlina(Integer.parseInt(dlina.get(i)));
            tr.setColor(color.get(i));
            Config.trubas.add(tr);
            trubas_str.add(name.get(i));
        }
    }

    void initAttributes(String attribut,ArrayList attrs,ArrayList attrs_str)
    {
        ArrayList<String> name = getData(attribut,"name");
        ArrayList<String> pod = getData(attribut,"pod");
        ArrayList<String> sliv = getData(attribut,"sliv");
        ArrayList<String> dlina = getData(attribut,"dlina");
        ArrayList<String> shirina = getData(attribut,"shirina");
        ArrayList<String> round = getData(attribut,"round");
        ArrayList<String> color = getData(attribut,"color");
        for (int i=0;i<name.size();i++)
        {
            Attribute tr = new Attribute();
            tr.setName(name.get(i));
            tr.setPodacha(Integer.parseInt(pod.get(i)));
            tr.setDlina(Integer.parseInt(dlina.get(i)));
            tr.setSliv(Integer.parseInt(sliv.get(i)));
            tr.setShirina(Integer.parseInt(shirina.get(i)));
            tr.setRound(Integer.parseInt(round.get(i)));
            tr.setColor(color.get(i));
            attrs.add(tr);
            attrs_str.add(name.get(i));
        }
    }

    public ArrayList<String> getData(String uzel,String attrib)
    {
        ArrayList<String> res = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("data.xml");

            Node root = document.getDocumentElement();

            NodeList attrs = root.getChildNodes();
            for (int i = 0; i < attrs.getLength(); i++) {
                Node attr = attrs.item(i);
                NodeList attrlist=attr.getChildNodes();
                if (attr.getNodeType() != Node.TEXT_NODE && attr.getNodeName().equals(uzel)) {
                    for(int j = 0; j < attrlist.getLength(); j++) {
                        Node item = attrlist.item(j);
                        if (item.getNodeType() != Node.TEXT_NODE) {
                            res.add(item.getAttributes().getNamedItem(attrib).getNodeValue());
                        }
                    }
                }
            }

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return res;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showDialogConfigSanuzel();
        init();
        cb1.setItems(FXCollections.observableArrayList(attrs_tualet_str));
        cb2.setItems(FXCollections.observableArrayList(attrs_vanna_str));
        cb3.setItems(FXCollections.observableArrayList(attrs_umivalnic_str));
        cb4.setItems(FXCollections.observableArrayList(attrs_mashinki_str));
        cb5.setItems(FXCollections.observableArrayList(trubas_str));
        cb_nal.setItems(FXCollections.observableArrayList(trubas_str));
        lv.getItems().add("Подача в санузел");
        lv.getItems().add("Слив с санузла");

        Platform.runLater(new Runnable() {
            @Override public void run() {
                System.out.println(panel.getWidth());
                Canvas canvas = new Canvas(panel.getWidth(), panel.getHeight());
                Config.hor = panel.getWidth();
                Config.vert = panel.getHeight();
                Config.gc = canvas.getGraphicsContext2D();
                panel.setStyle("-fx-background-color: #FFFFFF;");
                panel.getChildren().add(canvas);
                setK();
                Config.su_dlina = Config.su_dlina/Config.k;
                Config.su_shirina = Config.su_shirina/Config.k;
                drawShapes(Config.gc);
            }
        });

    }

    public void prov_click() {

    }

    public void add_truba_nal()
    {
        for (Truba a : Config.trubas) {
            if (a.getName().equals(cb_nal.getValue())) {
                a.setDlina(Integer.parseInt(tf_nal.getText()));
                Config.trubas_nal.add(a);
                lv_nal.getItems().add("d="+a.getName()+"  ; l="+a.getDlina()+" мм.");
            }
        }
    }

    public void tualcheck_click() {
        for (Attribute a : Config.attrs_tualet) {
            if (a.getName().equals(cb1.getValue())) {
                Config.attrs_all.add(a);
                lv.getItems().add(a.getName());
            }
        }


    }

    public void click_lv() {
        trubaf=false;
        podacha=false;
        sliv=false;

        if (lv.getSelectionModel().getSelectedItem().equals("Подача в санузел"))
        {
            podacha = true;
            sliv = false;
            drawShapes(Config.gc);
            zaxvat = true;
            trubaf = false;
            Config.attr_curr = null;
        }

        if (lv.getSelectionModel().getSelectedItem().equals("Слив с санузла"))
        {
            podacha = false;
            sliv = true;
            drawShapes(Config.gc);
            zaxvat = true;
            trubaf = false;
            Config.attr_curr = null;
        }

        for (Attribute a : Config.attrs_all) {
            if (a.getName().equals(lv.getSelectionModel().getSelectedItem())) {
                Config.attr_curr = a;
                Config.ugol = a.getUgol();
                drawShapes(Config.gc);
                zaxvat = true;
                trubaf = false;
            }
        }

        for (Truba a:Config.truba_all)
        {
            System.out.println(a.getName());
            System.out.println(  lv.getSelectionModel().getSelectedItem());
            if (a.getName().equals(lv.getSelectionModel().getSelectedItem())) {

                Config.truba_curr = a;
                trubaf=true;
                zatr=false;
            }
        }
    }

    boolean trubaf=false;

    public void vannacheck_click() {

        for (Attribute a : Config.attrs_vanna) {
            if (a.getName().equals(cb2.getValue())) {
                Config.attrs_all.add(a);
                lv.getItems().add(a.getName());
            }
        }
    }

    public void umivalcheck_click() {
        for (Attribute a : Config.attrs_umivalnic) {
            if (a.getName().equals(cb3.getValue())) {
                Config.attrs_all.add(a);
                lv.getItems().add(a.getName());
            }
        }
    }

    public void mashinkicheck_click() {

        for (Attribute a : Config.attrs_mashinki) {
            if (a.getName().equals(cb4.getValue())) {
                Config.attrs_all.add(a);
                lv.getItems().add(a.getName());
            }
        }
    }

    public void trubicheck_click() {

        for (Truba a : Config.trubas) {
            if (a.getName().equals(cb5.getValue())) {
                System.out.println(a.getName());
                Config.truba_all.add(a);
                lv.getItems().add(a.getName());
            }
        }
    }

    boolean zatr = false;

    ArrayList<Truba.Point> ps;
    Truba truba;
    public void panel_click(MouseEvent mouseEvent)
    {
        if (trubaf && !zatr)
        {
            ps=new ArrayList<>();
            truba=Config.truba_curr;
            Config.truba_all_panel.add(truba);
            Config.x0truba=mouseEvent.getX();
            Config.y0truba=mouseEvent.getY();
            zatr=true;
            return;
        }


        if (trubaf && zatr) {

            Config.x1truba = mouseEvent.getX();
            Config.y1truba = mouseEvent.getY();

            ps.add(new Truba.Point(Config.x0truba, Config.y0truba, Config.x1truba, Config.y1truba));
            truba.setPoints(ps);
            drawLine(Config.gc);
            Config.x0truba = mouseEvent.getX();
            Config.y0truba = mouseEvent.getY();
        }

        else {
            if (zaxvat) {
                zaxvat = false;
            }
        }
    }

    public void move(MouseEvent mouseEvent)
    {
        if (zaxvat)
        {
            Config.x = mouseEvent.getX();
            Config.y = mouseEvent.getY();

            if (podacha)
            {
                Config.xpod=mouseEvent.getX();
                Config.ypod=mouseEvent.getY();
            }
            if (sliv)
            {
                Config.xsliv = mouseEvent.getX();
                Config.ysliv = mouseEvent.getY();
            }
            drawShapes(Config.gc);
            drawLine(Config.gc);
        }
    }

    public void povorot()
    {
        if (Config.ugol==0) Config.ugol=90; else Config.ugol=0;
        drawShapes(Config.gc);
    }

    public void showDialogConfigSanuzel() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Размеры санузла");
        dialog.setHeaderText("Задайте размеры санузла");

        ButtonType loginButtonType = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField dlina = new TextField();
        dlina.setPromptText("Длина");
        TextField shirina = new  TextField();
        shirina.setPromptText("Ширина");

        grid.add(new Label("Длина (мм):"), 0, 0);
        grid.add(dlina, 1, 0);
        grid.add(new Label("Ширина (мм):"), 0, 1);
        grid.add(shirina, 1, 1);

        javafx.scene.Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        dlina.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> dlina.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(dlina.getText(), shirina.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(razmer -> {

            Config.su_dlina = Integer.parseInt(razmer.getKey());
            Config.su_shirina = Integer.parseInt(razmer.getValue());
        });
    }

    void setK()
    {
        int i=1;
        for ( i=1;i<20;i++)
        {
            if (Config.vert-20>Config.su_shirina/i && Config.hor-20>Config.su_dlina/i)
            {
                Config.k=i;
                break;
            }

        }

    }


    public void drawLine(GraphicsContext gc)
    {

        Affine rotate = new Affine();
        rotate.appendRotation(0, Config.su_shirina/2, Config.su_shirina/2);
        gc.setTransform(rotate);
        for (Truba a:Config.truba_all_panel) {
            for (Truba.Point pp : a.getPoints()){
                gc.setStroke(HexToColor(a.getColor()));
                gc.setLineWidth(a.getDiametr() / Config.k);;
                gc.strokeLine(pp.x0, pp.y0, pp.x1, pp.y1);
            }
        }
    }



    private void drawShapes(GraphicsContext gc) {
        Affine rotate = new Affine();
        rotate.appendRotation(0, Config.su_shirina/2, Config.su_shirina/2);
        gc.setTransform(rotate);
        Config.gc.clearRect(0,0,panel.getWidth(), panel.getHeight());
        gc.setFill(Color.rgb(230,230,230));
        gc.setStroke(Color.BLUE);
        gc.setLineCap(StrokeLineCap.SQUARE);
        gc.setLineJoin(StrokeLineJoin.MITER);
        gc.setLineWidth(0.5);
        gc.strokeRect(0, 0, Config.su_shirina, Config.su_dlina);
        gc.setFill(Color.rgb(150,0,150));
        gc.fillOval(Config.xpod,Config.ypod,40/Config.k,40/Config.k);
        gc.setFill(Color.rgb(0,150,0));
        gc.fillOval(Config.xsliv,Config.ysliv,80/Config.k,80/Config.k);

        if (Config.attr_curr!=null) {
            rotate(gc, Config.ugol, Config.attr_curr.getX0() , Config.attr_curr.getY0() );
            gc.setFill(HexToColor(Config.attr_curr.getColor()));
            gc.fillRoundRect(Config.attr_curr.getX0()-Config.attr_curr.getShirina()/2, Config.attr_curr.getY0()-Config.attr_curr.getDlina()/2, Config.attr_curr.getShirina(), Config.attr_curr.getDlina(), Config.attr_curr.getRound(), Config.attr_curr.getRound());
            Config.attr_curr.setUgol(Config.ugol);
            Config.attr_curr.setX0(Config.x);
            Config.attr_curr.setY0(Config.y);
        }
        for (Attribute a:Config.attrs_all)
        {
            if (!a.equals(Config.attr_curr))
            {
                rotate(gc, a.getUgol(), a.getX0() , a.getY0() );
                gc.setFill(HexToColor(a.getColor()));
                gc.fillRoundRect(a.getX0()-a.getShirina()/2, a.getY0()-a.getDlina()/2, a.getShirina(), a.getDlina(), a.getRound(), a.getRound());
            }
        }
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public static Color HexToColor(String hex)
    {
        switch (hex.length()) {
            case 6:
                return  Color.rgb(
                        Integer.valueOf(hex.substring(0, 2), 16),
                        Integer.valueOf(hex.substring(2, 4), 16),
                        Integer.valueOf(hex.substring(4, 6), 16));
            case 8:
                return  Color.rgb(
                        Integer.valueOf(hex.substring(0, 2), 16),
                        Integer.valueOf(hex.substring(2, 4), 16),
                        Integer.valueOf(hex.substring(4, 6), 16),
                        Integer.valueOf(hex.substring(6, 8), 16));
        }
        return null;
    }
}