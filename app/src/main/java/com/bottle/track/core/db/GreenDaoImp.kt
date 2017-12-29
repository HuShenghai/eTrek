package com.bottle.track.core.db

import android.content.Context
import com.bottle.track.core.db.gen.DaoMaster
import com.bottle.track.core.db.gen.DaoSession
import org.greenrobot.greendao.database.Database

/**
 * @Date 2017/12/5 10:46
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class GreenDaoImp {

    val database: Database

    val daoSession: DaoSession

    private constructor(context: Context, name: String){
        val helper = DaoMaster.DevOpenHelper(context, name)
        database = helper.writableDb
        daoSession = DaoMaster(database).newSession()
    }

    companion object {

        private var imp: GreenDaoImp? = null

        fun getInstance(context: Context, name: String): GreenDaoImp{
            if(imp == null){
                imp = GreenDaoImp(context, name)
            }
            return imp!!
        }
    }

}