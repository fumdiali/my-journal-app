package com.digirealis.thejournal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digirealis.thejournal.database.DatabaseHelper;
import com.digirealis.thejournal.database.model.Entry;
import com.digirealis.thejournal.utils.MyDividerItemDecoration;
import com.digirealis.thejournal.utils.RecyclerTouchListener;
import com.digirealis.thejournal.view.EntriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EntriesAdapter mAdapter;
    private List<Entry> entriesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noEntriesView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noEntriesView = findViewById(R.id.empty_notes_view);

        db = new DatabaseHelper(this);

        entriesList.addAll(db.getAllEntries());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryDialog(false, null, -1);
            }
        });


        mAdapter = new EntriesAdapter(this, entriesList);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

    toggleEmptyEntries();

    /**
     * On long press on RecyclerView item, open alert dialog
     * with options to choose
     * Edit and Delete
     * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                                                                      recyclerView, new RecyclerTouchListener.ClickListener() {
        @Override
        public void onClick(View view, final int position) {
        }

        @Override
        public void onLongClick(View view, int position) {
            showActionsDialog(position);
        }
    }));


  }// end of on create

    /**
     * Inserting new entry in db
     * and refreshing the list
     */
    private void createEntry(String entry) {
        // inserting entry in db and getting
        // newly inserted entry id
        long id = db.insertEntry(entry);

        // get the newly inserted note from db
        Entry e = db.getEntry(id);

        if (e != null) {
            // adding new note to array list at 0 position
            entriesList.add(0, e);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyEntries();
        }
    }

    /**
     * Updating note in db and updating
     * item in the list by its position
     */
    private void updateEntry(String entry, int position) {
        Entry e = entriesList.get(position);
        // updating entry text
        e.setEntry(entry);

        // updating entry in db
        db.updateEntry(e);

        // refreshing the list
        entriesList.set(position, e);
        mAdapter.notifyItemChanged(position);

        toggleEmptyEntries();
    }

    /**
     * Deleting entry from SQLite and removing the
     * item from the list by its position
     */
    private void deleteEntry(int position) {
        // deleting the entry from db
        db.deleteEntry(entriesList.get(position));

        // removing the entry from the list
        entriesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyEntries();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showEntryDialog(true, entriesList.get(position), position);
                } else if(which == 1){
                    deleteEntry(position);
                }else{ dialog.cancel(); }
            }
        });
        builder.show();
    }

    /**
     * Shows alert dialog with EditText options to enter / edit
     * an entry.
     * when shouldUpdate=true, it automatically displays old note and changes the
     * button text to UPDATE
     */
    private void showEntryDialog(final boolean shouldUpdate, final Entry entry, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.entry_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputEntry = view.findViewById(R.id.note);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && entry != null) {
            inputEntry.setText(entry.getEntry());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputEntry.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Make an entry..", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && entry != null) {
                    // update note by it's id
                    updateEntry(inputEntry.getText().toString(), position);
                } else {
                    // create new note
                    createEntry(inputEntry.getText().toString());
                }
            }
        });
    }

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyEntries() {
        // you can check notesList.size() > 0

        if (db.getEntriesCount() > 0) {
            noEntriesView.setVisibility(View.GONE);
        } else {
            noEntriesView.setVisibility(View.VISIBLE);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
