package genius.rifatrashid.planes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.jar.Attributes;

/**
 * Created by rifatrashid on 5/13/16.
 */
public class JoyStickControl extends View {
    /**
     * @see JoyStickControl for controllerWidth, controllerHeight implementation
     */
    private int controllerWidth = 0;
    private int controllerHeight = 0;

    /**
     * JoySticksControl's xml (X & Y) position on the screen
     */
    private int controllerViewX;
    private int controllerViewY;

    /**
     * Paint to be used for UI component
     *
     * @see JoyStickControl for implementation of BorderCirclePaint
     */
    private Paint BorderCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint InnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Context mContext;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    @SuppressWarnings("deprecation")
    public JoyStickControl(Context context, AttributeSet attributes) {
        super(context, attributes);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        controllerWidth = (int) (display.getWidth() / 2);
        controllerHeight = (int) (display.getHeight() / 2);
        setJoyStickControllerViewX(display.getWidth() / 2 - this.controllerWidth / 2);
        setJoyStickControllerViewY(display.getHeight()/2 + this.controllerHeight/8);
    }

    /**
     * @void setup paints for canvas drawing
     */
    public void setupPaint(){
        BorderCirclePaint.setColor(Color.parseColor("#FFFFFF"));
        BorderCirclePaint.setStyle(Paint.Style.STROKE);
        BorderCirclePaint.setStrokeWidth(4.50f);
        BorderCirclePaint.setAntiAlias(true);
        InnerCirclePaint.setAntiAlias(true);
        InnerCirclePaint.setStyle(Paint.Style.STROKE);
        InnerCirclePaint.setStrokeWidth(9.0f);
    }

    /**
     * @onFinishInflate Called after a view and all of its children has been inflated from XML.
     */
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * @param x coordinate of the desired X
     */
    public void setJoyStickControllerViewX(float x) {
        getRootView().setX(x);
    }

    public void setJoyStickControllerViewY(float y) {
        getRootView().setY(y);
    }

    /**
     * @Param width, height: Width and Height passed through the root view
     */
    @Override
    protected void onMeasure(int width, int height) {
        setMeasuredDimension(getDesiredWidth(), getDesiredHeight());
    }

    /**
     * @return width of UI component int Integer form
     */
    public int getDesiredWidth() {
        return this.controllerWidth;
    }

    /**
     * @return height of UI component in Integer form
     */
    public int getDesiredHeight() {
        return this.controllerHeight;
    }

    /**
     * @Param canvas: passed through rootView to ChildView -> used to draw on the layout at layout inflation
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawCircle(controllerWidth / 2, controllerHeight / 2, controllerWidth / 3, BorderCirclePaint);
        canvas.drawCircle(controllerWidth/2, controllerHeight/2, controllerWidth/6, BorderCirclePaint);
        canvas.restore();
    }
}
