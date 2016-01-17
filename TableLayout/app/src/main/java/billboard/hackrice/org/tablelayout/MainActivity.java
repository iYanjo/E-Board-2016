package billboard.hackrice.org.tablelayout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

//@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    //the images to display
    Integer[] imageIDs = {
            R.drawable.black,
            R.drawable.blue,
            R.drawable.green,
            R.drawable.orange,
            R.drawable.red,
            R.drawable.white,
            R.drawable.yellow
    };

    private LinearLayout myGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGallery = (LinearLayout) findViewById(R.id.myGallery);

        String targetPath = targetFiles();

        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);

        File[] files = targetDirector.listFiles();
        //Toast.makeText(getApplicationContext(),files[0].getName(),Toast.LENGTH_LONG);

        /*for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                System.out.println("File " + files[i].getName());
            } else if (files[i].isDirectory()) {
                System.out.println("Directory " + files[i].getName());
            }
        }*/

        for (File file : files) {
            //Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT);
            myGallery.addView(insertPhoto(file.getAbsolutePath()));
        }

        /*HorizontalScrollView hScroll = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        hScroll.setAdapter(new ImageAdapter(this));
        hScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                // display the images selected
                ImageView imageView = (ImageView) findViewById(R.id.image1);
                imageView.setImageResource(imageIDs[position]);
            }
        });*/
    }

    public String targetFiles(){
        String targetPath = Environment.getExternalStorageDirectory() + "/hackrice2016/";
        if(targetPath.isEmpty()){
            new File(targetPath).mkdirs();
        }
        return targetPath;
    }

    public View insertPhoto(String path) {
        //Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);
        Bitmap bm = BitmapFactory.decodeFile(path);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(5,5,5,5);

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bm);

        layout.addView(imageView);
        return layout;
    }
}