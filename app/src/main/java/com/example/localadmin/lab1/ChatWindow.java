package com.example.localadmin.lab1;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;

        import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";
    static ListView listview = null;
    static Button sendButton = null;
    static ArrayList<String> arrayList = new ArrayList<String>();
    ChatDatabaseHelper dbHelper;    // Step 5 of Lab 5
    SQLiteDatabase db;  // Step 5 of Lab 5


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(ChatWindow.this); // Step 5 of Lab 5
        db = dbHelper.getWritableDatabase(); // Step 5 of Lab 5

        final EditText etext = (EditText) findViewById(R.id.chatEditText);

        listview = (ListView) findViewById(R.id.listView);
        sendButton = (Button) findViewById(R.id.sendButton);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listview.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String sendMsg = etext.getText().toString();
       //         arrayList.add(sendMsg);

                ContentValues contentValues = new ContentValues(); // Step 6 for Lab 5
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, etext.getText().toString()); // Step 6 for Lab 5
                db.insert(ChatDatabaseHelper.CHAT_TABLE, "", contentValues); // Step 6 for Lab 5- insert takes 3 parameters- tablename,
                //nullColumnHack to check for the null columns, ContentValues object

                messageAdapter.notifyDataSetChanged(); // This restarts the process of getCount() / getView()
                etext.setText("");
            }
        });


    }

    // Step 8 for Lab 5
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            db.close();
            dbHelper.close();
        } catch (Exception e) {
        }
    }




    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount()
        {
            arrayList.clear();
            // Step 5 of Lab 5
            Cursor cursor;
            cursor = db.rawQuery(ChatDatabaseHelper.READALL_CHAT_TABLE, null); //it takes 2 parameters- string sql and string[] selectionArgs
            int messageIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);

            // Print an information message about the Cursor
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
            // Then use a for loop to print out the name of each column returned by the cursor.
            for (int colIndex = 0; colIndex < cursor.getColumnCount(); colIndex++) {
                Log.i(ACTIVITY_NAME, "Column name of " + colIndex + " = " + cursor.getColumnName(colIndex));
            }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrayList.add(cursor.getString(messageIndex));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(messageIndex));
                cursor.moveToNext();
            }

            return arrayList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }

        @Override
        public String getItem(int position) {
            return arrayList.get(position);
        }
    }
}