package me.kyuubiran.qqcleaner.utils


import com.alibaba.fastjson.JSONArray
import me.kyuubiran.qqcleaner.utils.CleanManager.getFiles
import me.kyuubiran.qqcleaner.utils.CleanManager.getFullList
import me.kyuubiran.qqcleaner.utils.CleanManager.getHalfList
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_LIST
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_MODE
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_TOTAL_CLEANED_SIZE
import me.kyuubiran.qqcleaner.utils.ConfigManager.getConfig
import me.kyuubiran.qqcleaner.utils.ConfigManager.getLong
import java.io.File
import kotlin.concurrent.thread

/**
 * 如果需要定义一个新的分类 请在瘦身目录那块区域定义一个常量
 * 并且在res/values/arrays.xml下的customer_clean_list和customer_clean_list_value添加对应键值
 * @see getHalfList 添加目录到一键瘦身
 * @see getFullList 添加目录到彻底瘦身
 * @see getFiles 添加清理目录用
 */
object CleanManager {
    //瘦身模式
    const val HALF_MODE = "half_mode"
    const val FULL_MODE = "full_mode"
    const val CUSTOMER_MODE = "customer_mode"

    //瘦身目录
    private const val CACHES = "caches"
    private const val PICTURE = "picture"
    private const val SHORT_VIDEO = "short_video"
    private const val ADS = "ads"
    private const val ARK_APP = "ark_app"
    private const val WEB = "web"
    private const val DIY_CARD = "diy_card"
    private const val FONT = "font"
    private const val GIFT = "gift"
    private const val ENTRY_EFFECT = "entry_effect"
    private const val USER_ICON = "user_icon"
    private const val ICON_PENDANT = "icon_pendant"
    private const val USER_BACKGROUND = "user_background"
    private const val STICKER_RECOMMEND = "sticker_recommend"
    private const val STICKER_EMOTION = "sticker_emotion"
    private const val POKE = "poke"
    private const val VIP_ICON = "vip_icon"
    private const val DOU_TU = "dou_tu"
    private const val VIDEO_BACKGROUND = "video_background"
    private const val RECEIVE_FILE_CACHE = "receive_file_cache"
    private const val TBS = "tbs"
    private const val OTHERS = "others"

    //计算清理完毕后的释放的空间
    private var size = 0L

