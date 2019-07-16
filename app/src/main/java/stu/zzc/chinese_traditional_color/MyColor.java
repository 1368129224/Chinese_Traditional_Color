package stu.zzc.chinese_traditional_color;

import android.graphics.Color;

public class MyColor {

    private String colorname;
    private String colorRGB;
    private int colorIntRGB;
    private int darkcolor;

    public MyColor(String colorname, String colorRGB) {
        this.colorname = colorname;
        this.colorRGB = colorRGB;
        this.colorIntRGB = Integer.valueOf(colorRGB, 16);
        this.darkcolor = getDarkcolor(Integer.valueOf(colorRGB,16));
    }

    private int getDarkcolor(int n){
        final float[] array = new float[3];
        Color.colorToHSV(n, array);
        if (array[2] > 0.6f) {
            array[1] += 0.0f;
            array[2] -= 0.38f;
        }
        else {
            array[1] -= 0.0f;
            array[2] += 0.38f;
        }
        return Color.HSVToColor(array);
    }

    public String getColorname(){
        return this.colorname;
    }

    public int getIntColorRGB(){
        return this.colorIntRGB;
    }

    public String getColorRGB() { return this.colorRGB; }

    public int getDarkcolor() {return this.darkcolor; }
}


