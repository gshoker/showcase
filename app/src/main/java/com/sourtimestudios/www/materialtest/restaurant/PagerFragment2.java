package com.sourtimestudios.www.materialtest.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sourtimestudios.www.materialtest.R;

//import com.sourtime.exhibits.R;

/**
 * Created by user on 05/12/14.
 */
public class PagerFragment2 extends Fragment {

    ArrayAdapter<String> mMessageAdapter;
    Button sendButton;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.conversation, container, false);

        ListView listView = (ListView)v.findViewById(R.id.in);

        mMessageAdapter = new ArrayAdapter<String>(getActivity(),R.layout.message);
        listView.setAdapter(mMessageAdapter);

        editText = (EditText)v.findViewById(R.id.edit_text_out);

        sendButton = (Button)v.findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                mMessageAdapter.add(msg);
            }
        });

        return v;
    }
}
