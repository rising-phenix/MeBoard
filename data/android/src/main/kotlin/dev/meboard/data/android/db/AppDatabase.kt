package dev.meboard.data.android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.room.TypeConverters
import dev.meboard.data.android.dao.DictionaryDao
import dev.meboard.data.android.dao.NGramDao
import dev.meboard.data.android.dao.SettingsDao
import dev.meboard.data.android.model.AppSetting
import dev.meboard.data.android.model.DictionaryEntry
import dev.meboard.data.android.model.NGramCount

/** Database version. Increment when schema changes and add a migration. */
const val DATABASE_VERSION = 1

/** Migration from version 1 to 2 (placeholder for future schema changes). */
val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
        // No schema changes yet; reserved for future migrations.
    }
}

@Database(
    entities = [DictionaryEntry::class, NGramCount::class, AppSetting::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun nGramDao(): NGramDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meboard_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
