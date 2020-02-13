package com.example.f;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateTable {
    private static final String TABLENAME ="zhang";
    private SQLiteDatabase db=null;
    public OperateTable(SQLiteDatabase db)
    {
        this.db=db;
    }
    public void insert(String mon,String bei,String type,String time,String num)
    {
        String sql="INSERT INTO "+TABLENAME+" (mon,bei,type,time,num) VALUES ('"+mon+"','"+bei+"','"+type+"','"+time+"','"+num+"')";
        System.out.println(sql);
        this.db.execSQL(sql);


    }
    public void delete(String id)
    {
        String sql="DELETE FROM "+TABLENAME+" WHERE id='"+id+"'";
        this.db.execSQL(sql);


    }
    public void deleteall()
    {
        String sql="DELETE FROM "+TABLENAME;
        this.db.execSQL(sql);
    }

    public List<Map<String,Object>> getdata()
    {List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();

        String sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" ORDER BY id DESC ";
        Cursor result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            map=new HashMap<String,Object>();
            map.put("id",result.getInt(0));
            map.put("mon",result.getString(1));
            map.put("bei",result.getString(2));
            map.put("type",result.getString(3));
            map.put("time",result.getString(4));
            if(result.getString(1).equals("收入")){
            map.put("num","+"+result.getString(5));}
            else map.put("num","-"+result.getString(5));
            list.add(map);
        }
        return  list;}

    public data getd()
    {
        data d=new data();
        float yi=0,shi=0,zhu=0,xing=0,qita=0,chu=0,ru=0;
        float a=0;
        String sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  mon='收入' ";
        Cursor result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
           a+=Float.parseFloat(result.getString(5));
        }
        d.setRu(a);a=0;
        sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  mon='支出' ";
         result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            a+=Float.parseFloat(result.getString(5));
        }
    d.setChu(a);a=0;
        sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  type='衣服' ";
        result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            a+=Float.parseFloat(result.getString(5));
        }
        d.setYi(a);a=0;
        sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  type='家居' ";
        result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            a+=Float.parseFloat(result.getString(5));
        }
        d.setZhu(a);a=0;
        sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  type='出行' ";
        result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            a+=Float.parseFloat(result.getString(5));
        }
        d.setXing(a);a=0;
        sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  type='食品' ";
        result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            a+=Float.parseFloat(result.getString(5));
        }
        d.setShi(a);a=0;
        sql="SELECT id,mon,bei,type,time,num FROM "+TABLENAME+" WHERE  type='其他' ";
        result =this.db.rawQuery(sql,null);
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            a+=Float.parseFloat(result.getString(5));
        }
        d.setQita(a);a=0;
        return d;
    }

}
