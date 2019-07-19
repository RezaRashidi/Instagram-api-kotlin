

package instagramAPI.responses

import instagramAPI.Response

/**
 * CreateLiveResponse.
 *
 * @method int getAllowResolutionChange()
 * @method int getAvcRtmpPayload()
 * @method string getBroadcastId()
 * @method int getBroadcasterUpdateFrequency()
 * @method int getConnectWith_1rtt()
 * @method int getDisableSpeedTest()
 * @method int getHeartbeatInterval()
 * @method int getMaxTimeInSeconds()
 * @method mixed getMessage()
 * @method int getSpeedTestMinimumBandwidthThreshold()
 * @method int getSpeedTestRetryMaxCount()
 * @method int getSpeedTestRetryTimeDelay()
 * @method int getSpeedTestUiTimeout()
 * @method string getStatus()
 * @method int getStreamAudioBitRate()
 * @method int getStreamAudioChannels()
 * @method int getStreamAudioSampleRate()
 * @method int getStreamNetworkConnectionRetryCount()
 * @method int getStreamNetworkConnectionRetryDelayInSeconds()
 * @method int getStreamNetworkSpeedTestPayloadChunkSizeInBytes()
 * @method int getStreamNetworkSpeedTestPayloadSizeInBytes()
 * @method int getStreamNetworkSpeedTestPayloadTimeoutInSeconds()
 * @method mixed getStreamVideoAdaptiveBitrateConfig()
 * @method int getStreamVideoAllowBFrames()
 * @method int getStreamVideoBitRate()
 * @method int getStreamVideoFps()
 * @method int getStreamVideoWidth()
 * @method string getUploadUrl()
 * @method model._Message[] get_Messages()
 * @method bool isAllowResolutionChange()
 * @method bool isAvcRtmpPayload()
 * @method bool isBroadcastId()
 * @method bool isBroadcasterUpdateFrequency()
 * @method bool isConnectWith_1rtt()
 * @method bool isDisableSpeedTest()
 * @method bool isHeartbeatInterval()
 * @method bool isMaxTimeInSeconds()
 * @method bool isMessage()
 * @method bool isSpeedTestMinimumBandwidthThreshold()
 * @method bool isSpeedTestRetryMaxCount()
 * @method bool isSpeedTestRetryTimeDelay()
 * @method bool isSpeedTestUiTimeout()
 * @method bool isStatus()
 * @method bool isStreamAudioBitRate()
 * @method bool isStreamAudioChannels()
 * @method bool isStreamAudioSampleRate()
 * @method bool isStreamNetworkConnectionRetryCount()
 * @method bool isStreamNetworkConnectionRetryDelayInSeconds()
 * @method bool isStreamNetworkSpeedTestPayloadChunkSizeInBytes()
 * @method bool isStreamNetworkSpeedTestPayloadSizeInBytes()
 * @method bool isStreamNetworkSpeedTestPayloadTimeoutInSeconds()
 * @method bool isStreamVideoAdaptiveBitrateConfig()
 * @method bool isStreamVideoAllowBFrames()
 * @method bool isStreamVideoBitRate()
 * @method bool isStreamVideoFps()
 * @method bool isStreamVideoWidth()
 * @method bool isUploadUrl()
 * @method bool is_Messages()
 * @method this setAllowResolutionChange(int $value)
 * @method this setAvcRtmpPayload(int $value)
 * @method this setBroadcastId(string $value)
 * @method this setBroadcasterUpdateFrequency(int $value)
 * @method this setConnectWith_1rtt(int $value)
 * @method this setDisableSpeedTest(int $value)
 * @method this setHeartbeatInterval(int $value)
 * @method this setMaxTimeInSeconds(int $value)
 * @method this setMessage(mixed $value)
 * @method this setSpeedTestMinimumBandwidthThreshold(int $value)
 * @method this setSpeedTestRetryMaxCount(int $value)
 * @method this setSpeedTestRetryTimeDelay(int $value)
 * @method this setSpeedTestUiTimeout(int $value)
 * @method this setStatus(string $value)
 * @method this setStreamAudioBitRate(int $value)
 * @method this setStreamAudioChannels(int $value)
 * @method this setStreamAudioSampleRate(int $value)
 * @method this setStreamNetworkConnectionRetryCount(int $value)
 * @method this setStreamNetworkConnectionRetryDelayInSeconds(int $value)
 * @method this setStreamNetworkSpeedTestPayloadChunkSizeInBytes(int $value)
 * @method this setStreamNetworkSpeedTestPayloadSizeInBytes(int $value)
 * @method this setStreamNetworkSpeedTestPayloadTimeoutInSeconds(int $value)
 * @method this setStreamVideoAdaptiveBitrateConfig(mixed $value)
 * @method this setStreamVideoAllowBFrames(int $value)
 * @method this setStreamVideoBitRate(int $value)
 * @method this setStreamVideoFps(int $value)
 * @method this setStreamVideoWidth(int $value)
 * @method this setUploadUrl(string $value)
 * @method this set_Messages(model._Message[] $value)
 * @method this unsetAllowResolutionChange()
 * @method this unsetAvcRtmpPayload()
 * @method this unsetBroadcastId()
 * @method this unsetBroadcasterUpdateFrequency()
 * @method this unsetConnectWith_1rtt()
 * @method this unsetDisableSpeedTest()
 * @method this unsetHeartbeatInterval()
 * @method this unsetMaxTimeInSeconds()
 * @method this unsetMessage()
 * @method this unsetSpeedTestMinimumBandwidthThreshold()
 * @method this unsetSpeedTestRetryMaxCount()
 * @method this unsetSpeedTestRetryTimeDelay()
 * @method this unsetSpeedTestUiTimeout()
 * @method this unsetStatus()
 * @method this unsetStreamAudioBitRate()
 * @method this unsetStreamAudioChannels()
 * @method this unsetStreamAudioSampleRate()
 * @method this unsetStreamNetworkConnectionRetryCount()
 * @method this unsetStreamNetworkConnectionRetryDelayInSeconds()
 * @method this unsetStreamNetworkSpeedTestPayloadChunkSizeInBytes()
 * @method this unsetStreamNetworkSpeedTestPayloadSizeInBytes()
 * @method this unsetStreamNetworkSpeedTestPayloadTimeoutInSeconds()
 * @method this unsetStreamVideoAdaptiveBitrateConfig()
 * @method this unsetStreamVideoAllowBFrames()
 * @method this unsetStreamVideoBitRate()
 * @method this unsetStreamVideoFps()
 * @method this unsetStreamVideoWidth()
 * @method this unsetUploadUrl()
 * @method this unset_Messages()
 */
