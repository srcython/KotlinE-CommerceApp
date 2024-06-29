package com.sercancelik.turkcellgygfinal.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.wrapMappedColumns
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.models.UserAuthState

@Composable
fun UsernameTextField(username: String, onValueChange: (String) -> Unit) {
    TextField(
        value = username,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(23.dp)),
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(23.dp),
        placeholder = { Text("Username", color = Color.Gray) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = "Username Icon",
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordTextField(password: String, onValueChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(23.dp)),
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(23.dp),
        placeholder = { Text("Password", color = Color.Gray) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "Password Icon",
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    authState: UserAuthState,
    onLoginClick: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("oliviaw") }
    var password by remember { mutableStateOf("oliviawpass") }

    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is UserAuthState.Success) {
            android.widget.Toast.makeText(
                context,
                "Giriş yapıldı",
                android.widget.Toast.LENGTH_SHORT
            ).show()
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4C5264))
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Giriş Yap",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.png_login_header),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            UsernameTextField(username, onValueChange = { username = it })

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(password, onValueChange = { password = it })

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onLoginClick(username, password)
                },
                modifier = Modifier
                    .width(100.dp)
                    .border(
                        2.dp,
                        Color.White,
                        RoundedCornerShape(23.dp)
                    )
                    .height(40.dp),
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4C5264),
                    contentColor = Color.White
                )
            ) {
                if (authState is UserAuthState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(text = "Giriş yap")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when (authState) {
                is UserAuthState.Error -> {
                    Text(
                        "Giriş hatası: ${(authState).message}",
                        color = Color.Red
                    )
                }

                UserAuthState.InvalidCredentials -> {
                    Text(
                        "Hatalı kullanıcı adı veya parola!",
                        color = Color.White
                    )
                }

                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenIdlePreview() {
    LoginScreen(
        onLoginSuccess = {
            println("Login Success")
        },
        authState = UserAuthState.Idle,
        onLoginClick = { username, password ->
            println("Login clicked with username: $username and password: $password")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenLoadingPreview() {
    LoginScreen(
        onLoginSuccess = {
            println("Login Success")
        },
        authState = UserAuthState.Loading,
        onLoginClick = { username, password ->
            println("Login clicked with username: $username and password: $password")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenErrorPreview() {
    LoginScreen(
        onLoginSuccess = {
            println("Login Success")
        },
        authState = UserAuthState.Error("Sample error message"),
        onLoginClick = { username, password ->
            println("Login clicked with username: $username and password: $password")
        }
    )
}
