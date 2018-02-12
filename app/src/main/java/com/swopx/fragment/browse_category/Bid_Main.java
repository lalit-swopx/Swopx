package com.swopx.fragment.browse_category;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.PostImage;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.fragment.my_project_pkg.Bid_List;
import com.swopx.fragment.my_project_pkg.Project_Post;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;
import com.swopx.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;


/**
 * Created by Office Work on 20-12-2017.
 */

public class Bid_Main extends AppCompatActivity implements View.OnClickListener {
    private TextView browse,no_file_selected,cat,sab_cat,place_bid,cancel,select,transpotation,quantity;
    private ImageView rc_copy,demo;
    private EditText amount,charge,etfrt;
    final int Pick_FROM_GALLERY = 200;
    final int PICK_CAMERA_REQUEST = 100;
    private int cameraId = 0;
    private Bitmap bitmap;
    RadioGroup freight_grp;
    RadioButton yes,no;
    private String fright_value="";
    private LinearLayout image_layout,roylty_layout,freight_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_main_layout);
        intial();
    }

    private void intial() {
        charge=(EditText)findViewById(R.id.charge);
        amount=(EditText)findViewById(R.id.amount);
        etfrt=(EditText)findViewById(R.id.etfrt);
        no_file_selected=(TextView)findViewById(R.id.no_file_selected);
        select=(TextView)findViewById(R.id.select);
        transpotation=(TextView)findViewById(R.id.transpotation);
        cancel=(TextView)findViewById(R.id.cancel);
        freight_grp=(RadioGroup) findViewById(R.id.freight_grp);
        yes=(RadioButton) findViewById(R.id.yes);
        no=(RadioButton) findViewById(R.id.no);
        place_bid=(TextView)findViewById(R.id.place_bid);
        cat=(TextView)findViewById(R.id.cat);
        sab_cat=(TextView)findViewById(R.id.sab_cat);
        quantity=(TextView)findViewById(R.id.quantity);
        rc_copy=(ImageView) findViewById(R.id.rc_copy);
        demo=(ImageView) findViewById(R.id.demo);
        browse=(TextView)findViewById(R.id.browse);
        image_layout=(LinearLayout) findViewById(R.id.image_layout);
        roylty_layout=(LinearLayout) findViewById(R.id.roylty_layout);
        freight_layout=(LinearLayout) findViewById(R.id.freight_layout);
        cancel.setOnClickListener(this);
        place_bid.setOnClickListener(this);
        demo.setOnClickListener(this);
        select.setOnClickListener(this);
        rc_copy.setOnClickListener(this);
        sab_cat.setText(getIntent().getStringExtra("sub_cat"));
        cat.setText(getIntent().getStringExtra("cate_name"));
        quantity.setText("Rate per "+getIntent().getStringExtra("unit"));
        if(getIntent().hasExtra("royalty")||(getIntent().getStringExtra("royalty").equalsIgnoreCase("yes")))
        {
            roylty_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            roylty_layout.setVisibility(View.GONE);
        }
        freight_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.year)
                {
                    fright_value="Yes";
                }
                else if(checkedId==R.id.no)
                {
                    fright_value="No";
                }
            }
        });
//        Html.fromHtml("X<sup>2</sup>"))
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.demo:
                getImage();
                break;

                case R.id.rc_copy:
                getImage();
                break;

            case R.id.select:
                if(select.getText().toString().equalsIgnoreCase("F.O.R"))
                {
                    transpotation.setText("F.O.R");
                    select.setText("F.O.B");
                    freight_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    transpotation.setText("F.O.B");
                    select.setText("F.O.R");
                    freight_layout.setVisibility(View.GONE);
                }
                break;

            case R.id.place_bid:
               if(isValid())
               {
//                   String value[]=amount.getText().toString().split("\u20B9");
//                   Constant.ToastShort(Bid_Main.this,amount.getText().toString().replace("\u20B9",""));
                   alert_dialog();
               }
                break;
            case R.id.cancel:
                finish();
            break;
            default:
                break;
        }
    }
    private void alert_dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Bid_Main.this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.commom_custom_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView btn_no=(TextView)dialogView.findViewById(R.id.btn_no);
        TextView btn_yes=(TextView)dialogView.findViewById(R.id.btn_yes);
        TextView header=(TextView)dialogView.findViewById(R.id.header);
        TextView title=(TextView)dialogView.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.place_bid));
        header.setText(getResources().getString(R.string.place_bid_content));
        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));;
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if(Constant.isNetConnected(Bid_Main.this))
                {
                    getCompany_Entity();
                }
                else
                {
                    Constant.ToastShort(Bid_Main.this,getResources().getString(R.string.internet_connection));
                }

            }
        });

        alertDialog.show();
    }
    private boolean isValid() {
        if(TextUtils.isEmpty(amount.getText().toString().trim()))
        {
            Constant.ToastShort(Bid_Main.this,"Please enter the amount");
            return false;
        }

        else if(roylty_layout.getVisibility()==View.VISIBLE&&(TextUtils.isEmpty(charge.getText().toString().trim())))
        {
//            if(TextUtils.isEmpty(charge.getText().toString().trim()))
            {
                Constant.ToastShort(Bid_Main.this,"Please enter the charge");
                return false;
            }
        }
        else if(freight_layout.getVisibility()==View.VISIBLE&&(TextUtils.isEmpty(etfrt.getText().toString().trim())))
        {
//            if(TextUtils.isEmpty(fright_value))
            {
                Constant.ToastShort(Bid_Main.this,"Please select freight charge");
                return false;
            }
        }
        else if(TextUtils.isEmpty(no_file_selected.getText().toString().trim()))
        {
            Constant.ToastShort(Bid_Main.this,"Please upload image");
            return false;
        }
        return true;
    }

    public void getImage() {
        final AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
        exitDialog.setTitle("Choose");
        exitDialog.setMessage("Select from ?");
        exitDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cameraId = Constant.findFrontFacingCamera();
                if (ActivityCompat.checkSelfPermission(Bid_Main.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(Bid_Main.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Bid_Main.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PICK_CAMERA_REQUEST);
                    return;
                }
                Util.openCamera(Bid_Main.this, PICK_CAMERA_REQUEST, 0);

            }
        });

        exitDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (ActivityCompat.checkSelfPermission(Bid_Main.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(Bid_Main.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Bid_Main.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            Pick_FROM_GALLERY);
                    return;
                }
                Util.openGallery(Bid_Main.this, Pick_FROM_GALLERY);
                // imageBool_first = false;
            }
        });
        exitDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Pick_FROM_GALLERY && resultCode == Activity.RESULT_OK && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = Bid_Main.this.getContentResolver().query(filePath, filePathColumn, null, null, null);
            assert cursor != null;
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String yourRealPath = cursor.getString(columnIndex);
                image_layout.setVisibility(View.VISIBLE);
                demo.setVisibility(View.GONE);
                bitmap = Util.customDecodeFile(yourRealPath, 300, 300);// BitmapFactory.decodeFile(yourRealPath);//  Util.customDecodeFile(yourRealPath, StaticItems.displayWidth, StaticItems.displayWidth);
                Util.imageFile = new File(yourRealPath);
                rc_copy.setImageBitmap(bitmap);
                Util.saveImage(bitmap, Bid_Main.this, getResources().getString(R.string.app_name));
                no_file_selected.setText(Util.imageFile.getName());
