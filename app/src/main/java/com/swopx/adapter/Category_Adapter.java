package com.swopx.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.fragment.browse_category.Category;
import com.swopx.fragment.browse_category.Category_Main;
import com.swopx.fragment.browse_category.Request;
import com.swopx.fragment.category.Size;
import com.swopx.fragment.my_project_pkg.Category_My_Project;
import com.swopx.fragment.my_project_pkg.Description_My_Project;
import com.swopx.fragment.my_project_pkg.Post_Project_Main;
import com.swopx.fragment.my_project_pkg.Size_My_Project;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 18-12-2017.
 */

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Items> items;
    String values;

    public Category_Adapter(Activity activity, ArrayList<Items> items, String values) {
        this.activity=activity;
        this.items=items;
        this.values=values;
    }

    @Override
    public Category_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.categories_row, parent, false);
        return new Category_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Category_Adapter.MyViewHolder holder, int position) {
        if(values.equalsIgnoreCase("my_projectcategory")||values.equalsIgnoreCase("category"))
        {
           holder.sub_category.setVisibility(View.VISIBLE);
           holder.sub_category.setText(items.get(position).getSub_category());
        }
        else
        {
            holder.sub_category.setVisibility(View.GONE);
        }
        holder.category.setText(items.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category,sub_category;
        public MyViewHolder(View itemView) {
            super(itemView);
            category=(TextView)itemView.findViewById(R.id.category);
            sub_category=(TextView)itemView.findViewById(R.id.sub_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(values.equalsIgnoreCase("subinstry"))
                    {
                        Intent i=new Intent(activity, Category_Main.class);
                        i.putExtra("id",items.get(getAdapterPosition()).getId());
                        i.putExtra("title",items.get(getAdapterPosition()).getTitle());
                        i.putExtra("second","second");
                        activity.startActivity(i);
                    }
                    else if(values.equalsIgnoreCase("category"))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(getAdapterPosition()).getId());
                        bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                        bundle.putString("royalty", items.get(getAdapterPosition()).getRoyalty());
                        Request fragment = new Request();
                        fragment.setArguments(bundle);
                        ((Category_Main) activity).replaceFragment(fragment);
                    }
                   /* else if(values.equalsIgnoreCase("my_projectcategory"))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(getAdapterPosition()).getId());
                        bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                        bundle.putString("royalty", items.get(getAdapterPosition()).getRoyalty());
                        Request fragment = new Request();
                        fragment.setArguments(bundle);
//                        ((Post_Project_Main) activity).replaceFragment(fragment);
                    }*/
                    else if(values.equalsIgnoreCase("myproject_sub"))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(getAdapterPosition()).getId());
                        bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                        Category_My_Project fragment = new Category_My_Project();
                        fragment.setArguments(bundle);
                        ((Post_Project_Main) activity).replaceFragment(fragment);
                    }
                    else if(values.equalsIgnoreCase("my_projectcategory"))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(getAdapterPosition()).getId());
                        bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                        bundle.putString("unit", items.get(getAdapterPosition()).getUnit());
                        bundle.putString("sub_cat", "sub_cat");
                        Size_My_Project fragment = new Size_My_Project();
                        fragment.setArguments(bundle);
                        ((Post_Project_Main) activity).replaceFragment(fragment);
                    }
                    else if(values.equalsIgnoreCase("size"))
                    {
                        int count=Integer.parseInt(items.get(getAdapterPosition()).getNumber());
                        if(count>0)
                        {
                            ((Post_Project_Main) activity).sub_cat_id=items.get(getAdapterPosition()).getId();
                            ((Post_Project_Main) activity).sub_cat=items.get(getAdapterPosition()).getTitle();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", items.get(getAdapterPosition()).getId());
                            bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                            bundle.putString("sub_cat", "sub_cat_child");
                            Size_My_Project fragment = new Size_My_Project();
                            fragment.setArguments(bundle);
                            ((Post_Project_Main) activity).replaceFragment(fragment);
                        }
                        else
                        {
                            ((Post_Project_Main) activity).sub_cat_id=items.get(getAdapterPosition()).getId();
                            ((Post_Project_Main) activity).sub_cat=items.get(getAdapterPosition()).getTitle();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", items.get(getAdapterPosition()).getId());
                            bundle.putString("name", items.get(getAdapterPosition()).getTitle());
//                        Size_My_Project fragment = new Size_My_Project();
                            Description_My_Project fragment = new Description_My_Project();
                            fragment.setArguments(bundle);
                            ((Post_Project_Main) activity).replaceFragment(fragment);
                        }



                    }
                    else if(values.equalsIgnoreCase("sub_cat_child"))
                    {
                        int count=Integer.parseInt(items.get(getAdapterPosition()).getNumber());
                        if(count>0)
                        {

                            ((Post_Project_Main) activity).child.setId(items.get(getAdapterPosition()).getId());
                            ((Post_Project_Main) activity).child.setName(items.get(getAdapterPosition()).getTitle());
                            ((Post_Project_Main) activity).child_cat.add(((Post_Project_Main) activity).child);
                            if(Constant.isNetConnected(activity))
                            {
                                getCompany_Entity(getAdapterPosition());
                            }
                            else
                            {
                                Constant.ToastShort(activity,activity.getResources().getString(R.string.internet_connection));
                            }
                        }
                        else
                        {
                            ((Post_Project_Main) activity).child.setId(items.get(getAdapterPosition()).getId());
                            ((Post_Project_Main) activity).child.setName(items.get(getAdapterPosition()).getTitle());
                            ((Post_Project_Main) activity).child_cat.add(((Post_Project_Main) activity).child);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", items.get(getAdapterPosition()).getId());
                            bundle.putString("name", items.get(getAdapterPosition()).getTitle());
//                        Size_My_Project fragment = new Size_My_Project();
                            Description_My_Project fragment = new Description_My_Project();
                            fragment.setArguments(bundle);
                            ((Post_Project_Main) activity).replaceFragment(fragment);
                        }


                    }
                }
            });
        }
    }


    private void getCompany_Entity(final int adpposition) {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();

        try {
            params.put("category_id", items.get(adpposition).getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(activity, params, Url_Links.GetSize_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        if(obj.getJSONArray("sub-category").length()>0)
                        {
                            items.clear();
//                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("sub-category").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("sub-category").getJSONObject(i).getJSONObject("_id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("sub-category").getJSONObject(i).getString("title"));
                                items.add(data);
                            }
//                            Category_Adapter recyclerView_Adapter = new Category_Adapter(activity,items,"size");
//                            recycler.setAdapter(recyclerView_Adapter);
                            notifyDataSetChanged(items);
                        }

                    }
                    else
                    {
                        Constant.ToastShort(activity,obj.getString("msg"));
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(adpposition).getId());
                        bundle.putString("name", items.get(adpposition).getTitle());
//                        Size_My_Project fragment = new Size_My_Project();
                        Description_My_Project fragment = new Description_My_Project();
                        fragment.setArguments(bundle);
                        ((Post_Project_Main) activity).replaceFragment(fragment);
                    }


//                    Constant.ToastShort(getActivity(),obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(activity, error.getMessage());
            }
        };
    }

    private void notifyDataSetChanged(ArrayList<Items> items) {
        Constant.Log("Response===","Response==="+items.size());
        this.items=items;
    }
}
