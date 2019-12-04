-keepattributes SourceFile, LineNumberTable

-renamesourcefileattribute SourceFile
-repackageclasses kotlin

-keep class org.slf4j.LoggerFactory

-keepclassmembers class com.mcal.kotlin.utils.Utils {
    public static java.lang.String xor(java.lang.Sting, boolean);
}

-obfuscationdictionary proguard-dictionary.txt
-packageobfuscationdictionary proguard-dictionary.txt
-classobfuscationdictionary proguard-dictionary.txt