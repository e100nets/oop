package santech.model;

import javafx.scene.paint.Color;
import santech.Config;

public class Attribute {

    String name;
    int dlina;
    int shirina;
    int podacha;
    int sliv;
    int figure = 0; // 0-прямоугольник 1-круг
    double x0 = 0;
    double y0 = 0;
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;
    int x3 = 0;
    int y3 = 0;
    double ugol = 0;
    int round = 10;
    String color="FFFFFF";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }




    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getY3() {
        return y3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }


    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public double getUgol() {
        return ugol;
    }

    public void setUgol(double ugol) {
        this.ugol = ugol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getDlina() {
        return dlina/ Config.k;
    }

    public void setDlina(int dlina) {
        this.dlina = dlina;
    }



    public int getShirina() {
        return shirina/ Config.k;
    }

    public void setShirina(int shirina) {
        this.shirina = shirina;
    }

    public int getPodacha() {
        return podacha;
    }

    public void setPodacha(int podacha) {
        this.podacha = podacha;
    }



    public int getSliv() {
        return sliv;
    }

    public void setSliv(int sliv) {
        this.sliv = sliv;
    }



}
