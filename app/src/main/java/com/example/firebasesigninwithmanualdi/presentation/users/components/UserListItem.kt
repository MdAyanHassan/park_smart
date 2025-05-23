package com.example.firebasesigninwithmanualdi.presentation.users.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firebasesigninwithmanualdi.core.SAMPLE_USER_DISPLAY_NAME
import com.example.firebasesigninwithmanualdi.core.SAMPLE_USER_EMAIL
import com.example.firebasesigninwithmanualdi.domain.model.User
import com.example.firebasesigninwithmanualdi.R
import com.example.firebasesigninwithmanualdi.core.Utils

@Composable
fun UserListItem(
    user: User,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable {
            onItemClick()
        },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.photo_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(36.dp)
                    .width(36.dp)
            )

            Text(
                text = user.display_name ?: SAMPLE_USER_DISPLAY_NAME,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Text(
            text = user.email ?: SAMPLE_USER_EMAIL,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.created_on, Utils.getDateTime(user.created_at)),
            modifier = Modifier.padding(8.dp)
        )
    }
}
