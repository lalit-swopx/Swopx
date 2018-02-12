package com.swopx.registration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.swopx.R;
import com.swopx.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by reve on 09-01-2018.
 */

public class ContactIndexing extends Activity implements View.OnClickListener {

    Map<String, Integer> mapIndex;
    ListView fruitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_indexing);

        String[] fruits = getResources().getStringArray(R.array.fruits_array);

        Arrays.asList(fruits);

        fruitList = (ListView) findViewById(R.id.list_fruits);
        fruitList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fruits));

        getIndexList(fruits);

        displayIndex();

    }

    private void getIndexList(String[] fruits) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.length; i++) {
            String fruit = fruits[i];
            String index = fruit.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        Constant.ToastShort(ContactIndexing.this,""+mapIndex.get(selectedIndex.getText()));
        fruitList.setSelection(mapIndex.get(selectedIndex.getText()));
    }
}

