package com.example.f;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelp extends SQLiteOpenHelper{
    private static final String DATABASENAME ="zhang";//数据库名称
    private static final int DATABASEVERSION =1;//数据库版本
    private static final String TABLENAME="zhang";//表名
    public DataBaseHelp(Context context)//定义构造
    {
    super(context,DATABASENAME,null,DATABASEVERSION);    //调用父类构造
    }
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE "+TABLENAME+"("+
                "id   INTEGER PRIMARY KEY AUTOINCREMENT,"+                   //设置自动增长列
                "mon  VARCHAR(50) NOT NULL,"+
                "bei  VARCHAR(50) NOT NULL," +
                "type  VARCHAR(50) NOT NULL," +
                "time  VARCHAR(50) NOT NULL," +
                "num  VARCHAR(50) NOT NULL" +
                ")";
    db.execSQL(sql);    //执行sql语句
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        String sql="DROP TABLE IF EXISTS "+TABLENAME;
        db.execSQL(sql);
        this.onCreate(db);//创建表

    }

}
