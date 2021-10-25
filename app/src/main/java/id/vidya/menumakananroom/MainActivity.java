package id.vidya.menumakananroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtRevisiNamaMakanan;
    Button btnTambahMakanan;
    Button btnResetAll;
    RecyclerView rvMenu;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtRevisiNamaMakanan = findViewById(R.id.edtNamaMakanan);
        btnTambahMakanan = findViewById(R.id.btnTambahMakanan);
        btnResetAll = findViewById(R.id.btnResetAll);
        rvMenu = findViewById(R.id.rvMenu);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        linearLayoutManager =  new LinearLayoutManager(this);

        rvMenu.setLayoutManager(linearLayoutManager);
        adapter =  new MainAdapter(MainActivity.this, dataList);
        rvMenu.setAdapter(adapter);
        btnTambahMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = edtRevisiNamaMakanan.getText().toString().trim();
                if(!sName.equals("")){
                    MainData data = new MainData();
                    data.setName(sName);
                    database.mainDao().insert(data);
                    edtRevisiNamaMakanan.setText("");
                   dataList.clear();
                   dataList.addAll(database.mainDao().getAll());
                   adapter.notifyDataSetChanged();
                }
            }
        });

        btnResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}