package com.swopx.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Invite_Ten_Adapter;
import com.swopx.fragment.my_project_pkg.Project_Post;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Office Work on 11-01-2018.
 */

public class Refer_Earn extends AppCompatActivity implements View.OnClickListener {

    private TextView link,refer_code,how_work;
    private ImageButton back,fb,whtzapp,more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        intiali();


    }

    private void intiali() {
        refer_code=(TextView)findViewById(R.id.refer_code);
        link=(TextView)findViewById(R.id.link);
        how_work=(TextView)findViewById(R.id.how_work);
        back=(ImageButton) findViewById(R.id.back);
        fb=(ImageButton) findViewById(R.id.fb);
        whtzapp=(ImageButton) findViewById(R.id.whtzapp);
        more=(ImageButton) findViewById(R.id.more);
        refer_code.setText(CustomPreference.readString(Refer_Earn.this, CustomPreference.Refferal_Code,""));
        link.setText(Url_Links.App_Play_Store_link);
        back.setOnClickListener(this);
        fb.setOnClickListener(this);
        whtzapp.setOnClickListener(this);
        more.setOnClickListener(this);
        how_work.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.back:
                finish();
                break;

            case R.id.fb:
                break;

            case R.id.whtzapp:
                Constant.ToastShort(Refer_Earn.this,CustomPreference.readString(Refer_Earn.this,CustomPreference.Refferal_Code,""));
              /*  Bitmap imgBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.share_image_social);
                String imgBitmapPath= MediaStore.Images.Media.insertImage(getContentResolver(),imgBitmap,"title",null);
//                Uri imgBitmapUri=Uri.parse(imgBitmapPath);
                Constant.Log("refer and Earn","refer and Earn===:"+imgBitmapPath);
                Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                        + "/drawable/" + "share_image_social");*/
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.setType("text/plain");

                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Just invited you to Swopx and sent Rs. 2000. Join in 48 hours and get more upto Rs 1 Lakh free in wallet use Refer Code: "+
                        CustomPreference.readString(Refer_Earn.this,CustomPreference.Refferal_Code,"")+ ". Download now: "+Url_Links.App_Play_Store_link);
//                whatsappIntent.setType("text/plain");
//                whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Constant.ToastShort(Refer_Earn.this,"Whatsapp have not been installed.");
                }



                break;

            case R.id.more:
                share();
//                Uri imageUri1 = null;
//                try {
//                    imageUri1 = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
//                            BitmapFactory.decodeResource(getResources(), R.drawable.share_image_social), null, null));
//                } catch (NullPointerException e) {
//                }
//
////                Uri screenshotUri = Uri.parse(path);
////
////                sharingIntent.setType("image/png");
////                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//                Intent intentShare = new Intent(Intent.ACTION_SEND);
//                intentShare.setType("image/png");
//                intentShare.putExtra(Intent.EXTRA_STREAM, imageUri1);
////                intentShare.putExtra(Intent.EXTRA_SUBJECT,"Ticket PNR Status For " + pnrNumber);
//                intentShare.putExtra(Intent.EXTRA_TEXT,"Just invited you to Swopx & sent you free money, use refer code:"+
//                        CustomPreference.readString(Refer_Earn.this,CustomPreference.Refferal_Code,"")+"\n" +
//                        Url_Links.App_Play_Store_link);
//
//                startActivity(Intent.createChooser(intentShare,"Share Swopx"));
                break;

                case R.id.how_work:
                    alert_dialog();
                break;
        }
    }


    public void share() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
               getResources().getString(R.string.app_name));

      /*  sharingIntent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.just_2000) +
                Url_Links.App_Play_Store_link);*/

        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Just invited you to Swopx and sent Rs. 2000. Join in 48 hours and get more upto Rs 1 Lakh free in wallet use Refer Code: "+
                CustomPreference.readString(Refer_Earn.this,CustomPreference.Refferal_Code,"")+ ". Download now: "+Url_Links.App_Play_Store_link);

       /* Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.share_image_social);
        Log.e("Image Path","======="+icon);
        sharingIntent.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if(icon!=null)
        {
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri uri = Uri.fromFile(f);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        }
*/

//        sharingIntent.putExtra(Intent.EXTRA_STREAM, "\n"+category);
        Intent i = Intent.createChooser(sharingIntent, "Share using");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);


    }
    private void alert_dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Refer_Earn.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.how_it_work_layout, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
}
