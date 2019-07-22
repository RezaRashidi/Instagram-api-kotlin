

package instagramAPI.settings.Storage

import instagramAPI.Constants
import instagramAPI.settings.Storage.Components.PDOStorage
import instagramAPI.Utils
import PDO

/**
 * Persistent storage backend which uses a SQLite database file.
 *
 * Read the PDOStorage documentation for extra details about this backend.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
class SQLite : PDOStorage(){
    /**
     * Constructor.
     *
     * {@inheritdoc}
     */
    fun constructor(){
        // Configure the name of this backend.
        super constructor("SQLite")
    }

    /**
     * Create a PDO connection to the database.
     *
     * {@inheritdoc}
     */
    protected fun _createPDO(array locationConfig){
        // Determine the filename for the SQLite database.
        val sqliteFile = if ( !locationConfig["dbfilename"].isBlank() && !locationConfig["dbfilename"].isEmpty() )
                       locationConfig["dbfilename"] else Constants.SRC_DIR + "/../sessions/instagram.db"

        // Ensure that the whole directory path to the database exists.
        val sqliteDir = dirname(sqliteFile) // Can be "." in case of CWD.
        if (!Utils.createFolder(sqliteDir)) {
            throw RuntimeException("The \"$sqliteDir\" folder is not writable.")
        }

        return PDO("sqlite:{$sqliteFile}")
    }

    /**
     * Enable UTF-8 encoding on the connection.
     *
     * {@inheritdoc}
     */
    protected fun _enableUTF8(){
        // NOTE: SQLite can only set encoding when the database is 1st created,
        // so this only does something if we"re the ones creating the db file!
        // Afterwards, SQLite always keeps the encoding used at db creation.
        _pdo.query("PRAGMA encoding = \"UTF-8\"").closeCursor()
    }

    /**
     * Automatically create the database table if necessary.
     *
     * {@inheritdoc}
     */
    protected fun _autoCreateTable(){
        // Abort if we already have the necessary table.
        val sth = _pdo.prepare("SELECT count(*) FROM sqlite_master WHERE (type = \"table\") AND (name = :tableName)")
        sth.execute( mapOf(":tableName" to _dbTableName) )
        val result = sth.fetchColumn()
        sth.closeCursor()
        if (result > 0) {
            return
        }

        // Create the database table. Throws in case of failure.
        // NOTE: We store all settings as a JSON blob so that we support all
        // current and future data without having to alter the table schema.
        // NOTE: SQLite automatically increments "integer primary key" cols.
        _pdo.exec("CREATE TABLE `" + _dbTableName + "` (
            id INTEGER PRIMARY KEY NOT NULL,
            username TEXT NOT NULL UNIQUE,
            settings BLOB,
            cookies BLOB,
            last_modified DATETIME DEFAULT CURRENT_TIMESTAMP
        )")

        // Set up a trigger to automatically update the modification timestamp.
        // NOTE: The WHEN claimport is important to avoid infinite recursive loops,
        // otherwise you"ll get "Error: too many levels of trigger recursion" if
        // recursive triggers are enabled in SQLite. The WHEN constraint simply
        // ensures that we only update last_modified automatically after UPDATEs
        // that did NOT change last_modified. So our own UPDATEs of other fields
        // will trigger this automatic UPDATE, which does an UPDATE with a NEW
        // last_modified value, meaning that the trigger won"t execute again!
        _pdo.exec("CREATE TRIGGER IF NOT EXISTS `" + _dbTableName + "_update_last_modified`
            AFTER UPDATE
            ON `".this._dbTableName."`
            FOR EACH ROW
            WHEN NEW.last_modified = OLD.last_modified -- Avoids infinite loop.
            BEGIN
                UPDATE `".this._dbTableName."` SET last_modified=CURRENT_TIMESTAMP WHERE (id=OLD.id)
            END"
        )
    }
}
