package kibs.mcm.edu.ph.externalio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;

    private String filename = "Cardoza.txt";
    private String filepath = "Scandal";
    File myExternalFile;
    String myData = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final EditText txtbox = findViewById(R.id.txtbox);
        final Button btnClr = findViewById(R.id.btnClr);
        final Button btnRea = findViewById(R.id.btnRea);
        final Button btnWri = findViewById(R.id.btnWri);

        btnClr.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtbox.setText("");
                    }
                });

        btnRea.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fis = new FileInputStream(myExternalFile);
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));
                            String strLine;
                            while ((strLine = br.readLine()) != null) {
                                myData = myData + strLine;
                            }
                            in.close();
                        }
                        catch (IOException e) {e.printStackTrace();}
                        txtbox.setText(myData);
                        Toast.makeText(getBaseContext(), "File data retrieved from Internal Storage", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnWri.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fis = new FileInputStream(myExternalFile);
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br =
                                    new BufferedReader(new InputStreamReader(in));
                            String strLine;
                            while ((strLine = br.readLine()) != null) {
                                myData = myData + strLine;
                            }
                            in.close();
                            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        txtbox.setText(myData);
                    }
                }
        );

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnWri.setEnabled(false);
        }else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}