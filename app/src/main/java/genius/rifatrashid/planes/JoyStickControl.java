package genius.rifatrashid.planes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * Created by rifatrashid on 5/13/16.
 */
public class JoyStickControl extends View {
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public JoyStickControl(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int width, int height){
        setMeasuredDimension(350, 350);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
    }
}
