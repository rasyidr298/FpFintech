package id.fp.core.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.securepreferences.SecurePreferences
import id.fp.core.domain.model.Sample
import java.lang.reflect.Type

private const val PREFS_NAME = "money manager"

class PrefManager(context: Context) {

    //    encrypt
    private val sp: SharedPreferences by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()

            EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            SecurePreferences(context, PREFS_NAME, PREFS_NAME)
        }
    }

//    no encrypt
//    private val sp: SharedPreferences by lazy {
//        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//    }

    val spe: SharedPreferences.Editor by lazy {
        sp.edit()
    }

    /* ==============================================PREF VAR================================================*/


    //Pref Thema
    var spTheme: Int
        get() = sp.getInt("spTheme", 3)
        set(value) {
            spe.putInt("spTheme", value)
            spe.apply()
        }


    /* ==============================================PREF OBJECT================================================*/

    fun spSaveSample(list: List<Sample>) {
        val gson = Gson()
        val json: String = gson.toJson(list)
        spe.putString("spSaveSample", json)
        spe.apply()
    }

    fun spGetSample(): List<Sample>? {
        val gson = Gson()
        val json: String? = sp.getString("spSaveSample", "")
        val type: Type = object : TypeToken<List<Sample?>?>() {}.type
        return gson.fromJson(json, type)
    }

}
