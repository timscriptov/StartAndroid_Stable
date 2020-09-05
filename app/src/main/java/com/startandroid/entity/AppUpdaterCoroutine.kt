package com.startandroid.entity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.startandroid.App.Companion.getContext
import com.startandroid.BuildConfig
import com.startandroid.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.svolf.melissa.sheet.SweetContentDialog
import java.lang.ref.WeakReference
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

class AppUpdaterCoroutine : CoroutineScope {
    private val TAG = "AppUpdater"
    private lateinit var version_name: String
    private var version_code = 0
    private lateinit var release_notes: String
    private lateinit var download_link: String
    private lateinit var context: WeakReference<Context>

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun with(ctx: Context) {
        context = WeakReference(ctx)
    }

    fun execute() = launch {
        Log.d(TAG, "execute() called")
        val checkUpdate = doInBackground()
        onPostExecute(checkUpdate)
    }

    private suspend fun doInBackground(): Boolean = withContext(Dispatchers.IO) {
        Log.d(TAG, "doInBackground() called")
        try {
            val url = URL("https://mcal-llc.github.io/sa/config/update.xml")
            val con = url.openConnection() as HttpsURLConnection
            val dbf = DocumentBuilderFactory.newInstance()
            val builder = dbf.newDocumentBuilder()
            val doc = builder.parse(con.inputStream)
            con.disconnect()
            version_name = doc.getElementsByTagName("version_name").item(0).textContent
            version_code = doc.getElementsByTagName("version_code").item(0).textContent.toInt()
            release_notes = doc.getElementsByTagName("release_notes").item(0).textContent
            download_link = doc.getElementsByTagName("download_link").item(0).textContent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext version_code > BuildConfig.VERSION_CODE
    }

    private suspend fun onPostExecute(needUpdate: Boolean) = withContext(coroutineContext) {
        Log.d(TAG, "onPostExecute() called")
        try {
            if (needUpdate) {
                updateApp()
            }
            Log.d(TAG, "checkResult: $needUpdate")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateApp() {
        val updateDialog = SweetContentDialog(context.get()!!).apply {
            setTitle(context.getString(R.string.version_available) + " " + version_name)
            setMessage(release_notes)
            setCancelable(false)
        }
        updateDialog.setPositive(R.drawable.ic_update, context.get()!!.getString(R.string.update)) {
            getContext()!!.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(download_link)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            exitProcess(0)
        }
        updateDialog.show()
    }
}