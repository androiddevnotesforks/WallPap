package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun EditorBottomSheet(
    navController: NavHostController,
    itemList: MutableList<CustomWallpaperBackgroundColor>,
    customWallpaperViewModel: CustomWallpaperViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    randomItems: LazyPagingItems<UnsplashImage>,
    context: Context,
) {
    if (customWallpaperViewModel.wallpaperDialogState.value) {
        TextFormatDialog(
            dialogState = customWallpaperViewModel.wallpaperDialogState,
            context = context,
            itemList,
            customWallpaperViewModel
        )
    }

    if (!bottomSheetState.isVisible) {
        customWallpaperViewModel.bgColorBottomSheet.value = false
        customWallpaperViewModel.textBottomSheet.value = false
        customWallpaperViewModel.bgColorBottomSheet.value = false
    }
    if (customWallpaperViewModel.bgColorBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "Background Color",
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.textBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    itemList.forEachIndexed { index, s ->
                        ColorChips(
                            color = s.color,
                            selected = customWallpaperViewModel.selectedBgColorIndex.value == index,
                            onClick = {
                                customWallpaperViewModel.selectedBgColorIndex.value = index
                                customWallpaperViewModel.bgImageFullUrl.value = null
                                customWallpaperViewModel.boxColor.value = s.color
                            })
                    }
                }

//                LazyRow(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp)
//                ) {
//                    items(itemList) { item ->
//                        Box(
//                            modifier = Modifier
//                                .size(46.dp)
//                                .clip(CircleShape)
//                                .border(1.dp, MaterialTheme.colors.textColor, CircleShape)
//                                .background(item.color)
//                                .clickable {
//                                    customWallpaperViewModel.bgImageUrl.value = null
//                                    customWallpaperViewModel.boxColor.value = item.color
//                                }
//                        )
//                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
//                    }
//                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

    if (customWallpaperViewModel.textBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "Text",
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.textBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }

                androidx.compose.material3.OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = customWallpaperViewModel.wallpaperText.value,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = { customWallpaperViewModel.wallpaperText.value = it },
                    label = {
                        androidx.compose.material3.Text(
                            "Enter text here",
                            color = MaterialTheme.colors.textColor
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colors.textColor,
                        focusedBorderColor = MaterialTheme.colors.textColor,
                        unfocusedBorderColor = MaterialTheme.colors.iconColor,
                        textColor = MaterialTheme.colors.textColor
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                customWallpaperViewModel.wallpaperDialogState.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.TextFormat,
                                contentDescription = null,
                                tint = MaterialTheme.colors.bottomAppBarContentColor,
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }

    if (customWallpaperViewModel.bgImageBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "Background Image",
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.textBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    content = {
                        items(randomItems.itemCount) {
                            randomItems[it]?.let { unsplashImage ->
                                BackgroundImageListItem(unsplashImage, customWallpaperViewModel, context)
                            }
                        }
                    })

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    textAlign = TextAlign.Start,
                    text = "Image Clarity",
                    color = MaterialTheme.colors.topAppBarTitle,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                )

                androidx.compose.material3.Slider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
                    value = customWallpaperViewModel.imageTransparencySliderPosition.value,
                    onValueChange = {
                        customWallpaperViewModel.imageTransparencySliderPosition.value = it
                    },
                    valueRange = 0f..1f,
                    onValueChangeFinished = {
                        customWallpaperViewModel.bgImageTransparency.value =
                            customWallpaperViewModel.imageTransparencySliderPosition.value
                    },
                    colors = androidx.compose.material3.SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(0.5f),
                        thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}