package com.hamza.wallpap.ui.screens.home

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.UnsplashImageUI
import com.hamza.wallpap.ui.screens.common.HomeListContent
import com.hamza.wallpap.ui.screens.search.SearchChips
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.ui.theme.topAppBarTitle
import com.hamza.wallpap.util.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    items: LazyPagingItems<UnsplashImageUI>,
//    refreshState: SwipeRefreshState,
    context: Context,
    scope: CoroutineScope,
    systemUiController: SystemUiController,
) {
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val activity = (context as? Activity)

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            activity?.finish()
        }
    }

    if (!isOnline(context)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Icon(
                tint = MaterialTheme.colors.topAppBarTitle,
                imageVector = Icons.Default.NetworkCheck,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = stringResource(id = R.string.check_network) + "\n" + stringResource(id = R.string.reopen_app),
                color = MaterialTheme.colors.textColor,
                fontFamily = maven_pro_regular,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 4.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                homeViewModel.wallpaperItems.forEachIndexed { index, s ->
                    SearchChips(text = s.title,
                        selected = homeViewModel.selectedIndex.value == index,
                        onClick = {
                            homeViewModel.selectedIndex.value = index
                            homeViewModel.query.value = homeViewModel.wallpaperItems[index].query
                        })
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                }
            }
            HomeListContent(
                items = items,
                navController,
                homeViewModel
//                onRefresh = { items.refresh() },
//                refreshState
            )
        }
    }
}