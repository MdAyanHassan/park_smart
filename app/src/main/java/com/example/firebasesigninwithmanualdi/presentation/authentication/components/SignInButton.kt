package com.example.firebasesigninwithmanualdi.presentation.authentication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasesigninwithmanualdi.R

@Composable
fun SignInButton(
    oneTapSignInClick: () -> Unit
) {
    Button(
        modifier = Modifier.padding(bottom = 48.dp),
        shape = RoundedCornerShape(6.dp),
        onClick = { oneTapSignInClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_google_logo), 
            contentDescription = null
        )
        
        Text(
            text = stringResource(id = R.string.sign_in_with_google),
            modifier = Modifier.padding(6.dp),
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun SignInButtonPreview() {
    SignInButton(
        oneTapSignInClick = {}
    )

}