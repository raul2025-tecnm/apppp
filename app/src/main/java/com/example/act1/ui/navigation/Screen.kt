package com.example.act1.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    
    object TeamList : Screen("team_list")
    object TeamDetail : Screen("team_detail/{teamId}") {
        fun createRoute(teamId: Int) = "team_detail/$teamId"
    }
    object TeamForm : Screen("team_form?teamId={teamId}") {
        fun createRoute(teamId: Int? = null) = if (teamId != null) "team_form?teamId=$teamId" else "team_form"
    }

    object PlayerList : Screen("player_list")
    object PlayerDetail : Screen("player_detail/{playerId}") {
        fun createRoute(playerId: Int) = "player_detail/$playerId"
    }
    object PlayerForm : Screen("player_form?playerId={playerId}") {
        fun createRoute(playerId: Int? = null) = if (playerId != null) "player_form?playerId=$playerId" else "player_form"
    }

    object MatchList : Screen("match_list")
    object MatchForm : Screen("match_form?matchId={matchId}") {
        fun createRoute(matchId: Int? = null) = if (matchId != null) "match_form?matchId=$matchId" else "match_form"
    }
    object MatchResults : Screen("match_results")
    
    object CoachList : Screen("coach_list")
    object CoachForm : Screen("coach_form?coachId={coachId}") {
        fun createRoute(coachId: Int? = null) = if (coachId != null) "coach_form?coachId=$coachId" else "coach_form"
    }

    object StatsList : Screen("stats_list")
    
    object Goleadores : Screen("goleadores")
}
