package com.startandroid.data;

import com.startandroid.BuildConfig;
import com.startandroid.utils.Utils;

import static com.startandroid.data.Preferences.isOffline;

public final class Constants {
    private static final String PACKAGE_NAME = Utils.xor("てどと〨ぶひつぴぱでにぢぷどぬぢ", true);//com.startandroid
    public static final String UPDATE_PATH = Utils.xor("ねひぱぶぶ〼〪〩とづつな〨などづ〫ちぬひねびで〨ぬど〪ふつ〩てどにだぬち〪びふぢつひだ〨ぽにど", true);//https://mcal-llc.github.io/sa/config/update.xml
    public static final String PREMIUM = BuildConfig.DEBUG ? "android.test.purchased" : Utils.xor("ふぴだにぬびと", true); // premium
    public static final String INITIALIZE = Utils.xor("てで〨でふぶ〨ぶばつ〨〷〱〷〴〲〼〳〱〴〲〱〱〷〵〳〰へ〶〴〴〰〼〳〱〷〴〾", true); // ca-app-pub-1411495427741055~3216954118
    public static final String ADMOB_BANNER = Utils.xor("てで〨でふぶ〨ぶばつ〨〷〱〷〴〲〼〳〱〴〲〱〱〷〵〳〰〩〰〿〰〿〵〿〷〳〰〲", true); // ca-app-pub-1411495427741055/5959092554
    public static final String ADMOB_INTERSTITIAL = Utils.xor("てで〨でふぶ〨ぶばつ〨〷〱〷〴〲〼〳〱〴〲〱〱〷〵〳〰〩〶〴〱〷〵〶〱〾〱〴", true); // ca-app-pub-1411495427741055/3241004842
    public static final String SIGNATURE = Utils.xor("ひ〾〪〿ぴったばぼぷぁが〲のぉ〲ぢたとぶつお〮だぱぇの", true); // w8/9qeZvyqDJ7hL4gYmpaL+ftAk
    public static final String SIGNATURE_2 = Utils.fromBase64("dzgvOXFlWnZ5cURKN2hMNGdZbXBhTCtmdEFr"); // w8/9qeZvyqDJ7hL4gYmpaL+ftAk
    public static final String DOWNLOAD_ZIP = Utils.xor("ねひぱぶぶ〼〪〩ぱはとふてぴぬぶぱどび〨ぢはぱのばつ〫はな〩どっぶふなとぶ〩ぶひつぴぱでにぢぷどぬぢ〫ぼぬぶ", true); // https://timscriptov.github.io/lessons/startandroid.zip
    public static final String RATE = Utils.xor("とでぷねだひ〿〩〪ぢだひつはどふ〺はち〻てどと〨ぶひつぴぱでにぢぷどぬぢ", true); // market://details?id=com.startandroid
    public static final String LK = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjwVwPnZ+iwu4+dAUMoO+SOY4XKsLIzsE9hCgxcgjZdKY0poUgqcadOAebfW+LkZfced9VaRodxQIXVZcdYruFW9mDMW69/dqnvjY7adYMMBpEh58E6Mq2Yt4AZYbOaquW+I6DmtvlHa34Vh3eyj3RDiaY3bS2yhpeCd5Rdwcnhjk7mE15gY+wM9bSe9AGmqx+FKlU6xww8drqWYR/xnov5seF1NuIQTzWoe0muKB+lnqhOeN9q8cOawoZqlSRagqYe9zrQ0SrfJ7N3e5nT2LpgQ33IC+Tvzkz3TwfFPbVGheuTlcWV0PweXwvaMkf2dYRcfiF4zFhIZnzKmm46aELwIDAQAB";
    private static final String MCAL_LLC = Utils.xor("ねひぱぶぶ〼〪〩ぱはとふてぴぬぶぱどび〨ぢはぱのばつ〫はな〩ざひつぴぱぇにぢぷどぬぢ〪ぶつちだふ", true); // https://timscriptov.github.io/StartAndroid/pages

