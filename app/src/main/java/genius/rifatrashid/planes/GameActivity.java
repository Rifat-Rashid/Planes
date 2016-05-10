package genius.rifatrashid.planes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.util.logging.LogRecord;

public class GameActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private Handler handlerApplication;
    private SurfaceHolder _surfaceHolder;
    private SurfaceView _surfaceView;
    private GameLoopThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set view to full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        //inflate layout
        setContentView(R.layout.activity_main);
        _surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        _surfaceHolder = _surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
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

        public GameLoopThread(SurfaceHolder surfaceHolder, Handler handler) {
            _surfaceHolder = surfaceHolder;
            handlerApplication = handler;
            gamePhysicsThread = new GamePhysicsThread();
            run = true;
        }

        public void doStart() {
            synchronized (_surfaceHolder) {
                Player = new player(canvasWidth / 2, canvasHeight / 2);
                bgScroller = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundairplanes);
            }
        }

        public void run() {
            long ticksFPS = 1000 / FPS_GAME;
            long startTime;
            long sleepTime;

            while (run) {
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
                canvas.drawBitmap(backgroundDraw);
                canvas.drawColor(Color.parseColor("#FFFFFF"));
                Player.Draw(canvas);

            }
            canvas.restore();
        }

        class GamePhysicsThread {

            public GamePhysicsThread() {

            }

            public void update() {
                backgroundDraw = Bitmap.createBitmap(bgScroller, bgScroller.getWidth()/2 - canvasWidth/2, bgScroller.getHeight()/2 - canvasHeight/2, canvasWidth, canvasHeight);

                Player.setX(Player.getX() + 0.5f);
            }
        }
    }
}
