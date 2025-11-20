package com.chan.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chan.database.dao.CategoryDao
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.OrdersDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.ProductDetailDao
import com.chan.database.dao.ProductsDao
import com.chan.database.dao.SearchHistoryDao
import com.chan.database.dao.UserDao
import com.chan.database.entity.CommonCategoryEntity
import com.chan.database.entity.CommonProductEntity
import com.chan.database.entity.ProductDetailEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.UserEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.database.entity.order.OrderEntity
import com.chan.database.entity.search.SearchHistoryEntity

val MIGRATION_13_14 = object : Migration(13, 14) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE `searchHistory_new` (
                `search` TEXT NOT NULL, 
                `timeStamp` INTEGER NOT NULL, 
                PRIMARY KEY(`search`)
            )
        """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO `searchHistory_new` (search, timeStamp)
            SELECT search, MAX(timeStamp) FROM `searchHistory` GROUP BY search
        """.trimIndent()
        )

        db.execSQL("DROP TABLE `searchHistory`")

        db.execSQL("ALTER TABLE `searchHistory_new` RENAME TO `searchHistory`")
    }
}

val MIGRATION_15_16 = object : Migration(15, 16) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `cart_products` (
                `userId` TEXT NOT NULL,
                `productId` TEXT NOT NULL,
                `quantity` INTEGER NOT NULL,
                PRIMARY KEY(`userId`, `productId`)
            )
        """.trimIndent()
        )
    }
}

val MIGRATION_16_17 = object : Migration(16, 17) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE cart_products 
            ADD COLUMN isSelected INTEGER NOT NULL DEFAULT 1
            """.trimIndent()
        )
    }
}
val MIGRATION_14_15 = object : Migration(14, 15) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `products` (
                `productId` TEXT NOT NULL, 
                `productName` TEXT NOT NULL, 
                `brandName` TEXT NOT NULL, 
                `imageUrl` TEXT NOT NULL, 
                `originalPrice` INTEGER NOT NULL, 
                `discountPercent` INTEGER NOT NULL, 
                `discountPrice` INTEGER NOT NULL, 
                `tags` TEXT NOT NULL, 
                `reviewRating` REAL NOT NULL, 
                `reviewCount` INTEGER NOT NULL, 
                `categoryIds` TEXT NOT NULL, 
                PRIMARY KEY(`productId`)
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `category` (
                `id` TEXT NOT NULL, 
                `name` TEXT NOT NULL, 
                `parentCategoryId` TEXT, 
                PRIMARY KEY(`id`)
            )
        """.trimIndent())
    }
}

val MIGRATION_17_18 = object : Migration(17, 18) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS cart")
    }
}

val MIGRATION_18_19 = object : Migration(18, 19) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS homeBanner")
        database.execSQL("""
            CREATE TABLE homeBanner (
                id TEXT NOT NULL,
                imageUrl TEXT NOT NULL,
                linkType TEXT NOT NULL,
                linkUrl TEXT NOT NULL,
                PRIMARY KEY(id)
            )
        """)
    }
}

val MIGRATION_19_20 = object : Migration(19, 20) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS orders (
                orderId TEXT NOT NULL PRIMARY KEY,
                orderData TEXT NOT NULL,
                totalPrice INTEGER NOT NULL,
                createdAt INTEGER NOT NULL,
                items TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}


@Database(
    entities = [HomeBannerEntity::class, ProductEntity::class, ProductDetailEntity::class, UserEntity::class, SearchHistoryEntity::class, CommonProductEntity::class, CommonCategoryEntity::class, OrderEntity::class],
    version = 20,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 12, to = 13)
    ]
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun productDetailDao(): ProductDetailDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productsDao(): ProductsDao
    abstract fun ordersDao(): OrdersDao
}