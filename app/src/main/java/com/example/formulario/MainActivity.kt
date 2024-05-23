package com.example.formulario



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formulario.ui.theme.FormularioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FormularioTheme {
                Scaffold(
                    Modifier
                        .background(Color.Gray)
                        .fillMaxSize()
                ) { innerPadding ->
                    App()
                }
            }
        }
    }
}


@Composable
fun App(){
    var nome by remember { mutableStateOf("") }
    //genero
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Selecione um gênero") }

    val items = listOf("Masculino", "Feminino")

    var nascimento by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier
                .background(Color.Gray)
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(
                text = "App Formulário",
                fontSize = 36.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            )
        }



        //linha de espaçamento
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
        }

        //Nome
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = {nome = it},
                label = { Text(text = "Nome:") }
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
        }

        //Data de Nascimento
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            OutlinedTextField(
                value = nascimento,
                onValueChange = {
                    if (it.length < 9){
                        nascimento = it
                    }
                },
                label = { Text(text = "Data de Nascimento:") },
                visualTransformation = DateVisualTransformation()
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
        }

        //CPF
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            OutlinedTextField(
                value = cpf,
                onValueChange = {
                    if (it.length < 12){
                        cpf = it
                    }
                },
                label = { Text(text = "CPF:") },
                visualTransformation = CpfVisualTransformation()
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
        Column {
            OutlinedTextField(

                value = selectedItem,
                onValueChange = {},
                label = { Text("Gênero") },
                readOnly = true,
                modifier = Modifier
                    .clickable { expanded = true }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
        }

        //Telefone
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            OutlinedTextField(
                value = telefone,
                onValueChange = {
                    if (it.length < 12){
                        telefone = it
                    }
                },
                label = { Text(text = "Número de telefone:") },
                visualTransformation = PhoneVisualTransformation()
            )
        }

        //linha de espaçamento
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
        }


        Row(
            Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 16.dp),
            Arrangement.Center
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cadastrar")
            }
        }
    }
}



//máscara de cpf
class CpfVisualTransformation : VisualTransformation {


    override fun filter(text: AnnotatedString): TransformedText {

        val cpfMask = text.text.mapIndexed { index, c ->
            when(index) {
                2 -> "$c."
                5 -> "$c."
                8 -> "$c-"
                else -> c
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(cpfMask),
            CpfOffsetMapping
        )
    }

    object CpfOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 8 -> offset + 3
                offset > 5 -> offset + 2
                offset > 2 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 8 -> offset - 3
                offset > 5 -> offset - 2
                offset > 2 -> offset + 1
                else -> offset
            }
        }

    }

}

//máscara de data
class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val dateMask = text.text.mapIndexed { index, c ->
            when(index) {
                1 -> "$c/"
                3 -> "$c/"
                else -> c
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(dateMask),
            DateOffsetMapping
        )
    }

    object DateOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 3 -> offset + 2
                offset > 1 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 3 -> offset - 2
                offset > 1 -> offset - 1
                else -> offset
            }
        }

    }

}

//máscara de telefone
class PhoneVisualTransformation : VisualTransformation {


    override fun filter(text: AnnotatedString): TransformedText {

        val phoneMask = text.text.mapIndexed { index, c ->
            when(index) {
                0 -> "($c"
                1 -> "$c) "
                6 -> "$c-"
                else -> c
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(phoneMask),
            PhoneOffsetMapping
        )
    }

    object PhoneOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 6 -> offset + 4
                offset > 1 -> offset + 3
                offset > 0 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 6 -> offset - 4
                offset > 1 -> offset - 3
                else -> offset
            }
        }

    }

}


@Preview
@Composable
fun AppPreview(){
    FormularioTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            App()
        }
    }
}


