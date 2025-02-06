# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# tasker
-keepattributes *Annotation*
-keep public class com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject { *; }
-keep public class com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable { *; }

-keepclasseswithmembers class * {
    @com.joaomgcd.taskerpluginlibrary.input.TaskerInputField <fields>;
}
-keep @com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot public class *
-keepclassmembers @com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot class * {
   public <init>(...);
}
-keep @com.joaomgcd.taskerpluginlibrary.input.TaskerInputObject public class *
-keepclassmembers @com.joaomgcd.taskerpluginlibrary.input.TaskerInputObject class * {
   public <init>(...);
}
-keep @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject public class *
-keepclassmembers class * {
    @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject *;
}
-keepclassmembers class * {
    @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable *;
}
-keepclassmembers @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject class * { *; }
-keep public class * extends com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginRunner { *; }

-keep public class net.dinglisch.android.tasker.PluginResultReceiver { *; }

# health connect classes via reflection
-keep class androidx.health.connect.client.records.** { *; }
-keep class androidx.health.connect.client.units.** { *; }

-dontwarn android.**
-dontwarn com.google.**

# for jackson
-keepattributes SourceFile,LineNumberTable,*Annotation*,EnclosingMethod,Signature,Exceptions,InnerClasses
-keep class kotlin.Metadata { *; }
-keep class kotlin.Unit { *; }
-keep class kotlin.reflect.** { *; }

-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
-keep @com.fasterxml.jackson.annotation.JsonCreator class * { *; }
-keep @com.fasterxml.jackson.annotation.JsonValue class * { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
}

-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**

-keepclassmembers class * {
     @com.fasterxml.jackson.annotation.* *;
}