    /**
     * 根据tag获取文件列表
     * @param item Tag
     * @return ArrayList<File>
     */
    private fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            //缓存
            CACHES -> {
                arr.add(File("$rootDataDir/cache"))
                arr.add(File("$MobileQQDir/diskcache"))
                arr.add(File("$MobileQQDir/Scribble/ScribbleCache"))
            }
            //图片缓存
            PICTURE -> {
                arr.add(File("$MobileQQDir/photo"))
                arr.add(File("$MobileQQDir/chatpic"))
                arr.add(File("$MobileQQDir/thumb"))
                arr.add(File("$QQ_Images/QQEditPic"))
                arr.add(File("$MobileQQDir/hotpic"))
            }
            //短视频
            SHORT_VIDEO -> {
                arr.add(File("$MobileQQDir/shortvideo"))
            }
            //广告
            ADS -> {
                arr.add(File("$MobileQQDir/qbosssplahAD"))
                arr.add(File("$MobileQQDir/pddata"))
            }
            //小程序
            ARK_APP -> {
                arr.add(File("$TencentDir/mini"))
            }
            //网页
            WEB -> {
                arr.add(File("$rootTencentDir/msflogs/com/tencent/mobileqq"))
            }
            //diy名片
            DIY_CARD -> {
                arr.add(File("$MobileQQDir/.apollo"))
                arr.add(File("$MobileQQDir/vas/lottie"))
            }
            //字体
            FONT -> {
                arr.add(File("$MobileQQDir/.font_info"))
                arr.add(File("$MobileQQDir/.hiboom_font"))
            }
            //礼物
            GIFT -> {
                arr.add(File("$MobileQQDir/.gift"))
            }
            //进场特效
            ENTRY_EFFECT -> {
                arr.add(File("$MobileQQDir/.troop/enter_effects"))
            }
            //头像
            USER_ICON -> {
                arr.add(File("$MobileQQDir/head"))
            }
            //挂件
            ICON_PENDANT -> {
                arr.add(File("$MobileQQDir/.pendant"))
            }
            //背景
            USER_BACKGROUND -> {
                arr.add(File("$MobileQQDir/.profilecard"))
            }
            //表情推荐
            STICKER_RECOMMEND -> {
                arr.add(File("$MobileQQDir/.sticker_recommended_pics"))
                arr.add(File("$MobileQQDir/pe"))
            }
            //聊天表情缓存
            STICKER_EMOTION -> {
                arr.add(File("$MobileQQDir/.emotionsm"))
            }
            //戳一戳
            POKE -> {
                arr.add(File("$MobileQQDir/.vaspoke"))
                arr.add(File("$MobileQQDir/newpoke"))
                arr.add(File("$MobileQQDir/poke"))
            }
            //vip图标
            VIP_ICON -> {
                arr.add(File("$MobileQQDir/.vipicon"))
            }
            //斗图
            DOU_TU -> {
                arr.add(File("$MobileQQDir/DoutuRes"))
            }
            //视频通话背景
            VIDEO_BACKGROUND -> {
                arr.add(File("$MobileQQDir/funcall"))
            }
            //接收的文件缓存
            RECEIVE_FILE_CACHE -> {
                arr.add(File("$QQfile_recv/trooptmp"))
                arr.add(File("$QQfile_recv/tmp"))
                arr.add(File("$QQfile_recv/thumbnails"))
            }
            //其他
            OTHERS -> {
                arr.add(File("$MobileQQDir/qav"))
                arr.add(File("$MobileQQDir/qqmusic"))
                arr.add(File("$MobileQQDir/pddata"))
                arr.add(File("$TencentDir/TMAssistantSDK"))
            }
        }
        return arr
    }

    //    storage/emulated/0/Android/data/com.tencent.mobileqq
    private var rootDataDir: String? = appContext?.externalCacheDir?.parentFile?.path

    //    storage/emulated/0/
    private var rootDir: String? = appContext?.obbDir?.parentFile?.parentFile?.parentFile?.path

    //    storage/emulated/0/tencent
    private var rootTencentDir = "$rootDir/tencent"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent
    private var TencentDir = "$rootDataDir/Tencent"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/MobileQQ
    private var MobileQQDir = "$TencentDir/MobileQQ"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/QQ_Images
    private var QQ_Images = "$rootDataDir/QQ_Images"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv
    private var QQfile_recv = "$TencentDir/QQfile_recv"

    /**
     * @return 获取普通(一键)瘦身的列表
     */
    private fun getHalfList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(SHORT_VIDEO))
        addAll(getFiles(ADS))
        addAll(getFiles(ARK_APP))
        addAll(getFiles(WEB))
        addAll(getFiles(DIY_CARD))
        addAll(getFiles(USER_BACKGROUND))
        addAll(getFiles(VIDEO_BACKGROUND))
    }

    /**
     * @return 获取全部(彻底)瘦身的列表
     */
    private fun getFullList() = ArrayList<File>().apply {
        addAll(getHalfList())
        addAll(getFiles(FONT))
        addAll(getFiles(GIFT))
        addAll(getFiles(ENTRY_EFFECT))
        addAll(getFiles(USER_ICON))
        addAll(getFiles(ICON_PENDANT))
        addAll(getFiles(STICKER_RECOMMEND))
        addAll(getFiles(STICKER_EMOTION))
        addAll(getFiles(POKE))
        addAll(getFiles(VIP_ICON))
        addAll(getFiles(DOU_TU))
        addAll(getFiles(RECEIVE_FILE_CACHE))
        addAll(getFiles(TBS))
        addAll(getFiles(OTHERS))
    }

    //保存清理的内存
    private fun saveSize() {
        val totalSize = getLong(CFG_TOTAL_CLEANED_SIZE).plus(size)
        ConfigManager.setConfig(CFG_TOTAL_CLEANED_SIZE, totalSize)
    }

    /**
     * @return 获取用户自定义的瘦身列表
     */
    private fun getCustomerList(): ArrayList<File> {
        val customerList = getConfig(CFG_CUSTOMER_CLEAN_LIST) as JSONArray
        val arr = ArrayList<File>()
        for (s in customerList) {
            arr.addAll(getFiles(s.toString()))
        }
        return arr
    }

    /**
     * @param showToast 是否显示Toast
     * 一键瘦身
     */
    fun halfClean(showToast: Boolean = true) {
        doClean(getHalfList(), showToast)
    }

    /**
     * @param showToast 是否显示Toast
     * 彻底瘦身
     */
    fun fullClean(showToast: Boolean = true) {
        doClean(getFullList(), showToast)
    }

    /**
     * @param showToast 是否显示Toast
     * 自定义瘦身
     */
    fun customerClean(showToast: Boolean = true) {
        doClean(getCustomerList(), showToast)
    }

    /**
     * @param files 瘦身列表
     * @param showToast 是否显示toast
     * 执行瘦身的函数
     */
    private fun doClean(files: ArrayList<File>, showToast: Boolean = true) {
        thread {
            size = 0L
            if (showToast) appContext?.makeToast("好耶 开始清理了!")
            try {
                for (f in files) {
//                    appContext?.makeToast("开始清理${f.path}")
                    delFiles(f)
                }
                appContext?.makeToast("好耶 清理完毕了!腾出了${formatSize(size)}空间!")
                saveSize()
            } catch (e: Exception) {
                loge(e)
                appContext?.makeToast("坏耶 清理失败了!")
            }
        }
    }

    /**
     * @param file 文件/文件夹
     * 删除文件/文件夹的函数
     */
    private fun delFiles(file: File) {
        if (!file.exists()) return
        if (file.isFile) {
            size += file.length()
            file.delete()
        } else {
            val list = file.listFiles()
            if (list == null || list.isEmpty()) {
                file.delete()
            } else {
                for (f in list) {
                    delFiles(f)
                }
            }
        }
    }

    //自动瘦身的类
    class AutoClean {
        private var time = 0L
        private val delay = ConfigManager.getInt(CFG_CLEAN_DELAY, 24) * 3600L * 1000L
        private var mode = ""

        //在QQ加载模块的时候会检测并执行一次
        init {
            time = getLong(CFG_CURRENT_CLEANED_TIME)
            //判断间隔
            if (getConfig(CFG_AUTO_CLEAN_ENABLED) as Boolean && System.currentTimeMillis() - time > if (delay < 3600_000L) 24 * 3600L * 1000L else delay) {
                mode = getConfig(CFG_CUSTOMER_CLEAN_MODE).toString()
                autoClean()
                time = System.currentTimeMillis()
                ConfigManager.setConfig(CFG_CURRENT_CLEANED_TIME, time)
            }
        }

        //自动瘦身的函数
        private fun autoClean() {
            appContext?.makeToast("好耶 开始自动清理了!")
            when (mode) {
                FULL_MODE -> {
                    fullClean(false)
                }
                CUSTOMER_MODE -> {
                    customerClean(false)
                }
                else -> {
                    halfClean(false)
                }
            }
        }
    }
}