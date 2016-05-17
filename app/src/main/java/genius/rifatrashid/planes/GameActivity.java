package genius.rifatrashid.planes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    /**
     @GameLoopThread manages drawing calls and physics calculations
     @see GameLoopThread
     */
    private Handler handlerApplication;
    private SurfaceHolder _surfaceHolder;
    private SurfaceView _surfaceView;
    private GameLoopThread thread;

    /**
     *@variable fpsCounter -> var keeping track of fps count
     */
    private String fpsCounter = "";

    private Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     *@Class JoyStickControl - custom UI component for joystick controller
     */
    private JoyStickControl mJoyStickControl;

    /**
     *
     * @param savedInstanceState Bundle with saved instances such as onPause, onResume etc...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set view to full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        //inflate layout
        setContentView(R.layout.activity_main);
        _surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        _surfaceHolder = _surfaceView.getHolder();
        _surfaceHolder.addCallback(this);

        mJoyStickControl = (JoyStickControl) findViewById(R.id.joystickControllerView);
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        p.setTextSize(45f);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameLoopThread(_surfaceHolder, new Handler());
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class GameLoopThread extends Thread {
        private int canvasHeight = 0;
        private int canvasWidth = 0;
        static final long FPS_GAME = 60;
        private boolean run;
        private GamePhysicsThread gamePhysicsThread;
        player Player;
        Bitmap bgScroller;
        Bitmap backgroundDraw;
        BackgroundBitmap backgroundBitmap;

        public GameLoopThread(SurfaceHolder surfaceHolder, Handler handler) {
            _surfaceHolder = surfaceHolder;
            handlerApplication = handler;
            gamePhysicsThread = new GamePhysicsThread();
            run = true;
        }

        public void doStart() {
            synchronized (_surfaceHolder) {
                Player = new player(canvasWidth / 2, canvasHeight / 2);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                bgScroller = BitmapFactory.decodeResource(getResources(), R.drawable.fieldbackgroundnodpi, options);
                backgroundBitmap = new BackgroundBitmap(bgScroller, canvasWidth, canvasHeight);
            }
        }

        public void run() {
            long ticksFPS = 1000 / FPS_GAME;
            long startTime;
            long sleepTime;
            final int max_skip_frames = 2;
            int skipframes = 0;

            while (run) {
                startTime = SystemClock.currentThreadTimeMillis();
                Canvas c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        gamePhysicsThread.update();
                         doDraw(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                    sleepTime = SystemClock.currentThreadTimeMillis() - startTime;
                    fpsCounter = String.valueOf(1000/sleepTime);
                    if (sleepTime <= ticksFPS) {
                        try {
                            sleep(Math.max(ticksFPS - sleepTime, 0));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        skipframes++;
                    }
                    while(skipframes >= max_skip_frames){
                        gamePhysicsThread.update();
                        skipframes--;
                    }
                }
            }
        }

        /*
         @Param boolean b
         set thread running
         */
        public void setRunning(boolean b) {
            run = b;
        }

        /*
         @Param screen width, screen height
         Force set surfaceView height and width
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (_surfaceHolder) {
                canvasHeight = height;
                canvasWidth = width;
                doStart();
            }
        }

        /*
        @Param Canvas
        function: draws all objects to screen
         */
        private void doDraw(Canvas canvas) {
            if (run) {
                canvas.save();
                backgroundBitmap.Draw(canvas);
                Player.Draw(canvas);
                canvas.drawText(fpsCounter, canvasWidth - 130, 100, p);

            }
            canvas.restore();
        }

        /**
         *@Class control physics update of canvas objects
         */
        class GamePhysicsThread extends Thread {
            long startTime;
            long lastStartTime;

            public GamePhysicsThread() {

            }

            /**
             @Param milliseconds passed since last update
             */
            public void update() {
                backgroundBitmap.update();
            }
        }
    }
}
