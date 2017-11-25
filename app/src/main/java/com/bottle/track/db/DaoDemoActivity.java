package com.bottle.track.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bottle.track.R;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;

import java.util.Date;

import static com.bottle.util.JsonHelperKt.toJsonString;
import static com.bottle.util.JsonHelperKt.toObject;

public class DaoDemoActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;
    private NoteDao noteDao;
    private Query<Note> notesQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao_demo);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        noteDao = daoSession.getNoteDao();
        notesQuery = noteDao.queryBuilder().orderAsc(NoteDao.Properties.Text).build();

        Note note = new Note();
        note.setText("This is a note");
        note.setComment("This is comment");
        note.setDate(new Date());
        noteDao.insert(note);
        Log.d(TAG, "Inserted new note, ID: " + note.getId());
        String json = toJsonString(note);
        Log.d(TAG, json);
        Note note2 = toObject(json, Note.class);
        Log.d(TAG, note2.getText());
    }
}
