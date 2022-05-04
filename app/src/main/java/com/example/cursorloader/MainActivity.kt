package com.example.cursorloader

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity() {

    private var adapter:SimpleCursorAdapter? = null

    val CONTACT_LOADER_ID = 78

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCursorAdapter()

        supportLoaderManager.initLoader(
            CONTACT_LOADER_ID,
            Bundle(),
            contactsLoader
        )
        val lvContacts: ListView = findViewById<View>(R.id.lvNames) as ListView
        lvContacts.setAdapter(adapter)
    }

    private fun setupCursorAdapter() {
        val uiBindFrom = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI
        )
        val uiBindTo = intArrayOf(R.id.tvName,R.id.image)

        adapter = SimpleCursorAdapter(
            this,R.layout.item_layout,
            null,uiBindFrom,uiBindTo,
            0
        )
    }
    private val contactsLoader: LoaderManager.LoaderCallbacks<Cursor?> =
        object  : LoaderManager.LoaderCallbacks<Cursor?> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {
                val projectionFields = arrayOf(
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI
                )
                return CursorLoader(
                    this@MainActivity,
                    ContactsContract.Contacts.CONTENT_URI,
                    projectionFields,
                    null,
                    null,
                    null
                )
            }

            override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
                adapter!!.swapCursor(data)
            }

            override fun onLoaderReset(loader: Loader<Cursor?>) {
                adapter!!.swapCursor(null)
            }

        }
}