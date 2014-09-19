package com.mcfly.cstracker.sqlite;

/**
 * Created by mcfly on 12/02/14.
 */

 import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.nio.channels.FileChannel;


        import android.content.Context;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DbManager extends SQLiteOpenHelper
{
	private final static String TAG = DbManager.class.getName();
	
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kayentis_epro.db";

    public static final String DATABASE_DATABASE_FULLPATH = "/data/data/com.kayentis.epro/databases/";
    public static final String DATABASE_DATABASE_CREATE_FILENAME = "sql/create.sql";
    public static final String DATABASE_DATABASE_CREATE_BASETEST = "sql/baseTest.sql";

    public static final String DATABASE_DATABASE_VIEWS = "sql/views.sql";
    public static final String DATABASE_DATABASE_TRIGGERS = "sql/triggers.sql";

    private static Context context;
    private static DbManager instance;

    public static synchronized DbManager getInstance(Context c) {
        if(instance==null) {
            context = c;
            instance= new DbManager();
        }
        return instance;
    }

    private DbManager() {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DbManager", "...call onCreate");
        createFromSQL(db);
        createViews(db);
//        createTriggers(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d("DbManager", "...call onOpen");
        Log.d("DbManager", "   ... path:"+db.getPath());
        Log.d("DbManager", "   ... opened:"+db.isOpen());
        super.onOpen(db);
        if(!isDataBaseFilled()) {
        	insertDbValues(db);
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DbManager", "...call onUpgrade");
//		db.execSQL("DROP TABLE IF EXISTS " + IFormTable.TABLE_NAME);
        onCreate(db);
    }
    
    //----------------------------
    
    
    private boolean isDataBaseFilled() {
    	// TODO
    	return true;
    }
    
    

    private void copyDataBase() throws IOException
    {
        InputStream inputStream = null;
        inputStream = context.getAssets().open(DATABASE_DATABASE_CREATE_BASETEST);
        String outFileName = DATABASE_DATABASE_FULLPATH+DATABASE_NAME;
        File dir = new File("DATABASE_DATABASE_FULLPATH");
        dir.mkdir();
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[8192];
        int length;
        while ((length = inputStream.read(buffer))!=-1){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }



    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }


    private void createFromSQL(SQLiteDatabase db) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(DATABASE_DATABASE_CREATE_FILENAME);
        } catch (IOException e1) {
        	Log.e(TAG, "No such file", e1);
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DbManager", "...creating db :");
        Log.d("DbManager", db.getPath());
        String[] statements = getStatementsFromFile(total.toString());
        Log.d("DbManager", statements.length+" statements will be executed");
        for(String statement : statements) {
            db.execSQL(statement);
        }
    }
    
    
    private void insertDbValues(SQLiteDatabase db) {
    	Log.d(TAG, "...insert db values :");
    	String[] statements = getStatementsFromFile(readFile(DATABASE_DATABASE_CREATE_BASETEST));
    	for(String statement : statements) {
            db.execSQL(statement);
        }
    }
    
    private String readFile(String path) {
    	InputStream is = null;
    	try {
    		is = context.getAssets().open(DATABASE_DATABASE_CREATE_BASETEST);
    	} catch (IOException e ){
    		Log.e(TAG, "No such file", e);
    	}
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
    	StringBuilder stringBuilder = new StringBuilder();
    	String line;
    	try {
    		while ((line = bufferedReader.readLine()) != null) {
    			stringBuilder.append(line);
            }
    	} catch (IOException e ) {
    		Log.e(TAG, "Error while reading file "+path, e);
    	}
    	return stringBuilder.toString();
    }
    
    

    private void createViews(SQLiteDatabase db) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(DATABASE_DATABASE_VIEWS);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "...creating views to db :");
        Log.d(TAG, db.getPath());
        String[] statements = getStatementsFromFile(total.toString());
        Log.d(TAG, statements.length+" statements will be executed");
        for(String statement : statements) {
//        	Log.d(TAG, "###"+statement);
            db.execSQL(statement);
        }
    }


    private void createTriggers(SQLiteDatabase db) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(DATABASE_DATABASE_TRIGGERS);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "...creating triggers to db :");
        Log.d(TAG, db.getPath());
        String[] statements = getTriggerStatementsFromFile(total.toString());
        Log.d(TAG, statements.length+" statements will be executed");
        for(String statement : statements) {
            try {
                db.execSQL(statement+" END;");
            } catch (SQLException e) {
                Log.e(TAG, "..trigger creation exception",e);
            }

        }
    }


    private String[] getStatementsFromFile(String statements)
    {
        String[] result = statements.split(";");
        return result;
    }

    private String[] getTriggerStatementsFromFile(String statements)
    {
        String[] result = statements.split("END;");
        return result;
    }

}
