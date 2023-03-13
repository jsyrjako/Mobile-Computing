package com.jsyrjako.reminderapp.ui.reminder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import com.jsyrjako.reminderapp.ui.category.CategoryViewModel
import com.jsyrjako.reminderapp.ui.category.CategoryViewState
import com.jsyrjako.reminderapp.ui.home.getCurrentLocation
import java.util.*

@Composable
fun NearbyRemindersScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    reminderViewModel: ReminderViewModel = hiltViewModel(),
) {
    val viewState by categoryViewModel.uiState.collectAsState()

    when (viewState) {
        is CategoryViewState.Success -> {
            val selectedCategory = (viewState as CategoryViewState.Success).selectedCategory
            val categories = (viewState as CategoryViewState.Success).data

            Surface(modifier = Modifier.fillMaxSize()) {
                HomeContent(
                    selectedCategory = selectedCategory!!,
                    categories = categories,
                    onCategorySelected = categoryViewModel::onCategorySelected,
                    navController = navController,
                    reminderViewModel = reminderViewModel
                )
            }
        }
        is CategoryViewState.Error -> {

        }
        is CategoryViewState.Loading -> {

        }
    }
}

@Composable
fun HomeContent(
    selectedCategory: Category,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    navController: NavController,
    reminderViewModel: ReminderViewModel
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )
    val context = LocalContext.current
    val latLng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                cutoutShape = CircleShape,
            ) {
                Button(
                    onClick = {
                            navController.navigate("Location")
                        }
                ) {
                    Text(text = "Set Virtual Location")
                }
            }
        }


    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            CategoryTabs(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
            )

            if (latLng != null) {
                Text(
                    text = "Set Location \n Latitude: ${latLng.latitude}, \nLongitude: ${latLng.longitude}"
                )
            }

            ReminderList(
                selectedCategory = selectedCategory,
                reminderViewModel = reminderViewModel,
                navController = navController,
                latLng = latLng
            )
        }
    }
}

@Composable
private fun CategoryTabs(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 24.dp,
        indicator = emptyTabIndicator,
        modifier = Modifier.fillMaxWidth(),
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) }
            ) {
                ChoiceChipContent(
                    text = category.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                )
            }

        }
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}

@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.secondary.copy(alpha = 0.87f)
            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> Color.Black
            else -> MaterialTheme.colors.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun ReminderList(
    selectedCategory: Category,
    reminderViewModel: ReminderViewModel,
    navController: NavController,
    latLng: LatLng?
) {
    reminderViewModel.loadRemindersFor(selectedCategory)

    val reminderViewState by reminderViewModel.uiState.collectAsState()

    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data

            LazyColumn(
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.Center
            ) {

                items(reminderList) { item ->

                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = System.currentTimeMillis() + 1000

                    var virtualDistance = 5000.00

                    try {
                        val reminderTime = item.reminder_time.split(" ")
                        val reminderDate = reminderTime[0].split("-")
                        val reminderTimeOnly = reminderTime[1].split(":")

                        var year = reminderDate[0].toInt()
                        var month = reminderDate[1].toInt() - 1
                        var day = reminderDate[2].toInt()
                        var hour = reminderTimeOnly[0].toInt()
                        var minute = reminderTimeOnly[1].toInt()


                        calendar.set(year, month, day, hour, minute)

                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }

                    try {
                        virtualDistance = SphericalUtil.computeDistanceBetween(
                            latLng,
                            LatLng(item.location_x.toDouble(), item.location_y.toDouble())
                        )
                    } catch (e: Exception) {
                        Log.d("Error", "Error while getting virtualDistance")
                    }

                     if (virtualDistance <= 2000) {
                        ReminderListItem(
                            reminder = item,
                            category = selectedCategory,
                            onClick = {},
                            reminderViewModel = reminderViewModel,
                            navController = navController
                        )

                    } else {
                        Log.d("Error", "Current distance: $virtualDistance")
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    reminderViewModel: ReminderViewModel,
    navController: NavController,
) {
    ConstraintLayout(
        modifier = modifier
            .clickable {
                reminderViewModel.setReminder(reminder)
                navController.navigate("ReminderEdit")
            }
            .fillMaxWidth(),

        ) {
        val (dividerRef, titleRef, categoryRef, iconRef, iconRef2, dateRef) = createRefs()
        Divider(

            Modifier.constrainAs(dividerRef) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // title
        Text(
            text = reminder.title,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(titleRef) {
                linkTo(
                    start = parent.start,
                    end = iconRef.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // category
        Text(
            text = category.name,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(categoryRef) {
                linkTo(
                    start = parent.start,
                    end = iconRef.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(titleRef.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        var formattedDate = "Lat: ${reminder.location_x} Lng: ${reminder.location_y}"

        try {
            val reminderTime = reminder.reminder_time.split(" ")
            val reminderDate = reminderTime[0].split("-")
            val reminderTimeOnly = reminderTime[1].split(":")

            formattedDate = "${reminderDate[2]}.${reminderDate[1]}.${reminderDate[0]} klo ${reminderTimeOnly[0]}:${reminderTimeOnly[1]}"
        } catch (e: Exception) {
            println("Error in formatting date: ${e.message}")
        }
        // date
        Text(
            text = formattedDate,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(dateRef) {
                linkTo(
                    start = categoryRef.end,
                    end = iconRef.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                centerVerticallyTo(categoryRef)
                top.linkTo(titleRef.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )

        // icon
        IconButton(
            onClick = {
                reminderViewModel.deleteReminder(reminder)
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(iconRef) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = ""
            )
        }
        // icon
        IconButton(
            onClick = {
                reminderViewModel.setReminder(reminder)
                navController.navigate("ReminderEdit")
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(iconRef2) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(iconRef.start)
                }


        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = ""
            )
        }
    }
}



private fun requestPermission(
    context: Context,
    permission: String,
    requestPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermission()
    }
}