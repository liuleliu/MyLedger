package com.example.f.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.f.DataBaseHelp;
import com.example.f.OperateTable;
import com.example.f.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private madaper adapter=null;
private FloatingActionButton add=null;
private ListView listView=null;
    private OperateTable mytable =null;
    private SQLiteOpenHelper helper=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        helper=new DataBaseHelp(getContext());

        this.mytable=new OperateTable(this.helper.getWritableDatabase());

        listView=(ListView)root.findViewById(R.id.vi);
        adapter = new madaper(getContext(),this.mytable.getdata(), R.layout.list
                , new String[]{"mon","bei","type","time","id","num"},
                new int[]{R.id.mon,R.id.bei,R.id.type,R.id.time,R.id.id,R.id.num});

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new OnItemLongClick());

        add=(FloatingActionButton)root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup=new PopupMenu(getContext(),v);
                MenuInflater inflate=popup.getMenuInflater();
                inflate.inflate(R.menu.addmenu,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.chu:
                                LayoutInflater flater= LayoutInflater.from(getContext());
                                final View dialogview=flater.inflate(R.layout.chu,null);
                                Dialog dialog=new AlertDialog.Builder(getContext()).setTitle("支出")
                                        .setView(dialogview)
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        String msg;
                                                        Spinner ty=(Spinner)dialogview.findViewById(R.id.mon);
                                                        msg=ty.getSelectedItem().toString();
                                                        EditText num=(EditText)dialogview.findViewById(R.id.num);
                                                        EditText bei=(EditText)dialogview.findViewById(R.id.bei);
                                                        if(!num.getText().toString().equals("")&&!num.getText().toString().equals(null))
                                                        mytable.insert("支出",bei.getText().toString(),msg,getdate(),num.getText().toString());
                                                        else Toast.makeText(getContext(),"金额不能为空",Toast.LENGTH_SHORT).show();
                                                        onResume();

                                                    }
                                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).create();
                                dialog.show();




                                break;

                            case R.id.ru:
                                 flater= LayoutInflater.from(getContext());
                                final View dialogview2=flater.inflate(R.layout.ru,null);
                                dialog=new AlertDialog.Builder(getContext()).setTitle("收入")
                                        .setView(dialogview2)
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        EditText num=(EditText)dialogview2.findViewById(R.id.num);
                                                        EditText bei=(EditText)dialogview2.findViewById(R.id.bei);
                                                        if(!num.getText().toString().equals("")&&!num.getText().toString().equals(null))
                                                            mytable.insert("收入",bei.getText().toString(),"   ",getdate(),num.getText().toString());
                                                          else Toast.makeText(getContext(),"金额不能为空",Toast.LENGTH_SHORT).show();
                                                            onResume();
                                                    }
                                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).create();
                                dialog.show();


                                break;
                            default:
                                 break;
                        }
                        return false;
                    }
                }


                );
                popup.show();
            }
        });
        return root;
    }
    private class OnItemLongClick implements AdapterView.OnItemLongClickListener
    {
        public boolean onItemLongClick(AdapterView<?>arg0, View arg1, int arg2, long arg3){


            HashMap<String,Object> map=(HashMap<String,Object>)listView.getItemAtPosition(arg2);
            final String info;
            info=map.get("id").toString();

            AlertDialog myAlertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("确认" )
                    .setMessage("确定删除吗？" )
                    .setPositiveButton("是" , new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mytable.delete(info);
                            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    })
                    .setNegativeButton("否" , null)
                    .show();

            return true;


        }

    }

       public class madaper extends SimpleAdapter{
            public madaper(Context context, List<Map<String, Object>> items, int resource, String[] from, int[] to) {
                super(context, items, resource, from, to);

            }
           public View getView(int position, View convertView, ViewGroup parent){
              convertView=super.getView(position, convertView, parent);
               TextView mon=(TextView)convertView.findViewById(R.id.mon);
               TextView num=(TextView)convertView.findViewById(R.id.num);
              final LinearLayout par=(LinearLayout)convertView.findViewById(R.id.parent);
              if(mon.getText().toString().equals("支出"))num.setTextColor(Color.RED);
              else num.setTextColor(Color.GREEN);

              convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                  @Override
                  public void onFocusChange(View v, boolean hasFocus) {
                      if(hasFocus){par.setBackgroundColor(Color.BLUE);}
                      else par.setBackgroundColor(00000000);
                  }
              });

                return convertView;
           }

        }
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        adapter = new madaper(getContext(),this.mytable.getdata(), R.layout.list
                , new String[]{"mon","bei","type","time","id","num"},
                new int[]{R.id.mon,R.id.bei,R.id.type,R.id.time,R.id.id,R.id.num});

        listView.setAdapter(adapter);

    }
    public String getdate()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 ");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time1;
        time1=simpleDateFormat.format(date);
        return time1;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.uper, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem delete=menu.findItem(R.id.delete);
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog myAlertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("确认" )
                        .setMessage("确定删除全部记录吗？" )
                        .setPositiveButton("是" , new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mytable.deleteall();
                                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                        })
                        .setNegativeButton("否" , null)
                        .show();

                return false;
            }
        });

    }
}