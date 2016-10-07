package com.example.localadmin.lab1;


        import android.content.Context;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {


    static ListView listview = null;
    static   Button sendButton = null ;
    static   ArrayList<String> arrayList  = new ArrayList<String> () ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final EditText etext  = (EditText)findViewById(R.id.chatEditText)  ;

        listview = (ListView) findViewById(R.id.listView) ;
        sendButton = (Button) findViewById(R.id.sendButton) ;

        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listview.setAdapter (messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String sendMsg =  etext.getText().toString() ;

                arrayList.add(sendMsg);
                messageAdapter.notifyDataSetChanged();
                etext.setText("");
            }
        });


    }
    private class ChatAdapter extends ArrayAdapter<String>
    {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return  arrayList.size() ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
        @Override
        public String getItem(int position) {
            return arrayList.get(position);
        }
    }
}