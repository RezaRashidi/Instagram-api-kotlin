package InstagramAPI

interface ExperimentsInterface {
    /**
     * Checks if a parameter is enabled in the given experiment.
     *
     * @param (string) $experiment
     * @param (string) $param
     * @param (bool)   $default
     *
     * @return bool
     */
    fun isExperimentEnabled(experiment: String, param: String, default: Boolean = false): Boolean

    /**
     * Get a parameter value for the given experiment.
     *
     * @param (string) $experiment
     * @param (string) $param
     * @param (bool)   $default
     *
     * @return mixed
     */
     fun getExperimentParam(experiment: String, param: String, default: Boolean ?= null)
}
