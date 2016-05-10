package genius.rifatrashid.planes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by rifatrashid on 5/10/16.
 */
public class player {

    private float x = 0;
    private float y = 0;
    private float radius = 100;
    Paint paint;

    public player(){

    }

    public player(float x, float y){
        this.x = x;
        this.y = y;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
    }

    public void Draw(Canvas canvas){
        canvas.drawRect(this.x - radius, this.y - radius, this.x + radius, this.y + radius, paint);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }


}
