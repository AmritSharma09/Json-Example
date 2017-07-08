package com.example.golu.jsonprojecteg;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecFragment extends Fragment {
//TextView tv;
Button click;
    RecyclerView rec;
    private ArrayList<Actor> actors;
    private MyTask myTask;

    private MyAdapter myAdapter;////adaprter

    public RecFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_rec, container, false);
        click=(Button)v.findViewById(R.id.click);
        rec=(RecyclerView)v.findViewById(R.id.rec);
       // tv=(TextView)v.findViewById(R.id.text);
        actors=new ArrayList<Actor>();
        myTask=new MyTask();
       myAdapter=new MyAdapter();



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              myTask.execute("http://api.androidhive.info/contacts/");
                click.setVisibility(View.GONE);
            }
        });
        rec.setAdapter(myAdapter);

        LinearLayoutManager mng=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rec.setLayoutManager(mng);
        return v;
    }



    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Load Row XML
            View view=getActivity().getLayoutInflater().inflate(R.layout.row,parent,false);
            //pass row xml to view holder
            ViewHolder viewHolder=new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
         Actor ac=actors.get(position);
            holder.tv1.setText(ac.getSno());
            Log.d("Hello",""+ac.getSno());
            holder.tv2.setText(ac.getActorName());
            holder.tv3.setText(ac.getEmail());
            holder.tv4.setText(ac.getMobile());
        }

        @Override
        public int getItemCount() {
            return actors.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv1,tv2,tv3,tv4;
            public ViewHolder(View itemView) {
                super(itemView);
                tv1=(TextView)itemView.findViewById(R.id.tv_count);
                tv2=(TextView)itemView.findViewById(R.id.tv_name);
                tv3=(TextView)itemView.findViewById(R.id.tv_mail);
                tv4=(TextView)itemView.findViewById(R.id.tv_mobile);


            }
        }
    }

    private class MyTask extends AsyncTask<String,Void,String>{

        URL url;  //THIS VARIABLE FOR PREAPAREING URL OF THE WEBSITE
        HttpURLConnection connection;// THIS IS FRO OPENNIG SERVER CONNECTION
        InputStream inputStream;//THIS IOS TO OPEN INPUTSTREAM, SO THAT WE CAN READ SERVER DATA
        InputStreamReader inputStreamReader;//THIS IS TO CONVER VITS AND BYTE TO ACSII CHARS
        BufferedReader bufferedReader;// THIS IS TO BUFFER SERVER DATA IN LINE BY LINE FASHION
        String line;// THIS IS TO READ EACH LINE FROM BUFFERED-READER
        StringBuilder stringBuilder;//THIS IS TO PILE UP EACH LINE (ABOVE ) TO DISPLAY ON TEXTVIEW
        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(),"Task is about to Start",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //15  Go to asyncTask - write networl connection logic in - do in background
                //a. prepare website url
                url=new URL(strings[0]); //strings[0] will contain url of the website -from button
                //b. open connection with server
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                //c. open input Stream -cruicial- this might take couple of seconds
                inputStream  =connection.getInputStream();
                //d. open input stream reader
                inputStreamReader =new InputStreamReader(inputStream);
                //e. open buffer reader
                bufferedReader=new BufferedReader(inputStreamReader);
                //f. using loop read line by line from buffered reader
                stringBuilder=new StringBuilder();
                do{
                    line=bufferedReader.readLine();
                    stringBuilder.append(line);

                }while(line!=null);

                //done , now return data which is in string builder -to- onpost execute


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("WEB Services","URL PROBLEM");
                //Toast.makeText(getActivity(),"URL PROBLEM",Toast.LENGTH_SHORT).show();
                return "INVALID URL";//IF URL IS NOT WRITE, THIS WILL BE DISPLAYED
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("B36","Io Exceptiom"+e.getCause() );
                //Toast.makeText(getActivity(),"Io Exception"+e.getCause(),Toast.LENGTH_SHORT).show();
                return "UNABLE TO OPEN CONNECTION"+e.getMessage();
            }


            return stringBuilder.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject j=new JSONObject(s);
                JSONArray contacts=j.getJSONArray("contacts");
                for(int i=0;i<contacts.length();i++){
                    JSONObject mycontact=contacts.getJSONObject(i);
                    String id=mycontact.getString("id");
                    String name=mycontact.getString("name");
                    String email=mycontact.getString("email");
                    JSONObject phone=mycontact.getJSONObject("phone");
                    String mobile=phone.getString("mobile");
                    //push above details[name,email,contact] to arraylist
                    actors.add(new Actor(id,name,email,mobile));
          //tell to adapter
                    new MyAdapter().notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"JSon Parsing Error"+e.getCause(),Toast.LENGTH_SHORT).show();
            }
            // tv.setText(s);
        }
    }

}


