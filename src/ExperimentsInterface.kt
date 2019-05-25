package InstagramAPI

interface ExperimentsInterface {
    /**
     * Checks if a parameter is enabled in the given experiment.
     *
     * @param string $experiment
     * @param string $param
     * @param bool   $default
     *
     * @return bool
     */
    public fun isExperimentEnabled(
            experiment,
            param,
            default = false)

    /**
     * Get a parameter value for the given experiment.
     *
     * @param string $experiment
     * @param string $param
     * @param mixed  $default
     *
     * @return mixed
     */
    public fun getExperimentParam(
            experiment,
            param,
            default = null)
}
