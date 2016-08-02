package naivecode.wirelessandroidmouse;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import static android.widget.Toast.LENGTH_SHORT;

public class FirstPageActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "FPA";
    WifiManager.MulticastLock lock;
    private JmDNS jmdns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                setUp();
//
//                try {
//                    setupJmdns();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                begin();

            }
        }).start();
    }

    private void begin() {
        {
            try {

                // Activate these lines to see log messages of JmDNS
                boolean log = true;
                if (log) {
                    Logger logger = Logger.getLogger(JmDNS.class.getName());
                    ConsoleHandler handler = new ConsoleHandler();
                    logger.addHandler(handler);
                    logger.setLevel(Level.FINER);
                    handler.setLevel(Level.FINER);
                }

                jmdns = JmDNS.create();
                String type = "_dremote._tcp.local.";
                jmdns.addServiceListener(type, new SampleListener());

//                System.out.println("Press q and Enter, to quit");
//                int b;
//                while ((b = System.in.read()) != -1 && (char) b != 'q') {
//                /* Stub */
//                }
//                jmdns.close();
//                System.out.println("Done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            Log.d(TAG,"Service added   : " + event.getName() + "." + event.getType());
            Log.d(TAG,"toast start");
//            Toast.makeText(getApplicationContext(), "Got a nearby service", LENGTH_SHORT).show();
            Log.d(TAG,"toast end");
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed : " + event.getName() + "." + event.getType());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            jmdns.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lock != null) {
            lock.release();
        }
    }
}
