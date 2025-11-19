package com.chan.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    state: MyPageContract.State,
    onEvent: (MyPageContract.Event) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            Column {
                MainTopBar(
                    navigationIcon = null,
                    titleContent = {
                        Text(
                            text = stringResource(R.string.my_page_title)
                        )
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Settings, contentDescription = "설정")
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "장바구니")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.userState.userId ?: "아이디 모름"
            )

            Spacer(modifier = Modifier.height(Spacing.spacing2))

            Text(
                text = "마이 페이지 입니다."
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.spacing4),
                onClick = { onEvent(MyPageContract.Event.OnLogoutClicked) }) {
                Text("로그아웃")
            }
        }
    }
}