data class CreateLiveResponse (
    val broadcast_id                                          : String,
    val upload_url                                            : String,
    val max_time_in_seconds                                   : Int,
    val speed_test_ui_timeout                                 : Int,
    val stream_network_speed_test_payload_chunk_size_in_bytes : Int,
    val stream_network_speed_test_payload_size_in_bytes       : Int,
    val stream_network_speed_test_payload_timeout_in_seconds  : Int,
    val speed_test_minimum_bandwidth_threshold                : Int,
    val speed_test_retry_max_count                            : Int,
    val speed_test_retry_time_delay                           : Int,
    val disable_speed_test                                    : Int,
    val stream_video_allow_b_frames                           : Int,
    val stream_video_width                                    : Int,
    val stream_video_bit_rate                                 : Int,
    val stream_video_fps                                      : Int,
    val stream_audio_bit_rate                                 : Int,
    val stream_audio_sample_rate                              : Int,
    val stream_audio_channels                                 : Int,
    val heartbeat_interval                                    : Int,
    val broadcaster_update_frequency                          : Int,
    val stream_video_adaptive_bitrate_config                  : String,
    val stream_network_connection_retry_count                 : Int,
    val stream_network_connection_retry_delay_in_seconds      : Int,
    val connect_with_1rtt                                     : Int,
    val avc_rtmp_payload                                      : Int,
    val allow_resolution_change                               : Int
){
//    override val JSON_PROPERTY_MAP = mapOf(
//        "broadcast_id"                                          to "string",
//        "upload_url"                                            to "string",
//        "max_time_in_seconds"                                   to "int",
//        "speed_test_ui_timeout"                                 to "int",
//        "stream_network_speed_test_payload_chunk_size_in_bytes" to "int",
//        "stream_network_speed_test_payload_size_in_bytes"       to "int",
//        "stream_network_speed_test_payload_timeout_in_seconds"  to "int",
//        "speed_test_minimum_bandwidth_threshold"                to "int",
//        "speed_test_retry_max_count"                            to "int",
//        "speed_test_retry_time_delay"                           to "int",
//        "disable_speed_test"                                    to "int",
//        "stream_video_allow_b_frames"                           to "int",
//        "stream_video_width"                                    to "int",
//        "stream_video_bit_rate"                                 to "int",
//        "stream_video_fps"                                      to "int",
//        "stream_audio_bit_rate"                                 to "int",
//        "stream_audio_sample_rate"                              to "int",
//        "stream_audio_channels"                                 to "int",
//        "heartbeat_interval"                                    to "int",
//        "broadcaster_update_frequency"                          to "int",
//        "stream_video_adaptive_bitrate_config"                  to "",
//        "stream_network_connection_retry_count"                 to "int",
//        "stream_network_connection_retry_delay_in_seconds"      to "int",
//        "connect_with_1rtt"                                     to "int",
//        "avc_rtmp_payload"                                      to "int",
//        "allow_resolution_change"                               to "int"
//    )
}