    public static final String UTF_8 = Utils.xor("ぐげぃ〫〽", true); // UTF-8
    public static final String POSITION = Utils.xor("ふどぶはぱはなと", true); // position
    public static final String URL = Utils.xor("ばぴど", true); // url
    public static final String IS_READ = Utils.xor("ぬふしっつぢ", true); // isRead
    public static final String TEXT_HTML = Utils.xor("ぱっぽひ〪のぱにど", true); // text/html
    public static final String FILE = Utils.xor("っはどっ〿〩〪〩", true); // file:///
    public static final String RESOURCES = Utils.xor("ぷっぶどばぴてっぶ", true); // resources
    public static final String OFFLINE_ZIP = Utils.xor("なだっなぬとだ〨みはふ", true); // offline.zip
    public static final String IS_PREMIUM = Utils.xor("ぬふさぴだにぬびと", true); // isPremium
    public static final String ANTIPATCH = Utils.xor("てづ〫つぬととひ〫ふぬちにでぱびぷっ〫ざとふきどなねいぶふなぬづつひぬどに", true); // cc.binmt.signature.PmsHookApplication
    public static final String ANTIPATCH1 = Utils.xor("つとぼにぼ〨ぶはぢと〫いぬとざはぢとつひばぴだ\u3040ぬま", true); // anymy.sign.BinSignatureFix
    public static final String ANTIPATCH2 = Utils.xor("つぶのっちはぱどぷ〨ふでぱづね〨ぶはぢとつひばぴだ〨ぃはぽ", true); // apkeditor.patch.signature.Fix
    public static final String ANTIPATCH3 = Utils.xor("てどと〨つとぼにぼ〨ぷっっなだづぱはなと", true); // com.anymy.reflection
    public static final String ANTIPATCH4 = Utils.xor("ではに〨とひ〫でふねぶはぢとつひばぴだねぬなどっぷぶどびぶ〨きどなねいぶふなぬづつひぬどに", true); // bin.mt.apksignaturekillerplus.HookApplication
    public static final String ANTIPATCH5 = Utils.xor("てづ〫つぬととひ〫ふぬちにでぱびぷっ〫ぎなどの", true); // cc.binmt.signature.Hook
    public static final String META_INF = Utils.xor("えぃけぇ〨くか぀", true); // META-INF/
    public static final String RSA = Utils.xor("〫ござぇ", true); // .RSA
    public static final String DSA = Utils.xor("〫あざぇ", true); // .DSA
    public static final String SHA = Utils.xor("ざぎい", true); // SHA
    public static final String SHA_256 = Utils.xor("ざぎい〫〷〳〳", true); // SHA-256
    public static final String MORE_APPS = Utils.xor("とでぷねだひ〿〩〪ふだでぷづね〹ぴ〻ふびで〼㐝㐴㐵㐻〥㐤㐽㐺㐵㑎㐿㐸㐷", true); // market://search?q=pub:Иван Тимашков
    public static final String HTML = Utils.xor("〫のぱにど", true); // .html
    public static final String LESSON_PATH = Utils.xor("〪なだふぶどにす", true); // /lesson_
    public static final String LESSON = Utils.xor("どっぶふなと", true); // lesson
    public static final String OFFLINE = Utils.xor("なだっなぬとだ", true); // offline
    public static final String IS_RATED = Utils.xor("ぬふしでぱっち", true); // isRated

    static final String FIRSTLAUNCH = Utils.xor("っはぷふぱおつびにづね", true); // firstLaunch
    static final String FONT = Utils.xor("っどにひ", true); // font
    static final String BOOKMARK = Utils.xor("でどなねとでぷね", true); // bookmark
    static final String FULLSCREEN_MODE = Utils.xor("っびどなぶづぷっだとずになぢだ", true); // fullscreen_mode
    static final String NIGHT_MODE = Utils.xor("にはぢのぱすとどちっ", true); // night_mode
    static final String GRID_MODE = Utils.xor("ぢぴぬぢずになぢだ", true); // grid_mode
    static final String FONT_SIZE = Utils.xor("っどにひずふぬぼだ", true); // font_size
    static final String LANG = Utils.xor("どでにち", true); // lang

    private static final String DATA_DATA = Utils.xor("ちでぱで〪ぢつひつ〩", true); // data/data/
    private static final String FILES_RESOURCES_PAGES = Utils.xor("〪だぬなだふ〪ぴだふなびぷづだふ〪ぶつちだふ", true); // /files/resources/pages

    public static String getResPath() {
        if (isOffline()) return DATA_DATA + PACKAGE_NAME + FILES_RESOURCES_PAGES;
        else return MCAL_LLC;
    }
}