package com.stehno.dd.campaigntools.model

data class DurationTimer(val id: Long?, val description: String, val startRound: Int, val endRound: Int) {

    fun isActive(currentRound: Int?): Boolean {
        return when {
            currentRound != null -> currentRound in startRound..endRound
            else -> false
        }
    }
}