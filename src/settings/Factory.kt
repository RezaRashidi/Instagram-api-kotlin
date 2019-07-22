

package instagramAPI.settings

import instagramAPI.exception.SettingsException
import instagramAPI.settings.Storage.File
import instagramAPI.settings.Storage.Memcached
import instagramAPI.settings.Storage.MySQL
import instagramAPI.settings.Storage.SQLite
import java.lang.System.getenv

/**
 * Effortlessly instantiates a StorageHandler with the desired Storage backend.
 *
 * Takes care of resolving user configuration values from multiple sources, then
 * creates the correct Storage backend and hooks it up to a StorageHandler.
 *
 * @author SteveJobzniak (https://github.com/SteveJobzniak)
 */
object Factory
{
    /**
     * Create a StorageHandler instance.
     *
     * @param array $storageConfig Configuration for the desired
     *                             user settings storage backend.
     * @param array callbacks     Optional StorageHandler callback funs.
     *
     * @throws .instagramAPI.exception.SettingsException
     *
     * @return .instagramAPI.settings.StorageHandler
     */
   fun createHandler(storageConfig:MutableMap<String,String>, callbacks:MutableMap<String,()->Unit> = mutableMapOf() ): StorageHandler{

        // Resolve the storage backend choice if none provided in array.
        if (storageConfig["storage"].isNullOrBlank()) {
            val cmdOptions = getCmdOptions(mutableListOf("settings_storage::"))
            var storage = getUserConfig("storage", storageConfig, cmdOptions)
            if (storage === null) {
                storage = "file"
            }
            storageConfig["storage"] = storage
        }

        // Determine the user"s final storage configuration.
        val locationConfig: MutableMap<String, String?>
        val storageInstance
        val cmdOptions

        when (storageConfig["storage"]) {
            "file" ->{
                // Look for allowed command-line values related to this backend.
                cmdOptions = getCmdOptions(mutableListOf( "settings_basefolder::"))

                // These settings are optional:
                val baseFolder = getUserConfig("basefolder", storageConfig, cmdOptions)

                // Generate the final storage location configuration.


                locationConfig = mutableMapOf("basefolder" to baseFolder)


                storageInstance = File()
            }

            "mysql" ->{
                // Look for allowed command-line values related to this backend.
                cmdOptions = getCmdOptions( mutableListOf(
                    "settings_dbusername::",
                    "settings_dbpassword::",
                    "settings_dbhost::",
                    "settings_dbname::",
                    "settings_dbtablename::"
                  ))

                // These settings are optional, and can be provided regardless of
                // connection method:
                locationConfig = mutableMapOf("dbtablename" to getUserConfig("dbtablename", storageConfig, cmdOptions))

                // These settings are required, but you only have to import one method:
                if (storageConfig["pdo"].isNullOrBlank()) {
                    // If "pdo" is set in the factory config, assume the user wants
                    // to re-import an existing PDO connection. In that case we ignore
                    // the username/password/host/name parameters and import their PDO.
                    // NOTE: Beware that we WILL change attributes on the PDO
                    // connection to suit our needs! Primarily turning all error
                    // reporting into exceptions, and setting the charset to UTF-8.
                    // If you want to re-import a PDO connection, you MUST accept the
                    // fact that WE NEED exceptions and UTF-8 in our PDO! If that is
                    // not acceptable to you then DO NOT re-import your own PDO object!
                    locationConfig["pdo"] = storageConfig["pdo"]
                } else {
                    // Make a connection. Optional settings for it:
                    locationConfig["dbusername"] = getUserConfig("dbusername", storageConfig, cmdOptions)
                    locationConfig["dbpassword"] = getUserConfig("dbpassword", storageConfig, cmdOptions)
                    locationConfig["dbhost"]     = getUserConfig("dbhost", storageConfig, cmdOptions)
                    locationConfig["dbname"]     = getUserConfig("dbname", storageConfig, cmdOptions)
                }
                storageInstance = MySQL()
            }

            "sqlite" -> {
                // Look for allowed command-line values related to this backend.
                cmdOptions = getCmdOptions( mutableListOf( "settings_dbfilename::", "settings_dbtablename::"))

                // These settings are optional, and can be provided regardless of
                // connection method:
                locationConfig = mutableMapOf("dbtablename" to getUserConfig("dbtablename", storageConfig, cmdOptions) )

                // These settings are required, but you only have to import one method:
                if ( storageConfig.contains("pdo") ) {
                    // If "pdo" is set in the factory config, assume the user wants
                    // to re-import an existing PDO connection. In that case we ignore
                    // the SQLite filename/connection parameters and import their PDO.
                    // NOTE: Beware that we WILL change attributes on the PDO
                    // connection to suit our needs! Primarily turning all error
                    // reporting into exceptions, and setting the charset to UTF-8.
                    // If you want to re-import a PDO connection, you MUST accept the
                    // fact that WE NEED exceptions and UTF-8 in our PDO! If that is
                    // not acceptable to you then DO NOT re-import your own PDO object!
                    locationConfig["pdo"] = storageConfig["pdo"]
                } else {
                    // Make a connection. Optional settings for it:
                    locationConfig["dbfilename"] = getUserConfig("dbfilename", storageConfig, cmdOptions)
                }
                storageInstance = SQLite()
            }

            "memcached" -> {
                // The memcached storage can only be configured via the factory
                // configuration array (not via command line or environment vars).

                // These settings are required, but you only have to import one method:
                if ( storageConfig.contains("memcached") ) {
                    // Re-import the user"s own Memcached object.
                    locationConfig = mutableMapOf("memcached" to storageConfig["memcached"])
                } else {
                    // Make a connection. Optional settings for it:
                    locationConfig = mutableMapOf(
                        // This ID will be passed to the .Memcached() constructor.
                        // NOTE: Memcached"s "persistent ID" feature makes Memcached
                        // keep the settings even after you disconnect.
                        "persistent_id" to if (!storageConfig["persistent_id"]!!.isBlank()) storageConfig["persistent_id"] else null,
                        // Array which will be passed to .Memcached::setOptions().
                        "memcached_options" to  if(!storageConfig["memcached_options"]!!.isBlank()) storageConfig["memcached_options"] else null,
                        // Array which will be passed to .Memcached::addServers().
                        // NOTE: Can contain one or multiple servers.
                        "servers" to if(!storageConfig["servers"]!!.isBlank()) storageConfig["servers"] else null,
                        // SASL username and password to be used for SASL
                        // authentication with all of the Memcached servers.
                        // NOTE: PHP"s Memcached API doesn"t support individual
                        // authentication credentials per-server, so these values
                        // apply to all of your servers if you import this feature!
                        "sasl_username" to if(!storageConfig["sasl_username"]!!.isBlank()) storageConfig["sasl_username"] else null,
                        "sasl_password" to if(!storageConfig["sasl_password"]!!.isBlank()) storageConfig["sasl_password"] else null
                    )
                }
                storageInstance = Memcached()
            }

            "custom"-> {
                // Custom storage classes can only be configured via the main array.
                // When using a custom class, you must provide a StorageInterface:
                if ( storageConfig["class"]!!.isBlank() || storageConfig["class"] !is StorageInterface) {
                    throw SettingsException("Invalid custom storage class.")
                }

                // Create a clean storage location configuration array.
                locationConfig = storageConfig
                unset(locationConfig["storage"])
                unset(locationConfig["class"])

                storageInstance = storageConfig["class"]
            }

            else -> throw SettingsException("Unknown settings storage type \"${storageConfig["storage"]}\".")
        }
        // Create the storage handler and connect to the storage location.
        return StorageHandler( storageInstance, locationConfig, callbacks)
   }
    /**
    * Get option values via command-line parameters.
    *
    * @param array longOpts The longnames for the options to look for.
    *
    * @return array
    */
    fun getCmdOptions (longOpts:MutableList<String>){
        var cmdOptions = getopt("", longOpts)
        if (!is_array(cmdOptions)) {
            cmdOptions = []
        }
        return cmdOptions
    }

