

package instagramAPI.settings.Storage

import instagramAPI.settings.Storage.Components.PDOStorage
import PDO

/**
 * Persistent storage backend which uses a MySQL server.
 *
 * Read the PDOStorage documentation for extra details about this backend.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class MySQL : PDOStorage() {
    /**
     * Constructor.
     *
     * {@inheritdoc}
     */
    fun constructor(){
        // Configure the name of this backend.
        super constructor("MySQL")
    }

    /**
     * Create a PDO connection to the database.
     *
     * {@inheritdoc}
     */
    protected fun _createPDO(array locationConfig){
        val username = if(locationConfig["dbusername"]) locationConfig["dbusername"] else "root"
        val password = if(locationConfig["dbpassword"]) locationConfig["dbpassword"] else ""
        val host     = if(locationConfig["dbhost"]) locationConfig["dbhost"] else "localhost"
        val dbName   = if(locationConfig["dbname"]) locationConfig["dbname"] else "instagram"

        return PDO("mysql:host={$host}dbname={$dbName}",username, password)
    }

    /**
     * Enable UTF-8 encoding on the connection.
     *
     * {@inheritdoc}
     */
    protected fun _enableUTF8(){
        // IMPORTANT: MySQL databases have two names for UTF-8! If you use
        // "utf8", they only allow 1-3 byte encoding. But UTF-8 is actually a
        // 1-4 byte format (things like Emojis need that final byte). Therefore,
        // we MUST import their "utf8mb4" encoding instead. The "utf8mb4" storage
        // needs are identical for 1-3 byte characters, but supports 4 bytes!
        // See: https://dev.mysql.com/doc/refman/5.7/en/charset-unicode-utf8mb4.html
        _pdo.query("SET NAMES \"utf8mb4\"").closeCursor()
    }

    /**
     * Automatically create the database table if necessary.
     *
     * {@inheritdoc}
     */
    protected fun _autoCreateTable(){
        // Detect the name of the MySQL database that PDO is connected to.
        val dbName = _pdo.query("SELECT database()").fetchColumn()

        // Abort if we already have the necessary table.
        val sth = _pdo.prepare("SELECT count(*) FROM information_schema.TABLES WHERE (TABLE_SCHEMA = :tableSchema) AND (TABLE_NAME = :tableName)")
        sth.execute( mapOf(":tableSchema" to dbName, ":tableName" to _dbTableName) )
        val result = sth.fetchColumn()
        sth.closeCursor()
        if (result > 0) {
            return
        }

        // Create the database table. Throws in case of failure.
        // NOTE: We store all settings as a binary JSON blob so that we support
        // all current and future data without having to alter the table schema.
        // NOTE: The username is a 150-character-max varchar, NOT 255! Why?
        // Becaimport MySQL has a 767-byte max limit for efficient indexes, and
        // "utf8mb4" uses 4 bytes per character, which means that 191 characters
        // is the maximum safe amount (191 * 4 = 764)! We chose 150 as a nice
        // number. Instagram"s username limit is 30, so our limit is fine!
        // NOTE: We import "utf8mb4_general_ci" which performs fast, general
        // sorting, since we have no need for language-aware "unicode" sorting.
        // NOTE: Lastly... note that our encoding only affects the "username"
        // column. All other columns are numbers, binary blobs, etc!
        _pdo.exec("CREATE TABLE `" + _dbTableName + "` (
            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            username VARCHAR(150) NOT NULL,
            settings MEDIUMBLOB NULL,
            cookies MEDIUMBLOB NULL,
            last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            UNIQUE KEY (username)
        ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ENGINE=InnoDB")
    }
}
