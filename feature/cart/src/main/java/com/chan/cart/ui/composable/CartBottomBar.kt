package com.chan.cart.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.DarkGray
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.theme.dividerColor
import com.chan.cart.R

@Composable
fun CartBottomBar(
    totalItemCount: Int,
    totalPrice: Int,
    onPurchaseClick: () -> Unit,
) {
    val formattedTotalPrice = "%,d".format(totalPrice)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(Spacing.spacing4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "총 ${totalItemCount}건 ${formattedTotalPrice}원",
                style = MaterialTheme.appTypography.cartTotalCount
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${formattedTotalPrice}원",
                style = MaterialTheme.appTypography.cartTotalPrice
            )
        }
        Spacer(modifier = Modifier.height(Spacing.spacing4))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            OutlinedButton(
                onClick = { onPurchaseClick() },
                border = BorderStroke(1.dp, dividerColor),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(Radius.radius2)
            ) {
                Text(
                    text = stringResource(R.string.gift),
                    style = MaterialTheme.appTypography.bottomBarButton.copy(
                        color = DarkGray.copy(
                            alpha = 0.8f
                        )
                    )
                )
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(Radius.radius2),
                colors = ButtonDefaults.buttonColors(containerColor = Black)
            ) {
                Text(
                    text = stringResource(R.string.purchase),
                    style = MaterialTheme.appTypography.bottomBarButton.copy(color = White)
                )
            }
        }
    }
}