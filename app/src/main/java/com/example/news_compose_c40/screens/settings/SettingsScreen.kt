import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_compose_c40.R
import com.example.news_compose_c40.activity.HomeActivity
import com.example.news_compose_c40.screens.settings.SettingViewModel
import com.example.news_compose_c40.ui.theme.green
import com.example.news_compose_c40.widgets.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable


@Serializable
object SettingsRoute

@Composable
fun SettingsScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    vm: SettingViewModel = hiltViewModel()
) {
    val activity = (LocalContext.current) as HomeActivity
    val expanded = remember { mutableStateOf(false) }
    val languageMap = mapOf("en" to "English", "ar" to "العربية")

    Scaffold(topBar = {
        NewsTopAppBar(
            titleString = stringResource(R.string.settings),
            shouldDisplayMenuIcon = true,
            shouldDisplaySearchIcon = false,
            drawerState = drawerState,
            scope = scope
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .background(Color.White)
                .paint(painterResource(R.drawable.bg_pattern), contentScale = ContentScale.Crop)
                .padding(30.dp)
        ) {
            Text(
                text = stringResource(R.string.language),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { expanded.value = true }
                    .background(Color.White)
                    .fillMaxWidth()
                    .border(width = 2.dp, color = green)
                    .padding(15.dp)
            ) {
                Text(
                    text = languageMap[vm.selectedLanguage] ?: "Select Language",
                    color = green
                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.fillMaxWidth().background(Color.White).padding(20.dp)
                ) {
                    languageMap.forEach { (key, language) ->
                        DropdownMenuItem(
                            text = { Text(text = language, color = Color.Black) },
                            onClick = {
                                vm.setSelectedLanguage(key)
                                expanded.value = false
                                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(key))
                                activity.recreate()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        scope = rememberCoroutineScope(),
        drawerState = rememberDrawerState(DrawerValue.Closed)
    )
}
