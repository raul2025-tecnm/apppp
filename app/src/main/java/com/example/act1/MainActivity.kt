package com.example.act1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.act1.ui.navigation.Screen
import com.example.act1.ui.screens.*
import com.example.act1.ui.theme.Act1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Act1Theme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("Inicio") },
                                selected = currentRoute == Screen.Home.route,
                                onClick = { navController.navigate(Screen.Home.route) }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.List, contentDescription = "Equipos") },
                                label = { Text("Equipos") },
                                selected = currentRoute?.startsWith("team") == true,
                                onClick = { navController.navigate(Screen.TeamList.route) }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Person, contentDescription = "Jugadores") },
                                label = { Text("Jugadores") },
                                selected = currentRoute?.startsWith("player") == true || currentRoute == Screen.Goleadores.route,
                                onClick = { navController.navigate(Screen.PlayerList.route) }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Resultados") },
                                label = { Text("Resultados") },
                                selected = currentRoute == Screen.MatchResults.route,
                                onClick = { navController.navigate(Screen.MatchResults.route) }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        
                        // Teams
                        composable(Screen.TeamList.route) { TeamListScreen(navController) }
                        composable(
                            Screen.TeamDetail.route,
                            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val teamId = backStackEntry.arguments?.getInt("teamId") ?: return@composable
                            TeamDetailScreen(teamId, navController)
                        }
                        composable(
                            Screen.TeamForm.route,
                            arguments = listOf(navArgument("teamId") { 
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) { backStackEntry ->
                            val teamId = backStackEntry.arguments?.getInt("teamId").takeIf { it != -1 }
                            TeamFormScreen(teamId, navController)
                        }

                        // Players
                        composable(Screen.PlayerList.route) { PlayerListScreen(navController) }
                        composable(
                            Screen.PlayerDetail.route,
                            arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val playerId = backStackEntry.arguments?.getInt("playerId") ?: return@composable
                            PlayerDetailScreen(playerId, navController)
                        }
                        composable(
                            Screen.PlayerForm.route,
                            arguments = listOf(navArgument("playerId") { 
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) { backStackEntry ->
                            val playerId = backStackEntry.arguments?.getInt("playerId").takeIf { it != -1 }
                            PlayerFormScreen(playerId, navController)
                        }

                        // Matches
                        composable(Screen.MatchList.route) { MatchListScreen(navController) }
                        composable(Screen.MatchResults.route) { MatchResultsScreen(navController) }
                        composable(
                            Screen.MatchForm.route,
                            arguments = listOf(navArgument("matchId") { 
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) { backStackEntry ->
                            val matchId = backStackEntry.arguments?.getInt("matchId").takeIf { it != -1 }
                            MatchFormScreen(matchId, navController)
                        }

                        // Coaches
                        composable(Screen.CoachList.route) { CoachListScreen(navController) }
                        composable(
                            Screen.CoachForm.route,
                            arguments = listOf(navArgument("coachId") { 
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) { backStackEntry ->
                            val coachId = backStackEntry.arguments?.getInt("coachId").takeIf { it != -1 }
                            CoachFormScreen(coachId, navController)
                        }

                        // Stats
                        composable(Screen.StatsList.route) { StatsListScreen(navController) }

                        // Special Queries
                        composable(Screen.Goleadores.route) { GoleadoresScreen(navController) }
                    }
                }
            }
        }
    }
}
