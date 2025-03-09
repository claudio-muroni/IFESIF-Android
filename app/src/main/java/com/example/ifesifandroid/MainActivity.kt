package com.example.ifesifandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ifesifandroid.ui.theme.IFESIFAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IFESIFAndroidTheme {
                HomePage()
            }
        }
    }
}

@Composable
fun HomePage() {
    var presidentList by remember { mutableStateOf(listOf<President>()) }
    var contractList by remember { mutableStateOf(listOf<Contract>()) }
    var buttonClicked by remember { mutableStateOf(false) }
    var presidentClicked by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        delay(2000)
        presidentList = SupabaseApi.retrofitService.getPresidents()
        contractList = SupabaseApi.retrofitService.getContracts()

    }

    Column {
        Button(
            onClick = {
                buttonClicked = !buttonClicked
            }
        ) {
            Text("Show Presidents")
        }

        if (buttonClicked) {
            Column {
                for (pres in presidentList) {
                    Button(
                        onClick = {
                            presidentClicked = pres.nome
                        }
                    ) {
                        Text(pres.nome)
                    }
                }
            }

            if (presidentClicked != "") {
                Column {
                    for (contract in contractList.filter { it.nomePresidente == presidentClicked }) {
                        Text("${contract.giocatore} ${contract.ruolo} ${contract.prezzoRinnovo}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IFESIFPreview() {
    IFESIFAndroidTheme {
        HomePage()
    }
}