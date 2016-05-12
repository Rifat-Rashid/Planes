package genius.rifatrashid.planes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.Settings;

/**
 * Created by rifatrashid on 5/10/16.
 */
public class BackgroundBitmap{
    private int x, y, width, height;
    private Bitmap BigBackground;
    //private Bitmap screenBackground;

    public BackgroundBitmap() {

    }

    public BackgroundBitmap(Bitmap BigBackground, int width, int height){
        this.BigBackground = BigBackground;
        this.width = width;
        this.height = height;
        this.x = BigBackground.getWidth()/2 - width/2;
        this.y = BigBackground.getHeight()/2 - height/2;
    }

    public void resetCoordinates(){
        this.x = width;
        this.y = BigBackground.getHeight()/2 - height/2;
        System.out.println("Players X coordinate reset");
    }

    public void Draw(Canvas canvas){
        //screenBackground = Bitmap.createBitmap(BigBackground, this.x, this.y, width, height);
        canvas.drawBitmap(BigBackground, new Rect(this.x, this.y, this.x + this.width, this.y + this.height), new RectF(0, 0, this.width, this.height), null);
    }

    public Bitmap getBigBackground(){
        return this.BigBackground;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