//                String url = Url_Links.FileUploadImage + "5a032a03eb46c21fd8002b13"/* + "/" + Util.imageFile.getName()*/;
//                uploadImage(Util.imageFile, url, Util.imageFile.getName(), "image");
            } else {
                Constant.ToastShort(Bid_Main.this, "Choose Another Pic");
            }
            cursor.close();
        } else if (requestCode == PICK_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Constant.Log("pickCAMERA_Request", "pickCAMERA_Request" + PICK_CAMERA_REQUEST+":"+Util.imageFile.getAbsolutePath());
            image_layout.setVisibility(View.VISIBLE);
            demo.setVisibility(View.GONE);
            bitmap = Util.customDecodeFile(Util.imageFile.getAbsolutePath(), 300, 300);// BitmapFactory.decodeFile(Util.imageFile.getAbsolutePath());
            rc_copy.setImageBitmap(bitmap);
            no_file_selected.setText(Util.imageFile.getName());
            Util.saveImage(bitmap, Bid_Main.this, getResources().getString(R.string.app_name));
        }

    }

    void uploadImage(final File file, String url, String name, final String values,final String price,final String quantity,final String unit,final String msg) {
        Constant.Log("RC Copy==:0" + ".0", url);
        Constant.Log("Parameters====:",":"+file+";"+name+";"+values);
        PostImage post = new PostImage(file, url, name, Bid_Main.this, "image")
        {
            @Override
            public void receiveData(String response) {
                finish();
                try {
                    Log.e("RESPONSE", "reasponseee" + response.toString());
                    JSONObject response1 = new JSONObject(response);
                    Log.e("json", response);
//                    if(response1.getString("status").equalsIgnoreCase("200"))
                    Constant.ToastShort(Bid_Main.this,msg);
                    Constant.ToastShort(Bid_Main.this, "Bid placed sucessfully.");
                    Intent i=new Intent(Bid_Main.this,Placed_Bid.class);
                    i.putExtra("price",price);
                    i.putExtra("quantity",quantity);
                    i.putExtra("unit",unit);
                    startActivity(i);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void receiveError() {
                Log.e("PROFILE", "ERROR");
            }
        };

        post.execute(url, null, null);
    }

    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("request_id" ,getIntent().getStringExtra("request_id"));
            params.put("subindustry" ,getIntent().getStringExtra("sub_Indus"));
            params.put("category" ,getIntent().getStringExtra("cate_name"));
            params.put("subcategory" ,getIntent().getStringExtra("sub_cat"));
            params.put("qty" ,getIntent().getStringExtra("quantity"));
            params.put("unit" ,getIntent().getStringExtra("quantity1"));
            params.put("price" ,amount.getText().toString());
            params.put("royalti_price" ,charge.getText().toString().trim());
            params.put("freight_price" ,etfrt.getText().toString().trim());
            params.put("client_id" , CustomPreference.readString(Bid_Main.this,CustomPreference.Client_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Bid_Main.this,params, Url_Links.Post_SellerResponce, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        String url = Url_Links.Image_Upload +"/"+obj.getString("bid_id")/* + "/" + Util.imageFile.getName()*/;
                        uploadImage(Util.imageFile, url, Util.imageFile.getName(), "image",obj.getString("price"),obj.getString("qty"),obj.getString("unit"),obj.getString("msg"));
                    }

//                    Constant.ToastShort(getActivity(),obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Bid_Main.this, error.getMessage());
            }
        };
    }

}