    /**
     * Looks for the highest-priority result for a Storage config value.
     *
     * @param string settingName   The name of the setting.
     * @param array  storageConfig The Factory"s configuration array.
     * @param array  cmdOptions    All parsed command-line options.
     *
     * @return string|null The value if found, otherwise NULL.
     */
    fun getUserConfig(settingName:String, storageConfig: MutableMap<String, String>, cmdOptions: MutableMap<String, String>): String?{
        // Command line options have the highest precedence.
        // NOTE: settings provided via cmd must have a "settings_" prefix.
        if (cmdOptions.keys.contains("settings_{settingName}")) {
            return cmdOptions["settings_{settingName}"]
        }

        // Environment variables have the second highest precedence.
        // NOTE: settings provided via env must be UPPERCASED and have
        // a "SETTINGS_" prefix, for example "SETTINGS_STORAGE".
        val envValue = getenv("SETTINGS_${settingName.toUpperCase()}" )
        if (envValue !== "") {
            return envValue
        }

        // Our factory config array has the lowest precedence, so that you can
        // easily override it via the other methods when testing other storage
        // backends or different connection parameters.
        if (storageConfig.keys.contains(settingName)) {
            return storageConfig[settingName]
        }

        // Couldn"t find any user-provided value. Automatically returns null.
        // NOTE: Damn you StyleCI for not allowing "return null" for clarity.
    }
}
