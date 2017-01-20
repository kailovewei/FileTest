package ykk.file.com;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private Button r_button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r_button= (Button) findViewById(R.id.r_Id);
        editText= (EditText) findViewById(R.id.editText_Id);
        //从文件中读取数据的监听器
        r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText=load();
                if(!TextUtils.isEmpty(inputText))
                {
                    editText.setText(inputText);
                    editText.setSelection(inputText.length());
                    Toast.makeText(MainActivity.this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText=editText.getText().toString();
        save(inputText);
    }
    public void save(String inputText)
    {
        FileOutputStream out=null;
        BufferedWriter bufferedWriter=null;
        try {
            out=openFileOutput("yan", Context.MODE_PRIVATE);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String load()
    {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try {
            in=openFileInput("yan");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null)
            {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null)
            {
                try {
                    reader.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        }
        return content.toString();
    }
}
