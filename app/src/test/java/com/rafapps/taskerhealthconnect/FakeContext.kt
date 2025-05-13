package com.rafapps.taskerhealthconnect

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.UserHandle
import android.view.Display
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class FakeContext: Context() {
    override fun bindService(service: Intent, conn: ServiceConnection, flags: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkCallingOrSelfPermission(permission: String): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingOrSelfUriPermission(uri: Uri?, modeFlags: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingPermission(permission: String): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingUriPermission(uri: Uri?, modeFlags: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkPermission(permission: String, pid: Int, uid: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkSelfPermission(permission: String): Int {
        TODO("Not yet implemented")
    }

    override fun checkUriPermission(uri: Uri?, pid: Int, uid: Int, modeFlags: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkUriPermission(
        uri: Uri?,
        readPermission: String?,
        writePermission: String?,
        pid: Int,
        uid: Int,
        modeFlags: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun clearWallpaper() {
        TODO("Not yet implemented")
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        TODO("Not yet implemented")
    }

    override fun createContextForSplit(splitName: String?): Context {
        TODO("Not yet implemented")
    }

    override fun createDeviceProtectedStorageContext(): Context {
        TODO("Not yet implemented")
    }

    override fun createDisplayContext(display: Display): Context {
        TODO("Not yet implemented")
    }

    override fun createPackageContext(packageName: String?, flags: Int): Context {
        TODO("Not yet implemented")
    }

    override fun databaseList(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun deleteDatabase(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteFile(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteSharedPreferences(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun enforceCallingOrSelfPermission(permission: String, message: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingOrSelfUriPermission(uri: Uri?, modeFlags: Int, message: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingPermission(permission: String, message: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingUriPermission(uri: Uri?, modeFlags: Int, message: String?) {
        TODO("Not yet implemented")
    }

    override fun enforcePermission(permission: String, pid: Int, uid: Int, message: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceUriPermission(
        uri: Uri?,
        pid: Int,
        uid: Int,
        modeFlags: Int,
        message: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun enforceUriPermission(
        uri: Uri?,
        readPermission: String?,
        writePermission: String?,
        pid: Int,
        uid: Int,
        modeFlags: Int,
        message: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun fileList(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getApplicationContext(): Context {
        TODO("Not yet implemented")
    }

    override fun getApplicationInfo(): ApplicationInfo {
        TODO("Not yet implemented")
    }

    override fun getAssets(): AssetManager {
        TODO("Not yet implemented")
    }

    override fun getCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getClassLoader(): ClassLoader {
        TODO("Not yet implemented")
    }

    override fun getCodeCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getContentResolver(): ContentResolver {
        TODO("Not yet implemented")
    }

    override fun getDataDir(): File {
        TODO("Not yet implemented")
    }

    override fun getDatabasePath(name: String?): File {
        TODO("Not yet implemented")
    }

    override fun getDir(name: String?, mode: Int): File {
        TODO("Not yet implemented")
    }

    override fun getExternalCacheDir(): File? {
        TODO("Not yet implemented")
    }

    override fun getExternalCacheDirs(): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getExternalFilesDir(type: String?): File? {
        TODO("Not yet implemented")
    }

    override fun getExternalFilesDirs(type: String?): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getExternalMediaDirs(): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getFileStreamPath(name: String?): File {
        TODO("Not yet implemented")
    }

    override fun getFilesDir(): File {
        TODO("Not yet implemented")
    }

    override fun getMainLooper(): Looper {
        TODO("Not yet implemented")
    }

    override fun getNoBackupFilesDir(): File {
        TODO("Not yet implemented")
    }

    override fun getObbDir(): File {
        TODO("Not yet implemented")
    }

    override fun getObbDirs(): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getPackageCodePath(): String {
        TODO("Not yet implemented")
    }

    override fun getPackageManager(): PackageManager {
        TODO("Not yet implemented")
    }

    override fun getPackageName(): String {
        TODO("Not yet implemented")
    }

    override fun getPackageResourcePath(): String {
        TODO("Not yet implemented")
    }

    override fun getResources(): Resources {
        TODO("Not yet implemented")
    }

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences {
        TODO("Not yet implemented")
    }

    override fun getSystemService(name: String): Any {
        TODO("Not yet implemented")
    }

    override fun getSystemServiceName(serviceClass: Class<*>): String? {
        TODO("Not yet implemented")
    }

    override fun getTheme(): Resources.Theme {
        TODO("Not yet implemented")
    }

    override fun getWallpaper(): Drawable {
        TODO("Not yet implemented")
    }

    override fun getWallpaperDesiredMinimumHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun getWallpaperDesiredMinimumWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun grantUriPermission(toPackage: String?, uri: Uri?, modeFlags: Int) {
        TODO("Not yet implemented")
    }

    override fun isDeviceProtectedStorage(): Boolean {
        TODO("Not yet implemented")
    }

    override fun moveDatabaseFrom(sourceContext: Context?, name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun moveSharedPreferencesFrom(sourceContext: Context?, name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun openFileInput(name: String?): FileInputStream {
        TODO("Not yet implemented")
    }

    override fun openFileOutput(name: String?, mode: Int): FileOutputStream {
        TODO("Not yet implemented")
    }

    override fun openOrCreateDatabase(
        name: String?,
        mode: Int,
        factory: SQLiteDatabase.CursorFactory?
    ): SQLiteDatabase {
        TODO("Not yet implemented")
    }

    override fun openOrCreateDatabase(
        name: String?,
        mode: Int,
        factory: SQLiteDatabase.CursorFactory?,
        errorHandler: DatabaseErrorHandler?
    ): SQLiteDatabase {
        TODO("Not yet implemented")
    }

    override fun peekWallpaper(): Drawable {
        TODO("Not yet implemented")
    }

    override fun registerReceiver(receiver: BroadcastReceiver?, filter: IntentFilter?): Intent? {
        TODO("Not yet implemented")
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        flags: Int
    ): Intent? {
        TODO("Not yet implemented")
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        broadcastPermission: String?,
        scheduler: Handler?
    ): Intent? {
        TODO("Not yet implemented")
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        broadcastPermission: String?,
        scheduler: Handler?,
        flags: Int
    ): Intent? {
        TODO("Not yet implemented")
    }

    override fun removeStickyBroadcast(intent: Intent?) {
        TODO("Not yet implemented")
    }

    override fun removeStickyBroadcastAsUser(intent: Intent?, user: UserHandle?) {
        TODO("Not yet implemented")
    }

    override fun revokeUriPermission(uri: Uri?, modeFlags: Int) {
        TODO("Not yet implemented")
    }

    override fun revokeUriPermission(toPackage: String?, uri: Uri?, modeFlags: Int) {
        TODO("Not yet implemented")
    }

    override fun sendBroadcast(intent: Intent?) {
        TODO("Not yet implemented")
    }

    override fun sendBroadcast(intent: Intent?, receiverPermission: String?) {
        TODO("Not yet implemented")
    }

    override fun sendBroadcastAsUser(intent: Intent?, user: UserHandle?) {
        TODO("Not yet implemented")
    }

    override fun sendBroadcastAsUser(
        intent: Intent?,
        user: UserHandle?,
        receiverPermission: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun sendOrderedBroadcast(intent: Intent?, receiverPermission: String?) {
        TODO("Not yet implemented")
    }

    override fun sendOrderedBroadcast(
        intent: Intent,
        receiverPermission: String?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun sendOrderedBroadcastAsUser(
        intent: Intent?,
        user: UserHandle?,
        receiverPermission: String?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun sendStickyBroadcast(intent: Intent?) {
        TODO("Not yet implemented")
    }

    override fun sendStickyBroadcastAsUser(intent: Intent?, user: UserHandle?) {
        TODO("Not yet implemented")
    }

    override fun sendStickyOrderedBroadcast(
        intent: Intent?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun sendStickyOrderedBroadcastAsUser(
        intent: Intent?,
        user: UserHandle?,
        resultReceiver: BroadcastReceiver?,
        scheduler: Handler?,
        initialCode: Int,
        initialData: String?,
        initialExtras: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun setTheme(resid: Int) {
        TODO("Not yet implemented")
    }

    override fun setWallpaper(bitmap: Bitmap?) {
        TODO("Not yet implemented")
    }

    override fun setWallpaper(data: InputStream?) {
        TODO("Not yet implemented")
    }

    override fun startActivities(intents: Array<out Intent>?) {
        TODO("Not yet implemented")
    }

    override fun startActivities(intents: Array<out Intent>?, options: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun startActivity(intent: Intent?) {
        TODO("Not yet implemented")
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        TODO("Not yet implemented")
    }

    override fun startInstrumentation(
        className: ComponentName,
        profileFile: String?,
        arguments: Bundle?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun startIntentSender(
        intent: IntentSender?,
        fillInIntent: Intent?,
        flagsMask: Int,
        flagsValues: Int,
        extraFlags: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun startIntentSender(
        intent: IntentSender?,
        fillInIntent: Intent?,
        flagsMask: Int,
        flagsValues: Int,
        extraFlags: Int,
        options: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun startService(service: Intent?): ComponentName? {
        TODO("Not yet implemented")
    }

    override fun stopService(service: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun unbindService(conn: ServiceConnection) {
        TODO("Not yet implemented")
    }

    override fun unregisterReceiver(receiver: BroadcastReceiver?) {
        TODO("Not yet implemented")
    }
}
