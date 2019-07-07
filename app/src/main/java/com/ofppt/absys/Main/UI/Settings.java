package com.ofppt.absys.Main.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.ofppt.absys.Main.Adapters.SettingsAdapter;
import com.ofppt.absys.Main.TestActivities.TestTables;
import com.ofppt.absys.Main.Utils.HelperUtils;
import com.ofppt.absys.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import com.ofppt.absys.Main.Models.ABSENCES;
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.Main.Models.STAGIAIRES;

import mehdi.sakout.aboutpage.AboutPage;

public class Settings extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    ListView list ;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        list = findViewById(R.id.List_Settings);
        Items[] weather_data = new Items[]
                {
                        new Items(R.drawable.ic_down_name, "Export"),
                        new Items(R.drawable.ic_up_name, "Import"),
                        new Items(R.drawable.ic_help_name, "About"),
                        new Items(R.drawable.ic_add_aser, "Ajouter Un Formateur"),
                        new Items(R.drawable.ic_add_aser, "Test Activity"),
                        new Items(R.drawable.add_fil, "Ajouter Une Filiere"),
                        new Items(R.drawable.add_group, "Ajouter Un Group"),

                };
        SettingsAdapter adapter = new SettingsAdapter(this, R.layout.listview_item_row, weather_data);
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            // bar.setDisplayShowTitleEnabled();
            bar.setTitle("Parametre");

        }
        if (list != null) {
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListViewClick(position);
                }
            });
        }


    }
    public void ListViewClick(int pos){
        String xx ="";
        switch (pos){
            case 0:
                xx = "Export";
                checkPermissionsAndSave();
                break;
            case 1:
                xx = "import";
                checkPermissionsAndOpenFilePicker();
                break;
            case 2:
                xx = "About";
//                Intent intent = new Intent(this,About.class);
//                startActivity(intent);
                View aboutPage = new AboutPage(this)
                                         .isRTL(false)
                                         .setDescription("This is an app to Observe Students Absence For ISTA AGADIR Made By Achraf Benbamoula && Brahim Afassy \n © BBA")
                                         .addGroup("Connect with us")
                                         .addEmail("FrancXPT@gmail.com")
                                         .addEmail("brahimafassy@gmail.com")
                                         .addWebsite("https://github.com/BrahimAfa/AbSys")
                                         .addGitHub("FrancXPT")
                                         .addGitHub("BrahimAfa")
                                         .create();
                setContentView(aboutPage);
                break;
            case 3:
                xx = "Add User";
                Intent FormateurInt = new Intent(this, AddFormateur.class);
                startActivity(FormateurInt);
                break;
            case 4:
                xx = "Test Page";
                //TestActivity
                Intent Itn = new Intent(this, TestTables.class);
                startActivity(Itn);
                break;
                case 5:
                xx = "Filiere Add Page";
                //ADD Filiere
                Intent Fil = new Intent(this, AddFiliere.class);
                startActivity(Fil);
                break;
            case 6:
                xx = "Group Add Page";
                //ADD Group
                Intent GRp = new Intent(this, AddGroup.class);
                startActivity(GRp);
                break;
        }
//        Toast.makeText(this,"position :"+xx,Toast.LENGTH_SHORT).show();
    }
    public FileReader Absence_File;
    String[] ids;
    private void READCSV(){
        BufferedReader reader = new BufferedReader(Absence_File);
        try {
            String csvLine;
            new Delete().from(STAGIAIRES.class).execute();
            ActiveAndroid.beginTransaction();
            while ((csvLine = reader.readLine()) != null) {
                ids=csvLine.split(",");
                Log.i("xx", "READCSV: "+ids[0]+" $$ "+ids[1]);
                STAGIAIRES item = new STAGIAIRES();
                try{
                    Log.e("Collumn 1 ",""+ids[0]) ;
                    item._CEF = ids[1];
                    item._Nom = ids[2];
                    item._Prenom = ids[3];
                    item._groupes = new Select().from(GROUPES.class).where("CodeGroupe = ?",ids[9]).executeSingle();
                    item._Absence_Cumulee = 0d;
                    item.save();

                }catch (Exception e){
                    Log.e("Unknown fuck",e.toString());
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }finally {
            ActiveAndroid.endTransaction();
        }
    }
    public void EXPORTCSV() {
        {
            Date c = Calendar.getInstance().getTime();
            //System.out.println("Current time => " + c);
//            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//            String formattedDate = df.format(c);
            String Named = String.format("AbSys-%s.csv", HelperUtils.DateFormatter(c));
            final File folder = new File(Environment.getExternalStorageDirectory(), ""+Named);
            boolean var = false;
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                    try {
                        String[] arr;
                        FileWriter fw = new FileWriter(folder);
                        List<ABSENCES> xxc = ABSENCES.getAll();
                        arr = new  String[xxc.size()];
                        String x = "idAbsence";
                        x+=","+ "CEF";
                        x+=","+ "Matricule";
                        x+=","+ "DateAbsence";
                        x+=","+ "Seance";
                        fw.append(x).append("\n");
                        for(int i = 0; i < xxc.size();i++ ){
                            String y = xxc.get(i).getId()+"";
                            y+=","+ xxc.get(i)._Stagiere._CEF;
                            y+=","+ xxc.get(i)._Formateurs._Matricule;
                            y+=","+ HelperUtils.DateFormatter(xxc.get(i)._DateAbsence);
                            y+=","+ xxc.get(i)._Seance;
                            Log.d("xxxix","Ligne Nr: "+i );
                            fw.append(y).append("\n");
                        }
                        fw.close();
                    } catch (Exception e) {
                    }

        }
    }
    private void checkPermissionsAndSave() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            boolean x = false;
            AlertDialog.Builder xx = new AlertDialog.Builder(this);
            xx.setTitle("Absence")
                    .setMessage("Voulez vous Supprimer la list des Absence du base Donner?")
                    .setIcon(R.drawable.error_icon)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Clear the table if you Clicked Yes
                            deleteAb();
                        }})
                    .setNegativeButton("Non", null);
            AlertDialog dialog = xx.create();
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EXPORTCSV();
                }

            }).start();

        }
    }
    private void deleteAb(){
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Log.d("xxx","Deleted");
                new Delete().from(ABSENCES.class).execute();
            }
        }.start();
    }
    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }
    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker();
            } else {
                showError();
            }
        }
    }
    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(false)
                .withTitle("List Stagiere")
                .withFilter(Pattern.compile(".*\\.csv"))
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            if (path != null) {
                Log.d("Path: ", path);
                try {
                    Absence_File = new FileReader(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final ProgressDialog dialog=new ProgressDialog(this);
                dialog.setMessage("Importation......");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                    }
                    @Override
                    public void run() {
                        READCSV();
                        dialog.dismiss();
                    }
                }).start();
            }
        }
    }
}
