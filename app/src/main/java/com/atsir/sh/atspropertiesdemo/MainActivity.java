package com.atsir.sh.atspropertiesdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(
                R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        try {
            GetAllProperties("zh_CN.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 根据Key读取Value
    public String GetValueByKey(String name, String key) {
        Properties pps = new Properties();
        try {
            InputStream in = MainActivity.this.getClass()
                    .getResourceAsStream("/assets/" + name);
            pps.load(in);
            String value = pps.getProperty(key);
            System.out.println(key + " = " + value);
            return value;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //
        // Properties pro = new Properties();
        // InputStream is = context.getAssets().open("test.properties");
        // pro.load(is);

        // InputStream is = context.getResources().openRawResource(R.raw.test);

        // Properties pro = new Properties();
        // pro.load(FileLoad.class.getResourceAsStream("/assets/test.properties"));
    }

    // 读取Properties的全部信息
    public void GetAllProperties(String name) throws IOException {
        Properties pps = new Properties();
        InputStream in = MainActivity.this.getClass()
                .getResourceAsStream("/assets/" + name);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        pps.load(bufferedReader);
        Enumeration en = pps.propertyNames(); // 得到配置文件的名字

        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            System.out.println(strKey + "=" + strValue);
        }

    }

    // 写入Properties信息
    public static void WriteProperties(String filePath, String pKey,
            String pValue) throws IOException {
        Properties pps = new Properties();

        InputStream in = new FileInputStream(filePath);
        // 从输入流中读取属性列表（键和元素对）
        pps.load(in);
        // 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream out = new FileOutputStream(filePath);
        pps.setProperty(pKey, pValue);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        pps.store(out, "Update " + pKey + " name");
    }
}
